package com.ysh.seckill;

import com.ysh.seckill.entity.SeckillGoods;
import com.ysh.seckill.service.SeckillGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeckillWebApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillGoodsService seckillGoodsService;

    @Test
    public void add() {
        redisTemplate.boundValueOps("name").set("ahh");
    }

    @Test
    public void add2() {
        List<SeckillGoods> seckillGoods = redisTemplate.boundHashOps("SeckillGoods").values();
        System.err.println(seckillGoods.size()+"------");
        for (SeckillGoods seckillGood : seckillGoods) {
            System.err.println(seckillGood);
        }
    }

    @Test
    public void delete(){
        Boolean name = redisTemplate.delete("seckillGoods");
        System.err.println(name);
    }

    @Test
    public void test(){
        boolean b = seckillGoodsService.emptySeckillGood(1L);
        System.out.println(b);
    }

}
