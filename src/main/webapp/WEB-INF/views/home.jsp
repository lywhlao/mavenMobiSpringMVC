<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="java.net.URI" %>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
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

    <title>欢迎来到kindle乐园</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
    <script>
        $(document).ready(function () {
            $("#searchForm").validate({
                rules: {
                    content: {
                        required: true,
                    }
                },
                messages: {
                    content: {
                        required: "请输入需要搜索的内容"
                    }
                }
            });
            //getRecommandContent();
            //getHotContent();
            //getRandomContent();
        });
    </script>

    <script type="text/javascript">
        /*获得推荐内容 */
        function getRecommandContent() {
            var userName = "${userBean.getUserName()}";
            $.ajax({
                headers: {
                    Accept: "application/json"
                },
                url: "/getRecommendContent",
                type: "POST",
                dataType: "json",
                success: function (response) {
                    if (response.resultCode == 0) {
                        var ul = $("#recommend-ul");
                        var list = response.data;
                        for (item in list) {
                            var $li_item = $("<a class='list-group-item text-center list-item' data-original-title='hi' data-trigger='hover'  rel='popover'></a>");
                            $li_item.text(list[item].contentDest);
                            $li_item.attr("href", list[item].url);
                            $li_item.attr("data-content", list[item].description.substr(0, 200) + "...");
                            $li_item.attr("data-original-title", list[item].contentDest + "--" + list[item].author);
                            if (item < 4) {
                                $li_item.attr("data-placement", "left");
                            } else {
                                $li_item.attr("data-placement", "top");
                            }
                            ul.append($li_item);
                            $("a[data-trigger=hover]").popover({trigger: "hover"});
                        }
                        $("a[data-trigger=hover]").bind("click", function () {
                            var contentName = $(this).attr("data-original-title").split("--")[0];
                            recordDownload(contentName)
                        });
                    } else {


                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + thrownError);
                }
            });
        }

        //记录下载记录
        function recordDownload(value) {
            $.post("/recordDownload", {
                content: value
            }, function (data, status) {
                alert("Data: " + data + "\nStatus: " + status);
            });
        }

        //获得热门推荐
        function getHotContent() {
            $.ajax({
                headers: {
                    Accept: "application/json"
                },
                url: "/getHotContent",
                type: "POST",
                dataType: "json",
                success: function (response) {
                    if (response.resultCode == 0) {
                        var ul = $("#hot-ul");
                        var list = response.data;
                        for (item in list) {
                            var $li_item = $("<a class='list-group-item text-center list-item' data-trigger='hover'  rel='popover'></a>");
                            $li_item.text(list[item].content);
                            $li_item.attr("href", list[item].url);
                            $li_item.attr("data-content", list[item].description.substr(0, 200) + "...");
                            $li_item.attr("data-original-title", list[item].content + "--" + list[item].author);
                            if (item < 4) {
                                $li_item.attr("data-placement", "left");
                            } else {
                                $li_item.attr("data-placement", "top");
                            }
                            ul.append($li_item);
                            $("a[data-trigger=hover]").popover({trigger: "hover"});
                        }
                        $("a[data-trigger=hover]").bind("click", function () {
                            var contentName = $(this).attr("data-original-title").split("--")[0];
                            recordDownload(contentName)
                        });
                    } else {

                        //TODO
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + thrownError);
                }
            });
        }

        //获得随机内容
        function getRandomContent() {
            $.ajax({
                headers: {
                    Accept: "application/json"
                },
                url: "/getRandomContent",
                type: "POST",
                dataType: "json",
                success: function (response) {
                    if (response.resultCode == 0) {
                        var ul = $("#random-ul");
                        var list = response.data;
                        for (item in list) {
                            var $li_item = $("<a class='list-group-item text-center list-item' data-trigger='hover'  rel='popover '></a>");
                            $li_item.text(list[item].content);
                            $li_item.attr("href", list[item].url);
                            $li_item.attr("data-original-title", list[item].content + "--" + list[item].author);
                            if (list[item].description.length == 0) {
                                $li_item.attr("data-content", "暂且无介绍，敬请期待");
                            } else {
                                $li_item.attr("data-content", list[item].description.substr(0, 200) + "...");
                            }
                            $li_item.attr("data-placement", "right");
                            ul.append($li_item);
                            $("a[data-trigger=hover]").popover({trigger: "hover"});
                        }
                        $("a[data-trigger=hover]").bind("click", function () {
                            var contentName = $(this).attr("data-original-title").split("--")[0];
                            recordDownload(contentName)
                        });
                    } else {

                        //TODO
                    }
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    alert(xhr.status + thrownError);
                }
            });
        }


    </script>

</head>

<body>
<br>
<br>
<div class="container">
    <div class="row">
        <div class="col-md-9 col-md-offset-3">
            <div class="row">
                <div class="col-md-3 col-md-offset-2">
                    <h1>Kindle</h1>
                </div>
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
            <div class="row">
                <c:if test="${empty userBean.getUserName()}">
                    <div class="col-md-3 col-md-offset-2">
                        <a id="user_login" href="/login" class="btn btn-success btn-lg"
                           role="button">登录</a>
                        <a id="user_register" href="/register" class="btn btn-info btn-lg"
                           role="button">注册</a>
                    </div>
                </c:if>
            </div>

        </div>
    </div>
</div>
<br>
<br>
<div class="container">
    <div class="row">
        <div class="col-sm-4">
            <ul class="list-group" id="random-ul">
                <li class="list-group-item list-group-item-info text-center list-title">随便看看</li>
            </ul>
        </div>
        <div class="col-sm-4">
            <ul class="list-group" id="hot-ul">
                <li class="list-group-item list-group-item-danger text-center list-title">热门推荐</li>
            </ul>
        </div>
        <div class="col-sm-4">
            <ul class="list-group" id="recommend-ul">
                <li id="li_test" class="list-group-item list-group-item-success text-center list-title">猜你喜欢</li>
            </ul>
        </div>
    </div>
</div>
<footer class="container-fluid text-center">
    <p>本站所有资源均来自互联网！！！</p>
</footer>
</body>
</html>
