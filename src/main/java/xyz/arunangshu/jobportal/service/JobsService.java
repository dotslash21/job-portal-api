package xyz.arunangshu.jobportal.service;

import java.time.LocalDate;
import java.util.List;
import xyz.arunangshu.jobportal.exchange.GetJobsResponse;
import xyz.arunangshu.jobportal.exchange.PostJobsRequest;
import xyz.arunangshu.jobportal.exchange.StatusMessageResponse;

public interface JobsService {

  /**
   * Get filtered list of jobs based on given arguments.
   *
   * @param location The location of the job.
   * @param skills   The skills required for the job.
   * @return The list of filtered jobs.
   */
  GetJobsResponse getJobs(String location, List<String> skills)
      throws Exception;

  /**
   * Post a new job.
   *
   * @param postJobsRequest The job data.
   * @param currentDate     The date of posting of the job.
   * @return The operation status and job id if successful.
   */
  StatusMessageResponse addJob(PostJobsRequest postJobsRequest, LocalDate currentDate)
      throws Exception;
}
