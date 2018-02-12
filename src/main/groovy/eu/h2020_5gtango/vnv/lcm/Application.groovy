package eu.h2020_5gtango.vnv.lcm

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.properties['info.app.start.time']=new Date().format('yyyy-MM-dd HH:mm:ss z')
		SpringApplication.run(Application.class, args)
	}

}
