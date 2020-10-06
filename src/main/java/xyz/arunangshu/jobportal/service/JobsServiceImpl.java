package xyz.arunangshu.jobportal.service;

import java.time.LocalDate;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.arunangshu.jobportal.dal.JobDAL;
import xyz.arunangshu.jobportal.exchange.GetJobsResponse;
import xyz.arunangshu.jobportal.exchange.PostJobsRequest;
import xyz.arunangshu.jobportal.exchange.StatusMessageResponse;
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
  public GetJobsResponse getJobs(String location, List<String> skills)
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
  public StatusMessageResponse addJob(PostJobsRequest postJobsRequest, LocalDate currentDate) {
    JobEntity jobEntity = new JobEntity(postJobsRequest.getJobTitle(),
        postJobsRequest.getJobDescription(), postJobsRequest.getCompany(),
        postJobsRequest.getLocation(), postJobsRequest.getSkills(),
        currentDate, postJobsRequest.getExpiresAfter());

    jobRepository.save(jobEntity);

    return new StatusMessageResponse("SUCCESS", "Job created successfully!");
  }
}
