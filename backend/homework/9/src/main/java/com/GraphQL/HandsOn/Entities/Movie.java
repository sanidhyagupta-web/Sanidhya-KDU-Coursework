package com.GraphQL.HandsOn.Entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Movie {
    private String id;
    private String title;
    private String genre;
    private String directorId;
    List<Review> reviews;
}