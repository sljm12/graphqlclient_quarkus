# Sample GraphQL app using Hasura

This is a sample app that uses Hasura and Quarkus to show how Quarkus using smallrye-graphql-client can access a DB that is linked to Hasura.

## Hasura setup

* Setup Hasura using the information here https://hasura.io/docs/latest/graphql/core/getting-started/docker-simple/
* Deploy the Hello World template by opening the Hasura console http://localhost:8080/console
* Go to "DATA"
* Click on the name of the connected database
* Look under the Templates and deploy the "Hello" World template. This will create a *_helloworld* database and the *article* and *author* table.

## Running 

You can just run "quarkus dev" to run the application in development mode. The application will run on port 9000.

## Examples

There are typesafe and dynamic examples 

## References
* [Quarkus Graphql Client Guide](https://quarkus.io/guides/smallrye-graphql-client)
* [Smallrye GraphQL Guide](https://smallrye.io/smallrye-graphql/1.4.3/)




