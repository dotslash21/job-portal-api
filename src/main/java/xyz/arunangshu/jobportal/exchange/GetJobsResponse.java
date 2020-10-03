package xyz.arunangshu.jobportal.exchange;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.arunangshu.jobportal.model.JobEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetJobsResponse {
  private List<JobEntity> jobs;
}
