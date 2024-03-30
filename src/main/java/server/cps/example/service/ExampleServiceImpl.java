package server.cps.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.cps.entity.Example;
import server.cps.example.dao.ExampleDAO;

@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService{
    private final ExampleDAO exampleDAO;
    @Override
    public Example save(Example example) {
        return exampleDAO.save(example);
    }

    @Override
    public Example findExample(Long id) {
        return exampleDAO.findById(id);
    }

    @Override
    public void deleteExample(Long id) {
        exampleDAO.delete(id);
    }
}
