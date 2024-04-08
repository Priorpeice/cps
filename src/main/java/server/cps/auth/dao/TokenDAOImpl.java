package server.cps.auth.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.cps.auth.repository.TokenRepository;
import server.cps.redis.Token;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class TokenDAOImpl implements TokenDAO{
    private final TokenRepository tokenRepository;

    @Override
    public Optional<Token> findByRefreshToken(String rt) {
        return tokenRepository.findByRefreshToken(rt);
    }

    @Override
    public Token findById(String id) {
        return tokenRepository.findById(id).orElseThrow(()->new NoSuchElementException());
    }

    @Override
    public void delete(String rt) {
       tokenRepository.deleteByRefreshToken(rt);
    }

    @Override
    public void save(String id,String rt) {
       tokenRepository.save(new Token(id,rt));
    }
}
