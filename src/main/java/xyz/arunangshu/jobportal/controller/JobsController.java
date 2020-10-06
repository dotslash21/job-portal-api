package xyz.arunangshu.jobportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.arunangshu.jobportal.exchange.GetJobsResponse;
import xyz.arunangshu.jobportal.exchange.PostJobsRequest;
import xyz.arunangshu.jobportal.exchange.PostJobsResponse;
import xyz.arunangshu.jobportal.exchange.StatusMessageResponse;
import xyz.arunangshu.jobportal.service.JobsService;
import xyz.arunangshu.jobportal.swagger.SwaggerConfig;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(JobsController.JOB_API_ENDPOINT)
@Log4j2
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
  @PreAuthorize("hasRole('USER') or hasRole('RECRUITER') or hasRole('ADMIN')")
  @ApiOperation(value = "Get list of jobs.", response = GetJobsResponse.class, authorizations = {
      @Authorization("Bearer Token")})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Job list retrieval successful", response = GetJobsResponse.class)
  })
  public ResponseEntity<?> getJobs(
      @RequestParam(required = false) Optional<String> location,
      @RequestParam(required = false) Optional<List<String>> skills) {
    try {
      GetJobsResponse getJobsResponse = jobsService
          .getJobs(location, skills);

      return ResponseEntity.ok().body(getJobsResponse);
    } catch (Exception e) {
      log.error("Error getting the jobs list", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Post a job.
   *
   * @param postJobsRequest The job information.
   * @return The operation status and job id if successful.
   */
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
  @ApiOperation(value = "Post a job.", response = PostJobsResponse.class, authorizations = {
      @Authorization("Bearer Token")})
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Job posted successfully", response = StatusMessageResponse.class)
  })
  public ResponseEntity<?> addJob(
      @Valid @RequestBody PostJobsRequest postJobsRequest) {
    try {
      StatusMessageResponse statusMessageResponse = jobsService
          .addJob(postJobsRequest, LocalDate.now());

      return ResponseEntity.ok().body(statusMessageResponse);
    } catch (Exception e) {
      log.error("Error posting the job", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
