// ----------Task 1 ---------------

function Task(title, priority) {
    this.id = Date.now();
    this.title = title;
    this.priority = priority;
    this.completed = false;
}

Task.prototype.markComplete = function(){
    this.completed = true
    return this
}

Task.prototype.updatePriority = function(newPriority){
    const validPriorities = ["low" , "medium" , "high"]

    if(!validPriorities.includes(newPriority)){
        throw new Error("Invalid Priority use low , medium or high")
    }

    this.priority = newPriority
    return this 
}

Task.prototype.getInfo = function(){
      let info = `Task: ${this.title} | Priority: ${this.priority} | Completed: ${this.completed}`;
      return info;
}

function PriorityTask(title, priority,dueDate){
    Task.call(this,title,priority)
    this.dueDate = dueDate || null
}

PriorityTask.prototype = Object.create(Task.prototype)

PriorityTask.prototype.constructor = PriorityTask 

PriorityTask.prototype.getInfo = function(){
      let info = `Task: ${this.title} | Priority: ${this.priority} | Completed: ${this.completed}`;
      if (this.dueDate)
         info += ` | Due: ${this.dueDate}`;
      return info;
}

Task.prototype.getAllTasksInfo = function(allTasks){
    return allTasks.map(task => this.getInfo.call(task))
}

const task1 = new Task("Java" , "medium")
task1.markComplete() 
     .updatePriority("high")
console.log(task1)


// --------------Task2 --------------------

function createTaskAsync(title, priority) {
  console.log("Creating tasks...");

  return new Promise((resolve) => {
    setTimeout(() => {
      console.log("Task created!");
      resolve(new Task(title, priority));
    }, 1000);
  });
}

function demonstrateEventLoop(){
    setTimeout(()=>{
        console.log(1)
    },2000)

    setTimeout(()=>{
        console.log(4)
    },4000)

    setTimeout(()=>{
        console.log(3)
    },6000)

    setTimeout(()=>{
        console.log(2)
    },8000)
}

async function createAndSaveTask(title, priority) {
  try {
    const task = await createTaskAsync(title, priority);

    const preparedTask = await createTaskAsync(
      `${title} (prepared)`,
      priority
    );

    console.log("Task created and saved successfully!");

    return task;
  } catch (error) {
    console.error("Error creating or saving task:", error);
    throw error; 
  }
}

async function createMultipleTasksAsync(taskDataArray) {
  console.log(`Creating ${taskDataArray.length} tasks...`);

  try {
    const taskPromises = taskDataArray.map(({ title, priority }) =>
      createTaskAsync(title, priority)
    );

    const tasks = await Promise.all(taskPromises);

    console.log("All tasks created!");
    return tasks;
  } catch (error) {
    console.error("Error creating multiple tasks:", error);
    throw error;
  }
}


createTaskAsync("Learn Event Loop", "high")
  .then(task => {
    console.log(task);
  });

console.log("This runs before the task is created");

demonstrateEventLoop()

createAndSaveTask("Write docs", "high")
  .then(task => {
    console.log("Saved task:", task);
  })
  .catch(err => {
    console.log("Handled by caller:", err.message);
  });

