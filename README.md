
## Student API – Spring Boot

This is a simple Student Management REST API built with Spring Boot.

## 🧠 Features

- CRUD operations for `Student` entity
- Pagination & filtering support
- Email uniqueness validation
- Proper HTTP status responses
- Redis-based caching (**only in `add-redis-support` branch**)

---

## 📂 Branches

### `main` (default)

> Clean Spring Boot API **without Redis** caching.

- Suitable for learning core REST, JPA, pagination, etc.
- Lightweight and fast
- No external cache dependency

---

### `add-redis-support`

> Advanced version with **Redis caching** using `@Cacheable`, `@CachePut`, and `@CacheEvict`.

- Requires Redis to be installed/running (local or cloud)
- Improves performance by caching student data
- Good for understanding Spring Cache abstraction

---

## ⚙️ Requirements

- Java 17+
- Maven or Gradle
- Redis (only for `add-redis-support` branch)

---

## 🔄 Switching Between Versions

Use Git to switch between branches:

```bash
# Clean version
git checkout main

# Caching-enabled version
git checkout add-redis-support

