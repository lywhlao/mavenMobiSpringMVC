<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'login.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<link href="bootstrap/css/custom.css" rel="stylesheet" type="text/css">
<script src="jquery/jquery.js"></script>
<script src="jquery/jquery.validate.js"></script>
<script src="jquery/jquery.metadata.js"></script>
<script src="jquery/jquery.validate.messages_cn.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	//验证登录表单
		$("#loginForm").validate({
			rules : {
				userName : {
					required : true,
					minlength:6
				},
				password : {
					required : true,
					minlength:6
				}
			},
			messages : {
				userName :{
					required:"必须输入用户名",
					minlength:"用户名长度不能少于6个字符"
				},
				password:{
					required:"必须输入密码",
					minlength:"密码长度不能少于6个字符"
				}
			}
		});
	});
</script>
</head>

<body>
	<div class="jumbotron vertical-center">
		<div class="container">
			<div class="row text-center">
				<h2>登 录</h2>
			</div>
			<div class="row">
				<div class="col-md-9 col-md-offset-3">
					<form class="form-horizontal" role="form" id="loginForm"
						method="post" action="/login">
						<div class="form-group">
							<label for="userName" class="col-md-1 control-label"><span
								class="glyphicon glyphicon-user"></span></label>
							<div class="col-md-6">
								<input type="text" class="form-control" id="content"
									name="userName" placeholder="请输入账号">
							</div>
						</div>
						<div class="form-group">
							<label for="password" class="col-md-1 control-label"><span
								class="glyphicon glyphicon-lock"></span></label>
							<div class="col-md-6">
								<input type="password" class="form-control" id="password"
									name="password" placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-6 col-md-offset-1">
								<button type="submit" class="btn btn-primary btn-lg btn-block">登录</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
