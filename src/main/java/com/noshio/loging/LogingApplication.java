package com.noshio.loging;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogingApplication implements ApplicationRunner {

	private static final Logger logger = LogManager.getLogger(LogingApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(LogingApplication.class, args);
	}

	@Override
	public void run(final ApplicationArguments applicationArguments) {
		logger.info("We have a  VISA CreditCard:4485845206032165, and another VISA CreditCard:4583903811386327 , we also have a CVV:234");
		logger.info("We have a  AMERICAN EXPRESS CreditCard:344472003047574, and another AMERICAN EXPRESS CreditCard:376729481321792 , we also have a CVV:234");
		logger.info("We have a  MASTERCARD CreditCard:5541836310917721, and another MASTERCARD CreditCard:5309023187132310 , we also have a CVV:234");
		logger.info("We have a  DINERSCLUB CreditCard:30059976868794, and another DINERSCLUB CreditCard:38933702225309 , we also have a CVV:234");
		logger.info("We have a  DISCOVERY CreditCard:6011325170145341, and another DISCOVERY CreditCard:5546560116842725 , we also have a CVV:234");
		logger.info("We have a  JCB15 CreditCard:180090199028005, and another JCB15 CreditCard:210027731854401 , we also have a CVV:234");
		logger.info("We have a  VISA13 CreditCard:4556776615749, and another VISA13 CreditCard:4532210570626 , we also have a CVV:234");


		logger.info("JWT is here Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
		logger.debug("JWT is here Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");

		logger.warn("JWT is here Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
	}
}
