document.addEventListener('DOMContentLoaded', function() {
  const todoItemsContainer = document.getElementById('todoItems');
  const addTaskBtn = document.getElementById('addTaskBtn');
  const dateDisplay = document.getElementById('dateDisplay');
  let todoItems = [];

  function getCurrentDate() {
    const current = new Date();
    const weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    const day = weekday[current.getDay()];
    const longMonth = current.toLocaleString("en-us", { month: "long" });
    const date = `${day}, ${longMonth} ${current.getDate()}`;
    return date;
  }

  function renderTodoItems() {
    todoItemsContainer.innerHTML = '';
    if (todoItems.length === 0) {
      todoItemsContainer.innerHTML = '<p>No tasks found.</p>';
    } else {
      todoItems.forEach(todoItem => {
        const todoItemElement = document.createElement('div');
        todoItemElement.classList.add('todo-item');
        todoItemElement.innerHTML = `
          <input type='checkbox' ${todoItem.completed ? 'checked' : ''}>
          <input type='text' value='${todoItem.task}' data-id='${todoItem.id}'>
          <button class='delete-btn'>Delete</button>
        `;
        const checkbox = todoItemElement.querySelector('input[type="checkbox"]');
        const taskInput = todoItemElement.querySelector('input[type="text"]');
        const deleteBtn = todoItemElement.querySelector('.delete-btn');

        checkbox.addEventListener('change', function() {
          todoItem.completed = this.checked;
          updateTodoItem(todoItem);
        });

        taskInput.addEventListener('input', function() {
          todoItem.task = this.value;
          updateTodoItem(todoItem);
        });

        deleteBtn.addEventListener('click', function() {
          deleteTodoItem(todoItem.id);
        });

        todoItemsContainer.appendChild(todoItemElement);
      });
    }
  }

  function updateTodoItem(updatedItem) {
    fetch(`http://localhost:8080/api/todoItems/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(updatedItem)
    })
    .then(response => response.json())
    .then(data => {
      const index = todoItems.findIndex(item => item.id === data.id);
      todoItems[index] = data;
      renderTodoItems();
    })
    .catch(error => console.error('Error updating todo item:', error));
  }

  function deleteTodoItem(id) {
    fetch(`http://localhost:8080/api/todoItems/delete/${id}`, {
      method: 'DELETE'
    })
    .then(() => {
      todoItems = todoItems.filter(item => item.id !== id);
      renderTodoItems();
    })
    .catch(error => console.error('Error deleting todo item:', error));
  }

  addTaskBtn.addEventListener('click', function() {
    const newTodoItem = {
      task: '', // Set default values or handle input for task description
      completed: false // Set default values or handle input for completion status
    };

    fetch('http://localhost:8080/api/todoItems/add', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newTodoItem)
    })
    .then(response => response.json())
    .then(data => {
      todoItems.push(data);
      renderTodoItems();
    })
    .catch(error => console.error('Error adding new todo item:', error));
  });

  // Initial fetch for todo items
  fetch('http://localhost:8080/api/todoItems')
    .then(response => response.json())
    .then(data => {
      todoItems = data;
      renderTodoItems();
    })
    .catch(error => console.error('Error fetching todo items:', error));

  // Display current date
  dateDisplay.textContent = getCurrentDate();
});
