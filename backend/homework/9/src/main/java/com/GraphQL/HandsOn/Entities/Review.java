package com.GraphQL.HandsOn.Entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    String comment;
    int rating;
}