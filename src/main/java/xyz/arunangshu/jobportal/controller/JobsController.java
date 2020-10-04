package xyz.arunangshu.jobportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import xyz.arunangshu.jobportal.config.SwaggerConfig;
import xyz.arunangshu.jobportal.exchange.GetJobsResponse;
import xyz.arunangshu.jobportal.exchange.PostJobsRequest;
import xyz.arunangshu.jobportal.exchange.PostJobsResponse;
import xyz.arunangshu.jobportal.service.JobsService;

@Controller
@RequestMapping(JobsController.JOB_API_ENDPOINT)
@Api(tags = {SwaggerConfig.JOBS_TAG})
public class JobsController {

  public static final String JOB_API_ENDPOINT = "/jobs";

  @Autowired
  private JobsService jobsService;

  /**
   * Get list of jobs.
   *
   * @param location The location of the job.
   * @param skills   The skill requirements of the job.
   * @return The list of jobs.
   */
  @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Get list of jobs.", response = GetJobsResponse.class)
  public ResponseEntity<GetJobsResponse> getJobs(
      @RequestParam(required = false) Optional<String> location,
      @RequestParam(required = false) Optional<List<String>> skills) {
    GetJobsResponse getJobsResponse;
    try {
      getJobsResponse = jobsService
          .getJobs(location, skills);
    } catch (Exception e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error getting the job list", e);
    }

    return ResponseEntity.ok().body(getJobsResponse);
  }

  /**
   * Post a job.
   *
   * @param postJobsRequest The job information.
   * @return The operation status and job id if successful.
   */
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Post a job.", response = PostJobsResponse.class)
  public ResponseEntity<PostJobsResponse> addJob(
      @Valid @RequestBody PostJobsRequest postJobsRequest) {
    PostJobsResponse postJobsResponse;
    try {
      postJobsResponse = jobsService.addJob(postJobsRequest, LocalDate.now());
    } catch (Exception e) {
      throw new ResponseStatusException(
          HttpStatus.INTERNAL_SERVER_ERROR, "Error posting the job", e);
    }

    return ResponseEntity
        .ok()
        .body(postJobsResponse);
  }
}
