// 登录函数
function login() {
    $.ajax({
        type: "POST",
        url: "/login",
        data: $('#loginForm').serialize(),// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                // 登录成功转到主页
                window.location.href="main.do";
            } else {
                // 登录失败提示
                alert(data.message);
            }
        }
    });
}

// 注册函数
function regist() {
    $.ajax({
        type: "POST",
        url: "/regist",
        data: $('#registForm').serialize(),// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                // 注册成功转到登录页面
                window.location.href="login.do"; //在原有窗口打开
            } else {
                // 注册失败提示
                alert(data.message);
            }
        }
    });
}

// 发送邮件表单函数
function sendEmail() {
    $.ajax({
        type: "POST",
        url: "/sendEmail",
        data: $('#mailForm').serialize(),// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            // 信息提醒div可见
            // 获取显示返回信息的div
            let responseMessDiv = document.getElementById("responseMess");
            // 如果该div存在，修改其内容为返回中的message字段
            if (responseMessDiv) {
                responseMessDiv.innerText = data.message;
            } else {
                console.log("获取div异常");
            }
            // 设置该div为可见
            if (responseMessDiv.style.display === 'none') {
                responseMessDiv.style.display = 'block';
            }
            // 如果发送邮件成功，显示修改密码需要的div
            if (data.success) {
                // 邮件输入框
                let emailInput = document.getElementById("email");
                // 邮件输入框改为不可修改
                emailInput.setAttribute("readonly", true);
                // 安全码div
                let resetSecretKeyDiv = document.getElementById("resetSecretKey");
                // 重置密码div
                let resetPasswordDiv = document.getElementById("resetPassword");
                // 如果获取div成功，设置div为可见
                if (resetPasswordDiv && resetSecretKeyDiv) {
                    if (resetSecretKeyDiv.style.display === 'none') {
                        resetSecretKeyDiv.style.display = 'block';
                    }
                    if (resetPasswordDiv.style.display === 'none') {
                        resetPasswordDiv.style.display = 'block';
                    }
                } else {
                    console.log("获取控件异常");
                }
                // 修改密码按钮
                let submitResetInput = document.getElementById("submitResetInput");
                // 发送邮件按钮
                let submitInput = document.getElementById("submitInput");
                if (submitResetInput) {
                    // 发送邮件按钮为不可见
                    if (submitInput.style.display === 'block') {
                        submitInput.style.display = 'none';
                    }
                    // 设置修改密码按钮为可见
                    if (submitResetInput.style.display === 'none') {
                        submitResetInput.style.display = 'block';
                    }
                } else {
                    console.log("获取控件异常");
                }

            } else {
                alert(data.message);
            }
        }
    });
}

// 修改密码函数
function resetPass() {
    $.ajax({
        type: "POST",
        url: "/reset",
        data: $('#mailForm').serialize(),// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                // 注册成功转到登录页面
                window.location.href="login.do"; //在原有窗口打开
            } else {
                // 修改密码失败提示
                // 获取显示返回信息的div
                let responseMessDiv = document.getElementById("responseMess");
                // 如果该div存在，修改其内容为返回中的message字段
                if (responseMessDiv) {
                    responseMessDiv.innerText = data.message;
                } else {
                    console.log("获取div异常");
                }
                // 设置该div为可见
                if (responseMessDiv.style.display === 'none') {
                    responseMessDiv.style.display = 'block';
                }
                alert(data.message);
            }
        }
    });
}

// 获取用户个人信息
function getUserInfo(userId) {
    $.ajax({
        type: "POST",
        url: "/userInfo",
        data: {"userId":userId},
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                // 填充表单
                let res = data.data;
                for(let k in res){
                    let type = $("[name="+k+"]").attr("type");
                    $("textarea[name="+k+"]").val(res[k]);
                    if (type!=undefined&&type!=null) {
                        if(type=="text"){
                            if(res[k]!=""&&res[k]!=null){
                                $("[name="+k+"]").val(res[k]);
                            }
                        }
                    }
                }
                // 填充右边头像的两条记录
                let besideEditFormAccount = document.getElementById("besideEditFormAccount");
                besideEditFormAccount.innerHTML = res.account;
                let besideEditFormUsingData = document.getElementById("besideEditFormUsingData");
                besideEditFormUsingData.innerHTML = "成为本站会员已经 " + usingData(res.createTime) + " 天";
            } else {
                // 获取个人信息失败提示
                alert(data.message);
            }
        }
    });
}

// 编辑用户信息函数
function edit() {
    $.ajax({
        type: "POST",
        url: "/edit",
        data: $('#editForm').serialize(),// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                // 注册成功转到登录页面
                window.location.href="user.do"; //在原有窗口打开
            } else {
                // 修改信息失败提示
                alert(data.message);
            }
        }
    });
}

// 计算注册几天
function usingData(olderData) {
    // 获取最新时间
    let today = new Date();
    // 时间差的毫秒数
    let compareDays = today.getTime() - new Date(olderData).getTime();
    //计算出相差天数
    return Math.floor(compareDays /(24*3600*1000))
}