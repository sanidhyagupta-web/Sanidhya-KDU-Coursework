package com.GraphQL.HandsOn.Service;

import com.GraphQL.HandsOn.Entities.Director;
import com.GraphQL.HandsOn.Entities.Movie;
import com.GraphQL.HandsOn.Exceptions.ResourceNotFoundException;
import com.GraphQL.HandsOn.Repository.DirectorRepository;
import org.springframework.stereotype.Service;

@Service
public class DirectorService {

    private static DirectorRepository directorRepository ;

    public DirectorService( DirectorRepository directorRepository) {
        DirectorService.directorRepository = directorRepository;
    }

    public static Director findDirectorById(Movie movie) {
        return DirectorRepository.directors.stream()
                .filter(d -> d.getId().equals(movie.getDirectorId()))
                .findFirst()
                .orElseThrow(()->new ResourceNotFoundException("Director Not Found"));
    }

}
