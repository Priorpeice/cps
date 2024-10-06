package server.cps.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import server.cps.board.dao.BoardDao;
import server.cps.board.dto.BoardDto;
import server.cps.board.dto.BoardRequestDto;
import server.cps.board.dto.BoardSerachRequestDTO;
import server.cps.entity.Board;
import server.cps.entity.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardDao boardDao;

    public Board saveBoard(BoardRequestDto boardRequestDto , Member member) {
        Board board =boardRequestDto.toEntity(member);
        return boardDao.save(board);

    }
    public Board findBoard(Long id){

        return boardDao.findById(id);
    }
    // 모든 게시판 조회

    public Board updateBoard(Long id, BoardRequestDto boardRequestDto)
    {
        Board board=boardDao.findById(id);
        board.update(boardRequestDto.getTitle(),boardRequestDto.getContent());
        return board;
    }
    public  Page<Board>  searchBoards(Pageable pageable,BoardSerachRequestDTO boardSerachRequestDTO){
     return  boardDao.search(pageable, boardSerachRequestDTO.getTitle());
    }

    // 특정 ID에 해당하는 게시판 삭제
    public void deleteBoard(Long id) {
        boardDao.deleteById(id);
    }

    // 제목으로 게시판 검색


    @Override
    public Page<Board>findAllBoards(Pageable pageable) {

       return boardDao.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

    }
}
