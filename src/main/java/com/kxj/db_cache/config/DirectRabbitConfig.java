package com.kxj.db_cache.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectRabbitConfig {

    //队列 起名：exchange.trade.canal
    @Bean
    public Queue testDirectQueue(){
        return new Queue("exchange.trade.canal",true);
    }

    //Direct交换机 起名：exchange.trade
    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("exchange.trade");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：example
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(TestDirectExchange()).with("example");
    }



}
