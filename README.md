# Company Management System
Small application built using **Angular and Spring Boot** that allows users to manage their companies, employees and devices.


All the information is stored in a **Mongo DB** database and can be accessed and modified through the Spring Boot API. 

The frontend uses this API for letting the user have a total control in a simpler way.

## About
The application was built with the purpose of improving my technical skills using Angular and Spring Boot, trying always to follow best practices.

## Architecture 
```
  Mongo DB
     ↓
 Spring Boot
     ↓
  Angular
```

## How to run the application
Clone the repository in your local machine
```
git clone https://github.com/martdbj/SpringBootAngularProject.git
```

Go to the repository folder 
```
cd .\SpringBootAngularProject\
```

Run the following python script and wait 1 minute for the application startup

Docker needs to be running in your computer before executing this command.
```
python .\init.py
```
