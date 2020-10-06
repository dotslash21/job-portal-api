package xyz.arunangshu.jobportal.dal;

import java.util.List;
import xyz.arunangshu.jobportal.model.JobEntity;

public interface JobDAL {

  /**
   * Fetch filtered list of jobs from the database based on given arguments.
   *
   * @param location The location of the job.
   * @param skills   The skills required for the job.
   * @return The list of filtered job entities.
   */
  List<JobEntity> filterJobs(String location, List<String> skills);
}
