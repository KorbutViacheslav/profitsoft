package org.profitsoft.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;


@Configuration
public class MailSenderConfig {

    private final Dotenv dotenv = Dotenv.load();

    private String host = dotenv.get("MAIL_HOST");
    private int port = Integer.parseInt(dotenv.get("MAIL_PORT"));
    private String username = dotenv.get("MAIL_USERNAME");
    private String password = dotenv.get("MAIL_PASSWORD");

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.debug", true);
        props.put("mail.smtp.ssl.enable", true);

        return mailSender;
    }
}
