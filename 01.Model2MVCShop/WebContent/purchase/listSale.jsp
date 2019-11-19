<%@page import="com.model2.mvc.common.SearchVO"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@page import="java.util.ArrayList"%>

<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
    <%
    Map<String, Object> map = (Map<String, Object>)request.getAttribute("map");
    SearchVO searchVO = (SearchVO)request.getAttribute("searchVO");
    
    int total = 0;
    List<PurchaseVO> list = null;
    if(map != null) {
    	total = ((Integer)map.get("count")).intValue();
    	list = (List<PurchaseVO>)map.get("list");
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
<title>판매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList() {
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listUser.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">판매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 <%=total %> 건수, 현재 <%=currentPage %> 페이지</td>
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
		
		<td class="ct_list_b"></td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>

	<%
	
	for(int i = 0; i < list.size(); i++) {
		PurchaseVO purchaseVO = (PurchaseVO)list.get(i);
	
	%>
	
	<tr class="ct_list_pop">
		<td align="center">
			<a href="/getPurchase.do?tranNo=<%=purchaseVO.getTranNo()%>"><%=purchaseVO.getTranNo()%></a>
		</td>
		<td></td>
		<td align="left">
			<a href="/getProduct.do?prodNo=<%=purchaseVO.getPurchaseProd().getProdNo()%>"><%=purchaseVO.getPurchaseProd().getProdName() %></a>
		</td>
		<td></td>
		<td align="left"><%=purchaseVO.getPurchaseProd().getPrice() %></td>
		<td></td>
		<td align="left"><%=purchaseVO.getOrderDate() %></td>
		<td></td>
		<td align="left">
		<%System.out.println(purchaseVO.getTranCode()); %>
		<%if(purchaseVO.getTranCode() != null) {
			if(purchaseVO.getTranCode().trim().equals("0")) {%>
				구매완료 <a href="/updateTranCode.do?menu=sale&tranNo=<%=purchaseVO.getTranNo() %>&tranCode=1&page=<%=currentPage%>&searchCondition=<%=searchVO.getSearchCondition()%>&searchKeyword=<%=searchVO.getSearchKeyword()%>">배송하기</a>
			<%} else if(purchaseVO.getTranCode().trim().equals("1")) {%>
				배송중
			<%} else if(purchaseVO.getTranCode().trim().equals("2")) {%>
				배송완료
			<%}
			
			
		}%>
		
			
		</td>
				
				
		<td></td>
		<td align="left">
			
			
			
		</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	
	
	
	<%} %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 
			<%
			for(int i=1;i<=totalPage;i++){
		%>
			<a href="/listSale.do?page=<%=i%>&menu=<%=request.getParameter("menu")%>&searchCondition=<%=searchVO.getSearchCondition()%>&searchKeyword=<%=searchVO.getSearchKeyword()%>"><%=i %></a>
		<%
			}
		%>
		
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>