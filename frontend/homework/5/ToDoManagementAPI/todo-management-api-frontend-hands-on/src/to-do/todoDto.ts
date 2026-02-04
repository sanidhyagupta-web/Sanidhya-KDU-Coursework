import {  IsOptional, IsString, Max, Min } from "class-validator";


export class TodoDto{

    @IsString()
    @Length(3, 100)
    title : string
    
    @IsOptional()
    @IsString()
    @MaxLength(500)
    description?:string
    
}
