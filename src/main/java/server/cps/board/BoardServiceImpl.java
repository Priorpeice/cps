package server.cps.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.cps.board.dao.BoardDao;
import server.cps.board.dto.BoardRequestDto;
import server.cps.board.dto.BoardSerachRequestDTO;
import server.cps.board.service.BoardService;
import server.cps.entity.Board;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardDao boardDao;

    public Board saveBoard(BoardRequestDto boardRequestDto) {
        Board board =boardRequestDto.toEntity();
        return boardDao.save(board);
    }
    public Board showBoard(Long id){
        return boardDao.findById(id);
    }
    // 모든 게시판 조회
    public List<Board> showBoardAll() {
        return boardDao.findAll();
    }
    public List<Board> searchBoards(BoardSerachRequestDTO boardSerachRequestDTO){
        return boardDao.search(boardSerachRequestDTO.getTitle());
    }

    // 특정 ID에 해당하는 게시판 삭제
    public void deleteBoard(Long id) {
        boardDao.deleteById(id);
    }

    // 제목으로 게시판 검색

}
