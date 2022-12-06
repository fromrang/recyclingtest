package demo.recycling.service;


import demo.recycling.controller.MailHandler;
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

    public boolean mailSend(Mail mail) {
        try {
            MailHandler mailHandler = new MailHandler(javaMailSender);
            mailHandler.setTo(mail.getUseremail());
            mailHandler.setSubject(mail.getTitle());
            String htmlContent = "<p>" + mail.getContent() + "<p> <img src='cid:1-img'>";
            mailHandler.setText(htmlContent, true);
            mailHandler.setAttach(mail.getFiles().getOriginalFilename(), mail.getFiles());
            mailHandler.setInline("1-img", mail.getFiles());
            mailHandler.send();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void mailSends(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getUseremail());
        message.setSubject(mail.getTitle());
        message.setText(mail.getContent());

        javaMailSender.send(message);
    }


    public boolean insertMail(Mail mail) {
        try {
            mailDao.insertMail(mail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
