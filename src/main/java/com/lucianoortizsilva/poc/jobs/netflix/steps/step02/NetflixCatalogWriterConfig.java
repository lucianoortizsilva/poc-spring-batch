package com.lucianoortizsilva.poc.jobs.netflix.steps.step02;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lucianoortizsilva.poc.jobs.netflix.vo.NetflixCatalogVO;

@Configuration
public class NetflixCatalogWriterConfig {
	
	@Bean
	JdbcBatchItemWriter<NetflixCatalogVO> netflixCatalogWriter(@Qualifier("datalakeDataSource") final DataSource dataSource) {
		final var sql = """
					INSERT INTO netflix_catalog (id, title, "cast", country, releaseYear, duration, listedIn)
					                     VALUES (:id, :title, :cast, :country, :releaseYear, :duration, :listedIn)
				""";
		return new JdbcBatchItemWriterBuilder<NetflixCatalogVO>()//
				.dataSource(dataSource)//
				.sql(sql)//
				.beanMapped()//
				.build();//
	}
}