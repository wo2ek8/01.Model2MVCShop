<%@page import="java.util.Map"%>
<%@page import="com.model2.mvc.service.product.vo.ProductVO"%>
<%@page import="com.model2.mvc.common.SearchVO"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
    <%
        	Map<String, Object> map = (Map<String, Object>)request.getAttribute("map");
                    SearchVO searchVO = (SearchVO)request.getAttribute("searchVO");
                    
                    
                    
                    int total = 0;
                    ArrayList<ProductVO> list = null;
                    if(map != null) {
                    	total = ((Integer)map.get("count")).intValue();
                    	list = (ArrayList<ProductVO>)map.get("list");
                    }
                    
                    int currentPage = searchVO.getPage();
                    
                    int totalPage = 0;
                    if(total > 0) {
                    	totalPage = total / searchVO.getPageUnit();
                    	if(total % searchVO.getPageUnit() > 0) {
                    		totalPage += 1;
                    	}
                    }
                    
                    
                    
                    
        %>
<!DOCTYPE html>
<html>
<head>
<title>상품 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
function fncGetProductList(){
	document.detailForm.submit();
}
-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=<%=request.getParameter("menu")%>" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">
					
							상품 관리
					
					</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		
		<%
					
						if(searchVO.getSearchCondition() != null) {
				%>
			
			<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
			
			<%
							if(searchVO.getSearchCondition().equals("0")) {
						%>
				
				<option value="0" selected>상품번호</option>
				<option value="1">상품명</option>
				<option value="2">상품가격</option>
			<%
				} else if(searchVO.getSearchCondition().equals("1")) {
			%>
				
				<option value="0">상품번호</option>
				<option value="1" selected>상품명</option>
				<option value="2">상품가격</option>
			<%
				} else if(searchVO.getSearchCondition().equals("2")){
			%>
				
				<option value="0">상품번호</option>
				<option value="1">상품명</option>
				<option value="2" selected>상품가격</option>
			<%
				} else {%>
					<option value="0" selected>상품번호</option>
					<option value="1">상품명</option>
					<option value="2">상품가격</option>
				<%}
			%>
			
			</select>
			<%
				if(request.getParameter("searchKeyword").equals("null")) {
			%>
			<input type="text" name="searchKeyword" class="ct_input_g" style="width:200px; height:19px" />
			<%
				} else {
			%>
			<input type="text" name="searchKeyword" value="<%=searchVO.getSearchKeyword()%>" class="ct_input_g" style="width:200px; height:19px" />
			<%
				}
			%>
			
			</td>
			
		<%
						} else {
					%>
			
			<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" selected>상품번호</option>
				<option value="1">상품명</option>
				<option value="2">상품가격</option>
			</select>
			<input type="text" name="searchKeyword" class="ct_input_g" style="width:200px; height:19px" />
		</td>
		<%
			}
		%>
		
	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체 <%=total%> 건수, 현재 <%=currentPage%> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
		
		<%
					int no = list.size();
						for(int i = 0; i < list.size(); i++) {
					ProductVO productVO = (ProductVO)list.get(i);
				%>
	<tr class="ct_list_pop">
		<td align="center"><%=productVO.getProdNo() %></td>
		<td></td>
				<%if(request.getParameter("menu").equals("manage")) {%>
					<td align="left"><a href="/updateProductView.do?prodNo=<%=productVO.getProdNo()%>"><%=productVO.getProdName()%></a></td>
				<%} else if(request.getParameter("menu").equals("search")) {%>
				
				<%
				if(productVO.getProTranCode() == null) {%>
					<td align="left"><a href="/getProduct.do?prodNo=<%=productVO.getProdNo()%>"><%=productVO.getProdName()%></a></td>
				<%} else {%>
					<td align="left"><%=productVO.getProdName()%></td>
				<%}
				%>
					
				<%}%>
					
				
				
		
		<td></td>
		<td align="left"><%=productVO.getPrice() %></td>
		<td></td>
		<td align="left"><%=productVO.getRegDate() %></td>
		<td></td>
		<td align="left">
		
			<%
			if(productVO.getProTranCode() == null) {%>
				판매중
			<%} else {%>
				재고 없음
			<%}
			%>
		
		</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>	
	
	
		
	<%} %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		
		
		<%int index = 0;
    	
    		index = totalPage / 5;
    		if(totalPage % 5 > 0) {
    		index += 1;
    		}
    	
    	
    	
		
		for(int i = 1; i <= index; i++) {
			
			int startIndex = 1 + (i - 1) * 5;
			int nextIndex = 1 + (5 * i);
			
			
			if(startIndex >= 6 && searchVO.getPage() >= startIndex && searchVO.getPage() < nextIndex) {%>
				<a href="/listProduct.do?page=<%=startIndex - 1 %>&menu=<%=request.getParameter("menu")%>&searchCondition=<%=searchVO.getSearchCondition()%>&searchKeyword=<%=searchVO.getSearchKeyword()%>">이전 </a>
			<%}%>
			
			
			
			<%if(startIndex + 4 > totalPage) {
				for(int j = startIndex; j <= totalPage; j++) {
					if(searchVO.getPage() >= startIndex && searchVO.getPage() <= startIndex + 4) {%>
					<a href="/listProduct.do?page=<%=j%>&menu=<%=request.getParameter("menu")%>&searchCondition=<%=searchVO.getSearchCondition()%>&searchKeyword=<%=searchVO.getSearchKeyword()%>"><%=j%></a>
				<%}%>
				<%}
			} else {
				for(int j = startIndex; j <= startIndex + 4; j++) {%>
				
				<%if(searchVO.getPage() >= startIndex && searchVO.getPage() <= startIndex + 4) {%>
					<a href="/listProduct.do?page=<%=j%>&menu=<%=request.getParameter("menu")%>&searchCondition=<%=searchVO.getSearchCondition()%>&searchKeyword=<%=searchVO.getSearchKeyword()%>"><%=j%></a>
				<%}%>
				
			<%}%>
			<%}%>
			
			
			<%
			if(totalPage >= nextIndex && searchVO.getPage() < nextIndex && searchVO.getPage() >= startIndex) {%>
				<a href="/listProduct.do?page=<%=nextIndex %>&menu=<%=request.getParameter("menu")%>&searchCondition=<%=searchVO.getSearchCondition()%>&searchKeyword=<%=searchVO.getSearchKeyword()%>"> 다음</a>
			<%}%>
		<%}%>
		
		
		
		
		
		
		
			 
		
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>