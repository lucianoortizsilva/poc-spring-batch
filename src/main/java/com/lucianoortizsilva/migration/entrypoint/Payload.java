package com.lucianoortizsilva.migration.entrypoint;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Payload {
	
	@NotNull private String name;
}