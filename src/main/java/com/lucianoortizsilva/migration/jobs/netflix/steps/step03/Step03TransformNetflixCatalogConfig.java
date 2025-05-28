package com.lucianoortizsilva.migration.jobs.netflix.steps.step03;

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
public class Step03TransformNetflixCatalogConfig {
	
	@Autowired private JobRepository jobRepository;
	@Autowired private PlatformTransactionManager transactionManager;
	
	@Bean
	Step step03TransformNetflixCatalog(final ItemReader<NetflixCatalogVO> netflixCatalogJdbcReader, final ItemWriter<NetflixCatalogVO> compositeNetflixCatalogJdbcWriter) {
		return new StepBuilder("step03TransformNetflixCatalog", jobRepository)//
				.<NetflixCatalogVO, NetflixCatalogVO> chunk(20, transactionManager)//
				.reader(netflixCatalogJdbcReader)//
				.writer(compositeNetflixCatalogJdbcWriter)//
				.build();//
	}
}