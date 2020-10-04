package xyz.arunangshu.jobportal.exchange;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSignupRequest {
  @NotBlank(message = "Username must not be blank")
  @Size(min = 3, max = 20, message = "Username must be of minimum 3 and maximum 20 characters long")
  private String username;

  @NotBlank(message = "Email must not be blank")
  @Size(max = 50, message = "Email must be of maximum 50 characters long")
  @Email
  private String email;

  private Set<String> roles;

  @NotBlank(message = "Password must not be blank")
  @Size(min = 8, max = 40, message = "Password must be of minimum 8 and maximum 40 characters long")
  private String password;
}
