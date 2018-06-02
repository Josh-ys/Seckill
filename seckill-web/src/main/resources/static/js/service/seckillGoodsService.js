app.service('seckillGoodsService', function ($http) {
    this.findAll = function () {
        return $http.get('/seckillGoods/findList');
    }

    this.findById = function (id) {
        return $http.get('/seckillGoods/findOne?id=' + id);
    }

    this.submitOrder = function (seckillId, userId) {
        return $http.get('/seckillOrder/submitOrder?id=' + seckillId + '&userId=' + userId);
    }
});