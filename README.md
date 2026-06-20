# Rate CS Teaching Staff

A Spring Boot CRUD web application that allows students to rate Computer Science teaching staff (TAs, Professors, Instructors, and other staff members).

## Project Overview

Features:
- Create, Read, Update, and Delete ratings for teaching staff.
- Polymorphic profile display depending on the role (e.g., "Teaching Assistant John", "Professor Smith").
- Beautiful, modern glassmorphism UI using custom CSS and Thymeleaf templates.
- Strict server-side validation using Jakarta Validation API.

## Tech Stack

- Java 17
- Spring Boot 3.x (Web, Data JPA, Validation)
- Thymeleaf
- PostgreSQL (Production/Local) / H2 (Testing)
- Maven

## How to Run Locally

1. **Clone the repository.**
2. **Setup PostgreSQL:** Create a local PostgreSQL database.
3. **Configure Environment Variables:**
   Set the following environment variables before running:
   ```bash
   export DB_URL=jdbc:postgresql://localhost:5432/your_db_name
   export DB_USER=your_username
   export DB_PASSWORD=your_password
   ```
4. **Run the Application:**
   ```bash
   ./mvnw spring-boot:run
   ```
5. **Access the App:** Open `http://localhost:8080` in your browser.

## Render Deployment

To deploy this application on Render:
1. Create a **PostgreSQL Database** on Render and copy its Database URL.
2. Create a **Web Service** on Render and connect this repository.
3. In the Render Web Service settings, set the Build Command to `./mvnw clean package -DskipTests` and Start Command to `java -jar target/staff-rating-0.0.1-SNAPSHOT.jar`.
4. Add the following Environment Variables in Render:
   - `DB_URL`: The JDBC URL for the Render PostgreSQL instance (e.g., `jdbc:postgresql://<host>/<dbname>`)
   - `DB_USER`: Your Render DB username
   - `DB_PASSWORD`: Your Render DB password
5. Deploy and visit the generated Render URL.

## Known Issues / Future Work

- Currently, there is no user authentication. Anyone can create, edit, or delete ratings.
- Pagination should be added to the index page for a large number of ratings.
- A search and filter feature by role or name would improve discoverability.
