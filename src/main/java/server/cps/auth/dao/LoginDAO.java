package server.cps.auth.dao;

import server.cps.entity.Login;

public interface LoginDAO {
    Login findBySeq(Long id);
    Login findByLoginId(String id);
    Login save(Login login);

}
