package xyz.arunangshu.jobportal.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.arunangshu.jobportal.exchange.JwtResponse;
import xyz.arunangshu.jobportal.exchange.PostLoginRequest;
import xyz.arunangshu.jobportal.exchange.PostSignupRequest;
import xyz.arunangshu.jobportal.exchange.StatusMessageResponse;
import xyz.arunangshu.jobportal.model.Role;
import xyz.arunangshu.jobportal.model.RoleEntity;
import xyz.arunangshu.jobportal.model.UserEntity;
import xyz.arunangshu.jobportal.repository.RoleRepository;
import xyz.arunangshu.jobportal.repository.UserRepository;
import xyz.arunangshu.jobportal.security.jwt.JwtUtils;
import xyz.arunangshu.jobportal.security.service.UserDetailsImpl;
import xyz.arunangshu.jobportal.swagger.SwaggerConfig;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(AuthController.AUTH_API_ENDPOINT)
@Api(tags = {SwaggerConfig.AUTH_TAG})
public class AuthController {

  public static final String AUTH_API_ENDPOINT = "/auth";

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping(value = "/signin", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Sign In")
  public ResponseEntity<?> signin(
      @Valid @RequestBody PostLoginRequest postLoginRequest) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(postLoginRequest.getUsername(),
            postLoginRequest.getPassword())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
        userDetails.getEmail(), roles));
  }

  @PostMapping(value = "/signup", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  @ApiOperation(value = "Sign Up")
  public ResponseEntity<?> signup(@Valid @RequestBody PostSignupRequest postSignupRequest) {
    if (userRepository.existsByUsername(postSignupRequest.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new StatusMessageResponse("FAILED", "Username is already taken!"));
    }

    if (userRepository.existsByEmail(postSignupRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(new StatusMessageResponse("FAILED", "Email already in use!"));
    }

    UserEntity userEntity = new UserEntity(postSignupRequest.getEmail(),
        postSignupRequest.getUsername(), encoder.encode(postSignupRequest.getPassword()));

    Set<String> requestedRoles = postSignupRequest.getRoles();
    Set<RoleEntity> roles = new HashSet<>();

    if (requestedRoles == null) {
      RoleEntity userRole = roleRepository.findByName(Role.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Role is not found."));
      roles.add(userRole);
    } else {
      requestedRoles.forEach(role -> {
        switch (role) {
          case "admin":
            RoleEntity adminRole = roleRepository.findByName(Role.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(adminRole);
            break;

          case "recruiter":
            RoleEntity recruiterRole = roleRepository.findByName(Role.ROLE_RECRUITER)
                .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(recruiterRole);
            break;

          default:
            RoleEntity userRole = roleRepository.findByName(Role.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role is not found."));
            roles.add(userRole);
        }
      });
    }

    userEntity.setRoles(roles);
    userRepository.save(userEntity);

    return ResponseEntity.ok(
        new StatusMessageResponse("SUCCESS", "User registered successfully!"));
  }
}
