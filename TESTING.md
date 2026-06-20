# Testing Strategy and Documentation

The application uses JUnit 5, Mockito, and Spring Boot Test to ensure robustness across all required features.

## Test Strategy Choice: Hybrid Approach Explained

As required by the assignment, we must choose between full integration tests (`@SpringBootTest`) and slice tests (`@WebMvcTest`, `@DataJpaTest`). 
I chose a **consistent hybrid approach**:
1. **Controller Layer**: I used slice testing (`@WebMvcTest` with `@MockitoBean`) to purely isolate and test the web layer (HTTP statuses, routing, form validation) without loading the entire application context or database.
2. **Persistence Layer**: I used full integration testing (`@SpringBootTest` with an isolated H2 database configuration in `src/test/resources/application.properties`) to ensure that our repository interacts perfectly with the actual application context and JPA lifecycle. 

This approach ensures the web layer tests are blazing fast and isolated, while the database tests run against a robust, realistic application context safely configured for local execution.

## Test Coverage (Feature by Feature)

### 1. Validation Tests (`StaffRatingValidationTest`)
We directly tested the model constraints using the Jakarta Validation API to ensure the domain model is robust before data ever hits the web or persistence layers:
- **Missing Required Fields**: Verified that an empty or null name is correctly rejected.
- **Out-of-Range Score Rejected**: Verified that scores (clarity, niceness, knowledgeable) outside the `[1, 10]` range are rejected.
- **Invalid Email Rejected**: Verified that malformed email strings are rejected by the `@Email` constraint.

### 2. Web / Controller Tests (`StaffRatingControllerTest`)
Using `MockMvc`, we tested all CRUD web endpoints for correct HTTP statuses, model attributes, and redirects:
- **Read (GET)**: Verified that `GET /` returns `200 OK`, renders the `index` template, and includes the expected `ratings` model attribute.
- **Create (POST)**:
  - **Success Path**: Verified that submitting a valid form redirects appropriately (`3xx Redirection`) to the index page.
  - **Failure Path**: Verified that submitting an invalid form returns `200 OK` (no redirect) and re-renders the `create` form with field errors.
- **Update (POST)**: Verified that submitting an edit on an existing entry correctly updates it and redirects to the detail page.
- **Delete (POST)**: Verified that `POST`ing to the delete route issues a `3xx Redirection` back to the index.

### 3. Persistence Tests (`StaffRatingRepositoryTest`)
We tested the `StaffRatingRepository` against an embedded H2 database to ensure database correctness:
- **Save and Retrieve**: Verified that a `StaffRating` entity can be saved and subsequently retrieved by its ID, preserving all attributes correctly.
- **Delete**: Verified that invoking the repository delete method actually removes the entry from the database.

## How to Run Tests

To execute the entire test suite, simply run:
```bash
./mvnw test
```
The application test configuration is entirely self-contained and does not require PostgreSQL to be running on the host machine.
