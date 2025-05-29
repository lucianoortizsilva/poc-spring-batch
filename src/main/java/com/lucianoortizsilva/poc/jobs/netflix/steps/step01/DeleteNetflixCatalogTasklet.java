package com.lucianoortizsilva.poc.jobs.netflix.steps.step01;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DeleteNetflixCatalogTasklet implements Tasklet {
	
	@Autowired private JdbcTemplate jdbcTemplateDatalakeSource;
	@Autowired private JdbcTemplate jdbcTemplateDatawarehouseSource;
	
	@Override
	public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {
		jdbcTemplateDatalakeSource.execute("DELETE FROM netflix_catalog");
		jdbcTemplateDatawarehouseSource.execute("DELETE FROM netflix_catalog_documentary");
		jdbcTemplateDatawarehouseSource.execute("DELETE FROM netflix_catalog_comedie");
		return RepeatStatus.FINISHED;
	}
}
