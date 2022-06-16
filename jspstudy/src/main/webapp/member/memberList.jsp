<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import ="jspstudy.domain.*" %>   
<%@ page import = "java.util.*" %> 
<%
	//select 쿼리를 사용하기 위해서 function에서 메소드를 만든다
	//memberSelectAll 메소드 호출한다
	//MemberDao md = new MemberDao();
//	ArrayList<MemberVo> alist = md.memberSelectAll() ;
//	out.println(alist.get(0).getMembername()+"<br>");

    ArrayList<MemberVo> alist=(ArrayList<MemberVo>)request.getAttribute("alist");
	
%>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
</head>
<body>
<% 
if (session.getAttribute("midx") != null){
	out.println("회원아이디:"+session.getAttribute("memberId")+"<br>");
	out.println("회원이름:"+session.getAttribute("memberName")+"<br>");
	
	out.println("<a href='"+request.getContextPath()+"/member/memberLogout.do'>로그아웃</a>");
}

%>

<H1>회원목록 리스트</H1>

<table border=1 style="width:800px">
<tr style="color:green;">
<th>번호</th>
<th>회원이름</th>
<th>회원연락처</th>
<th>작성일</th>
</tr>

<% for (MemberVo mv : alist) {%>
<tr>
<td><%=mv.getMidx() %></td>
<td><%=mv.getMembername() %></td>
<td><%=mv.getMemberphone() %></td>
<td><%=mv.getWriteday() %></td>
</tr>
<% } %>

</table>
</body>
</html>