package alotra.milktea.service;

import java.util.Random;

import alotra.milktea.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Email {
	@Autowired
	private JavaMailSender javaMailSender;

	// Sinh code ngẫu nhiên
	public String getRandom() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	public String sendEmail(String toEmail, String subject, String body) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("alotramilktea@gmail.com");
			message.setTo(toEmail);
			message.setSubject(subject);
			message.setText(body);
			javaMailSender.send(message);
			return "Successfully!";
		} catch (Exception e) {
			return e.toString();
		}
	}

	public String sendEmailCode(User user) {
		try {
		String toEmail = user.getEmail();
		String code = user.getCode();
		String subject = "[No reply] ALOTRA - Confirm Register!";
		String body = "You registered ALOTRA!"
				+ " Please input your CODE: " +code +" to complete your register!";
		return sendEmail(toEmail, subject, body);
		} catch(Exception e) {
			 e.printStackTrace();
			return e.toString();
		}
	}
	public String sendResetPassCode(User user){
		try {
			String toEmail = user.getEmail();
			String code = user.getCode();
			String subject = "[No reply] ALOTRA - Reset your password!";
			String body = "This is email to reset password for your ALOTRA account!"
					+ " Please input your CODE: " +code +" to complete your action!";
			return sendEmail(toEmail, subject, body);
		} catch(Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}