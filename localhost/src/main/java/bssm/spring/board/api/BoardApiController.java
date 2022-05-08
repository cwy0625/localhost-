package bssm.spring.board.api;

import bssm.spring.board.domain.Board;
import bssm.spring.board.dto.BoardRequestDto;
import bssm.spring.board.dto.BoardResponseDto;
import bssm.spring.board.repository.BoardRepository;
import bssm.spring.board.service.BoardServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardServiceImpl boardService;
    private final BoardRepository boardRepository;

    @PostMapping("/api/post")
    public BoardResponseDto saveMemberV2(@RequestBody @Valid BoardRequestDto request) {

        boardService.savePost(request);

        return new BoardResponseDto(
                request.ToEntity().getTitle(),
                request.ToEntity().getWriter(),
                request.ToEntity().getContent(),
                request.ToEntity().getLocalDateTime());
    }

    @PutMapping("/api/post/{id}")
    public BoardResponseDto updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid BoardRequestDto request) {

        boardService.update(id, request);
        Optional<Board> findPost = boardRepository.findById(id);
        Board board = findPost.get();

        return new BoardResponseDto(
                board.getTitle(),
                board.getWriter(),
                board.getContent(),
                board.getLocalDateTime());
    }

    @GetMapping("/api/board/posts")
    public List<BoardRequestDto> findPosts(){
        List<Board> findAll = boardRepository.findAll();
        List<BoardRequestDto> allPost = new ArrayList<>();

        for(Board board : findAll){
            BoardRequestDto build = BoardRequestDto.builder()
                    .content(board.getContent())
                    .localDateTime(board.getLocalDateTime())
                    .writer(board.getWriter())
                    .title(board.getTitle())
                    .build();

            allPost.add(build);
        }

        return allPost;
    }

    @DeleteMapping("/api/post/delete/{id}")
    public void delete(@PathVariable("id") Long id){
        boardService.deletePost(id);
    }

}
