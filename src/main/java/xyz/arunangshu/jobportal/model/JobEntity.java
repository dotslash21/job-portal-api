package xyz.arunangshu.jobportal.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "jobs")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
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
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate datePosted;

  @NonNull
  @NotNull
  @JsonDeserialize(using = DurationDeserializer.class)
  @JsonSerialize(using = DurationSerializer.class)
  private Duration expiresAfter;
}
