package com.lucianoortizsilva.poc.jobs.netflix;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NetflixJobConfig {
	
	@Autowired private JobRepository jobRepository;
	@Autowired private Step step01DeleteNetflixCatalog;
	@Autowired private Step step02LoadNetflixCatalog;
	@Autowired private Step step03TransformNetflixCatalog;
	
	@Bean
	Job netflixJob() {
		return new JobBuilder("netflixJob", jobRepository)//
				.start(step01DeleteNetflixCatalog)//
				.next(step02LoadNetflixCatalog)//
				.next(step03TransformNetflixCatalog)//
				.build();//
	}
}