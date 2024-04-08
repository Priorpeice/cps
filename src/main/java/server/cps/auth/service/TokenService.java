package server.cps.auth.service;

public interface TokenService {
    void save(String id,String rt);
    void delete(String ac);
}
