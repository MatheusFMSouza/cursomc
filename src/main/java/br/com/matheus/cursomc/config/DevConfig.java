package br.com.matheus.cursomc.config;

import br.com.matheus.cursomc.services.DBService;
import br.com.matheus.cursomc.services.EmailService;
import br.com.matheus.cursomc.services.MockEmailService;
import br.com.matheus.cursomc.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService dbService;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        if (!"create".equals(strategy)) {
            return false;
        }
        dbService.instatiateTestDatabase();
        return true;
    }



    @Bean
    public EmailService emailServiceSmtp() {
        return new SmtpEmailService();
    }



}
