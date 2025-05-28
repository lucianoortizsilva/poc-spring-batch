package com.lucianoortizsilva.migration.jobs.netflix.steps.step02;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import com.lucianoortizsilva.migration.jobs.netflix.vo.NetflixCatalogVO;

@Configuration
public class Step02LoadNetflixCatalogConfig {
	
	@Autowired private JobRepository jobRepository;
	@Autowired private PlatformTransactionManager transactionManager;
	
	@Bean
	Step step02LoadNetflixCatalog(final ItemReader<NetflixCatalogVO> netflixCatalogFileReader, final ItemWriter<NetflixCatalogVO> netflixCatalogWriter) {
		return new StepBuilder("step02LoadNetflixCatalog", jobRepository)//
				.<NetflixCatalogVO, NetflixCatalogVO> chunk(100, transactionManager)//
				.reader(netflixCatalogFileReader)//
				.writer(netflixCatalogWriter)//
				.build();//
	}
}