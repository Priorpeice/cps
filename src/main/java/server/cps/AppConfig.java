package server.cps;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = "server.cps",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASPECTJ, pattern = "server.cps.redis.repository.*"
        )
)
public class AppConfig {

}
