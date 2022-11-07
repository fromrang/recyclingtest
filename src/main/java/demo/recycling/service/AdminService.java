package demo.recycling.service;

import demo.recycling.dto.Admin;
import demo.recycling.repository.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    Admin admin;

    @Autowired
    AdminDao adminDao;

    public boolean insertAdmin(Admin admin) {
        try {
            adminDao.insertAdmin(admin);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean emailCheck(String email) {
        try {
            Admin admin = adminDao.emailCheck(email);
            if (admin == null) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean loginEmailPw(String email, String pw) {
        try {
            Admin admin = adminDao.loginEmailPw(email, pw);
            if (admin != null) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAdmin(Admin admin) {
        try{
            int result = adminDao.updateAdmin(admin);
            if(result < 1) return false;
            else return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAdmin(int aseq) {
        try{
            int result = adminDao.deleteAdmin(aseq);
            if(result < 1) return false;
            else return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Admin> selectAdminList() {
        try{
            return adminDao.selectAdminList();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

