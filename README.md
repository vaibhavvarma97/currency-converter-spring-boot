# Currency Converter API - Spring Boot Project

This project is a **Spring Boot** application that fetches live currency exchange rates from an external API and stores them in a database for later use in currency conversion.

## Features

- Fetches exchange rates with a base currency (EUR) from an external API.
- Stores the fetched exchange rates in a database.
- Converts currency values based on the stored rates.
- Handles both `Integer` and `Double` types of exchange rates seamlessly.

## Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA** (for database interaction)
- **H2 Database** (or your choice of DB)
- **RestTemplate** (for consuming external API)
- **Maven** (for project management)

## How It Works

1. **Fetching Exchange Rates**:  
   The application makes an API request to fetch the latest exchange rates with `EUR` as the base currency. The response is parsed and stored in the database.

2. **Currency Conversion**:  
   Once the rates are stored, the application allows conversion of currencies using the stored exchange rates.

3. **Handling API Response**:  
   The API response contains exchange rates that might be of type `Integer` or `Double`. This is handled using type checking to ensure proper conversion of values.

## API Endpoints

- **`GET /api/exchange-rates`**: Fetches and stores exchange rates from the external API.
- **`POST /api/convert`**: Converts an amount from one currency to another based on stored rates.
  
   Example request body:
   ```json
   {
       "fromCurrency": "USD",
       "toCurrency": "INR",
       "amount": 100
   }

## Setup and Installation
   1. **Clone the repository:
      --git clone https://github.com/vaibhavvarma97/currency-converter-spring-boot.git cd currency-converter-spring-boot
   
   2. **Build the project using Maven:
      --mvn clean install

   3. **Run the application:
      --mvn spring-boot:run

   4. **Access the APIs via:
      --http://localhost:8080/api/exchange-rates
      --http://localhost:8080/api/convert   

 ## Future Improvements 
   1. **Adding user authentication for secure API access.
   2. **Implementing real-time exchange rate updates.
   3. **Support for additional currencies.
