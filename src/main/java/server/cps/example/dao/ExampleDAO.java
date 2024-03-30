package server.cps.example.dao;

import server.cps.entity.Example;

public interface ExampleDAO {
    Example save(Example example);
    Example findById(Long id);

    void delete(Long id);
}
