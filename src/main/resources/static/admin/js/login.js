$(function () {
    loginManager.init();
});

var loginManager = {
    init: function () {
        // 解决 window 嵌套
        if (window.top != window) {
            window.top.location.href = window.location.href;
        }

        $("#loginBtn").on("click",function () {
            loginManager.login();
        });

        $(document).on("keyup",function (e) {
            if (e.keyCode == 13) {
                loginManager.login();
            }
        });

    },
    login: function () {
        var username = $("#username").val();
        var password = $("#password").val();

        if (!username) {
            swal("用户名不能为空", "","error");
            return;
        }

        if (!password) {
            swal("密码不能为空", "","error");
            return;
        }

        $.post("/admin/login",{"username":username,"password":password},function (resp) {
            if (resp.code == 200) {
                window.location.href = resp.data;
            } else {
                swal("登录失败", resp.msg,"error");
            }
        },"json");
    }
}
