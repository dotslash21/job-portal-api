package xyz.arunangshu.jobportal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.arunangshu.jobportal.dal.JobDAL;
import xyz.arunangshu.jobportal.exchange.GetJobsResponse;
import xyz.arunangshu.jobportal.exchange.PostJobsRequest;
import xyz.arunangshu.jobportal.exchange.PostJobsResponse;
import xyz.arunangshu.jobportal.model.JobEntity;
import xyz.arunangshu.jobportal.repository.JobRepository;

@Service
@Log4j2
public class JobsServiceImpl implements JobsService {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private JobDAL jobDAL;

  @Override
  public GetJobsResponse getJobs(Optional<String> location, Optional<List<String>> skills)
      throws Exception {
    List<JobEntity> jobEntities;

    try {
      jobEntities = jobDAL.filterJobs(location, skills);
    } catch (Exception e) {
      log.error("Couldn't get the jobs list: {}", e.getMessage());
      throw new Exception("Error getting the job list");
    }

    return new GetJobsResponse(jobEntities);
  }

  @Override
  public PostJobsResponse addJob(PostJobsRequest postJobsRequest, LocalDate currentDate)
      throws Exception {
    JobEntity jobEntity = new JobEntity(postJobsRequest.getJobTitle(),
        postJobsRequest.getJobDescription(), postJobsRequest.getCompany(),
        postJobsRequest.getLocation(), postJobsRequest.getSkills(),
        currentDate, postJobsRequest.getExpiresAfter());

    String jobId;
    try {
      jobId = jobRepository.save(jobEntity).getId();
    } catch (Exception e) {
      log.error("Couldn't post the job: {}", e.getMessage());
      throw new Exception("Error posting the job");
    }

    return new PostJobsResponse(jobId);
  }
}
