# SkillRoute — Adaptive DSA Practice PoC

A repo-ready demonstration of an AI-native coding practice platform. It routes a learner using prerequisite mastery, presents a coding workspace, simulates test execution, provides progressive hints, and asks a conceptual understanding check before increasing mastery.

## What works

- Adaptive dashboard with next-question reasoning
- Topic mastery and concept-level signals
- Prerequisite map with locked and unlocked topics
- Responsive Java coding workspace
- Interactive run, hint, submit, and AI coach states
- Spring Boot endpoints for routing, paths, submissions, hints, and mastery updates
- Netlify-ready frontend and Docker-ready backend

## Quick demo

From the project folder, run:

    cd frontend
    python3 -m http.server 8888

Open http://localhost:8888, click Continue practice, then try Run code, Get hint, and Submit.

## Run the API

With Maven 3.9+ and Java 17:

    cd backend
    mvn spring-boot:run

Or with Docker:

    docker build -t skillroute-api backend
    docker run -p 8080:8080 skillroute-api

## API surface

| Method | Endpoint | Purpose |
|---|---|---|
| GET | /api/v1/learners/{id}/next | Route the next question |
| GET | /api/v1/learners/{id}/path | Return prerequisite graph state |
| POST | /api/v1/submissions | Judge attempt and update mastery |
| POST | /api/v1/hints | Generate a progressive hint |

## Production direction

The browser talks to a Spring API. The API owns routing and mastery, delegates code execution to Judge0, delegates qualitative feedback to an LLM, and persists the topic graph and learner evidence in PostgreSQL.

Suggested tables: topics, topic_prerequisites, questions, learner_mastery, submissions, and concept_evidence.

The demo mastery update is intentionally understandable: 75% prior mastery plus 25% latest performance. A production version can replace it with Bayesian Knowledge Tracing or Item Response Theory without changing the API contract.

Before production, add real Judge0 polling, AI output validation, authentication, PostgreSQL persistence, rate limiting, tests, and observability.
