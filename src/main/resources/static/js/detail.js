(() => {
  const jQuery = window.jQuery; // jQuery를 별도의 변수에 할당
  const board = document.getElementById('board');
  const editBoardBtn = document.getElementById('editBoardBtn');
  const deleteBoardBtn = document.getElementById('deleteBoardBtn');
  const modal = document.getElementById('modal');
  const editBoardModal = document.getElementById('editBoardModal');
  const closeBtns = document.querySelectorAll('.close-btn');
  const cardDeleteBtn = document.getElementById('delete-card-btn');
  const boardTitle = document.getElementById('board-title');
  const boardDescription = document.getElementById('board-description');
  const boardNameInput = document.getElementById('board-name-input');
  const boardDescriptionInput = document.getElementById('board-description-input');
  let currentEditingCard = null;

  const urlPath = window.location.pathname;
  const boardId = urlPath.substring(urlPath.lastIndexOf('/') + 1, urlPath.length);

  function initDraggables(draggables) {
    draggables.forEach(el => {
      el.addEventListener('dragstart', (e) => {
        e.stopPropagation();
        el.classList.add('dragging');
      });

      el.addEventListener('dragend', (e) => {
        e.stopPropagation();
        const deck = el.closest('.deck');
        const deckId = deck.getAttribute('data-id');
        const draggableId = el.getAttribute('data-id');
        const deckIndex = Array.from(board.children).indexOf(deck);
        const draggableIndex = Array.from(deck.querySelectorAll('.draggable')).indexOf(el);

        console.log(`Draggable ID: ${draggableId} - Deck Index: ${deckIndex}, Draggable Index: ${draggableIndex}`);

        // 카드 위치 변경 요청
        updateCardPosition(draggableId, deckId, draggableIndex);

        el.classList.remove('dragging');
      });

      el.addEventListener('dragover', handleScroll);
    });
  }

  function initDecks(decks) {
    decks.forEach(deck => {
      deck.addEventListener('dragstart', () => {
        deck.classList.add('dragging');
      });

      deck.addEventListener('dragend', (e) => {
        if (!e.target.classList.contains('draggable')) {
          const deckId = deck.getAttribute('data-id');
          const deckIndex = Array.from(board.children).indexOf(deck);

          console.log(`Deck ID: ${deckId} - Deck Index: ${deckIndex}`);

          // 덱 위치 변경 요청
          updateDeckPosition(deckId, deckIndex);

          deck.classList.remove('dragging');
        }
      });

      deck.addEventListener('dragover', e => {
        e.preventDefault();
        const afterElement = getDragAfterElement(deck, e.clientY);
        const draggable = document.querySelector('.dragging.draggable');
        if (draggable) {
          if (afterElement == null) {
            deck.querySelector('ul').appendChild(draggable);
          } else {
            deck.querySelector('ul').insertBefore(draggable, afterElement);
          }
        }
      });

      deck.addEventListener('dragover', handleScroll);
    });

    board.addEventListener('dragover', e => {
      e.preventDefault();
      const afterDeck = getDragAfterDeck(e.clientX);
      const draggingDeck = document.querySelector('.deck.dragging');
      if (draggingDeck) {
        if (afterDeck == null) {
          board.appendChild(draggingDeck);
        } else {
          board.insertBefore(draggingDeck, afterDeck);
        }
      }
    });

    board.addEventListener('drop', () => {
      const draggingElements = document.querySelectorAll('.dragging');
      draggingElements.forEach(el => el.classList.remove('dragging'));
    });

    board.addEventListener('dragover', handleScroll);
  }

  function updateDeckPosition(deckId, position) {
    const data = {
      position: position
    };

    jQuery.ajax({
      type: 'PATCH',
      url: '/decks/' + deckId,
      data: JSON.stringify(data),
      contentType: 'application/json;charset=utf-8',
      success: function(result, status, xhr) {
        console.log('덱 위치 변경 성공');
      },
      error: function(xhr, status, error) {
        console.error('덱 위치 변경 실패:', error);
      }
    });
  }

  function updateCardPosition(cardId, deckId, index) {
    const data = {
      deckId: deckId,
      index: index
    };

    jQuery.ajax({
      type: 'PATCH',
      url: '/cards/' + cardId,
      data: JSON.stringify(data),
      contentType: 'application/json;charset=utf-8',
      success: function(result, status, xhr) {
        console.log('카드 위치 변경 성공');
      },
      error: function(xhr, status, error) {
        console.error('카드 위치 변경 실패:', error);
      }
    });
  }

  function handleScroll(event) {
    const scrollMargin = 50; // 가장자리에 가까운 픽셀 수
    const maxScrollSpeed = 20; // 최대 스크롤 속도

    const rect = event.target.getBoundingClientRect();
    const y = event.clientY;
    const x = event.clientX;

    if (y - rect.top < scrollMargin) {
      window.scrollBy(0, -maxScrollSpeed);
    } else if (rect.bottom - y < scrollMargin) {
      window.scrollBy(0, maxScrollSpeed);
    }

    if (x - rect.left < scrollMargin) {
      window.scrollBy(-maxScrollSpeed, 0);
    } else if (rect.right - x < scrollMargin) {
      window.scrollBy(maxScrollSpeed, 0);
    }
  }

  function getDragAfterElement(container, y) {
    const draggableElements = [...container.querySelectorAll('.draggable:not(.dragging)')];

    return draggableElements.reduce((closest, child) => {
      const box = child.getBoundingClientRect();
      const offset = y - box.top - box.height / 2;
      if (offset < 0 && offset > closest.offset) {
        return { offset: offset, element: child };
      } else {
        return closest;
      }
    }, { offset: Number.NEGATIVE_INFINITY }).element;
  }

  function getDragAfterDeck(x) {
    const deckElements = [...board.querySelectorAll('.deck:not(.dragging)')];

    return deckElements.reduce((closest, child) => {
      const box = child.getBoundingClientRect();
      const offset = x - box.left - box.width / 2;
      if (offset < 0 && offset > closest.offset) {
        return { offset: offset, element: child };
      } else {
        return closest;
      }
    }, { offset: Number.NEGATIVE_INFINITY }).element;
  }

  function attachCommentActions() {
    const editButtons = document.querySelectorAll('.edit-comment-btn');
    const deleteButtons = document.querySelectorAll('.delete-comment-btn');

    editButtons.forEach(button => {
      button.addEventListener('click', (e) => {
        const li = e.target.closest('li');
        const commentId = li.getAttribute('data-id');
        const newCommentText = prompt('Edit your comment:', li.querySelector('span').textContent);
        if (newCommentText) {
          li.querySelector('span').textContent = newCommentText;
        }
      });
    });

    deleteButtons.forEach(button => {
      button.addEventListener('click', (e) => {
        const li = e.target.closest('li');
        if (confirm('Are you sure you want to delete this comment?')) {
          li.remove();
        }
      });
    });
  }

  editBoardBtn.addEventListener('click', () => {
    boardNameInput.value = boardTitle.textContent;
    boardDescriptionInput.value = boardDescription.textContent;
    editBoardModal.style.display = 'block';
  });

  deleteBoardBtn.addEventListener('click', () => {
  });

  closeBtns.forEach(btn => btn.addEventListener('click', (e) => {
    e.target.closest('.modal').style.display = 'none';
  }));

  cardDeleteBtn.addEventListener('click', () => {
    if (currentEditingCard && confirm("Are you sure you want to delete this card?")) {
      currentEditingCard.parentNode.removeChild(currentEditingCard);
      modal.style.display = 'none';
    }
  });

  window.addEventListener('click', (event) => {
    if (event.target.classList.contains('modal')) {
      event.target.style.display = 'none';
    }
  });

  initDraggables(document.querySelectorAll('li.draggable'));
  initDecks(document.querySelectorAll('.deck'));
})();