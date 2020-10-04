package xyz.arunangshu.jobportal.exchange;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusMessageResponse {
  private String status;
  private String message;
}
