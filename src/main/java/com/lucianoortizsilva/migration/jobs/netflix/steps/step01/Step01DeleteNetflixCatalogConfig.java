package com.lucianoortizsilva.migration.jobs.netflix.steps.step01;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class Step01DeleteNetflixCatalogConfig {
	
	@Autowired private JobRepository jobRepository;
	@Autowired private PlatformTransactionManager transactionManager;
	@Autowired private DeleteNetflixCatalogTasklet tasklet;
	
	@Bean
	Step step01DeleteNetflixCatalog() {
		return new StepBuilder("step01DeleteNetflixCatalog", jobRepository)//
				.tasklet(tasklet, transactionManager)//
				.build();//
	}
}