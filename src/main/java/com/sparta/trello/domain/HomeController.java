package com.sparta.trello.domain;

import com.sparta.trello.auth.UserDetailsImpl;
import com.sparta.trello.domain.board.dto.BoardResponseDto;
import com.sparta.trello.domain.board.service.BoardService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        Model model
    ) {
        List<BoardResponseDto> boards = boardService.getAllBoards(userDetails.getUser());
        model.addAttribute("boards", boards);
        return "home";
    }
}