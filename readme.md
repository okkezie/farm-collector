# Farm Collector API Read Me

## How to

- Clone the repo.
- Build the code and run tests with `mvn clean package`.
- Run the code with `mvn spring-boot:run` to start the api server locally.
- Open the swagger ui page to see the API documentation: http://localhost:8080/swagger-ui/index.html
- Call the API endpoints with postman or via the swagger page.

## Description:

### Crop Controller

*The Planting API* : The crop controller provides the create crop endpoint to enable the farmers record their crops on the system. The farmer will specify the season, crop type, farm, farmer, field, and expected yeild. 

*The Harvesting API :* 
This API on the crop controller allows the farmer to record their harvest by providing the crop id and the actual harvested yield. The system updates the existing crop record with the actual yield.

### Reporting Controller

*The Crop Report API :* 
This endpoint provides crop reports for a particular season. It returns a list of all the crops planted that season and their individual expected yeilds and actual yields.

*The Farm Report API :* 
This endpoint provides farm reports for a particular season. It returns a list of all the farms for a provided season and their individual expected yeilds and actual yields.

### Database
The application makes use of H2 database to provide an in-memory database that is suitable for both test and demo purposes.

### Repository
The application uses spring data repository to connect to the underlying database and map the entity classes to the database tables.

### Testing
The unit tests are written with junit, and mockito. All the controllers are tested.
