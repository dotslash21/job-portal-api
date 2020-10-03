package xyz.arunangshu.jobportal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.arunangshu.jobportal.model.JobEntity;

@Repository
public interface JobRepository extends MongoRepository<JobEntity, String> {
}
