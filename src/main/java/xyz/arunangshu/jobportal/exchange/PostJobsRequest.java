package xyz.arunangshu.jobportal.exchange;

import java.time.Duration;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostJobsRequest {
  @NonNull
  @NotNull(message = "Job title must not be null")
  @NotEmpty(message = "Job title must not be empty")
  private String jobTitle;

  @NonNull
  @NotNull(message = "Job description must not be null")
  @NotEmpty(message = "Job description must not be empty")
  private String jobDescription;

  @NonNull
  @NotNull(message = "Company name must not be null")
  @NotEmpty(message = "Company name must not be empty")
  private String company;

  @NonNull
  @NotNull(message = "Location must not be null")
  @NotEmpty(message = "Location must not be empty")
  private String location;

  @NonNull
  @NotNull(message = "Skills must not be null")
  @NotEmpty(message = "Skills must not be empty")
  private List<String> skills;

  private Duration expiresAfter = Duration.ofDays(60);
}
