app.service('payService', function ($http) {
    //本地支付

    this.createNative = function (id) {
        return $http.get('pay/createNative?userId=' + id);
    }

    //查询支付状态
    this.queryPayStatus = function (userId, out_trade_no) {
        return $http.get('pay/queryPayStatus?out_trade_no=' + out_trade_no + '&userId=' + userId);
    }
});