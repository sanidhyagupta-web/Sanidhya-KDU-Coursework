import {  IsOptional, IsString, Max, Min } from "class-validator";


export class TodoDto{

    @IsString()
    @Min(3)
    @Max(100)
    title : string
    
    @IsOptional()
    @IsString()
    @Max(500)
    description?:string
    
}