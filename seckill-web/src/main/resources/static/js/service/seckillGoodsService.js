app.service('seckillGoodsService', function ($http) {
    this.findAll = function () {
        return $http.get('/seckillGoods/findList');
    }

    this.findById = function (id) {
        return $http.get('/seckillGoods/findOne?id=' + id);
    }
});