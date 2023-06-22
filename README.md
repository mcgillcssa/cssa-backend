# CSSA - BACKEND

This is a backend application for CSSA. It provides connection to MongoDB Atlas for data handling and is built using Java, with IoC permitted by Spring and automated by Gradle.

## Getting Started

To use the backend application, you will need to have the following software installed on your machine:

- Java Development Kit (JDK) 17 or later
- Gradle build tool

Once you have installed the required software, follow these steps to set up and run the application:

1. Clone the repository to your local machine using the following command:
   `git clone <repository-url>`
2. Build the project using the following command:
   `./gradlew build`
3. To make sure that you can connect to the database, ensure that you have defined the credentials as an environment variable:
   -Windows Powershell
   `$env:DB_PASSWORD = "<password>"`
   -Unix
   `export DB_PASSWORD=<password>`
4. Run the application using the following command:
   `./gradlew bootRun`

This will start the application on `http://localhost:8080`.
