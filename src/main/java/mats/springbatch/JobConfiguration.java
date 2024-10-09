package mats.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
  @Bean
  public Step step(JobRepository jobRepository, JdbcTransactionManager transactionManager) {
    return new StepBuilder("step", jobRepository).tasklet((contribution, chunkContext) -> {
      System.out.println("Hello world!");
      return RepeatStatus.FINISHED;
    }, transactionManager).build();
  }

  @Bean
  public Job simpleJob(JobRepository jobRepository, Step step) {
    return new JobBuilder("job", jobRepository)
        .incrementer(new RunIdIncrementer()) // notwendig für mehrere Runs
        .start(step).build();
  }
}
