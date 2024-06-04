package server.cps.board.dao;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import server.cps.board.dto.BoardDto;
import server.cps.board.repository.BoardRepository;
import server.cps.entity.Board;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao {
    private final BoardRepository boardRepository;
    @Override
    public Board save(Board board) {
        return  boardRepository.save(board);
    }

    @Override
    public  Board findById(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(()->new EntityNotFoundException());
        return board;
    }

    @Override
    public List<BoardDto> findAllBoards() {
        return boardRepository.findAllBoards();
    }

    @Override
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public Page<Board> search(Pageable pageable,String title) {
        return boardRepository.findByTitleContainingIgnoreCase(pageable,title);
    }

    @Override
    public Page<Board> findAll(Pageable pageable) {
        return boardRepository.findAllWithMemberFetch(pageable);
    }
}
