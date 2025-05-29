package com.lucianoortizsilva.poc.jobs.flight.steps.step02.chunk;

import java.util.Objects;
import java.util.Optional;

public class FlightBusiness {
	
	static String getAirportName(final String code) {
		final Optional<Airport> airport = Airport.get(code);
		if (airport.isPresent()) {
			return airport.get().getDescription();
		}
		throw new RuntimeException("Airport name not found to code: ".concat(code));
	}
	
	static String getUniqueCompanyName(final String fullCompanyName) {
		if (Objects.isNull(fullCompanyName)) {
			return null;
		}
		if (fullCompanyName.contains("||")) {
			return fullCompanyName.split("\\|\\|")[0];
		}
		return fullCompanyName;
	}
}