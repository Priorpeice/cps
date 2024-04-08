package server.cps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CpsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CpsApplication.class, args);
    }

}
