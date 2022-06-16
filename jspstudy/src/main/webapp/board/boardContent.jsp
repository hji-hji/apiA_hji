<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="jspstudy.domain.BoardVo" %>    
<%
	BoardVo bv = (BoardVo)request.getAttribute("bv");
%>        
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

</head>
<body>
<h1>게시판 내용보기</h1>
<table border=1 style="width:800px;">

<tr>
<td style="width:100px">제목</td>
<td><%=bv.getSubject() %></td>
</tr>
<tr>
<td>내용</td>
<td style="height:100px;">
<%=bv.getContent() %>

</td>
</tr>
<td>파일다운로드</td>
<td style="height:100px;">
<% if (bv.getFilename() != null){ %>
<img src="<%=request.getContextPath()%>/images/<%=bv.getFilename()%>">

<a href="<%=request.getContextPath()%>/board/fileDownload.do?filename=<%=bv.getFilename()%>"><%=bv.getFilename()%></a>

<%} %>
</td>
</tr>
<tr>
<td>작성자</td>
<td><%=bv.getWriter() %></td>
</tr>
<tr>
<td colspan=2 style="text-align:right;">
<input type="button" name="modify" value="수정" onclick="location.href='<%=request.getContextPath()%>/board/boardModify.do?bidx=<%=bv.getBidx()%>'">
<input type="button" name="delete" value="삭제" onclick="location.href='<%=request.getContextPath()%>/board/boardDelete.do?bidx=<%=bv.getBidx()%>'">
<input type="button" name="reply" value="답변" onclick="location.href='<%=request.getContextPath()%>/board/boardReply.do?bidx=<%=bv.getBidx()%>&originbidx=<%=bv.getOriginbidx()%>&depth=<%=bv.getDepth()%>&level_=<%=bv.getLevel_()%>'">
<input type="button" name="list" value="목록" onclick="location.href='<%=request.getContextPath()%>/board/boardList.do'">
</td>
</tr>

</table>
</body>
</html>