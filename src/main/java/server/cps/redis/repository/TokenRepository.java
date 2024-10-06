package server.cps.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.cps.redis.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<Token,String> {
    Optional<Token> findByRefreshToken(String rt);
    void deleteByRefreshToken(String rt);
    Optional<Token>findById(String id);


}
