[![Build Status](http://jenkins.sonata-nfv.eu/buildStatus/icon?job=tng-vnv-lcm)](http://jenkins.sonata-nfv.eu/job/tng-vnv-lcm)

# Lifecycle Manager for 5GTANGO Verification and Validation
This is a [5GTANGO](http://www.5gtango.eu) component to coordinate the verification and validation activities of 5G Network Services.


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

Once the component finish start, you can access the component health endpoint at:

http://192.168.99.100:6100/health


## Contributing
Contributing to the Gatekeeper is really easy. You must:

1. Clone [this repository](http://github.com/sonata-nfv/tng-vnv-lcm);
1. Work on your proposed changes, preferably through submiting [issues](https://github.com/sonata-nfv/tng-vnv-lcm/issues);
1. Submit a Pull Request;
1. Follow/answer related [issues](https://github.com/sonata-nfv/tng-vnv-lcm/issues) (see Feedback-Chanel, below).


## License

This 5GTANGO component is published under Apache 2.0 license. Please see the [LICENSE](LICENSE) file for more details.

#### Lead Developers

The following lead developers are responsible for this repository and have admin rights. They can, for example, merge pull requests.

* Guo Du ([mrduguo](https://github.com/mrduguo))
* Felipe Vicens ([felipevicens](https://github.com/felipevicens))

#### Feedback-Chanels

Please use the [GitHub issues](https://github.com/sonata-nfv/tng-vnv-lcm/issues) and the 5GTANGO Verification and Validation group mailing list `tango-5g-wp3@lists.atosresearch.eu` for feedback.
