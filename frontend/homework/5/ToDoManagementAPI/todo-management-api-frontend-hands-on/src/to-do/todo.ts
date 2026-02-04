import { Type } from "class-transformer";
import { IsBoolean, IsDate, IsOptional, IsString, IsUUID, Max, Min } from "class-validator";

export class Todo{

    @IsUUID()
    id : string

    @IsString()
    @Min(3)
    @Max(100)
    title : string

    @IsOptional()
    @IsString()
    @Max(500)
    description?:string

    @IsBoolean()
    completed : boolean = false

    @IsDate()
    @Type(()=>Date)
    createdAt : Date

    @IsDate()
    @Type(()=>Date)
    updatedAt : Date
}