package com.GraphQL.HandsOn.Repository;

import com.GraphQL.HandsOn.Entities.Director;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DirectorRepository {
    public static  List<Director> directors = List.of(
            new Director("101", "Christopher Nolan", 34),
            new Director("102", "Quentin Tarantino", 48)
    );
}
