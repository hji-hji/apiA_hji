<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="jspstudy.domain.BoardVo" %>    
<%
 BoardVo bv = (BoardVo)request.getAttribute("bv");

 if (session.getAttribute("midx") == null){
	out.println("<script>alert('로그인해주세요');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>"); 
 } 
 %>   

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>

<script>
  function check(){
  //	alert("테스트");
  	
  	var fm = document.frm;
  	
  	if (fm.subject.value ==""){
  		alert("제목을 입력하세요");
  		fm.subject.focus();
  		return;
  	}else if (fm.content.value ==""){
  		alert("내용을 입력하세요");
  		fm.content.focus();
  		return;
  	}else if (fm.writer.value ==""){
  		alert("작성자를 입력하세요");
  		fm.writer.focus();
  		return;
  	}
  		fm.action = "<%=request.getContextPath()%>/board/boardModifyAction.do";
		fm.method = "post";
		fm.submit();
	
  }
</script>
</head>
<body>
<h1>게시판 글쓰기</h1>
<table border=1 style="width:800px;">
<form name="frm">
<input type="hidden" name="bidx" value="<%=bv.getBidx() %>">
<tr>
<td style="width:100px">제목</td>
<td><input type="text" name="subject" size="50" value="<%=bv.getSubject() %>"></td>
</tr>
<tr>
<td>내용</td>
<td>
<textarea name="content" placeholder="내용을 입력해주세요" cols="80" rows="10">
<%=bv.getContent() %>
</textarea>

</td>
</tr>
<tr>
<td>작성자</td>
<td><input type="text" name="writer" size="50" value="<%=bv.getWriter() %>"></td>
</tr>
<tr>
<td colspan=2 style="text-align:center;">
<input type="button" name="btn" value="확인" onclick="check();">
<input type="reset" name="reset" value="취소">
</td>
</tr>
</form>
</table>
</body>
</html>