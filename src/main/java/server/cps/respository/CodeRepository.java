package server.cps.respository;

import server.cps.dto.compile.CompileRequestDTO;
import server.cps.dto.problem.ProblemRequstDTO;

import java.util.List;

public interface CodeRepository<T> {
    void save (CompileRequestDTO compileRequestDTO);
    List <T> readFilesFromFolder(ProblemRequstDTO problemRequstDTO) ;
}
