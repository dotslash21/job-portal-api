package xyz.arunangshu.jobportal.exchange;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PostJobsResponse {

  @NonNull
  @NotNull(message = "Job ID must not be null")
  @NotEmpty(message = "Job ID must not be empty")
  private String jobId;
}
