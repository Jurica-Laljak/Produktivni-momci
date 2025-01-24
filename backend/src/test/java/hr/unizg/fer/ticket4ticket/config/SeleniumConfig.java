package hr.unizg.fer.ticket4ticket.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@Configuration
@ContextConfiguration(classes = SeleniumConfig.class)
public class SeleniumConfig {

    @Bean(destroyMethod = "quit")
    public WebDriver firefoxDriver() {
        return new FirefoxDriver();
    }
}
