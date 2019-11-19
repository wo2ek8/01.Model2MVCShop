package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "insert into transaction values(seq_transaction_tran_no.nextval, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, ?)";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		pstmt.setString(2, purchaseVO.getBuyer().getUserId());
		pstmt.setString(3, purchaseVO.getPaymentOption());
		pstmt.setString(4, purchaseVO.getReceiverName());
		pstmt.setString(5, purchaseVO.getReceiverPhone());
		pstmt.setString(6, purchaseVO.getDivyAddr());
		pstmt.setString(7, purchaseVO.getDivyRequest());
		pstmt.setString(8, purchaseVO.getTranCode());
		pstmt.setString(9, purchaseVO.getDivyDate().replace("-", ""));
		pstmt.executeUpdate();
		
		con.close();
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select tran_no, prod_no, buyer_id, payment_option, receiver_name, receiver_phone, demailaddr, dlvy_request, tran_status_code, order_data, dlvy_date from transaction where tran_no = " + tranNo;
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		PurchaseVO purchaseVO = null;
		while(rs.next()) {
			purchaseVO = new PurchaseVO();
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			
			String userId = rs.getString("buyer_id");
			
			UserService userService = new UserServiceImpl();
			UserVO userVO = userService.getUser(userId);
			
			purchaseVO.setBuyer(userVO);
			purchaseVO.setPaymentOption(rs.getString("payment_option"));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setDivyAddr(rs.getString("demailaddr"));
			purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			
			
			
			purchaseVO.setDivyDate(rs.getString("dlvy_date"));
			
			int prodNo = rs.getInt("prod_no");
			
			ProductService productService = new ProductServiceImpl();
			ProductVO product = productService.getProduct(prodNo);
			
			purchaseVO.setPurchaseProd(product);
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return purchaseVO;
	}
	
	public PurchaseVO findPurchase2(int prodNo) throws Exception {
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from transaction where prod_no = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, prodNo);
		
		ResultSet rs = pstmt.executeQuery();
		
		PurchaseVO purchase = null;
		
		while(rs.next()) {
			purchase = new PurchaseVO();
			purchase.setTranNo(rs.getInt("tran_no"));
			
			String userId = rs.getString("buyer_id");
			
			UserService userService = new UserServiceImpl();
			UserVO user = userService.getUser(userId);
			
			purchase.setBuyer(user);
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailarrd"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			
			ProductService productService = new ProductServiceImpl();
			ProductVO product = productService.getProduct(prodNo);
			
			purchase.setPurchaseProd(product);
		}
		
		rs.close();
		pstmt.close();
		con.close();
		
		return purchase;
	}
	
	public Map<String, Object> getPurchaseList(SearchVO search, String buyerId) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();
		
		//Original Query 구성
		String sql = "select "
				+ "transaction.tran_no, users.user_id, transaction.receiver_name, transaction.receiver_phone, transaction.tran_status_code "
				+ "from users, transaction "
				+ "where users.user_id = transaction.buyer_id and transaction.buyer_id = '" + buyerId
				+ "' order by tran_no desc";
		
		System.out.println("getPurchaseList에서 날린 쿼리 : " + sql);
		
		//==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("totalCount : " + totalCount);
		
		//==> CurrentPage 게시물만 받도록 Query 다시 구성
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		System.out.println("getPurchaseList에서 날린 search :" + search);
		
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		
		while(rs.next()) {
			PurchaseVO purchase = new PurchaseVO();
			purchase.setTranNo(rs.getInt("tran_no"));
			
			String userId = rs.getString("user_id");
			
			UserService userService = new UserServiceImpl();
			UserVO user = userService.getUser(userId);
			
			purchase.setBuyer(user);
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			
			list.add(purchase);
			
			System.out.println("PurchaseDAO의 getPurchaseList의 purchase : " + purchase);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(totalCount));
		//==> currentPage의 게시물 정보 갖는 List 저장
		map.put("list", list);
		
		rs.close();
		pstmt.close();
		con.close();
		
		
		
		return map;
	}
	
	public Map<String, Object> getSaleList(SearchVO searchVO) throws Exception {
		
		
		Connection con = DBUtil.getConnection();
		
		
		String sql = "select "
				+ "*  "
				+ "from users, transaction, product "
				+ "where users.user_id = transaction.buyer_id "
				+ "and transaction.prod_no = product.prod_no "
				+ "order by tran_no desc";
		
		
		
		
		
		PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = pstmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit() + 1);
		
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		
		if(total > 0) {
		for(int i = 0; i < searchVO.getPageUnit(); i++) {
			PurchaseVO purchaseVO = new PurchaseVO();
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			
			String userId = rs.getString("user_id");
			
			UserService userService = new UserServiceImpl();
			UserVO userVO = userService.getUser(userId);
			
			purchaseVO.setBuyer(userVO);
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			purchaseVO.setTranCode(rs.getString("tran_status_code"));
			purchaseVO.setOrderDate(rs.getDate("order_data"));
			
			String prodNo = rs.getString("prod_no");
			
			ProductService productService = new ProductServiceImpl();
			ProductVO productVO = productService.getProduct(Integer.parseInt(prodNo));
			
			purchaseVO.setPurchaseProd(productVO);
			
			
			list.add(purchaseVO);
			if(!rs.next()) {
				break;
			}
		}
		}
		
		
		map.put("list", list);
		
		rs.close();
		pstmt.close();
		con.close();
		
		
		
		return map;
	}
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		 
		Connection con = DBUtil.getConnection();
		 
		String sql = "update transaction set buyer_id = ?, payment_option = ?, receiver_name = ?, receiver_phone = ?, demailaddr = ?, dlvy_request = ?, dlvy_date = ? where tran_no = ?";
		 
		PreparedStatement pstmt = con.prepareStatement(sql);
		 pstmt.setString(1, purchaseVO.getBuyer().getUserId());
		 pstmt.setString(2, purchaseVO.getPaymentOption());
		 pstmt.setString(3, purchaseVO.getReceiverName());
		 pstmt.setString(4, purchaseVO.getReceiverPhone());
		 pstmt.setString(5, purchaseVO.getDivyAddr());
		 pstmt.setString(6, purchaseVO.getDivyRequest());
		 pstmt.setString(7, purchaseVO.getDivyDate());
		 pstmt.setInt(8, purchaseVO.getTranNo());
		 pstmt.executeUpdate();
		 System.out.println("update쿼리에들어갈purchaseVO" + purchaseVO);
		 System.out.println("update가됬나요?" + pstmt.executeUpdate());
		 
		 pstmt.close();
		 con.close();
	}
	
	public void updateTranCode(PurchaseVO purchase) throws Exception {
		Connection con = DBUtil.getConnection();
		 
		String sql = "update transaction set tran_status_code = ? where tran_no = ?";
		 
		PreparedStatement pstmt = con.prepareStatement(sql);
		 pstmt.setString(1, purchase.getTranCode());
		 pstmt.setInt(2, purchase.getTranNo());
		 pstmt.executeUpdate();
		 
		 
		 pstmt.close();
		 con.close();
	}
	
	//게시판 Page 처리를 위한 전체 Row(totalCount) return
	private int getTotalCount(String sql) throws Exception {
		
		sql = "select count(*) from (" + sql + ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		int totalCount = 0;
		if(rs.next()) {
			totalCount = rs.getInt(1);
		}
		
		pstmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	//게시판 currentPage Row만 return
	private String makeCurrentPageSql(String sql, SearchVO search) {
		sql = "select * from (select inner_table.*, rownum as row_seq from (" + sql + ") inner_table where rownum <= " + search.getPage() * search.getPageUnit() + ") where row_seq between " + ((search.getPage() - 1) * search.getPageUnit() + 1) + " and " + search.getPage() * search.getPageUnit();
		
		System.out.println("PurchaseDAO의 makeCurrentPageSql의 sql : " + sql);
		
		return sql;
	}
}
