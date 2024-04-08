package server.cps.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.cps.auth.dao.TokenDAO;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{
    private final TokenDAO tokenDAO;

    @Override
    public void save(String id,String rt) {
         tokenDAO.save(id,rt);
    }

    @Override
    public void delete(String ac) {
        tokenDAO.delete(ac);
    }
}
