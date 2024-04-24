package ua.hudyma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import ua.hudyma.repository.UserRepository;


@SpringBootApplication
/*@EnableJpaRepositories (excludeFilters =
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UserRepository.class))*/
public class SpringRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestApplication.class, args);
	}

}
