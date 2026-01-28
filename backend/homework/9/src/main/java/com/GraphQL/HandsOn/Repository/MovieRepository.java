package com.GraphQL.HandsOn.Repository;

import com.GraphQL.HandsOn.Entities.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MovieRepository {

    private final Map<String, Movie> moviesById = new ConcurrentHashMap<>();

    public MovieRepository() {
        moviesById.put("1", new Movie("1", "Inception", "Sci-Fi", "101", new ArrayList<>()));
        moviesById.put("2", new Movie("2", "The Dark Knight", "Action", "101", new ArrayList<>()));
        moviesById.put("3", new Movie("3", "Pulp Fiction", "Crime", "102", new ArrayList<>()));
    }

    public Movie findById(String id) {
        return moviesById.get(id);
    }
}
