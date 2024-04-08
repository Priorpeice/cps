package server.cps.example.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.cps.entity.Example;
import server.cps.example.repository.ExampleRepository;

@Repository
@Transactional
@RequiredArgsConstructor
public class ExampleDAOImpl implements ExampleDAO{
    private final ExampleRepository exampleRepository;
    @Override
    public Example save(Example example) {
        return exampleRepository.save(example);
    }

    @Override
    public Example findById(Long id) {
        return exampleRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
    }

    @Override
    public void delete(Long id) {
       Example example = exampleRepository.findById(id).orElseThrow(()-> new EntityNotFoundException());
       exampleRepository.delete(example);
    }
}
