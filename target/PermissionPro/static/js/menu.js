$(function () {
    $('#menu_dg').datagrid({
        url: '/menulist',
        columns: [[
            {
                field: 'text',
                title: '菜单名称',
                width: 100,
                align: 'center'
            },
            {
                field: 'url',
                title: '跳转地址',
                width: 100,
                align: 'center'
            },
            {
                field: 'parent',
                title: '父级菜单',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    return value ? value.text : '';
                }
            }
        ]],
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        singleSelect: true,
        striped: true,
        toolbar: '#tb'
    });

    $('#parentMenu').combobox({
        width: 160,
        panelHeight: 'auto',
        editable: false,
        url: '/getAllMenus',
        textField: 'text',
        valueField: 'id',
        onLoadSuccess: function () {
            $('#parentMenu').each(function (index, element) {
                let span = $(this).siblings('span')[index];
                let targetInput = $(span).find('input:first');
                if (targetInput) {
                    $(targetInput).attr('placeholder', $(this).attr('placeholder'));
                }
            });
        }
    });

    $('#dialog').dialog({
        width: 300,
        height: 230,
        modal: true,
        resizable: true,
        closed: true,
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-save',
                handler: function () {
                    let id = $('#menuForm [name="id"]').val();
                    let url;
                    if (id) {
                        url = '/updateMenu';

                        // 判断父菜单是不是自己
                        let parentId = $('[name="parent.id"]').val();
                        if (id === parentId) {
                            $.messager.alert('温馨提示', '父菜单不能设置为自己...');
                            return;
                        }
                    } else {
                        url = '/saveMenu';
                    }

                    $('#menuForm').form('submit', {
                        url: url,
                        success: function (data) {
                            data = $.parseJSON(data);
                            if (data.success) {
                                $.messager.alert('温馨提示', data.msg);
                                $('#dialog').dialog('close');
                                $('#menu_dg').datagrid('reload');
                                $('#parentMenu').combobox('reload');
                            } else {
                                $.messager.alert('温馨提示', data.msg);
                            }
                        }
                    })
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#dialog').dialog('close');
                }
            }
        ]
    });

    $('#add').click(function () {
        $('#menuForm').form('clear');
        $('#dialog').dialog('setTitle', '添加菜单');
        $('#dialog').dialog('open');
    });

    $('#edit').click(function () {
        let rowData = $('#menu_dg').datagrid('getSelected');
        if (!rowData) {
            $.messager.alert('温馨提示', '请先选中一条数据');
            return;
        }

        $('#menuForm').form('clear');
        $('#dialog').dialog('setTitle', '编辑菜单');
        $('#dialog').dialog('open');

        if (rowData['parent']) {
            rowData['parent.id'] = rowData['parent'].id;
        }
        $('#menuForm').form('load', rowData);
    });

    $('#delete').click(function () {
        let rowData = $('#menu_dg').datagrid('getSelected');
        if (!rowData) {
            $.messager.alert('温馨提示', '请先选中一条数据');
            return;
        }

        $.messager.confirm('确认', '是否删除该菜单', function (res) {
            if (res === true) {
                let url = '/deleteMenu?id=' + rowData.id;
                $.get(url, function (data) {
                    if (typeof data === 'string') {
                        data = $.parseJSON(data);
                    }

                    if (data.success) {
                        $.messager.alert('温馨提示', data.msg);
                        $('#menu_dg').datagrid('reload');
                        $('#parentMenu').combobox('reload');
                    } else {
                        $.messager.alert('温馨提示', data.msg);
                    }
                })
            }
        })
    });

    $('#reload').click(function () {
        $('#menu_dg').datagrid('load', {});
    });

});