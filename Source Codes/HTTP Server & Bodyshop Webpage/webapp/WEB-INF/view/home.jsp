<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>

<head>
<title>Hello</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	$(document).ready(function() {
		$('#btnLogin').click(function() {
			var bodyshop_id = $('#bodyshop_id').val();
			var bodyshop_pw = $('#bodyshop_pw').val();

			if (bodyshop_id == "") {
				alert("아이디를 입력하세요.");
				$("#bodyshop_id").focus();
				return;
			}
			if (bodyshop_pw == "") {
				alert("비밀번호를 입력하세요.");
				$("#bodyshop_pw").focus();
				return;
			}
			console.log(bodyshop_id);
			console.log(bodyshop_pw);
			document.form1.action = "${path}/Chavis/dashboard.do"
			document.form1.submit();
		});
	});
</script>
</head>
<link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet">
<body>
	<h2 style="text-align: center">Chavis 로그인</h2>
	<div style="text-align: center ">
		<form name="form1" method="POST" style="width: 250px; margin: 0 auto;">

			<div class="position-relative form-group">
				<label for="exampleEmail">ID</label> <input name="bodyshop_id"
					id="bodyshop_id" class="form-control">
			</div>
			<div class="position-relative form-group">
				<label for="examplePassword">Password</label> <input
					name="bodyshop_pw" id="bodyshop_pw" type="password" class="form-control">
			</div>

			<button type="button" id="btnLogin">로그인</button>
		</form>
	</div>
</body>
</html>