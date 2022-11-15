package demo.recycling.service;

import demo.recycling.dto.Users;
import demo.recycling.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public String userExistCheck(String email){
        try {
            Users users = userDao.selectUserDao(email);
            if(users != null){
                return users.getNickname();
            }
            return "false";
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }
    }
    public boolean userRegister(Users users){
        try{
            int result = userDao.insertUserDao(users);
            return result >= 1;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public Boolean userNicknameCheck(String nickname){
        try{
            Users users = userDao.selectNicknameDao(nickname);
            if(users != null){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
