package xyz.arunangshu.jobportal.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

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

  /**
   * Verifies the credentials of a user and generates a JWT token.
   *
   * @param postLoginRequest The request object containing user credentials.
   * @return The JwtResponse object containing JWT token and user info.
   */
  @Override
  public JwtResponse signIn(PostLoginRequest postLoginRequest) {
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

    return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
        userDetails.getEmail(), roles);
  }

  /**
   * Adds a user to the database.
   *
   * @param postSignupRequest The request object containing user details and credentials.
   * @return The status object containing the result of the operation.
   */
  @Override
  public StatusMessageResponse signUp(PostSignupRequest postSignupRequest) {
    if (userRepository.existsByUsername(postSignupRequest.getUsername())) {
      return new StatusMessageResponse("FAILED", "Username is already taken!");
    }

    if (userRepository.existsByEmail(postSignupRequest.getEmail())) {
      return new StatusMessageResponse("FAILED", "Email already in use!");
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

    return new StatusMessageResponse("SUCCESS", "User registered successfully!");
  }
}
