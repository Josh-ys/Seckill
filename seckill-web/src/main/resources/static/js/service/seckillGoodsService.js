app.service('seckillGoodsService', function ($http) {
    this.findByPage = function (page, size) {
        return $http.get('/seckillGoods/findList?start=' + page + '&limit=' + size);
    }
});