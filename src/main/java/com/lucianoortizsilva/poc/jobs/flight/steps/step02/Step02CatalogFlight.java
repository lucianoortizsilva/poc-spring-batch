package com.lucianoortizsilva.poc.jobs.flight.steps.step02;

import java.util.HashMap;
import java.util.Map;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import com.lucianoortizsilva.poc.jobs.flight.steps.step02.chunk.FlightDTO;
import com.lucianoortizsilva.poc.jobs.flight.steps.step02.chunk.FlightEconomicDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class Step02CatalogFlight {
	
	@Autowired private JobRepository jobRepository;
	@Autowired private PlatformTransactionManager transactionManager;
	private final static int FIFTY_THOUSAND = 50000;
	
	@Bean
	Step step02CatalogFlightManager() throws Exception {
		return new StepBuilder("step02CatalogFlightManager", jobRepository)//
				.partitioner("step02CatalogFlightSlave", partitioner())//
				.partitionHandler(partitionHandlerX(null))//
				.build();//
	}
	
	@Bean
	Step step02CatalogFlightSlave(final FlatFileItemReader<FlightDTO> fileFlightReader, final ItemProcessor<FlightDTO, FlightEconomicDTO> flightProcessor, final ItemWriter<FlightEconomicDTO> flightWriter) {
		return new StepBuilder("step02CatalogFlightSlave", jobRepository)//
				.<FlightDTO, FlightEconomicDTO> chunk(12500, transactionManager)//
				.reader(fileFlightReader)//
				.processor(flightProcessor)//
				.writer(flightWriter)//
				.stream(fileFlightReader)//
				.build();//
	}
	
	@Bean
	Partitioner partitioner() {
		return gridSize -> {
			final Map<String, ExecutionContext> partitionMap = new HashMap<>();
			final int range = FIFTY_THOUSAND / 4;
			final int remainder = FIFTY_THOUSAND % 4;
			int start = 1;
			for (int i = 0; i < 4; i++) {
				int end = start + range - 1;
				if (i < remainder) {
					end += 1;
				}
				final ExecutionContext ctx = new ExecutionContext();
				ctx.putInt("startLine", start);
				ctx.putInt("endLine", end);
				partitionMap.put("partition" + i, ctx);
				start = end + 1;
			}
			return partitionMap;
		};
	}
	
	@Bean
	TaskExecutor taskExecutor() {
		return new SimpleAsyncTaskExecutor("partition_");
	}
	
	@Bean
	PartitionHandler partitionHandlerX(final Step step02CatalogFlightSlave) throws Exception {
		final TaskExecutorPartitionHandler taskExecutorPartitionHandler = new TaskExecutorPartitionHandler();
		taskExecutorPartitionHandler.setTaskExecutor(taskExecutor());
		taskExecutorPartitionHandler.setStep(step02CatalogFlightSlave);
		return taskExecutorPartitionHandler;
	}
}