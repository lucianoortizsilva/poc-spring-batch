package com.lucianoortizsilva.poc.jobs.flight.steps.step01;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.lucianoortizsilva.poc.jobs.flight.steps.step01.tasklet.DeleteFlightTasklet;

@Configuration
public class Step01DeleteFlightConfig {
	
	@Autowired private JobRepository jobRepository;
	@Autowired private PlatformTransactionManager transactionManager;
	@Autowired private DeleteFlightTasklet tasklet;
	
	@Bean
	Step step01DeleteFlight() {
		return new StepBuilder("step01DeleteFlight", jobRepository)//
				.tasklet(tasklet, transactionManager)//
				.build();//
	}
}