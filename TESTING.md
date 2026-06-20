Testing

Controller layer
- Used slice testing with `@WebMvcTest` and `MockMvc`
- `StaffRatingService` mocked with `@MockBean`
- Covered:
  - `GET /` returns 200 and expected model attributes
  - `POST /create` success redirects
  - `POST /create` failure redisplays form with field errors
  - `POST /ratings/{id}/edit` success redirects
  - `POST /ratings/{id}/edit` failure redisplays form with field errors
  - `POST /ratings/{id}/delete` redirects and removes entry

Persistence layer
- Used slice testing with `@DataJpaTest`
- Covered:
  - saving and retrieving entries
  - delete removes the entry

Test coverage
- Validation:
  - invalid email rejected
  - out-of-range score rejected
  - missing required fields rejected
- Controller:
  - index, create, update, delete endpoints (happy + error paths)
- Persistence:
  - save/find/delete

How to run
- `./mvnw test`
