package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main entry point for the MixMavenApplication Spring Boot application. This class initializes
 * and starts the Spring Boot application.
 */
@SpringBootApplication
public class MixMavenApplication {

     /**
      * The main method that serves as the entry point for the MixMavenApplication. It initializes
      * and starts the Spring Boot application.
      * @param args Command-line arguments passed to the application.
      */
     public static void main(String[] args) {
          SpringApplication.run(MixMavenApplication.class, args);
     }
}
