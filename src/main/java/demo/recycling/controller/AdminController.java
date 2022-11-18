package demo.recycling.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import demo.recycling.dto.Notice;
import demo.recycling.repository.Program;
import demo.recycling.dto.DefaultRes;
import demo.recycling.dto.StatusCode;
import demo.recycling.dto.Admin;
import demo.recycling.repository.AdminDao;
import demo.recycling.service.AdminService;
import demo.recycling.service.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminDao adminDao;

    @Autowired
    Program program;


    // 관리자 회원가입
    @PostMapping("/admin")
    public ResponseEntity join(@RequestBody Admin admin) {

        try {

            if (admin.getEmail() == null || (admin.getPw() == null) ||
                    (admin.getName() == null) || (admin.getAuthority() == null) || admin.getPosition() == null) {
                return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[Fail]not null"), HttpStatus.BAD_REQUEST);
            }

            boolean result = adminService.emailCheck(admin.getEmail());
            if (!result) {
                return new ResponseEntity(DefaultRes.res(StatusCode.CREATED, "[Fail]exist user"), HttpStatus.OK);
            } else {
                admin.setPw(encrypt(admin.getPw()));
                adminDao.insertAdmin(admin);
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]join", admin), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    //관리자 로그인
    @PostMapping("/token")
    public ResponseEntity loginToken(@RequestBody Admin admin) {
        try {
            admin.setPw(encrypt(admin.getPw()));
            boolean login = adminService.loginEmailPw(admin.getEmail(), admin.getPw());
            if (!login) {
                return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[Fail]not exist user"), HttpStatus.OK);
            } else {
                String token = program.createToken(admin.getEmail());
                //loginUser.setPassword(null);
                return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]login", token), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    // 관리자 수정
    @PutMapping("/admin")
    public ResponseEntity update(@RequestBody Admin admin) throws NoSuchAlgorithmException {
        admin.setPw(encrypt(admin.getPw()));
        Boolean result = adminService.updateAdmin(admin);
        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]updateAdmin"), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]updateAdmin", admin), HttpStatus.OK);
        }
    }


    // 관리자 삭제
    @DeleteMapping("/admin/{aseq}")
    public ResponseEntity delete(@PathVariable int aseq) throws NoSuchAlgorithmException{
        Boolean result = adminService.deleteAdmin(aseq);
        if(!result){
            return new ResponseEntity(DefaultRes.res(StatusCode.BAD_REQUEST, "[FAIL]deleteAdmin"), HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]deleteAdmin", aseq), HttpStatus.OK);
        }
    }

    // 관리자 리스트 조회
    @GetMapping("/admin")
    public ResponseEntity selectAdminList(String status){
        List<Admin> adminList = adminService.selectAdminList();
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]selectAdminList", adminList), HttpStatus.OK);
    }

    public String encrypt(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(text.getBytes());

        return bytesToHex(md.digest());
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}




    //    @GetMapping("/admin")
//    public ResponseEntity login(Admin admin, HttpServletRequest request) {
//        try {
//            Admin existAdmin = suggestionDao.exist(admin.getEmail(), admin.getPw());
//            if (existAdmin == null) {
//                return new ResponseEntity(DefaultRes.res(StatusCode.CREATED, "[Fail]not exist admin"), HttpStatus.OK);
//            } else {
//                existAdmin.setPw(null);
//                //세션에 저장
//                HttpSession session = request.getSession();
//                session.setAttribute("userSession", existAdmin.getEmail());
//                return new ResponseEntity(DefaultRes.res(StatusCode.OK, "[SUCCESS]login", existAdmin), HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity(HttpStatus.BAD_REQUEST);
//        }
//    }





