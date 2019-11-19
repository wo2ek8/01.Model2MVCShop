package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductDAO {

	public ProductDAO() {
		
	}
	
	public void insertProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "insert into product (prod_no, prod_name, prod_detail, manufacture_day, price, image_file, reg_date) values (seq_product_prod_no.nextval, ?, ?, ?, ?, ?, sysdate)";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, productVO.getProdName());
		pstmt.setString(2, productVO.getProdDetail());
		pstmt.setString(3, productVO.getManuDate().replace("-", ""));
		pstmt.setInt(4, productVO.getPrice());
		pstmt.setString(5, productVO.getFileName());
		pstmt.executeUpdate();
		
		con.close();
	}
	
	public ProductVO findProduct(int prodNo) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql = "select * from product where prod_no = ?";
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setInt(1, prodNo);
		
		ResultSet rs = pstmt.executeQuery();
		
		ProductVO productVO = null;
		while(rs.next()) {
			productVO = new ProductVO();
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("manufacture_day"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("image_file"));
			productVO.setRegDate(rs.getDate("reg_date"));
			
		}
		
		con.close();
		
		
		
		return productVO;
	}
	
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql2 = "select count(*) from product";
		if(searchVO.getSearchCondition() != null && !(searchVO.getSearchKeyword().equals("null"))) {
			
			if(searchVO.getSearchCondition().equals("0")) {
				sql2 += " where prod_no = " + searchVO.getSearchKeyword();
			} else if(searchVO.getSearchCondition().equals("1")) {
				sql2 += " where prod_name like '%" + searchVO.getSearchKeyword() + "%'";
			} else if(searchVO.getSearchCondition().equals("2")) {
				sql2 += " where price = " + searchVO.getSearchKeyword();
			}
			
		}
		PreparedStatement pstmt2 = con.prepareStatement(sql2);
		ResultSet rs2 = pstmt2.executeQuery();
		int total2 = 0;
		while(rs2.next()) {
			total2 = rs2.getInt(1);
		}
		
		System.out.println("로우의 수2 : " + total2);
		
		
		
		
		
		int start = 1 + (3 * (searchVO.getPage() - 1));
		int cut = 3 * searchVO.getPage();
		
		System.out.println("start" + start);
		System.out.println("cut" + cut);
		String sql = "select vt2.rn, vt2.*, transaction.tran_status_code from transaction, ( "
				+ "select "
				+ "rownum rn, vt1.* "
				+ "from ( "
				+ "select "
				+ "* "
				+ "from product";
		if(searchVO.getSearchCondition() != null && !(searchVO.getSearchKeyword().equals("null"))) {
			
			if(searchVO.getSearchCondition().equals("0")) {
				sql += " where prod_no = " + searchVO.getSearchKeyword();
			} else if(searchVO.getSearchCondition().equals("1")) {
				sql += " where prod_name like '%" + searchVO.getSearchKeyword() + "%'";
			} else if(searchVO.getSearchCondition().equals("2")) {
				sql += " where price = " + searchVO.getSearchKeyword();
			}
			
		}
		sql += " order by prod_no desc "
				+ ") vt1 "
				+ ") vt2 "
				+ "where vt2.rn >= " + start
				+ " and vt2.rn <= " + cut
				+ " and vt2.prod_no = transaction.prod_no(+)";
		
		PreparedStatement pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = pstmt.executeQuery();
		
		rs.last();
		System.out.println("로오오" + rs.getRow());
		
		int total = total2;
		System.out.println("로우의 수 : " + total);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("count", new Integer(total));
		
		rs.absolute(1);
		System.out.println("searchVO.getPage() : " + searchVO.getPage());
		System.out.println("searchVO.getPageUnit() : " + searchVO.getPageUnit());
		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if(total > 0) {
			for(int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO productVO = new ProductVO();
				productVO.setProdNo(rs.getInt("prod_no"));
				productVO.setProdName(rs.getString("prod_name"));
				productVO.setProdDetail(rs.getString("prod_detail"));
				productVO.setManuDate(rs.getString("manufacture_day"));
				productVO.setPrice(rs.getInt("price"));
				productVO.setFileName(rs.getString("image_file"));
				productVO.setRegDate(rs.getDate("reg_date"));
				productVO.setProTranCode(rs.getString("tran_status_code"));
				
				list.add(productVO);
				if(!rs.next()) {
					break;
				}
			}
		}
			System.out.println("list.size() : " + list.size());
			map.put("list", list);
			System.out.println("map().size() : " + map.size());
			
			con.close();
			
			return map;
		}
	
		
		public void updateProduct(ProductVO productVO) throws Exception {
			
			Connection con = DBUtil.getConnection();
			
			String sql = "update product set prod_name = ?, prod_detail = ?, manufacture_day = ?, price = ?, image_file = ? where prod_no = ?";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, productVO.getProdName());
			pstmt.setString(2, productVO.getProdDetail());
			pstmt.setString(3, productVO.getManuDate().replace("-", ""));
			pstmt.setInt(4, productVO.getPrice());
			pstmt.setString(5, productVO.getFileName());
			pstmt.setInt(6, productVO.getProdNo());
			pstmt.executeUpdate();
			
			con.close();
			
		}
	
}
