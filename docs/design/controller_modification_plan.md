# Controller and Service Design Plan

This document summarizes the recommended additions and modifications for the backend
application based on the provided database schema. It is intended to guide further
implementation of controllers, services and mappers.

## 1. Dependencies

The project currently uses Spring Boot **3.0.5** with Java **17**. Key
libraries include MyBatis 3.0.0, Spring Security, and Fastjson 2.0.25.
When adding new modules, ensure the versions are compatible with Spring Boot 3.
Additional dependencies (e.g., Lombok or Spring Data JPA) should match the
existing versions to avoid conflicts.

## 2. Existing Structure

The codebase already defines controllers for authentication (`AuthorController`)
and basic user retrieval (`UserController`). Services and mappers handle user
registration, login and password reset.

## 3. Recommended Controllers

Based on the ER design, implement controllers for the following modules:

1. **Campus & Canteen**
   - Endpoints to list campuses, canteens, and canteen types.
   - CRUD operations for administrators.
2. **Stall & Dish**
   - Listing and management of stalls and dishes within each canteen.
   - Endpoints for rating and limited-time specials.
3. **Blog & Content**
   - Post blogs, attach images, manage topics and tags.
   - Retrieve user blogs and display details including likes and comments.
4. **Review & Comment**
   - Submit reviews for canteens, stalls and dishes.
   - Nested comment support with pagination.
5. **Social Interactions**
   - Like, favorite and follow operations.
   - Messaging and notification controller stubs.
6. **User Incentives**
   - Endpoints for credit records, sign‑in, medals and vouchers.
7. **System Management**
   - Configuration, sensitive words, logs and announcements.
   - Admin-only routes protected via Spring Security roles.

## 4. Service and Mapper Updates

Each controller should delegate business logic to service classes.
Create corresponding service interfaces and implementations for the above
modules. Use MyBatis mapper interfaces to perform database operations.
Follow the existing pattern in `AuthorService` and `UserMapper`.

## 5. Security Considerations

- Utilize the existing `AuthorInterceptor` to inject `AccountUser` information
  into the session for authenticated requests.
- Define roles and permissions in accordance with the `tb_role` and
  `tb_permission` tables. Configure Spring Security to restrict admin endpoints.

## 6. Error Handling and Validation

- Continue using `RestBean` as the uniform API response wrapper.
- Apply `@Validated` annotations and specific parameter constraints as shown in
  `AuthorController` to validate incoming requests.

## 7. Incremental Implementation

1. Start with read‑only endpoints (e.g., listing campuses and canteens) to
   verify database connectivity.
2. Gradually introduce write operations and complex interactions such as likes
   and favorites.
3. Write unit tests under `src/test/java` to ensure new services function with
   Spring Boot 3.


## 8. Example Endpoints

Below are example REST endpoints for newly introduced modules. These follow the same `RestBean` response style as existing controllers.

### BlogController
- `GET /api/blog/latest` – List recent blogs for the homepage.
- `GET /api/blog/{id}` – Fetch a blog by ID including basic fields.

### ReviewController
- `GET /api/review/dish/{dishId}` – List reviews for a specific dish.
- `GET /api/review/{id}` – Retrieve single review details.

### CommentController
- `GET /api/comment/blog/{blogId}` – List top-level comments under a blog.
- `GET /api/comment/review/{reviewId}` – List top-level comments under a review.

These basic endpoints demonstrate read-only access. Additional POST or PUT routes can be added later for creating new blogs, reviews and comments with authentication checks.
