var app = angular.module("PasswordChangeApp", []);

app.controller("PasswordChangeController", function ($scope, $http) {
    // 初始化表單數據
    $scope.passwordForm = {
        newPassword: "",
        confirmPassword: ""
    };

    // 提交密碼變更
    $scope.submitPasswordChange = function () {
        // 檢查密碼和確認密碼是否匹配
        if ($scope.passwordForm.newPassword !== $scope.passwordForm.confirmPassword) {
            alert("新密碼和確認密碼不匹配！");
            return;
        }

        // 創建請求數據
        var data = {
            account: $scope.member.loginForMember.login_account,
            newPassword: $scope.passwordForm.newPassword
        };

        // 發送 HTTP POST 請求來變更密碼
        $http({
            method: 'POST',
            url: 'http://localhost:8080/change-password', // 替換為您的 API 端點
            data: angular.toJson(data),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function successCallback(response) {
            alert("密碼已成功更改！");
            // 清空表單
            $scope.passwordForm = {
                newPassword: "",
                confirmPassword: ""
            };
        }, function errorCallback(response) {
            console.log("Error: ", response.statusText);
            alert("密碼更改失敗: " + response.statusText);
        });
    };
});