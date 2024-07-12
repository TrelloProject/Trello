package com.sparta.trello.repersitory;

import com.sparta.trello.domain.exam.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardReporsitory extends JpaRepository<Board, Long> {
}
