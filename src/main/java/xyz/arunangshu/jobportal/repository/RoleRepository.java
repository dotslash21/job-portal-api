package xyz.arunangshu.jobportal.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.arunangshu.jobportal.model.Role;
import xyz.arunangshu.jobportal.model.RoleEntity;

@Repository
public interface RoleRepository extends MongoRepository<RoleEntity, String> {
  Optional<RoleEntity> findByName(Role name);
}
