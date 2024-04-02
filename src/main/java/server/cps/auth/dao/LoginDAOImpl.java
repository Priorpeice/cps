package server.cps.auth.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.cps.auth.repository.LoginRepository;
import server.cps.entity.Login;
@Repository
@RequiredArgsConstructor
public class LoginDAOImpl implements LoginDAO{
    private final LoginRepository loginRepository;
    @Override
    public Login findBySeq(Long id) {
        return loginRepository.findBySeq(id).orElseThrow(()->new EntityNotFoundException());
    }
    @Override
    public Login findByLoginId(String id) {
        return loginRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
    }

    @Override
    public Login save(Login login) {
        return loginRepository.save(login);
    }
}
