package com.lucianoortizsilva.poc.jobs.flight.steps.step02.chunk;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightProcessorConfig {
	
	@Bean
	ItemProcessor<FlightDTO, FlightEconomicDTO> flightProcessor() {
		final ItemProcessor<FlightDTO, FlightEconomicDTO> process = new ItemProcessor<>() {
			
			@Override
			public FlightEconomicDTO process(final FlightDTO flight) throws Exception {
				if (flight.isBasicEconomy()) {
					final var id = flight.id();
					final var flightDate = flight.flightDate();
					final var startingAirport = FlightBusiness.getAirportName(flight.startingAirport());
					final var destinationAirport = FlightBusiness.getAirportName(flight.destinationAirport());
					final var segmentsAirlineName = FlightBusiness.getUniqueCompanyName(flight.segmentsAirlineName());
					return new FlightEconomicDTO(id, flightDate, startingAirport, destinationAirport, segmentsAirlineName);
				}
				return null;
			}
		};
		return process;
	}
}