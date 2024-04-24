package ua.hudyma.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import ua.hudyma.entity.User;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {
}
