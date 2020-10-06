package xyz.arunangshu.jobportal.service;

import xyz.arunangshu.jobportal.exchange.JwtResponse;
import xyz.arunangshu.jobportal.exchange.PostLoginRequest;
import xyz.arunangshu.jobportal.exchange.PostSignupRequest;
import xyz.arunangshu.jobportal.exchange.StatusMessageResponse;

public interface AuthService {

  /**
   * Verifies the credentials of a user and generates a JWT token.
   *
   * @param postLoginRequest The request object containing user credentials.
   * @return The JwtResponse object containing JWT token and user info.
   */
  JwtResponse signIn(PostLoginRequest postLoginRequest);

  /**
   * Adds a user to the database.
   *
   * @param postSignupRequest The request object containing user details and credentials.
   * @return The status of the operation.
   */
  StatusMessageResponse signUp(PostSignupRequest postSignupRequest);
}
