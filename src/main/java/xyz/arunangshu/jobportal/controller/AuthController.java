package xyz.arunangshu.jobportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.arunangshu.jobportal.exchange.JwtResponse;
import xyz.arunangshu.jobportal.exchange.PostLoginRequest;
import xyz.arunangshu.jobportal.exchange.PostSignupRequest;
import xyz.arunangshu.jobportal.exchange.StatusMessageResponse;
import xyz.arunangshu.jobportal.service.AuthService;
import xyz.arunangshu.jobportal.swagger.SwaggerConfig;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AuthController.AUTH_API_ENDPOINT)
@Log4j2
@Api(tags = {SwaggerConfig.AUTH_TAG})
public class AuthController {

  public static final String AUTH_API_ENDPOINT = "/auth";

  @Autowired
  AuthService authService;

  @PostMapping(value = "/signin", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Sign In")
  public ResponseEntity<?> signIn(
      @Valid @RequestBody PostLoginRequest postLoginRequest) {
    try {
      JwtResponse jwtResponse = authService.signIn(postLoginRequest);

      return ResponseEntity.ok(jwtResponse);
    } catch (Exception e) {
      log.error("Error signing in user", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Sign Up")
  public ResponseEntity<?> signUp(@Valid @RequestBody PostSignupRequest postSignupRequest) {
    try {
      StatusMessageResponse statusMessageResponse = authService.signUp(postSignupRequest);

      if (statusMessageResponse.getStatus().equals("SUCCESS")) {
        return ResponseEntity.ok(statusMessageResponse);
      } else {
        return ResponseEntity.badRequest().body(statusMessageResponse);
      }
    } catch (Exception e) {
      log.error("Error signing up user", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
