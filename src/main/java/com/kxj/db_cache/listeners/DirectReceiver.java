package com.kxj.db_cache.listeners;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

@Component
@Slf4j
public class DirectReceiver {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(queues = "exchange.trade.canal")
    public void process(Message message){
        String json = new String(message.getBody());
        log.info("消费消息:"+json);
        Map map= JSON.parseObject(json,Map.class);
        JSONArray array=null;
        String sqlType =(String) map.get("type");
        if (StringUtils.endsWithIgnoreCase("SELECT",sqlType)){
            array=JSONArray.parseArray((String)map.get("data"));
        }else {
            array=(JSONArray) map.get("data");
        }

        if (array==null) return;

        JSONObject object=array.getJSONObject(0);

        if(StringUtils.endsWithIgnoreCase("SELECT",sqlType)){
            stringRedisTemplate.boundValueOps(object.get("product_serial_number").toString()).set(object.toString());
        }else{
            stringRedisTemplate.delete(object.get("product_serial_number").toString());
        }


    }

}
