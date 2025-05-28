package com.lucianoortizsilva.migration.entrypoint.rest.dto;

import java.util.List;

public record JobSummaryDTO(int id, String name, boolean situation, List<StepDTO> steps) {}