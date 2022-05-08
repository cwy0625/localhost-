package bssm.spring.board.dto;

import bssm.spring.board.domain.Board;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime localDateTime;

    @Builder
    public BoardDto(Long id, String writer, String title, String content, LocalDateTime localDateTime) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.localDateTime = localDateTime;
    }

    public Board ToEntity(){
        return Board.builder()
                .writer(this.writer)
                .title(this.title)
                .content(this.content)
                .localDateTime(this.localDateTime)
                .build();
    }
}
