<%--
  Created by IntelliJ IDEA.
  User: zzq52
  Date: 2020/3/2
  Time: 17:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>menu</title>
    <%@include file="/static/common/common.jsp" %>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/menu.js"></script>
</head>
<body>

<%-- 数据表格 --%>
<table id="menu_dg"></table>

<%-- 对话框 --%>
<div id="dialog">
    <form id="menuForm" method="post">
        <input type="hidden" name="id">
        <table align="center" style="margin-top: 15px;">
            <tr>
                <td>菜单名称</td>
                <td><input type="text" name="text" placeholder="请输入菜单名称" class="easyui-validatebox"
                           data-options="required: true"></td>
            </tr>
            <tr>
                <td>跳转地址</td>
                <td><input type="text" name="url" placeholder="请输入菜单url" class="easyui-validatebox"
                           data-options="required: true"></td>
            </tr>
            <tr>
                <td>父级菜单</td>
                <td><input id="parentMenu" type="text" name="parent.id" class="easyui-combobox"
                           placeholder="请选择父菜单"/></td>
            </tr>
            <tr>
                <td>所需权限</td>
                <td><input id="permission" type="text" name="permission.pid" class="easyui-combobox"
                           placeholder="请选择所需权限"/></td>
            </tr>
        </table>
    </form>
</div>

<%-- 工具栏 --%>
<div id="tb">
    <div>
        <shiro:hasPermission name="menu:add">
            <a class="easyui-linkbutton" iconCls="icon-add" plain="true" id="add">新增</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="menu:edit">
            <a class="easyui-linkbutton" iconCls="icon-edit" plain="true" id="edit">编辑</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="menu:delete">
            <a class="easyui-linkbutton" iconCls="icon-remove" plain="true" id="delete">刪除</a>
        </shiro:hasPermission>
        <a class="easyui-linkbutton" iconCls="icon-reload" plain="true" id="reload">刷新</a>
    </div>
</div>

</body>
</html>
