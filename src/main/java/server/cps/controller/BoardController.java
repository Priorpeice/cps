package server.cps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import server.cps.service.BoardService;

@RestController
@CrossOrigin
public class BoardController {
    @Autowired
    BoardService boardService;
}
