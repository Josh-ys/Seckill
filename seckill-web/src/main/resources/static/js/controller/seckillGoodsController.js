//控制层
app.controller('seckillGoodsController', function ($scope, $location, $controller, $interval, seckillGoodsService) {
    $scope.findAll = function () {
        seckillGoodsService.findAll().success(
            function (response) {
                $scope.seckillList = response;	//显示当前页的数据
            }
        );
    }

    $scope.findOne = function () {
        seckillGoodsService.findById($location.search()['id']).success(
            function (response) {
                $scope.entity = response;

                //倒计时开始
                //获取从结束时间到当前日期的秒数
                allsecond = Math.floor((new Date($scope.entity.endTime).getTime() - new Date().getTime()) / 1000);

                time = $interval(function () {
                    allsecond = allsecond - 1;
                    $scope.timeString = convertTimeString(allsecond);

                    if (allsecond <= 0) {
                        $interval.cancel(time);
                    }

                }, 1000);
            }
        );
    }

    //转换秒为   天小时分钟秒格式  XXX天 10:22:33
    convertTimeString = function (allsecond) {
        var aTime = 60 * 60 * 24;
        var bTime = 60 * 60;
        var days = Math.floor(allsecond / aTime);//天数
        var hours;
        if (days >= 0) {
            hours = Math.floor((allsecond - days * aTime) / bTime);//小时数
        } else {
            hours = Math.floor(allsecond / bTime);
        }
        var minutes;
        if (hours >= 0) {
            minutes = Math.floor((allsecond - days * aTime - hours * bTime) / 60);//分钟数
        } else {
            minutes = Math.floor(allsecond / 60);//分钟数
        }
        var seconds;
        if (minutes >= 0) {
            seconds = allsecond - days * aTime - hours * bTime - minutes * 60;
        } else {
            seconds = allsecond;
        }
        var timeString = "";
        if (days > 0) {
            timeString = days + "天 ";
        }
        if (hours <= 0) {
            hours = 00;
        }
        if (minutes <= 0) {
            minutes = 00;
        }
        if (seconds <= 0) {
            seconds = 00;
        }
        return timeString + hours + ":" + minutes + ":" + seconds;
    }

});