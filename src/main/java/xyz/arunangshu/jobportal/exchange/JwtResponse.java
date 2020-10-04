package xyz.arunangshu.jobportal.exchange;

import java.util.List;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtResponse {
  @NonNull
  private String token;

  @NonNull
  private String id;

  @NonNull
  private String username;

  @NonNull
  private String email;

  @NonNull
  private List<String> roles;

  private String type = "Bearer";
}
