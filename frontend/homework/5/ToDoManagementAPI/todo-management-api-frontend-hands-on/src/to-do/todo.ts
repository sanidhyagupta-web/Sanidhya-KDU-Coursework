import { Type } from "class-transformer";
import { IsBoolean, IsDate, IsOptional, IsString, IsUUID, Max, Min } from "class-validator";

export class Todo{

    @IsUUID()
    id : string

    @IsString()
    @Length(3, 100)
    title : string

    @IsOptional()
    @IsString()
    @MaxLength(500)
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
