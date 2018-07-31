[![Build Status](http://jenkins.sonata-nfv.eu/buildStatus/icon?job=tng-vnv-lcm/master)](https://jenkins.sonata-nfv.eu/job/tng-vnv-lcm)


# Lifecycle Manager for 5GTANGO Verification and Validation
This is a [5GTANGO](http://www.5gtango.eu) component to coordinate the verification and validation activities of 5G Network Services.


<p align="center"><img src="https://github.com/sonata-nfv/tng-api-gtw/wiki/images/sonata-5gtango-logo-500px.png" /></p>

## What it is

The Lifecycle Manager acts as the main executor for all V&V test activities. It is responsible for sequencing, executing and presenting corresponding test results. Although the LCM is responsible for overall test activity, execution of individual test plans is given over to the TEE.
An outline of the architecture is given below

<p align="center"><img src="https://raw.githubusercontent.com/wiki/sonata-nfv/tng-vnv-lcm/images/v40-release-lcm.png" /></p>



## Build from source code

```bash
./gradlew
```

The project depends on java and docker to build. Once you have those two tools, you simply run `./gradlew` command without parameter to do a full build:
* clean : clean the project build directory
* compile code
* process resources
* package jar
* compile test
* process test resources
* execute test
* execute docker build
* execute docker push: optional, default to
  * `true` on jenkins build
  * `false` on local build

For advanced build arguments, please checkout the [gradle-buildscript](https://github.com/mrduguo/gradle-buildscript) project.


## Run the docker image

```bash
docker pull registry.sonata-nfv.eu:5000/tng-vnv-lcm
docker run -d --name tng-vnv-lcm -p 6100:6100 registry.sonata-nfv.eu:5000/tng-vnv-lcm
```

### Health checking

Once the component finish start, you can access (change IP depends on your docker setup) the component health endpoint at:

http://192.168.99.100:6100/tng-vnv-lcm/health

### Swagger UI

* static
    * http://petstore.swagger.io/?url=https://raw.githubusercontent.com/sonata-nfv/tng-vnv-lcm/master/src/main/resources/static/swagger.json
    * http://petstore.swagger.io/?url=https://raw.githubusercontent.com/sonata-nfv/tng-vnv-lcm/master/src/main/resources/static/swagger-dependencies.json
* pre integration 
    * http://pre-int-vnv-ave.5gtango.eu:6100/tng-vnv-lcm/swagger-ui.html
* local 
    * http://192.168.99.100:6100/tng-vnv-lcm/swagger-ui.html
    * http://192.168.99.100:6100/tng-vnv-lcm/swagger-ui.html?urls.primaryName=dependencies



## Dependencies

No specific libraries are required for building this project. The following tools are used to build the component

- `java (version 8)`
- `grade (version 4.9)`
- `docker (version 18.x)`


## Contributing
Contributing to the Gatekeeper is really easy. You must:

1. Clone [this repository](http://github.com/sonata-nfv/tng-vnv-lcm);
1. Work on your proposed changes, preferably through submiting [issues](https://github.com/sonata-nfv/tng-vnv-lcm/issues);
1. Submit a Pull Request;
1. Follow/answer related [issues](https://github.com/sonata-nfv/tng-vnv-lcm/issues) (see Feedback-Chanel, below).


## License

This 5GTANGO component is published under Apache 2.0 license. Please see the [LICENSE](LICENSE) file for more details.

## Lead Developers

The following lead developers are responsible for this repository and have admin rights. They can, for example, merge pull requests.

* George Andreou ([allemaos](https://github.com/allemaos))
* Guo Du ([mrduguo](https://github.com/mrduguo))
* Felipe Vicens ([felipevicens](https://github.com/felipevicens))

## Feedback-Chanels

Please use the [GitHub issues](https://github.com/sonata-nfv/tng-vnv-lcm/issues) and the 5GTANGO Verification and Validation group mailing list `5gtango-dev@list.atosresearch.eu` for feedback.
