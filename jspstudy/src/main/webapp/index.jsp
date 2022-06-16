<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jspstudy.dbconn.Dbconn" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<% //객체생성 테스트
Dbconn dbconn = new  Dbconn();
System.out.println("dbconn"+dbconn);
%>

<h1>메인 페이지 입니다.11111111</h1>

<a href="<%=request.getContextPath() %>/member/memberJoin.do">회원가입하기</a> 
<a href="<%=request.getContextPath() %>/member/memberList.do">회원 목록가기</a>
<a href="<%=request.getContextPath() %>/member/memberLogin.do">회원 로그인</a>
<a href="<%=request.getContextPath() %>/board/boardWrite.do">게시판 글쓰기</a>
<a href="<%=request.getContextPath() %>/board/boardList.do">게시판 리스트</a>


</body>
</html>