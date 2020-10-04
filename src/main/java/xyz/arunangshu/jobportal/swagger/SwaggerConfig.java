package xyz.arunangshu.jobportal.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {

  public static final String AUTH_TAG = "auth";
  public static final String JOBS_TAG = "jobs";

  /**
   * Method to get Docket bean for Swagger 2 configuration.
   *
   * @return The springfox Docket instance containing Swagger 2 configuration.
   */
  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("xyz.arunangshu.jobportal"))
        .build()
        .tags(new Tag(JOBS_TAG, "Operations pertaining to adding or viewing jobs."))
        .apiInfo(metaData());
  }

  /**
   * Method to get ApiInfo instance containing the metadata about the API.
   *
   * @return
   */
  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .title("Job Portal API")
        .description("\"Spring Boot REST API for Job Portal\"")
        .version("0.0.1-SNAPSHOT")
        .license("Apache License Version 2.0")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
        .contact(new Contact("Arunangshu Biswas", "http://arunangshu.xyz/",
            "arunangshubsws@gmail.com"))
        .build();
  }

  @Override
  protected void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("swagger-ui.html")
        .addResourceLocations("classpath:/META-INF/resources/");

    registry.addResourceHandler("/webjars/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/");
  }
}
