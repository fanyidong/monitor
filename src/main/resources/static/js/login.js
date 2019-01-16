// 登录函数
function login() {
    $.ajax({
        type: "POST",
        url: "/login",
        data: $('#loginForm').serialize(),// 获取form表单中的数据
        dataType: "json",// 预期服务器返回的数据类型
        success:function (data) {
            if (data.success) {
                //todo 登录成功转到主页
            } else {
                // 登录失败提示
                alert(data.message);
            }
        }
    });
}