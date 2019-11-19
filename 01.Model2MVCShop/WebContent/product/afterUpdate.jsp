<%@page import="com.model2.mvc.common.SearchVO"%>
<%@page import="com.model2.mvc.service.user.vo.UserVO"%>
<%@page import="com.model2.mvc.service.product.vo.ProductVO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
    <%
    ProductVO productVO = (ProductVO)request.getAttribute("productVO");
    
    
    UserVO loginUser = (UserVO)session.getAttribute("loginUser");
    String role = "";
    if(loginUser != null) {
    	role = loginUser.getRole();
    }
    
    %>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<title>Insert title here</title>
</head>

<body bgcolor="#ffffff" text="#000000">

<form name="detailForm" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"	width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">��ǰ����ȸ</td>
					<td width="20%" align="right">&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif"  width="12" height="37"/>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 13px;">
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			��ǰ��ȣ <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="105"><%=productVO.getProdNo() %></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			��ǰ�� <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=productVO.getProdName() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			��ǰ�̹��� <img 	src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01">
			<img src = "/images/uploadFiles/../../images/empty.GIF"/>
		</td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">
			��ǰ������ <img src="/images/ct_icon_red.gif" width="3" height="3" align="absmiddle"/>
		</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=productVO.getProdDetail() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">��������</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=productVO.getManuDate() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">����</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=productVO.getPrice() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
	<tr>
		<td width="104" class="ct_write">�������</td>
		<td bgcolor="D6D6D6" width="1"></td>
		<td class="ct_write01"><%=productVO.getRegDate() %></td>
	</tr>
	<tr>
		<td height="1" colspan="3" bgcolor="D6D6D6"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td width="53%"></td>
		<td align="right">

		<table border="0" cellspacing="0" cellpadding="0">
		
		<%
		if(loginUser != null && role.equals("user")) {%>
			<tr>
			<td width="17" height="23">
			<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
		</td>
		<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top: 3px;">
			<%-- <a href="/addPurchaseView.do?prodNo=<%=productVO.getProdNo()%>">����</a> --%>
			<a href="/addPurchaseView.do?prodNo=<%=productVO.getProdNo()%>">����</a>
		</td>
		<td width="14" height="23">
			<img src="/images/ct_btnbg03.gif" width="14" height="23">
		</td>
		<td width="30"></td>
			<td width="17" height="23">
				<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
			</td>
			<td background="/images/ct_btnbg02.gif" class="ct_btn01"	style="padding-top: 3px;">
				<a href="/listProduct.do?menu=search">Ȯ��</a>
				
			</td>
			<td width="14" height="23">
				<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
			</td>
		</tr>
		<%} else if(loginUser != null && role.equals("admin")) {%>
			<tr>
			<td width="17" height="23">
				<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
			</td>
			<td background="/images/ct_btnbg02.gif" class="ct_btn01"	style="padding-top: 3px;">
				<a href="/listProduct.do?menu=search">Ȯ��</a>
			</td>
			<td width="14" height="23">
				<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
			</td>
		</tr>
		<%} else {%>
			<tr>
			<td width="17" height="23">
				<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
			</td>
			<td background="/images/ct_btnbg02.gif" class="ct_btn01"	style="padding-top: 3px;">
				<a href="/listProduct.do?menu=search">Ȯ��</a>
			</td>
			<td width="14" height="23">
				<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
			</td>
		</tr>
		<%}
		%>
			
		</table>

		</td>
	</tr>
</table>
</form>

</body>
</html>