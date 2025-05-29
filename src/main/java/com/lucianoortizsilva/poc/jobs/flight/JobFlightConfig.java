package com.lucianoortizsilva.poc.jobs.flight;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JobFlightConfig {
	
	@Autowired private JobRepository jobRepository;
	@Autowired private Step step01DeleteFlight;
	@Autowired private Step step02CatalogFlightManager;
	
	@Bean
	Job jobFlight() {
		return new JobBuilder("jobFlight", jobRepository)//
				.start(step01DeleteFlight)//
				.next(step02CatalogFlightManager)//
				.build();//
	}
}