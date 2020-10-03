package xyz.arunangshu.jobportal.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "jobs")
@RequiredArgsConstructor
public class JobEntity {
  @Id
  private String id;

  @NonNull
  @NotNull
  private String jobTitle;

  @NonNull
  @NotNull
  private String jobDescription;

  @NonNull
  @NotNull
  private String company;

  @NonNull
  @NotNull
  private String location;

  @NonNull
  @NotNull
  private List<String> skills;

  @NonNull
  @NotNull
  private LocalDate datePosted;

  @NonNull
  @NotNull
  private Duration expiresAfter;
}
