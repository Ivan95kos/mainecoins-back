package mainecoins.Repository;

import mainecoins.model.CustomUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomUserRepository extends MongoRepository<CustomUser, Long> {

    CustomUser findByEmail(String email);

    boolean existsByEmail(String email);

}
