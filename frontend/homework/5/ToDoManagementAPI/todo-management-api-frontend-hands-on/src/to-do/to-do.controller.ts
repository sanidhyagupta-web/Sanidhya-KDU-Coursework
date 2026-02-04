import { Body, Controller, Delete, Get, Param, Post } from '@nestjs/common';
import { ToDoService } from './to-do.service';
import { TodoDto } from './todoDto';

@Controller('to-do')
export class ToDoController {

    constructor(private readonly toDoService : ToDoService){}

    @Get()
    GetAllToDo(){
        return this.toDoService.getAllToDo()
    }

    @Get(':id')
    GetToDoById(@Param('id') id : string){
        return this.toDoService.getToDoById(id)
    }

    @Post()
    createToDo(@Body() user : TodoDto){
        return this.toDoService.createTodo(user)
    }

    @Delete(':id')
    deleteToDo(@Param('id') id:string){
        return this.toDoService.deleteToDo(id)
    }
}
