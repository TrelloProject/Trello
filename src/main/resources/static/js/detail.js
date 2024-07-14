(() => {
  const $ = (select) => document.querySelectorAll(select);
  const board = document.getElementById('board');
  const addDeckBtn = document.getElementById('addDeckBtn');
  const editBoardBtn = document.getElementById('editBoardBtn');
  const deleteBoardBtn = document.getElementById('deleteBoardBtn');
  const modal = document.getElementById('modal');
  const editBoardModal = document.getElementById('editBoardModal');
  const modalTitle = document.getElementById('modal-title');
  const modalDescription = document.getElementById('modal-description');
  const commentList = document.getElementById('comment-list');
  const newComment = document.getElementById('new-comment');
  const addCommentBtn = document.getElementById('add-comment-btn');
  const closeBtns = document.querySelectorAll('.close-btn');
  const saveBoardBtn = document.getElementById('save-board-btn');
  const cardEditBtn = document.getElementById('card-edit-btn');
  const cardDeleteBtn = document.getElementById('card-delete-btn');
  const boardTitle = document.getElementById('board-title');
  const boardDescription = document.getElementById('board-description');
  const boardNameInput = document.getElementById('board-name-input');
  const boardDescriptionInput = document.getElementById('board-description-input');
  let currentEditingCard = null; // To track which card is being edited

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
        el.classList.remove('dragging');
      });

      el.addEventListener('click', () => {
        showCardDetails(el);
        currentEditingCard = el; // Set the current card being edited
      });
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

      const addCardBtn = deck.querySelector('.addCardBtn');
      addCardBtn.addEventListener('click', () => addCard(deck));

      const editBtn = deck.querySelector('.editBtn');
      editBtn.addEventListener('click', () => editDeck(deck));

      const deleteBtn = deck.querySelector('.deleteBtn');
      deleteBtn.addEventListener('click', () => deleteDeck(deck));
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
  }

  function addDeck() {
    const deckName = prompt('Enter the name of the new deck:');
    if (!deckName) {
      return; // If no name is provided, do not create the deck
    }

    const deckId = Date.now();
    const deck = document.createElement('div');
    deck.classList.add('deck');
    deck.draggable = true;
    deck.setAttribute('data-id', deckId);

    const header = document.createElement('div');
    header.classList.add('deck-header');
    header.innerHTML = `<div class="deck-menu"><button class="editBtn">수정</button><button class="deleteBtn">삭제</button></div><h2>${deckName}</h2>`;

    const ul = document.createElement('ul');
    ul.classList.add('card-list');

    const addCardBtn = document.createElement('button');
    addCardBtn.classList.add('addCardBtn');
    addCardBtn.textContent = 'Add Card';

    deck.appendChild(header);
    deck.appendChild(ul);
    deck.appendChild(addCardBtn);
    board.appendChild(deck);

    initDecks([deck]);

    // Ensure the new deck is visible by scrolling to the right
    board.scrollLeft = board.scrollWidth;
  }

  function addCard(deck) {
    const cardId = Date.now();
    const card = document.createElement('li');
    card.classList.add('draggable');
    card.draggable = true;
    card.setAttribute('data-id', cardId);
    card.innerHTML = '<div class="el">New Card</div>';

    const ul = deck.querySelector('ul');
    ul.appendChild(card);

    initDraggables([card]);

    // Adjust deck height and ensure it doesn't exceed max-height
    adjustDeckHeight(deck);
  }

  function adjustDeckHeight(deck) {
    const ul = deck.querySelector('.card-list');
    const maxHeight = parseInt(window.getComputedStyle(deck).maxHeight, 10);
    const deckPadding = parseInt(window.getComputedStyle(deck).paddingTop, 10) +
        parseInt(window.getComputedStyle(deck).paddingBottom, 10);
    const headerHeight = deck.querySelector('.deck-header').offsetHeight;
    const addCardBtnHeight = deck.querySelector('.addCardBtn').offsetHeight;

    ul.style.maxHeight = `${maxHeight - headerHeight - addCardBtnHeight - deckPadding}px`;
  }

  function editDeck(deck) {
    const deckTitle = deck.querySelector('h2');
    const newTitle = prompt('Enter new deck title:', deckTitle.textContent);
    if (newTitle) {
      deckTitle.textContent = newTitle;
    }
  }

  function deleteDeck(deck) {
    if (confirm('Are you sure you want to delete this deck?')) {
      deck.remove();
    }
  }

  function showCardDetails(card) {
    const cardTitle = card.querySelector('.el').textContent;
    const cardDescription = "This is a description for " + cardTitle; // Placeholder description
    const comments = [
      {id: 1, text: "This is a comment for " + cardTitle},
      {id: 2, text: "Another comment for " + cardTitle}
    ]; // Placeholder comments

    modalTitle.textContent = cardTitle;
    modalDescription.textContent = cardDescription;
    commentList.innerHTML = ''; // Clear existing comments

    comments.forEach(comment => {
      const li = document.createElement('li');
      li.setAttribute('data-id', comment.id);
      li.innerHTML = `
        <span>${comment.text}</span>
        <div class="comment-actions">
          <button class="edit-comment-btn">수정</button>
          <button class="delete-comment-btn">삭제</button>
        </div>`;
      commentList.appendChild(li);
    });

    modal.style.display = 'block';
    attachCommentActions();
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

  saveBoardBtn.addEventListener('click', () => {
    boardTitle.textContent = boardNameInput.value;
    boardDescription.textContent = boardDescriptionInput.value;
    editBoardModal.style.display = 'none';
  });

  cardEditBtn.addEventListener('click', () => {
    const newTitle = prompt("Edit card title:", modalTitle.textContent);
    if (newTitle !== null) {
      modalTitle.textContent = newTitle;
      if (currentEditingCard) currentEditingCard.querySelector('.el').textContent = newTitle;
    }

    const newDescription = prompt("Edit card description:", modalDescription.textContent);
    if (newDescription !== null) {
      modalDescription.textContent = newDescription;
    }

    const data = {
      title: newTitle,
      description: newDescription
    }
  });

  cardDeleteBtn.addEventListener('click', () => {
    if (currentEditingCard && confirm("Are you sure you want to delete this card?")) {
      currentEditingCard.parentNode.removeChild(currentEditingCard);
      modal.style.display = 'none'; // Close the modal after deleting the card
    }
  });

  addDeckBtn.addEventListener('click', addDeck);

  addCommentBtn.addEventListener('click', () => {
    const commentText = newComment.value.trim();
    if (commentText) {
      const commentId = Date.now();
      const li = document.createElement('li');
      li.setAttribute('data-id', commentId);
      li.innerHTML = `
        <span>${commentText}</span>
        <div class="comment-actions">
          <button class="edit-comment-btn">수정</button>
          <button class="delete-comment-btn">삭제</button>
        </div>`;
      commentList.appendChild(li);
      newComment.value = '';
      attachCommentActions();
    }
  });

  window.addEventListener('click', (event) => {
    if (event.target.classList.contains('modal')) {
      event.target.style.display = 'none';
    }
  });

  initDraggables($('li.draggable'));
  initDecks($('.deck'));
})();