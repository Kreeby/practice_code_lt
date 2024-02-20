# Practice Task Application

This Spring Boot application demonstrates a RESTful service to process and enrich trade data with product names from a CSV file.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 17
- Maven 3.6 or later
- Spring Boot 3.2.2

### Installing

1. **Clone the repository**

```bash
git clone https://github.com/yourrepository/practice.git
cd practice
```
2. **Build the project**
```bash
mvn clean install
```
3. **Run the application**
```bash
mvn spring-boot:run
```
The service will start and by default will be accessible at http://localhost:8080.

## Usage
To enrich trade data with product names, send a POST request to /api/v1/trades/with-product-names with a CSV file as multipart/form-data.

Example using curl:
```bash
curl -F "file=@{path}/src/main/java/resources/data/trade.csv" http://localhost:8080/api/v1/trades/with-product-names
```

## Running the tests
Execute the following command to run the unit tests:
```bash
mvn test
```

## Built With

- **Spring Boot** - The web framework used
- **Maven** - Dependency Management

## Ideas for Improvement

Given more time, the following improvements could be implemented:

- **Dynamic Configuration**: Enhance the application with externalized, dynamic configuration options for greater flexibility in different environments.
- **Dockerization**: Containerize the application with Docker for easier deployment and scaling.
- **Enhanced Error Handling**: Implement more comprehensive error handling and custom exceptions for better fault tolerance and user feedback.
- **Security**: Add security layers, such as Spring Security, to protect the API endpoints.
- **Performance Optimization**: Introduce caching mechanisms and optimize data processing to handle larger datasets more efficiently.
- **Comprehensive Logging**: Implement a more detailed logging strategy to facilitate easier debugging and monitoring.
- **API Documentation**: Utilize tools like Swagger to document the API, making it easier for other developers to understand and use the service.

## Limitations

- **Scalability**: The current implementation processes files in a synchronous manner, which might not scale well with very large files or high request volumes.
- **Error Handling**: Limited error handling might not cover all edge cases, potentially leading to unhandled exceptions.
- **Data Validation**: The current data validation logic, especially around date formats, could be more robust to handle various scenarios and formats.

## Authors

- **Rashad Naghiyev** - *Initial work* - [GitHub](https://github.com/Kreeby)

## License

This project is licensed under the MIT License - see the LICENSE.md file for details
