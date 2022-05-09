package bssm.spring.board.service;

import bssm.spring.board.domain.Board;
import bssm.spring.board.dto.BoardRequestDto;
import bssm.spring.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private static final int BLOCK_PAGE_NUM_COUNT = 5;
    private static final int PAGE_POST_COUNT = 4;

    @Transactional
    @Override
    public void savePost(BoardRequestDto boardDto){
        boardRepository.save(boardDto.ToEntity());
    }

    @Transactional
    @Override
    public List<BoardRequestDto> getBoardList(Integer pageNum){

        Page<Board> page = boardRepository
                .findAll(PageRequest
                        .of(pageNum - 1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC, "id")));

//        List<Board> all = boardRepository.findAll();
        List<Board> boards = page.getContent();
        List<BoardRequestDto> boardDtoList = new ArrayList<>();

        for(Board board : boards){
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

    @Override
    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        // 총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 수를 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        // 현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum <= 3) ? 1 : curPageNum - 2;

        for(int val = curPageNum, i = 0; val <= blockLastPageNum; val ++, i ++){
            pageList[i] = val;
        }

        return pageList;
    }


    @Transactional
    @Override
    public double getBoardCount() {
        return boardRepository.count();
    }

}
