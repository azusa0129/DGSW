package project.teaming.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
	@Value("${spring.mail.username}") private String username;
	@Value("${spring.mail.password}") private String password;

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); // 메일 보낼 때 사용되는 주요 구현체임

		mailSender.setHost("smtp.gmail.com"); // Gmail SMTP 서버 사용해서 메일 보내기 위함
		mailSender.setPort(587); // TLS 포트 넘버
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties mailProperties = new Properties();
		mailProperties.put("mail.transport.protocol", "smtp"); // 사용할 메인 프로토콜 지정, SMTP(일반적으로 이메일 전송에 사용함)
		mailProperties.put("mail.smtp.auth", "true"); // SMTP에 로그인 할 때 사용자 인증을 요구
		mailProperties.put("mail.smtp.starttls.enable", "true"); // 안전하지 않은 연결(평문 연결)을 암호화된 TLS 연결로 업그레이드함
		mailProperties.put("mail.debug", "true"); // 디버그 모드를 활성화하여 SMTP 통신 로그를 콘솔에 출력
		mailProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com"); // SSL 인증서 검증시, stmp.gmail.com을 신뢰할 수 있는 호스트로 지정
		mailProperties.put("mail.smtp.ssl.protocols", "TLSv1.3"); // TLS v1.3을 사용

		mailSender.setJavaMailProperties(mailProperties);
		return mailSender;
	}
}
