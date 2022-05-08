package bssm.spring.board.service;

import bssm.spring.board.domain.Board;
import bssm.spring.board.dto.BoardDto;
import bssm.spring.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BoardService {

        public void savePost(BoardDto boardDto);

        public List<BoardDto> getBoardList();

        public BoardDto getPost(Long id);

        public void deletePost(Long id);

        public List<BoardDto> searchPosts(String keyword);

}
