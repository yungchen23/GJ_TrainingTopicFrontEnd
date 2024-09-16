var app = angular.module("LoginManagement", []);

app.controller("LoginManagementController", function ($scope, $http) {
    // Initialize page with default data
    $scope.loginform = {
        login_id: -1,
        login_account: "",
        login_answer: "",
        login_password: "",
        confirm_password: "", // 新增 confirm_password 字段
        password_hint: "",
        password_hint_answer: "",
        login_phone: ""
    };

    // Load the data from the server
    _refreshPageData();

    // HTTP POST/PUT methods for add/edit member
    $scope.submitlogin = function () {
        // Check if passwords match
        if ($scope.loginform.login_password !== $scope.loginform.confirm_password) {
            alert("密碼和確認密碼不匹配！");
            return;
        }

        var method = "";
        var url = "";

        if ($scope.loginform.login_id === -1) {
            // Id is absent so add member - POST operation
            method = "POST";
            url = 'http://localhost:8080/login';
        } else {
            // If Id is present, it's an edit operation - PUT operation
            method = "PUT";
            url = 'http://localhost:8080/login/' + $scope.loginform.login_id;
        }

        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.loginform),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    };

    // HTTP DELETE - delete member by Id
    $scope.removelogin = function (login) {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8080/login/' + login.login_id
        }).then(_success, _error);
    };

    // In case of edit member, populate form with member data
    $scope.editlogin = function (login) {
        $scope.loginform = angular.copy(login);
    };

    /* Private Methods */

    // HTTP GET - get all login data
    function _refreshPageData() {
        $http({
            method: 'GET',
            url: 'http://localhost:8080/login'
        }).then(function successCallback(response) {
            $scope.login = response.data;
        }, function errorCallback(response) {
            console.log("Error fetching data: ", response.statusText);
        });
    }

    function _success(response) {
        _refreshPageData();
        _clearForm();
        alert("操作成功！");
        window.location.href = 'index2.html';
    }

    function _error(response) {
        console.log("Error: ", response.statusText);
        alert("操作失敗: " + response.statusText);
    }

    // Clear the form
    function _clearForm() {
        $scope.loginform = {
            login_id: -1,
            login_account: "",
            login_answer: "",
            login_password: "",
            confirm_password: "",
            password_hint: "",
            password_hint_answer: "",
            login_phone: ""
        };
    }
});