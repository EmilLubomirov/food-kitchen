# FOOD KITCHEN

An application where you can 
find a lot about cooking - 
variety of recipes, creative ideas, 
useful nutrition facts and tips.

Here you will explore interesting cook books, articles
and be able to communicate with the other enthusiasts
via the forum functionality.

There are 3 user roles:
- Guest
- User
- Admin

Users can sign in 
after creating an account themselves.

You should be logged in to
upload recipes and to access the forum page.

Users are capable of adding recipes they liked 
to their favorite list and see them quickly later on or 
remove them from it whenever they want.
Every single user can also rate particular recipe 
according to his / her preference and to
update his / her profile as well.
 
If you are authorized as an admin, you are able to upload 
cook books and articles.

#### Setup:

##### Backend:
1. `Open pom.xml and import the dependencies`
2. `Open application.properties file and 
provide:`

             spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
             spring.datasource.url=jdbc:mysql://localhost:yourMySQLPort/yourDBName?useSSL=false&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true
             spring.datasource.username=yourMySQLUsername
             spring.datasource.password=yourMySQLPassword

             spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
             spring.jpa.properties.hibernate.format_sql=TRUE
             spring.jpa.hibernate.ddl-auto=update
             spring.jpa.show-sql=true
             spring.jpa.open-in-view=false
             
             com.jwt.secret=yourSecret
             com.jwt.time=yourTime
             
3. `Run MySQL on your machine`
    
4. `Run FoodKitchenBackendApplication.java
    file` - to start the server locally` 
    
##### Frontend:
1. `npm install` - to install all dependencies
2. `Add .env file provided with:`
    - REACT_APP_AUTH_COOKIE_NAME="yourCookieName"
    - REACT_APP_CLOUD_NAME="yourCloudName (Cloudinary)"
    - REACT_APP_UPLOAD_PRESET="yourUploadPreset (Cloudinary)"
3. `npm start` - to start the server locally
4. `open localhost:3000`

#### Note:
`Make sure your Tomcat server is running on port 8080 (default) and React on port 3000 (default)`
    