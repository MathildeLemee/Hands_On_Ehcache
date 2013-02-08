# Backbone.js + Java org.coenraets.Wine Cellar Application #

The org.coenraets.Wine Cellar application is documented [here](http://coenraets.org).

This application provides an example of 
1. Building a complete RESTful API in Java using JAX-RS and Jersey.
2. Consuming these services using Backbone.js client application



Set Up:

1. Create a MySQL database name "wine".
2. Execute cellar.sql in src/main/resources to create and populate the "wine" table:

	mysql wine -uroot < cellar.sql

3. Run the project with mvn clean install jetty:run