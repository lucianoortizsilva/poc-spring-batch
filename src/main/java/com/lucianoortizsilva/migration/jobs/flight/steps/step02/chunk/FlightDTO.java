package com.lucianoortizsilva.migration.jobs.flight.steps.step02.chunk;

public record FlightDTO(String id, String flightDate, String startingAirport, String destinationAirport, String travelDuration, Boolean isBasicEconomy, String segmentsAirlineName, String segmentsEquipmentDescription) {}
