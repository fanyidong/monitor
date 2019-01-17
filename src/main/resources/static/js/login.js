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
                window.location.href="index.do"; //在原有窗口打开
            } else {
                // 注册失败提示
                console.log(data.message);
            }
        }
    });
}