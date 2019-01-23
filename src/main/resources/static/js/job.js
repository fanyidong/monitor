// 获取任务列表
function getMonitors(userId, pageNum) {
    $.ajax({
        type: "POST",
        url: "/getMonitors",
        data: {"userId":userId, "pageNum":pageNum, "pageSize":10},// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                alert(data.message)
            } else {
                // 修改信息失败提示
                alert(data.message);
            }
        }
    });
}

// 任务列表字段转换
function transformFields(field) {
    if (field==="name") {
        return "监控项目名称";
    } else if (field==="url") {
        return "监控地址"
    } else if (field==="watchTime") {
        return "监控频率(分钟)";
    } else if (field==="type") {
        return "监控类型";
    } else if (field==="warnMethod") {
        return "警告方式";
    } else if (field==="state") {
        return "状态";
    }
}