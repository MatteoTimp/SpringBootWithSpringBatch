package mats.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchController {

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job simpleJob;

  @GetMapping("/hello")
  public String greeting() {
    return "Hallo";
  }

  @GetMapping("/start")
  public ResponseEntity<String> start() {
    try {
      JobParameters jobParameters = new JobParametersBuilder()
          .toJobParameters();

      jobLauncher.run(simpleJob, jobParameters);
      return ResponseEntity.ok("Job gestartet!");
    } catch (JobExecutionAlreadyRunningException | JobRestartException |
             JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
      return ResponseEntity.status(500).body("Fehler beim Starten des Jobs: " + e.getMessage());
    }
  }

}
