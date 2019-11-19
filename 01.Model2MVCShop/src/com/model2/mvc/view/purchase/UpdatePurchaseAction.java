package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String tranNo = request.getParameter("tranNo");
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = purchaseService.getPurchase(Integer.parseInt(tranNo));
		
		System.out.println("updateÀü purchaseVO" + purchaseVO);
		
		String buyerId = request.getParameter("buyerId");
		
		UserService userService = new UserServiceImpl();
		UserVO userVO = userService.getUser(buyerId);
		
		purchaseVO.setBuyer(userVO);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		
		purchaseService.updatePurcahse(purchaseVO);
		
		System.out.println("updateÈÄ purchaseVO" + purchaseVO);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/getPurchase.jsp";
	}

}
