package com.lucianoortizsilva.migration.entrypoint;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	public static final String EXCHANGE_NAME = "job.exchange";
	public static final String QUEUE = "job_queue";
	
	@Bean
	Queue queue() {
		return new Queue(QUEUE, true);
	}
	
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}
}