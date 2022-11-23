package demo.recycling.service;

import demo.recycling.repository.MailDao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import demo.recycling.dto.Mail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class MailService {
    @Autowired
    Mail mail;

    @Autowired
    MailDao mailDao;

    private JavaMailSender javaMailSender;
    private static final String FROM_ADDRESS = "rhksgud123@gmail.com";

    public void mailSend(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getUseremail());
        message.setSubject(mail.getTitle());
        message.setText(mail.getContent());
        javaMailSender.send(message);
    }

    public boolean insertMail(Mail mail){
        try {
            mailDao.insertMail(mail);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
