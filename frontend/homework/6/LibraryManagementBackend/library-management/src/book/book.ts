import { IsBoolean, IsEnum, IsInt, IsNumber, IsOptional, IsString, Max, Min } from "class-validator";
import { Genre } from "./genre.enum";

export class CreateBook{
    @IsInt()
    id : Number

    @IsString()
    title : string

    @IsString()
    author : string

    @IsEnum(Genre)
    genre : Genre

    @IsInt()
    year : Number

    @IsInt()
    pages : Number

    @Min(0)
    @Max(5)
    @IsNumber({maxDecimalPlaces : 1})
    rating : Number

    @IsBoolean()
    available : Boolean

    @IsOptional()
    @IsString()
    description : string
}