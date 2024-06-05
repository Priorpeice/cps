package server.cps.example.service;

import server.cps.entity.Example;
import server.cps.entity.Problem;

public interface ExampleService {
    Example save(Example example);
    Example findExample(Long id);
    void deleteExample(Long id);

    void saveFile(Problem problem);
}
