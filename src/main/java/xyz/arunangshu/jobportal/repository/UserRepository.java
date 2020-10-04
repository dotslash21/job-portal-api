package xyz.arunangshu.jobportal.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.arunangshu.jobportal.model.UserEntity;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
  Optional<UserEntity> findByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByUsername(String username);
}
