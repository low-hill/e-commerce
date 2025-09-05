# eCommerce Project

This is a sample eCommerce application built with Spring Boot.


## Acknowledgements

This project is based on the concepts and examples from the book "[Modern API Development with Spring and Spring Boot](https://github.com/PacktPublishing/Modern-API-Development-with-Spring-and-Spring-Boot)" by Packt Publishing.

## ER 다이어그램

```mermaid
erDiagram
    %% --- Core Business Entities ---
    User {
        UUID id PK
        string username
        string email
        string user_status
    }
    Order {
        UUID id PK
        numeric total
        string status
        timestamp order_date
        UUID customer_id FK
        UUID address_id FK
    }
    Address {
        UUID id PK
        string street
        string city
        string country
        string pincode
    }

    %% --- Core Business Relationships ---
    User ||--o{ Order : "places"
    Order }o--|| Address : "shipped_to"
    User ||--o{ Address : "has"

    %% --- Other User-related Entities ---
    Card {
        UUID id PK
        string number
        string expires
    }
    Cart {
        UUID id PK
        UUID user_id FK
    }
    User ||--o{ Card : "has"
    User ||--|| Cart : "owns"
    Order }o--|| Card : "paid_with"
    Cart }o--o{ Item : "contains"


    %% --- Auxiliary Order-related Entities ---
    OrderItem {
      UUID id PK
      int quantity
      numeric unit_price
      UUID product_id FK
    }
    Payment {
        UUID id PK
        boolean authorized
        string message
    }
    Shipment {
        UUID id PK
        string carrier
        timestamp est_delivery_date
    }
    Authorization {
        UUID id PK
        boolean authorized
        string message
        UUID order_id FK
    }
    Order ||--|| Payment : "has"
    Order ||--|| Shipment : "has"
    Order ||--|| Authorization : "has"
    Order ||--o{ OrderItem: "includes"

    %% --- Product and Itemization ---
    Product {
      UUID id PK
      string name
      numeric price
      int count
    }
    Item {
      UUID id PK
      int quantity
      numeric unit_price
      UUID product_id FK
    }
    Tag {
      UUID id PK
      string name
    }
    
    Product ||--o{ Item : "is_base_for"
    Product }o--o{ Tag : "has"
    Product ||--o{ OrderItem : "is_base_for"
```

## Future Improvements

- **API Specification Enhancement:**
  - Add `examples` to API request and response schemas in `openapi.yaml` for better documentation and usability.
  - Define `securitySchemes` (e.g., JWT, OAuth2) in `openapi.yaml` to specify API authentication and authorization methods.


- **Code Refactoring:**
  - Refactor the `ItemRepository` to use `Optional<T>` for return types where an item may not be found, improving null safety.
  - Replace raw `DAO` patterns with Spring Data JPA repositories for more idiomatic and efficient data access.


- **Testing:**
  - Implement comprehensive integration tests for API endpoints to ensure reliability and correctness.
  - Add more unit tests for business logic in service layers.


- **Database:**
  - Create a `V2__` Flyway migration script to add new tables or modify existing ones as the application evolves.


- **CI/CD:**
  - Set up a CI/CD pipeline using GitHub Actions to automate building, testing, and deployment.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

