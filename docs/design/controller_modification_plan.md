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
- `POST /api/blog/add` – Create a new blog entry.

### ReviewController
- `GET /api/review/dish/{dishId}` – List reviews for a specific dish.
- `GET /api/review/{id}` – Retrieve single review details.

### CommentController
- `GET /api/comment/blog/{blogId}` – List top-level comments under a blog.
- `GET /api/comment/review/{reviewId}` – List top-level comments under a review.
- `POST /api/comment/add` – Submit a new comment.

These basic endpoints demonstrate read-only access. Additional POST or PUT routes can be added later for creating new blogs, reviews and comments with authentication checks.

### MessageController
- `GET /api/message/user/{userId}` – List the latest messages received by the user.

### TopicController
- `GET /api/topic/list` – List available topics.
- `GET /api/topic/{id}` – Fetch topic details.

### SignController
- `GET /api/sign/user/{userId}` – List a user's sign-in records in descending order by date.

### ConfigController
- `GET /api/config/all` – Retrieve all system configuration entries.
- `GET /api/config/{key}` – Fetch configuration value by key.

### NoticeController
- `GET /api/notice/latest` – List recently published notices.
- `GET /api/notice/{id}` – Fetch details for a single notice.

### VoucherController
- `GET /api/voucher/active` – List active vouchers that users can obtain.
- `GET /api/voucher/{id}` – Fetch voucher details by ID.

### UserVoucherController
- `GET /api/user/voucher/{userId}` – List vouchers owned by a user.

### MedalController
- `GET /api/medal/list` – List available medals in the system.
- `GET /api/medal/{id}` – Get a medal by ID.

### UserMedalController
- `GET /api/user/medal/{userId}` – List medals obtained by a user.

### ActivityController
- `GET /api/activity/ongoing` – List ongoing or upcoming activities.
- `GET /api/activity/{id}` – Fetch activity details.

### UserActivityController
- `GET /api/user/activity/{userId}` – List activity participation records for a user.

### LevelRuleController
- `GET /api/level/rules` – Retrieve all user level rules.

### FileController
- `GET /api/file/latest` – List recently uploaded files.
- `GET /api/file/{id}` – Retrieve a file record by ID.

### BannerController
- `GET /api/banner/active` – List active homepage banners.
- `GET /api/banner/{id}` – Fetch banner details.

### ImageCategoryController
- `GET /api/image-category/list` – List image categories.
- `GET /api/image-category/{id}` – Get category details.

### ImageController
- `GET /api/image/latest` – List recent system images.
- `GET /api/image/{id}` – Retrieve image info by ID.

### SensitiveWordController
- `GET /api/sensitive-word/list` – List all sensitive words.
- `GET /api/sensitive-word/{id}` – Fetch sensitive word details.

### LogController
- `GET /api/log/latest` – List recent system logs.
- `GET /api/log/{id}` – Get a log entry by ID.

### FeedbackController
- `GET /api/feedback/latest` – List user feedback records.
- `GET /api/feedback/{id}` – Retrieve feedback detail.

### ReportController
- `GET /api/report/latest` – List user reports awaiting handling.
- `GET /api/report/{id}` – Get report details.

### VersionController
- `GET /api/version/list` – List published app versions.
- `GET /api/version/{id}` – Fetch version detail by ID.

### RankingController
- `GET /api/ranking/type/{type}` – List top rankings by type.

### StatisticsController
- `GET /api/statistics/type/{type}/period/{period}` – Retrieve statistical data for a period.

### UserPreferenceController
- `GET /api/preference/user/{userId}` – Get stored preferences of a user.
### CanteenTypeController
- `GET /api/canteen-type/list` – List canteen types.
- `GET /api/canteen-type/{id}` – Get canteen type details.
### StallTypeController
- `GET /api/stall-type/list` – List stall types.
- `GET /api/stall-type/{id}` – Get stall type details.
