package com.api.usersapi.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class UserRabbitConfig {

        public static final String USER_SAVE_QUEUE = "save.user.queue";
        public static final String USER_SAVE_EXCHANGE = "user.save.exchange";
        public static final String USER_SAVE_ROUTING_KEY = "save.user.key";


        public static final String USER_SAVE_QUEUE_DEADLETTER = "save.user.queue.deadLetter";
        public static final String USER_SAVE_EXCHANGE_DEADLETTER = "user.save.exchange.deadLetter";
        public static final String USER_SAVE_ROUTING_KEY_DEADLETTER = "save.user.key.deadLetter";

        @Bean
        Queue queue() { return QueueBuilder.durable(USER_SAVE_QUEUE).withArgument("x-dead-letter-exchange", USER_SAVE_EXCHANGE_DEADLETTER).withArgument("x-dead-letter-routing-key", USER_SAVE_ROUTING_KEY_DEADLETTER).build();
        }

        @Bean
        DirectExchange exchange() { return new DirectExchange(USER_SAVE_EXCHANGE); }
        @Bean
        Binding binding() { return BindingBuilder.bind(queue()).to(exchange()).with(USER_SAVE_ROUTING_KEY); }






        @Bean
        DirectExchange exchangeDeadLetter() {
            return new DirectExchange(USER_SAVE_EXCHANGE_DEADLETTER);
        }

        @Bean
        Queue queueDeadLetter() { return QueueBuilder.durable(USER_SAVE_QUEUE_DEADLETTER).build(); }

        @Bean
        Binding bindingDeadLetter() { return BindingBuilder.bind(queueDeadLetter()).to(exchangeDeadLetter()).with(USER_SAVE_ROUTING_KEY_DEADLETTER); }

        @Bean
        public MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter();  }

        public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
                RabbitTemplate template = new RabbitTemplate(connectionFactory);
                RetryTemplate retryTemplate = new RetryTemplate();
                ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
                backOffPolicy.setInitialInterval(500);
                backOffPolicy.setMultiplier(10.0);
                backOffPolicy.setMaxInterval(500);
                retryTemplate.setBackOffPolicy(backOffPolicy);
                template.setRetryTemplate(retryTemplate);
                return template;
        }
    }
