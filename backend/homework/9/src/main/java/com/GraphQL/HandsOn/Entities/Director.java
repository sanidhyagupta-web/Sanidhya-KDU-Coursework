package com.GraphQL.HandsOn.Entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Director {
    private String id;
    private String name;
    private int totalAwards;
}
