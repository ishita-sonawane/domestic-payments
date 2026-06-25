Domestic Payments API- 
A production-grade REST API for secure domestic fund transfers, built during my internship at Hexaware Technologies Ltd (Jun–Jul 2025). The system handles the full payment lifecycle- authentication, validation, persistence, and observability, following a clean layered architecture with enterprise-grade security standards.

Tech Stack:
Language- Java 17
Framework- Spring Boot 3.5.3
Security- JWT + RSA (RS256), Spring Security 
Database- MySQL (AWS RDS) 
ORM- Spring Data JPA / Hibernate 
Testing- JUnit 5, Spring Boot Test 
Code Quality- SonarQube 
Observability- New Relic (APM) 
Frontend- React 
Build- Maven 

Architecture Overview:

The application follows a strict 'Controller → Service → Repository' layered architecture with centralized exception handling and stateless session management.

React Frontend
      ▼
JWTRequestFilter          ← validates RS256 token on every request
      ▼
DomesticPaymentController ← input format + business rule validation
      ▼
DomesticPaymentService    ← business logic, DTO → Entity mapping, SLF4J logging
      ▼
DomesticPaymentRepository ← Spring Data JPA (MySQL on AWS RDS)
      ▼
GlobalExceptionHandler    ← RFC-compliant structured error responses

Key Features:

Security:
- JWT with RSA asymmetric encryption (RS256)- tokens signed with a private key and verified via public key through an AWS-hosted validation endpoint
- Role-Based Access Control (RBAC)- roles extracted from JWT claims and injected into Spring Security context via SecurityContextHolder
- OWASP Top 10 compliance across all layers:
  - Broken Access Control- JWTRequestFilter guards every endpoint; unauthenticated requests receive 401 Unauthorized immediately
  - Injection- Spring Data JPA repositories eliminate raw SQL; @Pattern and @Valid annotations enforce field-level sanitization
  - Cryptographic Failures- RS256 asymmetric signing; no credentials stored in plaintext
  - Security Misconfiguration- CSRF disabled for stateless mode; CORS explicitly scoped to React origin; no default session creation
  - Security Logging & Monitoring- New Relic APM + SLF4J logging on all key flow transitions

Payment API:
- POST /api/domestic-payments- initiate a domestic fund transfer
- Request validation: beneficiary ID format, payment amount constraints (max ₹10,000), account number rules
- Payment status lifecycle: PENDING → SUCCESS / FAILURE
- Structured DomesticPaymentRequest DTO → DomesticPayment JPA entity mapping
- RFC-compliant ApiError response model for all failure cases

Database:
- MySQL hosted on AWS RDS 
- Schema enforces precision-based field constraints and regex validation at the DB level
- domestic_payments table with strict relational integrity

Observability & Quality:
- New Relic- APM metrics, distributed tracing, latency monitoring, real-time error visibility
- SonarQube- static analysis for code smells, duplications, and security vulnerabilities; zero critical defects in production
- JUnit 5- unit and integration tests covering edge cases, invalid inputs, and authentication failure scenarios using real JWTs and mocked payloads



Author:
Ishita Sonawane
B.Tech CS-IT (Cyber Security) | Symbiosis Skills and Professional University, Pune
[LinkedIn](https://linkedin.com/in/ishita-sonawane) · [GitHub](https://github.com/ishita-sonawane)

[PROJECT-REPORT.docx](https://github.com/user-attachments/files/21470034/PROJECT-REPORT.docx)
