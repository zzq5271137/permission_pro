<%--
  Created by IntelliJ IDEA.
  User: zzq52
  Date: 2020/3/2
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>employee</title>
    <%@include file="/static/common/common.jsp" %>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/employee.js"></script>
</head>
<body>

<%-- 数据表格 --%>
<table id="employee_dg"></table>

<%-- 对话框 --%>
<div id="dialog">
    <form id="employeeForm">
        <%-- 添加一个隐藏域, 用来区分save操作和update操作 --%>
        <input type="hidden" name="id">

        <table align="center" style="border-spacing: 0 10px">
            <tr>
                <td>用户名:</td>
                <td>
                    <input type="text" class="easyui-validatebox" data-options="required: true"
                           name="username" placeholder="请输入用户名">
                </td>
            </tr>
            <tr>
                <td>密码:</td>
                <td>
                    <input type="text" class="easyui-validatebox" required="required"
                           name="password" placeholder="请输入密码">
                </td>
            </tr>
            <tr>
                <td>手机:</td>
                <td>
                    <input type="text" name="tel" placeholder="请输入手机号">
                </td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td>
                    <input type="text" class="easyui-validatebox" data-options="validType:'email'"
                           name="email" placeholder="请输入邮箱">
                </td>
            </tr>
            <tr>
                <td>入职日期:</td>
                <td>
                    <input id="inputtime" type="text" class="easyui-datebox" editable="false"
                           name="inputtime" placeholder="请选择入职日期">
                </td>
            </tr>
            <tr>
                <td>部门:</td>
                <td>
                    <input id="department" editable="false" placeholder="请选择部门"
                           name="department.id"/>
                </td>
            </tr>
            <tr>
                <td>是否是管理员:</td>
                <td>
                    <input id="admin" editable="false" placeholder="请选择是否是管理员" name="admin"/>
                </td>
            </tr>
            <tr id="stateinput">
                <td>状态:</td>
                <td>
                    <input id="state" editable="false" placeholder="请选择状态" name="state"/>
                </td>
            </tr>
        </table>
    </form>
</div>

<%-- 工具栏 --%>
<div id="tb">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
       id="add">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
       id="edit">编辑</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
       id="remove">离职</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true"
       id="reload">刷新</a>

    <%-- 搜索框 --%>
    <input type="text" name="keyword" style="width: 200px; height: 30px; padding-left: 5px;">
    <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="searchbtn">搜索</a>
</div>

</body>
</html>
