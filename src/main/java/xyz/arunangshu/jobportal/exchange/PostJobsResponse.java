package xyz.arunangshu.jobportal.exchange;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PostJobsResponse {
  @NonNull
  @NotNull(message = "Status must not be null")
  @NotEmpty(message = "Status must not be empty")
  private String status;

  @NonNull
  @NotNull(message = "Message must not be null")
  @NotEmpty(message = "Message must not be empty")
  private String message;

  private String jobId;
}
