package com.kxj.db_cache.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kxj.db_cache.entity.Store;
import com.kxj.db_cache.mapper.StoreMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class MessageController {
    @Resource
    private StoreMapper storeMapper;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/update1")
    public void update1(){

        Store store=new Store();
        store.setProductSerialNumber("21213332233");

        store.setPrice(new BigDecimal("99.12"));
        storeMapper.update(store,
                new LambdaQueryWrapper<Store>()
                        .eq(Store::getProductSerialNumber,"21213332233"));
        //通过canal监听了数据变更,无需手动发送消息

        //模拟删除失败
        int i=1/0;
        stringRedisTemplate.delete("21213332233");
    }

    @GetMapping("/get")
    public String getMessage(){

        Store store=storeMapper.selectOne(
                new LambdaQueryWrapper<Store>()
                        .eq(Store::getProductSerialNumber,"21213332233"));
        log.info("进行了查询商品信息:"+store);
        Map<String,String> map=new HashMap<>();
        map.put("type","SELECT");
        map.put("data","[{'product_serial_number':'21213332233','price':"+store.getPrice1()+"}]");

        rabbitTemplate.convertAndSend("exchange.trade","example", JSON.toJSONString(map));

        return "测试完毕";
    }
}
