# C-Store
## CS3043 Project: Single Vendor E-commerce platform

### Instructions for testing the Server:
    1. Download & setup Postgres.
    
    2. Using **superuser privileges**, create a new database & a new user.
        Eg: 
           DROP DATABASE IF EXISTS cstore;
           DROP USER IF EXISTS cadmin;
           
           CREATE DATABASE cstore;
           CREATE USER cadmin PASSWORD 'cstore_GRP28_CSE21';
           
           GRANT ALL PRIVILEGES ON DATABASE cstore TO cadmin;
           
    3. Now proceed to __*C Store/Backend/backend/src/main/resources/application.yml*__ & update the following.
           spring.datasource.username: cadmin
           spring.datasource.password: cstore_GRP28_CSE21
           token.secret.key: e375e33b550272473c33aeca01c189141a64431c1d7495204cfa91d7bf4d8e59
           
    4. Run the schema.sql & data.sql files.
    5. Start the application & visit the [Swagger UI page](http://localhost:8080/swagger-ui/index.html#/).
