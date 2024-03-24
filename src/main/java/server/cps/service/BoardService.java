package server.cps.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.cps.dto.board.BoardRequestDto;
import server.cps.entity.Board;
import server.cps.respository.problem.BoardRepository;

import java.util.List;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    @Autowired
    public BoardService(BoardRepository boardRepository)
    {
        this.boardRepository = boardRepository;
    }

    public Board save(BoardRequestDto boardRequestDto) {
        Board board =boardRequestDto.toEntity();
        return boardRepository.save(board);
    }

    // 모든 게시판 조회
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    // 특정 ID에 해당하는 게시판 삭제
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    // 제목으로 게시판 검색

}
