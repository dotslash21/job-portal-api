package xyz.arunangshu.jobportal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.arunangshu.jobportal.dal.JobDAL;
import xyz.arunangshu.jobportal.exchange.GetJobsResponse;
import xyz.arunangshu.jobportal.exchange.PostJobsRequest;
import xyz.arunangshu.jobportal.exchange.PostJobsResponse;
import xyz.arunangshu.jobportal.model.JobEntity;
import xyz.arunangshu.jobportal.repository.JobRepository;

@Service
public class JobsServiceImpl implements JobsService {

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private JobDAL jobDAL;

  @Override
  public GetJobsResponse getJobs(Optional<String> location, Optional<List<String>> skills) {
    return new GetJobsResponse(jobDAL.filterJobs(location, skills));
  }

  @Override
  public PostJobsResponse addJob(PostJobsRequest postJobsRequest, LocalDate currentDate) {
    JobEntity jobEntity = new JobEntity(postJobsRequest.getJobTitle(),
        postJobsRequest.getJobDescription(), postJobsRequest.getCompany(),
        postJobsRequest.getLocation(), postJobsRequest.getSkills(),
        currentDate, postJobsRequest.getExpiresAfter());

    String jobId = jobRepository.save(jobEntity).getId();

    return new PostJobsResponse("SUCCESS", "Job posted successfully", jobId);
  }
}
