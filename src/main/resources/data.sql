INSERT INTO user (created_at, updated_at, name, username, auth_role, user_status, password, refresh_token)
VALUES (now(), now(), 'test1', 'testId1', 'ROLE_USER', 'ACTIVATED', '$2a$12$8tOBHhPyKyOtT1h7CZaI8uEVWgk1hnZxZdo2WMtefHgki9L/p62Wi', null),
       (now(), now(), 'test2', 'testId2', 'ROLE_USER', 'ACTIVATED', '$2a$12$8tOBHhPyKyOtT1h7CZaI8uEVWgk1hnZxZdo2WMtefHgki9L/p62Wi', null),
       (now(), now(), 'test3', 'testId3', 'ROLE_USER', 'ACTIVATED', '$2a$12$8tOBHhPyKyOtT1h7CZaI8uEVWgk1hnZxZdo2WMtefHgki9L/p62Wi', null),
       (now(), now(), 'test4', 'testId4', 'ROLE_USER', 'ACTIVATED', '$2a$12$8tOBHhPyKyOtT1h7CZaI8uEVWgk1hnZxZdo2WMtefHgki9L/p62Wi', null),
       (now(), now(), 'test5', 'testId5', 'ROLE_USER', 'ACTIVATED', '$2a$12$8tOBHhPyKyOtT1h7CZaI8uEVWgk1hnZxZdo2WMtefHgki9L/p62Wi', null);
-- 비밀번호 == password123@

INSERT INTO board (created_at, updated_at, head_deck_id, description, title)
VALUES (now(), now(), null, 'board1 description', 'board1');

INSERT INTO board_member (board_id, user_id, board_role)
VALUES (1, 1, 'MANAGER'),
       (1, 2, 'MANAGER'),
       (1, 3, 'USER'),
       (1, 4, 'USER');

INSERT INTO deck (created_at, updated_at, board_id, head_card_id, next_id, title)
VALUES (now(), now(), 1, null, null, 'deck1'),
       (now(), now(), 1, null, null, 'deck2'),
       (now(), now(), 1, null, null, 'deck3');

INSERT INTO card (created_at, updated_at, start_date, due_date, deck_id, next_id, user_id, description, title)
VALUES (now(), now(),now(), DATE_ADD(now(), INTERVAL 7 DAY), 1, null, 1, 'card1', 'card1'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 1, null, 1, 'card2', 'card2'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 1, null, 1, 'card3', 'card3'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 2, null, 1, 'card4', 'card4'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 2, null, 1, 'card5', 'card5'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 2, null, 1, 'card6', 'card6'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 3, null, 1, 'card7', 'card7'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 3, null, 1, 'card8', 'card8'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 3, null, 1, 'card9', 'card9'),
       (now(), now(), now(),  DATE_ADD(now(), INTERVAL 7 DAY), 1, null, 1, 'card10', 'card10');

INSERT INTO comment (created_at, updated_at, card_id, user_id, content)
VALUES (now(), now(), 1, 1, 'comment1'),
       (DATE_ADD(now(), INTERVAL 1 SECOND ), DATE_ADD(now(), INTERVAL 1 SECOND ), 2, 1, 'comment2'),
       (DATE_ADD(now(), INTERVAL 2 SECOND ), DATE_ADD(now(), INTERVAL 2 SECOND ), 3, 1, 'comment3'),
       (DATE_ADD(now(), INTERVAL 3 SECOND ), DATE_ADD(now(), INTERVAL 3 SECOND ), 4, 1, 'comment4'),
       (DATE_ADD(now(), INTERVAL 4 SECOND ), DATE_ADD(now(), INTERVAL 4 SECOND ), 5, 1, 'comment5'),
       (DATE_ADD(now(), INTERVAL 5 SECOND ), DATE_ADD(now(), INTERVAL 5 SECOND ), 1, 1, 'comment6'),
       (DATE_ADD(now(), INTERVAL 6 SECOND ), DATE_ADD(now(), INTERVAL 6 SECOND ), 2, 1, 'comment7'),
       (DATE_ADD(now(), INTERVAL 7 SECOND ), DATE_ADD(now(), INTERVAL 7 SECOND ), 3, 1, 'comment8'),
       (DATE_ADD(now(), INTERVAL 8 SECOND ), DATE_ADD(now(), INTERVAL 8 SECOND ), 4, 1, 'comment9'),
       (DATE_ADD(now(), INTERVAL 9 SECOND ), DATE_ADD(now(), INTERVAL 9 SECOND ), 5, 1, 'comment10'),
       (DATE_ADD(now(), INTERVAL 10 SECOND ), DATE_ADD(now(), INTERVAL 10 SECOND ), 1, 1, 'comment11'),
       (DATE_ADD(now(), INTERVAL 11 SECOND ), DATE_ADD(now(), INTERVAL 11 SECOND ), 2, 1, 'comment12'),
       (DATE_ADD(now(), INTERVAL 12 SECOND ), DATE_ADD(now(), INTERVAL 12 SECOND ), 3, 1, 'comment13'),
       (DATE_ADD(now(), INTERVAL 13 SECOND ), DATE_ADD(now(), INTERVAL 13 SECOND ), 4, 1, 'comment14'),
       (DATE_ADD(now(), INTERVAL 14 SECOND ), DATE_ADD(now(), INTERVAL 14 SECOND ), 5, 1, 'comment15'),
       (DATE_ADD(now(), INTERVAL 15 SECOND ), DATE_ADD(now(), INTERVAL 15 SECOND ), 1, 1, 'comment16'),
       (DATE_ADD(now(), INTERVAL 16 SECOND ), DATE_ADD(now(), INTERVAL 16 SECOND ), 1, 1, 'comment17'),
       (DATE_ADD(now(), INTERVAL 17 SECOND ), DATE_ADD(now(), INTERVAL 17 SECOND ), 1, 1, 'comment18'),
       (DATE_ADD(now(), INTERVAL 18 SECOND ), DATE_ADD(now(), INTERVAL 18 SECOND ), 1, 1, 'comment19');

-- board의 헤드 deck
UPDATE board SET head_deck_id = 1 WHERE id = 1;

-- deck의 헤드 card
UPDATE deck SET head_card_id = 1 WHERE id = 1;
UPDATE deck SET head_card_id = 4 WHERE id = 2;
UPDATE deck SET head_card_id = 7 WHERE id = 3;
-- deck의 next_id
UPDATE deck SET next_id = 2 WHERE id = 1;
UPDATE deck SET next_id = 3 WHERE id = 2;

-- card의 next_id
UPDATE card SET next_id = 2 WHERE id = 1;
UPDATE card SET next_id = 3 WHERE id = 2;
UPDATE card SET next_id = 10 WHERE id = 3;
UPDATE card SET next_id = 5 WHERE id = 4;
UPDATE card SET next_id = 6 WHERE id = 5;
UPDATE card SET next_id = 8 WHERE id = 7;
UPDATE card SET next_id = 9 WHERE id = 8;