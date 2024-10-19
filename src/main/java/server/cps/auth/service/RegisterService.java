package server.cps.auth.service;

import org.springframework.web.bind.annotation.RequestBody;
import server.cps.entity.Member;
import server.cps.member.dto.MemberRequestDTO;

import java.sql.SQLIntegrityConstraintViolationException;

public interface RegisterService {
    boolean checkDuplicateId(String id);
    Member signUp(@RequestBody MemberRequestDTO memberRequestDTO)throws SQLIntegrityConstraintViolationException;
}
