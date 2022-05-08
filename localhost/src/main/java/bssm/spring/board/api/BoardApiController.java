package bssm.spring.board.api;

import bssm.spring.board.repository.BoardRepository;
import bssm.spring.board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardServiceImpl boardService;
    private final BoardRepository boardRepository;
}
