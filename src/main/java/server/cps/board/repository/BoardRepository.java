package server.cps.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import server.cps.entity.Board;

import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Board save(Board board);
    Optional<Board> findById(Long id);
    @Query("SELECT DISTINCT b FROM Board b LEFT JOIN FETCH b.comments WHERE b.id = :id")
    Optional<Board> findByIdWithComments(@Param("id") Long id);

    @Query("SELECT b FROM Board b JOIN FETCH b.member m LEFT JOIN FETCH m.role LEFT JOIN FETCH m.login")
    Page<Board> findAllWithMemberFetch(Pageable pageable);
    void deleteById(Long id);

    @Query("SELECT b FROM Board b JOIN FETCH b.member m LEFT JOIN FETCH m.role LEFT JOIN FETCH m.login WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    Page<Board> findByTitleContainingIgnoreCase(Pageable pageable, @Param("title") String title);


}
