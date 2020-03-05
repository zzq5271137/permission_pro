$(function () {
    // 初始化表格数据
    $('#dg').datagrid({
        url: '/employeelist',
        columns: [[
            {
                field: 'username',
                title: '姓名',
                width: 100,
                align: 'center',
            },
            {
                field: 'inputtime',
                title: '入职时间',
                width: 100,
                align: 'center',
            },
            {
                field: 'tel',
                title: '电话',
                width: 100,
                align: 'center',
            },
            {
                field: 'email',
                title: '邮箱',
                width: 100,
                align: 'center',
            },
            {
                field: 'department',
                title: '部门',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    /*
                     * 两个参数, value和row;
                     * value: 是指当前字段;
                     * row: 是指整行;
                     * 此处的value就代表row.department;
                     */
                    return value ? value.name : '';
                }
            },
            {
                field: 'admin',
                title: '是否是管理员',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    if (value === true) {
                        return '是';
                    }
                    if (value === false) {
                        return '否'
                    }
                    if (value === null) {
                        return '';
                    }
                }
            },
            {
                field: 'state',
                title: '状态',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.state ? '在职' : '<span style="color: red">离职</span>';
                }
            },
        ]],
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        singleSelect: true,
        striped: true,
        toolbar: '#tb',
        onClickRow: function (rowIndex, rowData) {
            // easy-ui本身的enable和disable有bug(disable后仍可以点击), 需要使用一个插件(详见base.js)
            if (rowData.state === false) {
                // 禁用离职按钮
                $('#delete').linkbutton('disable');
            } else {
                // 启用离职按钮
                $('#delete').linkbutton('enable');
            }
        }
    });

    // 部门选择下拉列表
    $('#department').combobox({
        width: 160,
        panelHeight: 'auto',
        editable: false,
        url: '/departmentList',
        textField: 'name',
        valueField: 'id',
        // 数据加载完毕之后设置placeholder
        onLoadSuccess: function () {
            $('#department').each(function (index, element) {
                let span = $(this).siblings('span')[index];
                let targetInput = $(span).find('input:first');
                if (targetInput) {
                    $(targetInput).attr('placeholder', $(this).attr('placeholder'));
                }
            });
        }
    });

    // 是否为管理员选择下拉列表
    $('#admin').combobox({
        width: 160,
        panelHeight: 'auto',
        textField: 'label',
        valueField: 'value',
        editable: false,
        data: [
            {
                label: '是',
                value: 'true'
            },
            {
                label: '否',
                value: 'false'
            }
        ],
        // 数据加载完毕之后设置placeholder
        onLoadSuccess: function () {
            $('#admin').each(function (index, element) {
                let span = $(this).siblings('span')[index];
                let targetInput = $(span).find('input:first');
                if (targetInput) {
                    $(targetInput).attr('placeholder', $(this).attr('placeholder'));
                }
            });
        }
    });

    // 入职日期选择框
    $('#inputtime').datebox({
        // 数据加载完毕之后设置placeholder
        onHidePanel: function () {
            let span = $('#inputtime').siblings('span')[0];
            let targetInput = $(span).find('input:first');
            if (targetInput) {
                $(targetInput).attr('placeholder', $('#inputtime').attr('placeholder'));
            }
        }
    });

    // 状态选择下拉列表
    $('#state').combobox({
        width: 160,
        panelHeight: 'auto',
        textField: 'label',
        valueField: 'value',
        editable: false,
        data: [
            {
                label: '在职',
                value: 'true'
            },
            {
                label: '离职',
                value: 'false'
            }
        ],
        // 数据加载完毕之后设置placeholder
        onLoadSuccess: function () {
            $('#state').each(function (index, element) {
                let span = $(this).siblings('span')[index];
                let targetInput = $(span).find('input:first');
                if (targetInput) {
                    $(targetInput).attr('placeholder', $(this).attr('placeholder'));
                }
            });
        }
    });

    // 对话框
    $('#dialog').dialog({
        width: 400,
        height: 400,
        modal: true,
        resizable: true,
        closed: true,
        buttons: [
            {
                text: '保存',
                iconCls: 'icon-save',
                handler: function () {
                    // 区分提交的url (在表单中, 使用一个hidden的input来记录id)
                    let id = $('#employeeForm [name="id"]').val();
                    let url;
                    if (id) {
                        url = '/updateEmployee';
                    } else {
                        url = '/saveEmployee';
                    }

                    // 提交表单
                    $('#employeeForm').form('submit', {
                        url: url,
                        success: function (data) {
                            // 将服务器返回的数据解析成json
                            data = $.parseJSON(data);

                            if (data.success) {
                                // 提示信息
                                $.messager.alert('温馨提示', data.msg);
                                // 重新加载表格数据
                                $('#dg').datagrid('reload');
                                // 关闭对话框
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

    // 监听添加按钮
    $('#add').click(function () {
        // $('[name="password"]').validatebox({required: true}); // 取消password的必写验证
        // 隐藏状态下拉列表
        $('#stateinput').hide();
        // 清空表单
        $('#employeeForm').form('clear');
        // 设置标题
        $('#dialog').dialog('setTitle', '添加员工');
        // 弹出对话框
        $('#dialog').dialog('open');
    });

    // 监听编辑按钮
    $('#edit').click(function () {
        // 先判断是否选择了数据
        let rowData = $('#dg').datagrid('getSelected');
        if (!rowData) {
            $.messager.alert('温馨提示', '请先选中一条数据');
            return;
        }

        // $('[name="password"]').validatebox({required: false}); // 取消password的必写验证
        // 显示状态下拉列表
        $('#stateinput').show();
        // 清空表单
        $('#employeeForm').form('clear');
        // 设置标题
        $('#dialog').dialog('setTitle', '编辑员工信息');
        // 弹出对话框
        $('#dialog').dialog('open');

        // 做一些数据处理
        if (rowData['department']) {
            rowData['department.id'] = rowData['department'].id;
        }
        if (rowData['admin'] !== null) {
            rowData['admin'] = rowData['admin'] + '';
        }
        rowData['state'] = rowData['state'] + '';

        // 需要做数据的回显 (load方法根据同名匹配的原则进行数据填充)
        $('#employeeForm').form('load', rowData);
    });

    // 监听离职按钮(不是真正的删除员工数据, 而是将员工状态设置为离职, 称作软删除)
    $('#delete').click(function () {
        // 先判断是否选择了数据
        let rowData = $('#dg').datagrid('getSelected');
        if (!rowData) {
            $.messager.alert('温馨提示', '请先选中一条数据');
            return;
        }

        // 向用户确认, 是否真的要做这个操作
        $.messager.confirm('确认', '是否做离职操作', function (res) {
            if (res === true) {
                // 使用AJAX发送GET请求, 修改员工的state
                let url = '/softDeleteEmployee?id=' + rowData.id;
                $.get(url, function (data) {
                    /*
                     * 这里不需要解析data (不需要$.parseJSON(data));
                     * 因为使用AJAX的GET发送请求, 在接收服务器返回参数时会自动地将JSON字符串解析成JSON对象;
                     * 而在上面的dialog的表单提交中, 需要解析JSON;
                     */
                    if (data.success) {
                        $.messager.alert('温馨提示', data.msg);
                        $('#dg').datagrid('reload');
                    } else {
                        $.messager.alert('温馨提示', data.msg);
                    }
                });
            }
        });
    });

    // 搜索
    function search() {
        // 获取搜索框内容
        let keyword = $('[name="keyword"]').val();

        // 重新加载列表, 并把keyword传过去
        $('#dg').datagrid('load', {
            keyword: keyword
        });
    }

    // 监听搜索按钮
    $('#searchbtn').click(function () {
        search();
    });

    // 监听搜索框回车键按下
    $('[name="keyword"]').keydown(function (e) {
        if (e.keyCode === 13 || e.keyCode === 108) {
            search();
        }
    });

    // 监听刷新按钮
    $('#reload').click(function () {
        // 清空搜索框内容
        $('[name="keyword"]').val('');

        // 重新加载数据表格
        $('#dg').datagrid('load', {});
    });
});