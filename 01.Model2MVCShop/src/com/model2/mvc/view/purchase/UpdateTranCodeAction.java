package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeAction  extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		String tranCode = request.getParameter("tranCode");
		String currentPage = request.getParameter("page");
		String menu = request.getParameter("menu");
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = purchaseService.getPurchase(tranNo);
		
		purchaseVO.setTranCode(tranCode);
		
		purchaseService.updateTranCode(purchaseVO);
		
		if(menu.equals("sale")) {
			return "redirect:/listSale.do?page=" + currentPage;
		} else {
			return "redirect:/listPurchase.do?page=" + currentPage;
		}
		
	}

}
