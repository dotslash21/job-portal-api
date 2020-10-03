package xyz.arunangshu.jobportal.dal;

import java.util.List;
import java.util.Optional;
import xyz.arunangshu.jobportal.model.JobEntity;

public interface JobDAL {

  /**
   * Fetch filtered list of jobs from the database based on given arguments.
   *
   * @param location The location of the job.
   * @param skills The skills required for the job.
   * @return The list of filtered job entities.
   */
  List<JobEntity> filterJobs(Optional<String> location, Optional<List<String>> skills);
}
