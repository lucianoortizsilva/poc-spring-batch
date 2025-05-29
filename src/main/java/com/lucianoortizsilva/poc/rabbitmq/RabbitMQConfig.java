package com.lucianoortizsilva.poc.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	public static final String EXCHANGE_TOPIC_NAME = "job.exchange";
	public static final String EXCHANGE_TOPIC_ROUNTING_KEY = "job.execute";
	public static final String QUEUE_NAME = "job_queue";
	
	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME, true);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_TOPIC_NAME);
	}
}