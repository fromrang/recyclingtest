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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class MailController {

    @Autowired
    MailService mailService;

    @Autowired
    SuggestionService suggestionService;

    @PostMapping("/mail")
    public ResponseEntity sendMail(@RequestParam(value = "files", required = false) MultipartFile files, @RequestParam String useremail
            , @RequestParam String title, @RequestParam String content , @RequestParam int sseq) {
        Mail mail = new Mail();
        mail.setContent(content);
        mail.setTitle(title);
        mail.setUseremail(useremail);
        mail.setFiles(files);
        mail.setSseq(sseq);

        Boolean result = mailService.insertMail(mail);
//        mail.setSseq(sseq);
//        System.out.println(content);
//        System.out.println(files.getOriginalFilename());
//        boolean aa = mailService.mailSend(mail);

//        if(aa) System.out.println("성공");
//        else System.out.println("실패");s

        if(StringUtils.isEmpty(mail.getFiles())){
            mailService.mailSends(mail);
            suggestionService.serviceSuggestionDelete(mail.getSseq());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]mailInsertnotImage" ,useremail), HttpStatus.OK);
        }



        if (!result) {
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]mailInsert"), HttpStatus.BAD_REQUEST);
        } else {
            mailService.mailSend(mail);
            suggestionService.serviceSuggestionDelete(mail.getSseq());
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]mailInsert",useremail ), HttpStatus.OK);
        }
    }

//    @PostMapping("/mail")
//    public ResponseEntity sendMails(@RequestBody Mail mail) {
//        Boolean result = mailService.insertMail(mail);
//        if (!result) {
//            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]mailInsert"), HttpStatus.BAD_REQUEST);
//        } else {
//            mailService.mailSends(mail);
//            suggestionService.serviceSuggestionDelete(mail.getSseq());
//            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]mailInsert", mail), HttpStatus.OK);
//        }
//    }

}



