$(function () {
    $('#tree').tree({
        // url: 'static/tree.json',
        url: '/getTreeData',
        lines: true,
        onSelect: function (node) {
            // 在添加之前判断该标签是否存在
            let exists = $('#tabs').tabs('exists', node.text);
            if (exists) {
                // 如果存在, 让它成为选中状态
                $("#tabs").tabs('select', node.text);
            } else {
                if (node.url !== '' && node.url != null) {
                    // 如果不存在, 添加新标签
                    $("#tabs").tabs('add', {
                        title: node.text,
                        closable: true,
                        // href: node.url, // href只能包含body标签里的内容, 不包含样式和js
                        content: '<iframe src="' + node.url + '" width="100%" height="100%" style="border: 0"></iframe>',
                    })
                }
            }
        },
        onLoadSuccess: function (node, data) {
            if (data.length > 0) {
                if (data[0].children[0] !== null && data[0].children[0].id !== null) {
                    // 找到第一个元素
                    let n = $('#tree').tree('find', data[0].children[0].id);
                    // 调用选中事件, target参数表示该节点的DOM对象
                    $('#tree').tree('select', n.target);
                }
            }
        }
    });

    $('#tabs').tabs({
        fit: true
    });
});