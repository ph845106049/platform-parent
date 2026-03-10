package com.platform.smsservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.platform.common.base.mq.AlertMqConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类说明：RabbitMqConfig 负责声明短信服务消费提醒消息所需队列拓扑和消息转换器。
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public DirectExchange alertExchange() {
        return new DirectExchange(AlertMqConstants.EXCHANGE, true, false);
    }

    @Bean
    public Queue alertQueue() {
        return new Queue(AlertMqConstants.QUEUE, true);
    }

    @Bean
    public Binding alertBinding(Queue alertQueue, DirectExchange alertExchange) {
        return BindingBuilder.bind(alertQueue).to(alertExchange).with(AlertMqConstants.ROUTING_KEY);
    }

    @Bean
    public MessageConverter rabbitMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
