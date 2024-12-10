package hr.unizg.fer.ticket4ticket;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.util.Arrays;

@SpringBootApplication
@EnableWebSecurity
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

//	@Bean
//	public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
//		ForwardedHeaderFilter filter = new ForwardedHeaderFilter();
//		FilterRegistrationBean<ForwardedHeaderFilter> registration = new FilterRegistrationBean<>(filter);
//		registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR);
//		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
//		registration.setUrlPatterns(Arrays.asList("/api/*", "/login", "/oauth2/authorization/callback", "/oauth/callback"));
//		return registration;
//	}
}
