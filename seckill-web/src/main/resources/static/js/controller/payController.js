app.controller('payController', function ($scope, $location, payService) {

    $scope.createNative = function () {
        $scope.id = $location.search()['id'];
        payService.createNative($scope.id).success(
            function (response) {
                //显示订单号和金额
                $scope.money = (response.total_fee / 100).toFixed(2);
                $scope.out_trade_no = response.out_trade_no;

                //生成二维码
                /* var qr = new QRious({
                     element: document.getElementById('qrious'),
                     size: 250,
                     value: response.code_url,
                     level: 'H'
                 });*/

                $scope.queryPayStatus();//调用查询

            }
        );
    }

    //调用查询
    $scope.queryPayStatus = function () {
        payService.queryPayStatus($scope.id, $scope.out_trade_no).success(
            function (response) {
                if (response.flag) {
                    location.href = "paysuccess.html#?money=" + $scope.money;
                } else {
                    if (response.respMsg == '二维码超时') {
                        alert("您未在5分钟内完成支付，请重新参与！");
                        location.href = "payfail.html";
                    } else {
                        location.href = "payfail.html";
                    }
                }
            }
        );
    }

    //获取金额
    $scope.getMoney = function () {
        return $location.search()['money'];
    }
});