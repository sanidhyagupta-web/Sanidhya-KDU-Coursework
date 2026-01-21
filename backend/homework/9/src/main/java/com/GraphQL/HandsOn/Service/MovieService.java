package com.GraphQL.HandsOn.Service;

import com.GraphQL.HandsOn.Entities.Movie;
import com.GraphQL.HandsOn.Entities.Review;
import com.GraphQL.HandsOn.Exceptions.ResourceNotFoundException;
import com.GraphQL.HandsOn.Repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findMovieById(String id) {
        Movie movie = movieRepository.findById(id);
        if (movie == null) {
            throw new ResourceNotFoundException("Movie not found with id: " + id);
        }
        return movie;
    }

    public Movie addReview(String movieId,String comment,Integer rating){
        Movie movie = findMovieById(movieId);
        if (movie == null) return null; // or throw an exception for GraphQL errors

        int r = rating == null ? 0 : rating;
        if (r < 1 || r > 5) {
            throw new IllegalArgumentException("rating must be between 1 and 5");
        }

        movie.getReviews().add(new Review(comment, r));
        return movie; // returns updated object immediately
    }

}
