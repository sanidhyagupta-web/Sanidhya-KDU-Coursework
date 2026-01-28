package com.GraphQL.HandsOn.Controller;


import com.GraphQL.HandsOn.Entities.Director;
import com.GraphQL.HandsOn.Entities.Movie;
import com.GraphQL.HandsOn.Entities.Review;
import com.GraphQL.HandsOn.Service.MovieService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @QueryMapping
    public ResponseEntity<Movie> findMovieById(@Argument String id) {
        return new ResponseEntity<>(movieService.findMovieById(id), HttpStatus.OK);
    }

    @MutationMapping
    public ResponseEntity<Movie> addReview(@Argument String movieId,
                           @Argument String comment,
                           @Argument Integer rating) {

        return new ResponseEntity<>(movieService.addReview(movieId,comment,rating),HttpStatus.CREATED);
    }

}
