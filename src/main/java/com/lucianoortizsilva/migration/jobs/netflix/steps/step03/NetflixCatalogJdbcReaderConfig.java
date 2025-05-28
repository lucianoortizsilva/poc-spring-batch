package com.lucianoortizsilva.migration.jobs.netflix.steps.step03;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import com.lucianoortizsilva.migration.jobs.netflix.vo.NetflixCatalogVO;

@Configuration
public class NetflixCatalogJdbcReaderConfig {
	
	@Bean
	JdbcCursorItemReader<NetflixCatalogVO> netflixCatalogJdbcReader(@Qualifier("datalakeDataSource") final DataSource dataSource) {
		final var sql = "SELECT id, title, \"cast\", country, releaseYear, duration, listedIn FROM netflix_catalog";
		return new JdbcCursorItemReaderBuilder<NetflixCatalogVO>() //
				.name("netflixCatalogJdbcReader") //
				.dataSource(dataSource) //
				.sql(sql) //
				.rowMapper(rowMapper()) //
				.build();
	}
	
	private static RowMapper<NetflixCatalogVO> rowMapper() {
		return (rs, rowNum) -> {
			final String id = rs.getString("id");
			final String title = rs.getString("title");
			final String cast = rs.getString("cast");
			final String country = rs.getString("country");
			final String releaseYear = rs.getString("releaseYear");
			final String duration = rs.getString("duration");
			final String listedIn = rs.getString("listedIn");
			return new NetflixCatalogVO(id, title, cast, country, releaseYear, duration, listedIn);
		};
	}
}