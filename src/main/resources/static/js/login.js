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
                console.log(data.message);
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
                console.log(data.message);
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
                // 提交按钮
                let submitInput = document.getElementById("submitInput");
                // 改变提交按钮的值为修改密码
                submitInput.setAttribute("value", "修改密码");
                submitInput.setAttribute("onclick", "reset()");
                // 如果获取div成功，设置div为可见
                if (resetPasswordDiv && resetSecretKeyDiv) {
                    if (resetSecretKeyDiv.style.display === 'none') {
                        resetSecretKeyDiv.style.display = 'block';
                    }
                    if (resetPasswordDiv.style.display === 'none') {
                        resetPasswordDiv.style.display = 'block';
                    }
                } else {
                    console.log("获取div异常");
                }
            }
        }
    });
}