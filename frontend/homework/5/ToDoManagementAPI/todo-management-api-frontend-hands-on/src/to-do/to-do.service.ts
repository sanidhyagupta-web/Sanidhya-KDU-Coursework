import { Injectable } from '@nestjs/common';
import { TODO_LIST } from './to-do-datarepo';
import { TodoNotFoundException } from './to-do-not-found-exception';
import { TodoDto } from './todoDto';
import { randomUUID } from 'crypto';
import { Todo } from './todo';

@Injectable()
export class ToDoService {

    private toDoList: Todo[] = [...TODO_LIST];


    getAllToDo(){
        return this.toDoList;
    }

    getToDoById(id:string){
        const todo = this.toDoList.find(todo1 => todo1.id === id)
        if(!todo){
            throw new TodoNotFoundException(id)
        }
        return todo 
    }

  createTodo(dto: TodoDto):Todo {
    const now = new Date();

    const todo: Todo = {
      id: randomUUID(),
      title: dto.title,
      description: dto.description ,
      completed: false,
      createdAt: now,
      updatedAt: now,
    };

    this.toDoList.push(todo);
    return todo;
  }

    deleteToDo(id:string){
        const todo = this.getToDoById(id); 

        const index = this.toDoList.findIndex(t => t.id === id);
        this.toDoList.splice(index, 1);

        return todo;
    }
}
