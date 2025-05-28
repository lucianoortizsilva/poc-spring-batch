package com.lucianoortizsilva.migration.jobs.flight.steps.step02.chunk;

import javax.sql.DataSource;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightWriterConfig {
	
	@Bean
	JdbcBatchItemWriter<FlightEconomicDTO> flightWriter(@Qualifier("datalakeDataSource") final DataSource dataSource) {
		final var sql = """
				INSERT INTO flight(id, flightDate, startingAirport, destinationAirport, segmentsAirlineName)
				     VALUES (:id, :flightDate, :startingAirport, :destinationAirport, :segmentsAirlineName)
					""";
		return new JdbcBatchItemWriterBuilder<FlightEconomicDTO>()//
				.dataSource(dataSource)//
				.sql(sql)//
				.beanMapped()//
				.build();//
	}
}