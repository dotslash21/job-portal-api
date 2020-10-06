package xyz.arunangshu.jobportal.dal;

import java.util.List;
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
  public List<JobEntity> filterJobs(String location, List<String> skills) {
    Query query = new Query();
    if (location != null) {
      query.addCriteria(Criteria.where("location").is(location));
    }
    if (skills != null) {
      query.addCriteria(Criteria.where("skills").all(skills));
    }

    return mongoTemplate.find(query, JobEntity.class);
  }
}
