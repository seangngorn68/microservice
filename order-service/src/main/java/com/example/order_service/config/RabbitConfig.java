package com.example.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitConfig {

    // ================= ORDER FLOW =================
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String ORDER_QUEUE = "order.queue";
    public static final String ORDER_ROUTING_KEY = "order.routing";

    // ================= PAYMENT FLOW =================
  //  public static final String PAYMENT_EXCHANGE = "payment.exchange";
  //  public static final String PAYMENT_QUEUE = "payment.queue";
 //   public static final String PAYMENT_ROUTING_KEY = "payment.routing.key";

    // ---------------- ORDER ----------------
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue())
                .to(orderExchange())
                .with(ORDER_ROUTING_KEY);
    }

    // ---------------- PAYMENT (IMPORTANT FOR CONSUMER) ----------------
//    @Bean
//    public DirectExchange paymentExchange() {
//        return new DirectExchange(PAYMENT_EXCHANGE);
//    }
//
//    @Bean
//    public Queue paymentQueue() {
//        return new Queue(PAYMENT_QUEUE, true);
//    }
//
//    @Bean
//    public Binding paymentBinding() {
//        return BindingBuilder.bind(paymentQueue())
//                .to(paymentExchange())
//                .with(PAYMENT_ROUTING_KEY);
//    }

    // ---------------- COMMON ----------------
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
/*
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "order.exchange";
    public static final String ROUTING_KEY = "order.routing";
    public static final String QUEUE = "order.queue";

    public static final String PAYMENT_EXCHANGE = "payment.exchange";
    public static final String PAYMENT_ROUTING_KEY = "payment.routing.key";
    public static final String PAYMENT_QUEUE = "payment.queue";

    @Bean
    public DirectExchange paymentExchange() {
        return new DirectExchange(PAYMENT_EXCHANGE);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(PAYMENT_QUEUE, true);
    }

    @Bean
    public Binding paymentBinding() {
        return BindingBuilder.bind(paymentQueue())
                .to(paymentExchange())
                .with(PAYMENT_ROUTING_KEY);
    }


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

    // ✅ SIMPLE & SAFE JSON CONVERTER
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {

        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}*/
