package com.lucianoortizsilva.migration.jobs.flight.steps.step02.chunk;

import java.util.Optional;
import lombok.Getter;

public enum Airport {
	
	ATL("Hartsfield–Jackson Atlanta International Airport (Atlanta, Georgia)"), //
	BOS("Logan International Airport (Boston, Massachusetts)"), //
	CLT("Charlotte Douglas International Airport (Charlotte, North Carolina)"), //
	DEN("Denver International Airport (Denver, Colorado)"), //
	DFW("Dallas/Fort Worth International Airport (Dallas/Fort Worth, Texas)"), //
	DTW("Detroit Metropolitan Wayne County Airport (Detroit, Michigan)"), //
	EWR("Newark Liberty International Airport (Newark, New Jersey)"), //
	IAD("Washington Dulles International Airport (Washington, D.C. area, Virginia)"), //
	JFK("John F. Kennedy International Airport (New York City, New York)"), //
	LAX("Los Angeles International Airport (Los Angeles, California)"), //
	LGA("LaGuardia Airport (New York, NY)"), //
	MIA("Miami International Airport (Miami, FL)"), //
	OAK("Oakland International Airport (Oakland, CA)"), //
	ORD("O'Hare International Airport (Chicago, IL)"), //
	PHL("Philadelphia International Airport (Philadelphia, PA)"), //
	SFO("San Francisco International Airport (San Francisco, CA)"); //
	
	@Getter private final String description;
	
	Airport(final String description) {
		this.description = description;
	}
	
	public static Optional<Airport> get(final String code) {
		for (final Airport airport : Airport.values()) {
			if (airport.name().equalsIgnoreCase(code)) {
				return Optional.of(airport);
			}
		}
		return Optional.empty();
	}
}