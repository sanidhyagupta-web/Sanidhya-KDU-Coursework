Homework 9


In REST, data is typically split across multiple endpoints. For example, to show a movie along with its director and reviews, the client often needs to:


Fetch the movie:
GET /movies/1



Read directorId from the response (e.g., 5)



Fetch the director:
GET /directors/5



Potentially fetch reviews via yet another endpoint:
GET /movies/1/reviews



This leads to multiple HTTP round trips, extra client-side orchestration, and higher latency—especially noticeable on mobile networks or high-latency connections. This pattern is commonly called the N+1 / chained requests problem (even if it’s “just 2 calls” here, it grows quickly when rendering lists).



GraphQL avoids chained requests



With GraphQL, the client can request exactly the data it needs in one call, even if the data comes from different “entities” (Movie, Director, Reviews)
