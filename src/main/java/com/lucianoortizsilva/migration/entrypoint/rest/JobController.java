package com.lucianoortizsilva.migration.entrypoint.rest;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lucianoortizsilva.migration.entrypoint.rest.dto.JobDTO;
import com.lucianoortizsilva.migration.entrypoint.rest.dto.JobSummaryDTO;
import com.lucianoortizsilva.migration.entrypoint.rest.dto.StepDTO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
	
	final JobDTO job01 = new JobDTO(1, "Job Address", true, 5, "RUNNING", OffsetDateTime.now().minusDays(1), "98956656");
	final JobDTO job02 = new JobDTO(2, "Job 02", false, 3, "FAILED", OffsetDateTime.now(), "124545");
	final JobDTO job03 = new JobDTO(3, "Job 03", true, 12, "COMPLETED", OffsetDateTime.now().minusHours(3), "78451");
	final StepDTO step01 = new StepDTO(1, "Step01 ABC", 0);
	final StepDTO step02 = new StepDTO(2, "Step02 CDE", 1);
	final StepDTO step03 = new StepDTO(3, "Step01 EFG", 0);
	final StepDTO step04 = new StepDTO(4, "Step02 HIJ", 1);
	final StepDTO step05 = new StepDTO(5, "Step01 KLM", 0);
	final StepDTO step06 = new StepDTO(6, "Step02 MNO", 1);
	final List<StepDTO> steps1 = List.of(step01, step02);
	final List<StepDTO> steps2 = List.of(step03, step04);
	final List<StepDTO> steps3 = List.of(step05, step06);
	final JobSummaryDTO summary01 = new JobSummaryDTO(job01.id(), job01.name(), true, steps1);
	final JobSummaryDTO summary02 = new JobSummaryDTO(job02.id(), job02.name(), true, steps2);
	final JobSummaryDTO summary03 = new JobSummaryDTO(job03.id(), job03.name(), true, steps3);
	final List<JobSummaryDTO> jobSummaryDTOs = List.of(summary01, summary02, summary03);
	final List<JobDTO> jobs = List.of(job01, job02, job03);
	
	@GetMapping
	public ResponseEntity<List<JobDTO>> getAllJobs() {
		return ResponseEntity.ok(jobs.stream().toList());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<JobDTO> getById(@PathVariable final int id) {
		final Optional<JobDTO> job = jobs.stream().filter(obj -> obj.id() == id).findFirst();
		if (job.isPresent()) {
			return ResponseEntity.ok(job.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("{id}/summary")
	public ResponseEntity<JobSummaryDTO> getJobSummary(@PathVariable final int id) {
		final Optional<JobSummaryDTO> summary = jobSummaryDTOs.stream().filter(obj -> obj.id() == id).findFirst();
		if (summary.isPresent()) {
			return ResponseEntity.ok(summary.get());
		}
		return ResponseEntity.notFound().build();
	}
}