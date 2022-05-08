package bssm.spring.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardResponseDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime localDateTime;

    @Builder
    public BoardResponseDto(String writer, String title, String content, LocalDateTime localDateTime) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.localDateTime = localDateTime;
    }
}
