package demo.recycling.controller;

import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.Mail;
import demo.recycling.dto.StatusCode;
import demo.recycling.repository.SuggestionDao;
import demo.recycling.service.MailService;
import demo.recycling.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailController {

    @Autowired
    MailService mailService;

    @Autowired
    SuggestionService suggestionService;

    @PostMapping("/mail")
    public ResponseEntity sendMail(@RequestBody Mail mail) {
        Boolean result = mailService.insertMail(mail);
        if (!result) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]mailInsert"), HttpStatus.BAD_REQUEST);
        } else {
            mailService.mailSend(mail);
            suggestionService.serviceSuggestionDelete(mail.getSseq());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]mailInsert", mail), HttpStatus.OK);
        }
    }

}


