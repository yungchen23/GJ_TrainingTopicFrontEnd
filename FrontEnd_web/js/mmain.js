var app = angular.module("UserManagement", []);

app.controller("UserManagementController", function ($scope, $http) {
    // 初始化表單數據
    $scope.memberform = {
        member_id: -1,
        member_name: "",
        member_sex: "",
        member_phone: "",
        member_mail: "",
        member_birth: "",
        member_address: ""
    };

    $scope.loginform = {
        login_id: -1,
        login_account: "",
        login_answer: "",
        login_password: "",
        login_question: "",
    };

    // 從伺服器加載數據
    _refreshPageData();

    // 提交表單的 HTTP POST/PUT 方法
    $scope.submitMember = function () {
        var method = "";
        var url = "";
        if ($scope.memberform.member_id == -1) {
            method = "POST";
            url = 'http://localhost:8080/member';
        } else {
            method = "PUT";
            url = 'http://localhost:8080/member/' + $scope.memberform.member_id;
        }

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.memberform),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    };

    // HTTP DELETE - 根據 Id 刪除成員
    $scope.removeMember = function (member) {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8080/member/' + member.member_id
        }).then(_success, _error);
    };

    // 編輯成員，填充表單數據
    $scope.editMember = function (member) {
        $scope.memberform.member_id = member.member_id;
        $scope.memberform.member_name = member.member_name;
        $scope.memberform.member_sex = member.member_sex;
        $scope.memberform.member_phone = member.member_phone;
        $scope.memberform.member_mail = member.member_mail;
        $scope.memberform.member_birth = member.member_birth;
        $scope.memberform.member_address = member.member_address;
    };

    /* 私有方法 */
    function _refreshPageData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/member'
        }).then(function successCallback(response) {
            $scope.member = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    function _success(response) {
        _refreshPageData();
        _clearForm();
        alert("操作成功！");
    }

    function _error(response) {
        console.log(response.statusText);
        alert("操作失敗: " + response.statusText);
    }

    // 清空表單
    function _clearForm() {
        $scope.memberform.member_name = "";
        $scope.memberform.member_sex = "";
        $scope.memberform.member_phone = "";
        $scope.memberform.member_mail = "";
        $scope.memberform.member_birth = "";
        $scope.memberform.member_address = "";
        $scope.memberform.member_id = -1;
    }
});