<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<title>搜索结果</title>

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
<script src="bootstrap/js/bootstrap.min.js"></script>
<script>
	$(document).ready(function() {
		$("[data-content]").click(function() {
			var value = $(this).data("content");
			$.post("/sendEmail", {
				content : value
			}, function(data, status) {
				alert("Data: " + data + "\nStatus: " + status);
			});
		});

		$(".row a").click(function() {
			var value = $(this).attr("id")
			$.post("/recordDownload", {
				content : value
			}, function(data, status) {
				alert("Data: " + data + "\nStatus: " + status);
			});
		});
		//
		/*$("ul.pagination a").click(function() {
		    var value="${pagination.getContent()}";
			var page = $(this).text();
			alert(value+page);
			$.get("/sendEmail/"+value+"/"+page,function(data,textStatus){
			 alert("回调"+textStatus);
			});
		});
		 */
	});
</script>
</head>

<body>
	<div class="container">
		<div class="row">
			<div class="col-md-9 col-md-offset-3">
				<div class="row">
					<div class="col-md-3 col-md-offset-2">
						<h1>Kindle</h1>
					</div>
					<form class="form-horizontal" role="form" id="searchForm"
						method="post" action="/search">
						<div class="form-group">
							<div class="col-md-7">
								<input type="text" class="form-control" id="content"
									name="content" placeholder="请输入书名">
							</div>
							<div class="col-md-1">
								<button type="submit" class="btn btn-default">开始搜索</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<c:forEach items="${pagination.getResultList()}" var="temp">
				<div class="panel panel-success">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-book"></span>
						<c:out value="《${temp['content']}》" />
						<span class="glyphicon glyphicon-user"></span>
						<c:out value="作者: ${temp['author']}" />
						<button data-content="${temp['content']}" type="button"
							class="btn btn-primary">
							<span class="glyphicon glyphicon-envelope"></span> 发送到邮箱
						</button>
						<a  id="${temp['content']}" type="button"
							class="btn btn-primary" href="${temp['url']}" role="button">
							<span class="glyphicon glyphicon-download"></span> 直接下载
						</a>
					</div>
					<div class="panel-body">
						<span
							class="label label-pill label-info glyphicon glyphicon-hand-right desc-font desc-margin">
							内容简介:</span>
						<c:out value="${temp['description']}" />
					</div>
				</div>
			</c:forEach>
		</div>
		<div class=text-center>
			<ul class="pagination">
				<c:forEach var="i" begin="1" end="${pagination.getTotalPages()}">
					<li class="${pagination.getPage()==i?'active':''}"><a
						href="<c:url value='/searchListPage/${pagination.getContent()}/${i}'/>">${i}</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</body>
</html>
