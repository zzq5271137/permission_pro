<%--
  Created by IntelliJ IDEA.
  User: zzq52
  Date: 2020/3/2
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户权限管理系统</title>
    <%@include file="/static/common/common.jsp" %>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/index.js"></script>
</head>
<body class="easyui-layout">

<%-- 顶部 --%>
<div data-options="region:'north'" style="height:100px; background: #ec4e00; padding: 20px 20px">
    <img src="${pageContext.request.contextPath}/static/images/main_logo.png" alt="">

    <div style="position: absolute; right: 50px; top: 30px;">
        <img src="./static/images/user.png" style="vertical-align: middle; margin-right: 10px;"
             alt="信息管理系统">

        <span style="color: white; font-size: 20px; margin-right: 5px;">
            <%-- 显示当前登录用户名 --%>
            <shiro:principal property="username"/>
        </span>

        <%-- 取消认证(注销), 跳转到登录页面, 需要在shiro配置文件当中配置: /logout = logout --%>
        <a href="${pageContext.request.contextPath}/logout"
           style="font-size: 18px; color: white;text-decoration: none;">注销</a>
    </div>
</div>

<%-- 中间左侧 --%>
<div data-options="region:'west',split:true" style="width:300px;">
    <div id="aa" class="easyui-accordion" data-options="fit:true">
        <div title="菜单" data-options="iconCls:'icon-save',selected:true"
             style="overflow:auto;padding:10px;">
            <ul id="tree"></ul>
        </div>
        <div title="公告" data-options="iconCls:'icon-reload'" style="padding:10px;">

        </div>
    </div>
</div>

<%-- 主区域 --%>
<div data-options="region:'center'" style="background:#eee;">
    <!-- 标签 -->
    <div id="tabs" style="overflow: hidden"></div>
</div>

<%-- 底部 --%>
<div data-options="region:'south'" style="height:50px; border-bottom: 3px solid #ec4e00">
    <p style="font-size: 14px;text-align: center">
        广播电视节目制作经营许可证：（沪）字第233号|网络文化经营许可证：沪网文【2020】233-123号
    </p>
</div>

</body>
</html>
