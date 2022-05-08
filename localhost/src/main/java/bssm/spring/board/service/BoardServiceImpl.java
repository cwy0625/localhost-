package bssm.spring.board.service;

import bssm.spring.board.domain.Board;
import bssm.spring.board.dto.BoardRequestDto;
import bssm.spring.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    @PersistenceContext
    private EntityManager em;

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public void savePost(BoardRequestDto boardDto){
        boardRepository.save(boardDto.ToEntity());
    }

    @Transactional
    @Override
    public List<BoardRequestDto> getBoardList(){
        List<Board> all = boardRepository.findAll();
        List<BoardRequestDto> boardDtoList = new ArrayList<>();

        for(Board board : all){
            BoardRequestDto boardDto = BoardRequestDto.builder()
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .localDateTime(board.getLocalDateTime())
                    .build();

            boardDtoList.add(boardDto);
        }

        return boardDtoList;
    }

    @Transactional
    @Override
    public BoardRequestDto getPost(Long id){
        Optional<Board> boardWrapper = boardRepository.findById(id);
        Board board = boardWrapper.get();

        return BoardRequestDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .localDateTime(board.getLocalDateTime())
                .build();
    }

    @Transactional
    @Override
    public void deletePost(Long id){
        boardRepository.deleteById(id);
    }

    @Transactional
    @Override
    public List<BoardRequestDto> searchPosts(String keyword){
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardRequestDto> boardList = new ArrayList<>();

        for(Board board : boards){
            BoardRequestDto build = BoardRequestDto.builder()
                    .title(board.getTitle())
                    .content(board.getContent())
                    .writer(board.getWriter())
                    .localDateTime(board.getLocalDateTime())
                    .build();

            boardList.add(build);
        }

        return boardList;
    }

    @Transactional
    @Override
    public void update(Long id, BoardRequestDto dto) {
        Optional<Board> byId = boardRepository.findById(id);
        Board board = byId.get();

        board.updateBoard(dto.getWriter(), dto.getTitle(), dto.getContent());
    }

}
