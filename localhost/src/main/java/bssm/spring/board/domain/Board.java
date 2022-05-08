package bssm.spring.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(length = 15, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime localDateTime;

    @Builder
    public Board(String writer, String title, String content, LocalDateTime localDateTime) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.localDateTime = localDateTime;
    }

    public void updateBoard(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
