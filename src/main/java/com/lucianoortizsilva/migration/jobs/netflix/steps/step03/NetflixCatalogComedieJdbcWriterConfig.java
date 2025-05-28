package com.lucianoortizsilva.migration.jobs.netflix.steps.step03;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.lucianoortizsilva.migration.jobs.netflix.vo.NetflixCatalogVO;

@Configuration
public class NetflixCatalogComedieJdbcWriterConfig {
	
	@Bean
	JdbcBatchItemWriter<NetflixCatalogVO> netflixCatalogComedieJdbcWriter(@Qualifier("datawarehouseDataSource") final DataSource dataSource) {
		final var sql = """
					INSERT INTO netflix_catalog_comedie (id, title, "cast", country, releaseYear, duration)
					                             VALUES (:id, :title, :cast, :country, :releaseYear, :duration)
				""";
		return new JdbcBatchItemWriterBuilder<NetflixCatalogVO>()//
				.dataSource(dataSource)//
				.sql(sql)//
				.beanMapped()//
				.build();//
	}
}