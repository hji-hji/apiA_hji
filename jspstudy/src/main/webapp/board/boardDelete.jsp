<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% String bidx = (String)request.getAttribute("bidx"); %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>

<script>
  function check(){
  //	alert("테스트");
  	
  	var fm = document.frm;
  	
  	if (fm.bidx.value ==""){
  		alert("bidx가 없습니다.");
  		fm.bidx.focus();
  		return;
  	}
  		fm.action = "<%=request.getContextPath()%>/board/boardDeleteAction.do";
		fm.method = "post";
		fm.submit();
	
  }
</script>
</head>
<body>
<h1>게시판 글삭제</h1>
<table border=1 style="width:800px;">
<form name="frm">
<input type="hidden" name="bidx" value="<%=bidx%>">
<tr>
<td colspan=2  style="width:100px;text-align:center;">삭제하시겠습니까?</td>

</tr>
<tr>
<td colspan=2 style="text-align:center;">
<input type="button" name="btn" value="확인" onclick="check();">
<input type="button" name="cancel" value="취소" onclick="location.href='<%=request.getContextPath()%>/board/boardContent.do?bidx=<%=bidx%>'">
</td>
</tr>
</form>
</table>
</body>
</html>