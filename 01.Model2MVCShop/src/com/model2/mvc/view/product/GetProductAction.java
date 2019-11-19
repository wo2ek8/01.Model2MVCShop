package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class GetProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String prodNo = request.getParameter("prodNo");
		
		
		ProductService service = new ProductServiceImpl();
		ProductVO productVO = service.getProduct(Integer.parseInt(prodNo));
		
		request.setAttribute("productVO", productVO);
		
		
		
		
		Cookie[] cookies = request.getCookies();
		
		String noLine = "";
		
		if(cookies != null) {
			for(int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().equals("history")) {
					
					noLine += cookies[i].getValue();
				}
			}
			
		}
		if(noLine != "") {
			noLine = request.getParameter("prodNo") + "," + noLine;
		} else {
			noLine = request.getParameter("prodNo");
		}
		
		Cookie cookie = new Cookie("history", noLine);
		
		cookie.setMaxAge(-1);
		response.addCookie(cookie);
		
		
		
		
		return "forward:/product/afterUpdate.jsp";
	}

	
}
