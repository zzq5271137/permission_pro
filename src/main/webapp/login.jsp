<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用户权限管理系统</title>
    <link href="./static/css/base.css" rel="stylesheet">
    <link href="./static/css/login.css" rel="stylesheet">
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/plugins/easyui/jquery.min.js"></script>

</head>
<body class="white">
<div class="login-hd">
    <div class="left-bg"></div>
    <div class="right-bg"></div>
    <div class="hd-inner">
        <span class="logo"></span>
        <span class="split"></span>
        <span class="sys-name">用户权限管理系统</span>
    </div>
</div>

<div class="login-bd">
    <div class="bd-inner">
        <div class="inner-wrap">
            <div class="lg-zone">
                <div class="lg-box">
                    <div class="lg-label"><h4>用户登录</h4></div>

                    <form id="loginform">
                        <div class="lg-username input-item clearfix">
                            <i class="iconfont"></i>
                            <input type="text" value="空条承太郎" name="username"
                                   placeholder="请用户名">
                        </div>
                        <div class="lg-password input-item clearfix">
                            <i class="iconfont"></i>
                            <input type="password" value="jojo3" name="password"
                                   placeholder="请输入密码">
                        </div>

                        <div class="enter">
                            <a href="javascript:;" class="purchaser" id="loginBtn">点击登录</a>
                        </div>
                    </form>

                    <div class="line line-y"></div>
                    <div class="line line-g"></div>
                </div>
            </div>
            <div class="lg-poster"></div>
        </div>
    </div>
</div>

<div class="login-ft">
    <div class="ft-inner">
        <div class="about-us">
            <a href="javascript:;">关于我们</a>
            <a href="http://www.itlike.com/">撩课学院</a>
            <a href="javascript:;">服务条款</a>
            <a href="javascript:;">联系方式</a>
        </div>
        <div class="address"> 课程内容版权均归 撩课教育 所有 &nbsp;编号：210019&nbsp;&nbsp;Copyright&nbsp;©&nbsp;2019&nbsp;-&nbsp;2020&nbsp;撩课&nbsp;版权所有</div>
        <div class="other-info">
            建议使用IE8及以上版本浏览器&nbsp;沪ICP备&nbsp;18036896号&nbsp;E-mail：itlike@126.com
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function () {
        function login() {
            // 使用表单序列化, 提取登录参数
            // 表单的参数名最好是叫username和password, 因为shiro默认将username和password封装成token传入Realm方法中
            let params = $('#loginform').serialize();

            // 发送请求, 做登录认证
            let url = '/login'; // 这里的请求地址要与shiro配置文件中loginUrl配置的值相同, 详见application-shiro.xml
            // 通过Ajax发送请求, 是没有办法在内部跳转服务器当中的请求的(即不能在LoginFormFilter中进行跳转), 只能通过在浏览器中跳转
            $.post(url, params, function (data) {
                // 把json格式字符串转成json对象
                data = $.parseJSON(data);

                if (data.success) {
                    // 认证成功, 跳转到首页
                    alert(data.msg);
                    window.location.href = '/index.jsp';
                } else {
                    alert(data.msg);
                }
            });
        }

        $('#loginBtn').click(function () {
            login();
        });

        $('[name="username"]').keydown(function (e) {
            if (e.keyCode === 13 || e.keyCode === 108) {
                login();
            }
        });

        $('[name="password"]').keydown(function (e) {
            if (e.keyCode === 13 || e.keyCode === 108) {
                login();
            }
        });
    });
</script>

</body>
</html>
