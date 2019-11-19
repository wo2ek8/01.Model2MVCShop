package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {
	
	///Field
	private PurchaseDAO purchaseDAO;
	
	///Constructor
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}

	@Override
	public void addPurchase(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.insertPurchase(purchaseVO);
	}

	@Override
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public PurchaseVO getPurchase2(int prodNo) throws Exception {
		return purchaseDAO.findPurchase2(prodNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(searchVO, buyerId);
	}

	@Override
	public Map<String, Object> getSaleList(SearchVO searchVO) throws Exception {
		return purchaseDAO.getSaleList(searchVO);
	}

	@Override
	public void updatePurcahse(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.updatePurchase(purchaseVO);
	}
	
	@Override
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.updateTranCode(purchaseVO);
	}

	

	

}
