//控制层
app.controller('seckillGoodsController', function ($scope,$controller,seckillGoodsService) {
    $controller('baseController', {$scope: $scope});//继承

    $scope.findByPage = function (page, size) {
        seckillGoodsService.findByPage(page, size).success(
            function (response) {
                $scope.paginationConf.totalItems = response.totalCount;	//更新总记录数
                $scope.seckillList = response.dataList;	//显示当前页的数据
            }
        );
    }
});