// 获取任务列表
function getMonitors(userId, pageNum, whereDo) {
    $.ajax({
        type: "POST",
        url: "/getMonitors",
        data: {"userId":userId, "pageNum":pageNum, "pageSize":10},// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                // 调用创建table方法
                createShowingTable(data, whereDo);
            } else {
                // 修改信息失败提示
                alert(data.message);
            }
        }
    });
}

function createShowingTable(res, whereDo){
    //此处需要让其动态的生成一个table并填充数据
    var tableStr = "<table class='table'";
    if (whereDo === 1) {
        tableStr += "id='mainPageManageTable'>";
    } else {
        tableStr += "id='managePageManageTable'>";
    }
    // table表头
    tableStr = tableStr + "<thead class='text-primary'><th>监控任务名</th><th>监控地址</th><th>监控频率(分钟)</th><th>监控类型</th><th>警告方式</th><th>可用率</th><th>平均响应时间(ms)</th><th>状态</th>";
    if (whereDo !== 1) {
        tableStr += "<th>操作</th>";
    }
    //获取后台传过来的jsonData,并进行解析
    var dataArray = null;
    var len = 0;
    if (res.success && res.data.list != null) {
        //获取后台传过来的jsonData,并进行解析
        dataArray = res.data.list;
        len = dataArray.length;
    }
    // 若返回结果不为空则填充数据
    if (dataArray != null && len > 0) {
        for (var i = 0; i < len; i++) {
            tableStr += "<tbody><tr>"
                + "<td style='display: none'>" + dataArray[i].monitorId + "</td>"
                + "<td>" + dataArray[i].name + "</td>"
                + "<td>" + dataArray[i].url + "</td>"
                + "<td>" + dataArray[i].watchTime + "</td>"
                + "<td>" + transformType(dataArray[i].type) + "</td>"
                + "<td>" + transformWarnMethod(dataArray[i].warnMethod) + "</td>"
                + "<td>" + dataArray[i].usable+'%' + "</td>"
                + "<td>" + dataArray[i].averageResponseTime + "</td>"
                + "<td>" + transformState(dataArray[i].state) + "</td>";
                // whereDo=1表示main页面，其他表示manage页面
                if (whereDo !== 1) {
                    tableStr += "<td>"
                        + "<a href='#' onclick='toEditPage(this)' style='font-weight: bolder'>编辑</a>\t"
                        + "<a href='#' onclick='closeMonitor(this)' style='font-weight: bolder'>" + transformStateReverse(dataArray[i].state) + "</a>\t"
                        + "<a href='#' onclick='delMonitor(this)' style='font-weight: bolder'>删除</a>"
                        + "</td>"
                }
            tableStr += "</tr></tbody>";
        }
    }
    tableStr = tableStr + "</table>";
    //将动态生成的table添加的事先隐藏的div中.
    if (whereDo === 1) {
        $("#mainPageManageList").html(tableStr);
    } else {
        $("#managePageManageList").html(tableStr);
    }
}

function closeMonitor(obj) {
    var trObj = getRowObj(obj);
    // 获得td标签子集
    var tdA = trObj.children;
    // 获得td数组
    var tdArr = tdA[0].children;
    var monitorId = tdArr[0].innerHTML;
    // 获得改监控的状态
    var state = transformStateReverse(tdArr[8].innerHTML);
    if (monitorId != null && state != null) {
        $.ajax({
            type: "POST",
            url: "/closeMonitor",
            data: {"monitorId":monitorId, "state":state},
            dataType: "json",// 预期服务器返回的数据类型
            success:function (data) {
                if (data.success) {
                    // 成功则页面刷新
                    location.reload();
                } else {
                    // 修改信息失败提示
                    alert(data.message);
                }
            }
        });
    } else {
        alert("参数为空");
    }
}

function delMonitor(obj) {
    var trObj = getRowObj(obj);
    // 获得td标签子集
    var tdA = trObj.children;
    // 获得td数组
    var tdArr = tdA[0].children;
    var monitorId = tdArr[0].innerHTML;
    if (monitorId != null) {
        $.ajax({
            type: "POST",
            url: "/delMonitor",
            data: {"monitorId":monitorId},
            dataType: "json",// 预期服务器返回的数据类型
            success:function (data) {
                if (data.success) {
                    // 成功则页面刷新
                    location.reload();
                } else {
                    // 修改信息失败提示
                    alert(data.message);
                }
            }
        });
    } else {
        alert("参数为空");
    }
}

//得到行对象
function getRowObj(obj) {
    while(obj.tagName.toLowerCase() !== "tbody"){
        // a->td->tr->tbody->table
        // 获得tbody节点
        obj = obj.parentNode.parentNode.parentNode;
        if(obj.tagName.toLowerCase() === "table") {
            return null;
        }
    }
    return obj;
}

//根据得到的行对象得到所在的行数
function getRowNo(obj){
    // 获得行对象
    var trObj = getRowObj(obj);
    // 获得行对象的父级的子集
    var trArr = trObj.parentNode.children;
    for(var trNo= 0; trNo < trArr.length; trNo++){
        if(trObj === trObj.parentNode.children[trNo]){
            alert(trNo);
            // 返回行号
            return trNo;
        }
    }
}


// 警告方式(1邮件 2手机)字段转换
function transformWarnMethod(number) {
    if (number===1) {
        return "邮件";
    } else if (number===2) {
        return "手机";
    }
}

// 启用状态(0禁用 1启用)字段转换
function transformState(number) {
    if (number===1) {
        return "启用";
    } else if (number===0) {
        return "禁用";
    }
}

// 监控类型(1网站 2接口 3服务)字段转换
function transformType(number) {
    if (number===1) {
        return "网站";
    } else if (number===2) {
        return "接口"
    } else if (number===3) {
        return "服务";
    }
}
// 操作字段转换(与启用状态相反)
function transformStateReverse(number) {
    if (number===0) {
        return "启用";
    } else if (number===1) {
        return "禁用";
    } else if (number==="禁用") {
        return 1;
    } else if (number==="启用") {
        return 0;
    }
}

// 增加监控任务
function addMonitor() {
    $.ajax({
        type: "POST",
        url: "/addMonitor",
        data: $('#applyPageAddMonitorForm').serialize(),// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                // 注册成功转到登录页面
                window.location.href="manage.do"; //在原有窗口打开
            } else {
                // 修改信息失败提示
                alert(data.message);
            }
        }
    });
}

function toEditPage(obj) {
    // 得到行对象
    var trHTML = getRowObj(obj).children;
    var tds = trHTML[0].children;
    var tdHTML = tds[0];
    window.location.href="edit.do?monitorId=" + tdHTML.innerHTML;
}

function setFormByUrl(url) {
    var args = url.split("?");
    var monitorId = '';
    // 参数为空
    if(args[0] !== args) {
        var str = args[1];
        args = str.split("&");
        for(var i = 0; i < args.length; i++ ) {
            str = args[i];
            var arg = str.split("=");
            if(arg.length <= 1) continue;
            if(arg[0] === 'monitorId') {
                monitorId = arg[1];
                break;
            }
        }
        // 根据monitorId获取监控详情
        if (monitorId!==''){
            getMonitor(monitorId);
        }
    }
}

// 获取任务列表
function getMonitor(monitorId) {
    $.ajax({
        type: "POST",
        url: "/getMonitor",
        data: {"monitorId":monitorId},
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                var monitorObj = data.data;
                // 调用创建table方法
                document.getElementById("monitorId").value = monitorId;
                document.getElementById("name").value = monitorObj.name;
                document.getElementById("type").value = monitorObj.type;
                document.getElementById("url").value = monitorObj.url;
                document.getElementById("warnMethod").value = monitorObj.warnMethod;
                document.getElementById("watchTime").value = monitorObj.watchTime;
            } else {
                // 修改信息失败提示
                alert(data.message);
            }
        }
    });
}

// 编辑监控任务
function editMonitor() {
    $.ajax({
        type: "POST",
        url: "/editMonitor",
        data: $('#editPageAddMonitorForm').serialize(),// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                // 注册成功转到登录页面
                window.location.href="manage.do"; //在原有窗口打开
            } else {
                // 修改信息失败提示
                alert(data.message);
            }
        }
    });
}