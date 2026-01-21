package com.GraphQL.HandsOn.Controller;

import com.GraphQL.HandsOn.Entities.Director;
import com.GraphQL.HandsOn.Entities.Movie;
import com.GraphQL.HandsOn.Service.DirectorService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class DirectorController {

    private final DirectorService directorService;

    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }

    @SchemaMapping(typeName = "Movie", field = "director")
    public ResponseEntity<Director> director(Movie movie) {
        return new ResponseEntity<>(DirectorService.findDirectorById(movie), HttpStatus.OK);
    }
}