package com.sparta.trello.domain.user.service;

import com.sparta.trello.domain.boardMember.entity.BoardMember;
import com.sparta.trello.domain.boardMember.entity.BoardRole;
import com.sparta.trello.domain.boardMember.repository.BoardMemberAdapter;
import com.sparta.trello.domain.user.adapter.UserAdapter;
import com.sparta.trello.domain.user.dto.GrantBoardRoleRequestDto;
import com.sparta.trello.domain.user.dto.SignupRequestDto;
import com.sparta.trello.domain.user.entity.User;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberCodeEnum;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberDetailCustomException;
import com.sparta.trello.exception.custom.boardMember.detail.BoardMemberNotFoundException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;
    private final BoardMemberAdapter boardMemberAdapter;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        userAdapter.validUserSignup(requestDto.getUsername());

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
        User savedUser = userAdapter.save(User.builder()
            .name(requestDto.getName())
            .username(requestDto.getUsername())
            .password(encodedPassword)
            .build());
    }

    @Transactional
    public void withdraw(User loginUser) {
        loginUser.withdrawUser();
        userAdapter.save(loginUser);
    }

    @Transactional
    public void grantRole(User loginUser, GrantBoardRoleRequestDto grantBoardRoleRequestDto) {
        // 프론트에서 ID값으로 넘겨줄 수 있을 듯
        User grantUser = userAdapter.findByUsername(grantBoardRoleRequestDto.getUsername());
        if (loginUser.getId().equals(grantUser.getId())) {
            throw new BoardMemberDetailCustomException(
                BoardMemberCodeEnum.CANNOT_GRANT_PERMISSION_TO_SELF);
        }
        long start = System.currentTimeMillis();
        List<BoardMember> twoBoardMember = boardMemberAdapter.findByBoardIdTwoBoardMember(
            grantBoardRoleRequestDto.getBoardId(),
            List.of(loginUser.getId(), grantUser.getId())
        );
        long end = System.currentTimeMillis();
        log.info("{} ms", (end - start));
        BoardMember manager = null, user = null;
        for (BoardMember boardMember : twoBoardMember) {
            if (boardMember.getUser().getId().equals(loginUser.getId())) {
                manager = boardMember;
            } else if (boardMember.getUser().getId().equals(grantUser.getId())) {
                user = boardMember;
            }
        }

        if (manager == null || user == null) {
            // 내용도 바꾸면 좋을 듯 권한이 없다고
            throw new BoardMemberNotFoundException(BoardMemberCodeEnum.BOARD_MEMBER_NOT_FOUND);
        }

        if (Objects.equals(manager.getBoardRole(), BoardRole.USER)) {
            throw new BoardMemberDetailCustomException(BoardMemberCodeEnum.BOARD_MEMBER_FORBIDDEN);
        }

        if (!Objects.equals(manager.getBoardRole(), BoardRole.MANAGER)) {
            throw new BoardMemberDetailCustomException(BoardMemberCodeEnum.BOARD_MEMBER_FORBIDDEN);
        }

        user.grantBoardManager();
    }
}