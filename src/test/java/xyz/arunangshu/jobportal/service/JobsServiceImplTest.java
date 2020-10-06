package xyz.arunangshu.jobportal.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import xyz.arunangshu.jobportal.JobPortalApplication;
import xyz.arunangshu.jobportal.dal.JobDAL;
import xyz.arunangshu.jobportal.exchange.GetJobsResponse;
import xyz.arunangshu.jobportal.exchange.PostJobsRequest;
import xyz.arunangshu.jobportal.exchange.StatusMessageResponse;
import xyz.arunangshu.jobportal.model.JobEntity;
import xyz.arunangshu.jobportal.repository.JobRepository;
import xyz.arunangshu.jobportal.utils.FixtureHelpers;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = {JobPortalApplication.class})
class JobsServiceImplTest {

  @MockBean
  protected JobRepository jobRepository;

  @MockBean
  protected JobDAL jobDAL;

  @InjectMocks
  protected JobsServiceImpl jobsService;

  protected ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void getJobs() throws Exception {
    String fixture = FixtureHelpers.fixture("dummy_jobs_entries.json");
    JobEntity[] jobEntities = objectMapper.readValue(fixture, JobEntity[].class);

    when(jobDAL.filterJobs(ArgumentMatchers.any(),
        ArgumentMatchers.any()))
        .thenReturn(Arrays.asList(jobEntities));

    GetJobsResponse getJobsResponse = jobsService
        .getJobs("Remote", Collections.singletonList("Java"));

    // Assert return object matches expected values.
    assertEquals(getJobsResponse.getJobs().size(), 2);
    assertEquals(getJobsResponse.getJobs().get(0), jobEntities[0]);
    assertEquals(getJobsResponse.getJobs().get(1), jobEntities[1]);
  }

  @Test
  void addJob() throws Exception {
    String fixture = FixtureHelpers.fixture("dummy_job_post.json");
    PostJobsRequest postJobsRequest = objectMapper.readValue(fixture, PostJobsRequest.class);
    LocalDate date = LocalDate.parse("2020-10-04");

    ArgumentCaptor<JobEntity> jobEntityArgumentCaptor = ArgumentCaptor.forClass(JobEntity.class);
    when(jobRepository.save(jobEntityArgumentCaptor.capture())).thenReturn(
        new JobEntity("1234", postJobsRequest.getJobTitle(), postJobsRequest.getJobDescription(),
            postJobsRequest.getCompany(), postJobsRequest.getLocation(),
            postJobsRequest.getSkills(), date, postJobsRequest.getExpiresAfter()));

    StatusMessageResponse statusMessageResponse = jobsService.addJob(postJobsRequest, date);

    // Check formed job entity to save is valid.
    JobEntity jobEntity = jobEntityArgumentCaptor.getValue();
    assertNull(jobEntity.getId());
    assertEquals(jobEntity.getJobTitle(), postJobsRequest.getJobTitle());
    assertEquals(jobEntity.getJobDescription(), postJobsRequest.getJobDescription());
    assertEquals(jobEntity.getCompany(), postJobsRequest.getCompany());
    assertEquals(jobEntity.getLocation(), postJobsRequest.getLocation());
    assertEquals(jobEntity.getSkills(), postJobsRequest.getSkills());
    assertEquals(jobEntity.getExpiresAfter(), postJobsRequest.getExpiresAfter());
    assertEquals(jobEntity.getDatePosted(), date);
    assertEquals(statusMessageResponse.getStatus(), "SUCCESS");
  }
}