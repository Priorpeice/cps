package server.cps.example.service;

import server.cps.entity.Example;

public interface ExampleService {
    Example save(Example example);
    Example findExample(Long id);
    void deleteExample(Long id);
}
