var app = angular.module("LoginForEmployee", []);
     
      //Controller Part
      app.controller("LoginForEmployeeController", function($scope, $http) {
     
        //Initialize page with default data which is blank in this example
        //使用預設資料初始化頁面，本例為空白
        $scope.employees = [];
        $scope.form = {
          id : -1,
          firstName : "",
          lastName : "",
          email : ""
        };
     
        //Now load the data from server
        //現在從伺服器載入數據
        _refreshPageData();
      });

/* Private Methods */
        //HTTP GET- get all employees collection
        //HTTP GET-取得所有員工集合
        function _refreshPageData() {
          $http({
            method : 'GET',
            url : 'http://localhost:8080/employees'
          }).then(function successCallback(response) {
            $scope.employees = response.data;
          }, function errorCallback(response) {
            console.log(response.statusText);
          });
        }

