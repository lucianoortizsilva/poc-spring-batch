package com.lucianoortizsilva.poc.jobs.flight.steps.step02.chunk;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class FileFlightReaderConfig {
	
	@Bean
	@StepScope
	FlatFileItemReader<FlightDTO> fileFlightReader(@Value("#{stepExecutionContext['startLine']}") final Integer startLine, @Value("#{stepExecutionContext['endLine']}") final Integer endLine) {
		final int linesToRead = endLine - startLine + 1;
		final FlatFileItemReader<FlightDTO> reader = new FlatFileItemReader<>() {
			
			private int linesRead = 0;
			
			@Override
			protected FlightDTO doRead() throws Exception {
				if (linesRead >= linesToRead) {
					return null;
				}
				final FlightDTO pessoa = super.doRead();
				if (pessoa != null) {
					linesRead++;
				}
				return pessoa;
			}
		};
		reader.setResource(new FileSystemResource("files/itineraries.csv"));
		final DefaultLineMapper<FlightDTO> mapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setNames("id", "flightDate", "startingAirport", "destinationAirport", "travelDuration", "isBasicEconomy", "segmentsAirlineName", "segmentsEquipmentDescription");
		mapper.setLineTokenizer(tokenizer);
		mapper.setFieldSetMapper(fieldSet -> new FlightDTO(//
				fieldSet.readString("id"), //
				fieldSet.readString("flightDate"), //
				fieldSet.readString("startingAirport"), //
				fieldSet.readString("destinationAirport"), //
				fieldSet.readString("travelDuration"), //
				Boolean.parseBoolean(fieldSet.readString("isBasicEconomy")), //
				fieldSet.readString("segmentsAirlineName"), //
				fieldSet.readString("segmentsEquipmentDescription")));//
		reader.setLineMapper(mapper);
		reader.setLinesToSkip(startLine - 1);
		return reader;
	}
}