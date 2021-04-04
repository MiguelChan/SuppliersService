# Suppliers Service

Within this code package we're going to find all the code related to the
`SuppliersService`-MicroService we're building for our Bakery.

# Running

* `cd` to the root folder
* Run: `./gradlew release`: This will download the dependencies, build the project
and make minor checks. If using windows replace to `./gradlew.bat release`
* Run `./gradlew run`

# Packaging

Since the code package will be stored in Docker. We need to dockerize our app
before deploying the code. In order to do so follow the next steps:

* `docker build --rm -t mgl/suppliers-service .`: This will create your image and wait for you to run it.
* `docker run --rm -t -d mgl/suppliers-service bash`: This will run the actual image in your container and run it.

### Troubleshooting

* Sometimes when trying to build your Docker image you will find some errors, and you will need to log into the
Image's container terminal in order to see what went wrong. When this occurs, try to run the following commands:
  * `docker ps` - This will list the running containers. Take a note to the container you want to log into.
  * `docker exec --t <ContainerName> bash`: This will log into the container's Terminal and now you can debug in there.