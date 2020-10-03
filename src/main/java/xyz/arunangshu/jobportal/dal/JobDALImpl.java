package xyz.arunangshu.jobportal.dal;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import xyz.arunangshu.jobportal.model.JobEntity;

@Repository
public class JobDALImpl implements JobDAL {

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public List<JobEntity> filterJobs(Optional<String> location, Optional<List<String>> skills) {
    Query query = new Query();
    location.ifPresent(s -> query.addCriteria(Criteria.where("location").is(s)));
    skills.ifPresent(strings -> query.addCriteria(Criteria.where("skills").all(strings)));

    return mongoTemplate.find(query, JobEntity.class);
  }
}
