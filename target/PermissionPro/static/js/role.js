$(function () {
    $('#role_dg').datagrid({
        url: '/rolelist',
        columns: [[
            {
                field: 'rnum',
                title: '角色编号',
                width: 100,
                align: 'center',
            },
            {
                field: 'rname',
                title: '角色名称',
                width: 100,
                align: 'center',
            },
        ]],
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        singleSelect: true,
        striped: true,
        toolbar: '#tb',
    });

    $('#dialog').dialog({
        width: 600,
        height: 500,
        modal: true,
        resizable: true,
        closed: true,
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-save',
                handler: function () {
                    let rid = $('#roleForm [name="rid"]').val();
                    let url;
                    if (rid) {
                        url = '/updateRole';
                    } else {
                        url = '/saveRole';
                    }

                    $('#roleForm').form('submit', {
                        url: url,
                        // 提交表单时, 如果有额外的参数, 可以通过onSubmit中的param来提交
                        onSubmit: function (param) {
                            let allRows = $('#role_data2').datagrid('getRows');
                            for (let i = 0; i < allRows.length; i++) {
                                let row = allRows[i];
                                param['permissions[' + i + '].pid'] = row.pid;
                            }
                        },
                        success: function (data) {
                            data = $.parseJSON(data);
                            if (data.success) {
                                $.messager.alert('温馨提示', data.msg);
                                $('#role_dg').datagrid('reload');
                                $('#dialog').dialog('close');
                            } else {
                                $.messager.alert('温馨提示', data.msg);
                            }
                        }
                    });
                }
            },
            {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#dialog').dialog('close');
                }
            }
        ]
    });

    // 所有权限的列表
    $('#role_data1').datagrid({
        url: '/permissionList',
        title: '所有权限',
        width: 250,
        height: 330,
        fitColumns: true,
        singleSelect: true,
        striped: true,
        rownumbers: true,
        columns: [[
            {
                field: 'pname',
                title: '权限名称',
                width: 100,
                align: 'center',
            },
        ]],
        onClickRow: function (rowIndex, rowData) {
            let allRows = $('#role_data2').datagrid('getRows');
            for (let i = 0; i < allRows.length; i++) {
                let row = allRows[i];
                if (rowData.pid === row.pid) {
                    let index = $('#role_data2').datagrid('getRowIndex', row);
                    $('#role_data2').datagrid('selectRow', index);
                    return;
                }
            }
            $('#role_data2').datagrid('appendRow', rowData);
        }
    });

    // 已选权限的列表
    $('#role_data2').datagrid({
        title: '已选权限',
        width: 250,
        height: 330,
        fitColumns: true,
        singleSelect: true,
        striped: true,
        rownumbers: true,
        columns: [[
            {
                field: 'pname',
                title: '权限名称',
                width: 100,
                align: 'center',
            },
        ]],
        onClickRow: function (rowIndex, rowData) {
            $('#role_data2').datagrid('deleteRow', rowIndex);
        }
    });

    $('#add').click(function () {
        $('#roleForm').form('clear');
        $('#role_data2').datagrid('loadData', {rows: []});
        $('#dialog').dialog('setTitle', '添加角色');
        $('#dialog').dialog('open');
    });

    $('#edit').click(function () {
        let rowData = $('#role_dg').datagrid('getSelected');
        if (!rowData) {
            $.messager.alert('温馨提示', '请先选中一条数据');
            return;
        }

        $('#dialog').dialog('setTitle', '编辑角色');
        $('#dialog').dialog('open');

        // 表单数据回显
        $('#roleForm').form('load', rowData);

        // 已选权限的数据回显
        let options = $('#role_data2').datagrid('options');
        options.url = '/getPermissionsByRoleId?rid=' + rowData.rid;
        $('#role_data2').datagrid('load');
    });

    $('#remove').click(function () {
        let rowData = $('#role_dg').datagrid('getSelected');
        if (!rowData) {
            $.messager.alert('温馨提示', '请先选中一条数据');
            return;
        }

        $.messager.confirm('确认', '是否删除该角色', function (res) {
            if (res === true) {
                let url = '/deleteRole?rid=' + rowData.rid;
                $.get(url, function (data) {
                    if (typeof data === 'string') {
                        data = $.parseJSON(data);
                    }

                    if (data.success) {
                        $.messager.alert('温馨提示', data.msg);
                        $('#role_dg').datagrid('reload');
                    } else {
                        $.messager.alert('温馨提示', data.msg);
                    }
                })
            }
        })
    });
});