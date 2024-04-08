package server.cps.auth.dao;

import server.cps.redis.Token;

import java.util.Optional;

public interface TokenDAO {
    Optional<Token> findByRefreshToken(String rt);
    void delete(String id);
    void save(String id,String rt);

    Token findById(String id);

}
