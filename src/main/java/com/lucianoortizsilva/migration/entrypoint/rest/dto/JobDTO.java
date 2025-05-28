package com.lucianoortizsilva.migration.entrypoint.rest.dto;

import java.time.OffsetDateTime;

public record JobDTO(int id, String name, boolean situation, int quantitySteps, String lastStatus, OffsetDateTime lastProcessing, String totalProcessingTimeMs) {}
