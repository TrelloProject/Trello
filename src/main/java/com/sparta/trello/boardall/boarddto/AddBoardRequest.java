package com.sparta.trello.boardall.boarddto;

import com.sparta.trello.domain.exam.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddBoardRequest {
    private String title;

    private String description;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .description(description)
                .build();
    }
}
