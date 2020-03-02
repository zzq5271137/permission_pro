$(function () {
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
                    return value.name ? value.name : '无';
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
            {
                field: 'admin',
                title: '是否是管理员',
                width: 100,
                align: 'center',
                formatter: function (value, row, index) {
                    return row.admin ? '是' : '否';
                }
            },
        ]],
        fit: true,
        fitColumns: true,
        rownumbers: true,
        pagination: true,
        singleSelect: true,
        toolbar: '#tb',
    });

    // 对话框
    $('#dialog').dialog({
        width: 350,
        height: 350,
        modal: true,
        resizable: true,
        closed: true,
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
    $('#state').combobox({
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
            $('#state').each(function (index, element) {
                let span = $(this).siblings('span')[index];
                let targetInput = $(span).find('input:first');
                if (targetInput) {
                    $(targetInput).attr('placeholder', $(this).attr('placeholder'));
                }
            });
        }
    });

    // 入职日期选择框
    $("#inputtime").datebox({
        // 数据加载完毕之后设置placeholder
        onHidePanel: function () {
            let span = $('#inputtime').siblings('span')[0];
            let targetInput = $(span).find('input:first');
            if (targetInput) {
                $(targetInput).attr('placeholder', $('#inputtime').attr('placeholder'));
            }
        }
    });

    // 监听添加按钮
    $('#add').click(function () {
        $('#dialog').dialog('open');
    });
});