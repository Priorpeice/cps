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
import server.cps.board.dto.BoardResponseDto;
import server.cps.board.dto.BoardSerachRequestDTO;
import server.cps.board.mapper.BoardMapper;
import server.cps.entity.Board;
import server.cps.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {
    private final BoardDao boardDao;

    public BoardResponseDto saveBoard(BoardRequestDto boardRequestDto , Member member) {
        Board board =boardRequestDto.toEntity(member);
        Board saveBoard = boardDao.save(board);
        return BoardResponseDto.builder()
                .boardId(saveBoard.getId().toString())
                .title(saveBoard.getTitle())
                .content(saveBoard.getContent())
                .memberId(saveBoard.getMember().getId().toString())
                .build();
    }
    public Board findBoard(Long id){

        return boardDao.findById(id);
    }
    // 모든 게시판 조회
    public List<BoardDto> showBoardAll() {
        return boardDao.findAllBoards();
    }
    public Board updateBoard(Long id, BoardRequestDto boardRequestDto)
    {
        Board board=boardDao.findById(id);
        board.update(boardRequestDto.getTitle(),boardRequestDto.getContent());
        return board;
    }
    public List<BoardDto> searchBoards(Pageable pageable,BoardSerachRequestDTO boardSerachRequestDTO){
        Page<Board> searchs = boardDao.search(pageable, boardSerachRequestDTO.getTitle());
        List<BoardDto> boardDtoList = searchs.getContent()
                .stream()
                .map(BoardMapper::toDto) // BoardDto로의 매핑 메서드 호출
                .collect(Collectors.toList());
        return boardDtoList;
    }

    // 특정 ID에 해당하는 게시판 삭제
    public void deleteBoard(Long id) {
        boardDao.deleteById(id);
    }

    // 제목으로 게시판 검색


    @Override
    public  List<BoardDto> findAllBoards(Pageable pageable) {

        Page<Board> boards = boardDao.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
        List<BoardDto> boardDtoList = boards.getContent()
                .stream()
                .map(BoardMapper::toDto) // BoardDto로의 매핑 메서드 호출
                .collect(Collectors.toList());
        return boardDtoList;
    }
}
