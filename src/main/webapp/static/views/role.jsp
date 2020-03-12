<%--
  Created by IntelliJ IDEA.
  User: zzq52
  Date: 2020/3/2
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>role</title>
    <%@include file="/static/common/common.jsp" %>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/js/role.js"></script>
    <style>
        <%-- 修改/复写框架内的样式 --%>
        #dialog #roleForm .panel-header {
            height: 25px;
        }

        #dialog #roleForm .panel-title {
            font-weight: bold;
            color: black;
            margin-top: -5px;
        }

    </style>
</head>
<body>

<%-- 数据表格 --%>
<div id="role_dg"></div>

<%-- 对话框 --%>
<div id="dialog">
    <form id="roleForm">
        <input type="hidden" name="rid">
        <table align="center" style="border-spacing: 20px 30px">
            <tr align="center">
                <td>角色编号: <input type="text" name="rnum" class="easyui-validatebox"
                                 required="required" placeholder="请输入角色编号"></td>
                <td>角色名称: <input type="text" name="rname" class="easyui-validatebox"
                                 required="required" placeholder="请输入角色名称"></td>
            </tr>
            <tr>
                <td>
                    <div id="role_data1"></div>
                </td>
                <td>
                    <div id="role_data2"></div>
                </td>
            </tr>
        </table>
    </form>
</div>

<%-- 工具栏 --%>
<div id="tb">
    <shiro:hasPermission name="role:add">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
           id="add">添加</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="role:edit">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
           id="edit">编辑</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="role:delete">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true"
           id="remove">删除</a>
    </shiro:hasPermission>
</div>

</body>
</html>
