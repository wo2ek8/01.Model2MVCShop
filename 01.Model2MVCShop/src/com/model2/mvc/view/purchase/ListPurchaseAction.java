package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.model2.mvc.common.SearchVO;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class ListPurchaseAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		SearchVO searchVO = new SearchVO();
		
		int page = 1;
		
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		
		searchVO.setPage(page);
		
		//web.xml meta-data로부터 상수 추출
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		searchVO.setPageUnit(pageUnit);
		
		//Business logic 수행
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO)session.getAttribute("loginUser");
		
		String buyerId = userVO.getUserId();
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String, Object> map = purchaseService.getPurchaseList(searchVO, buyerId);
		
		
		
		//Model과 View 연결
		
		request.setAttribute("map", map);
		request.setAttribute("searchVO", searchVO);
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
