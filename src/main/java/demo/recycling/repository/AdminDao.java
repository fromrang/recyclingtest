package demo.recycling.repository;


import demo.recycling.dto.Admin;
import demo.recycling.mapper.AdminMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDao {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public int insertAdmin(Admin admin) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(AdminMapper.class).insertAdmin(admin);
            sqlSession.commit();
            return result;
        } finally {
            sqlSession.close();
        }
    }

    public Admin emailCheck(String email) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Admin admin = sqlSession.getMapper(AdminMapper.class).emailcheck(email);
            sqlSession.commit();
            return admin;
        } finally {
            sqlSession.close();
        }
    }

    public Admin loginEmailPw(String email, String pw) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            Admin admin = sqlSession.getMapper(AdminMapper.class).loginEmailPw(email,pw);
            sqlSession.commit();
            return admin;
        }finally {
            sqlSession.close();
        }
    }

    public int updateAdmin(Admin admin) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(AdminMapper.class).updateAdmin(admin);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }

    public int deleteAdmin(int aseq) throws Exception{
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            int result = sqlSession.getMapper(AdminMapper.class).deleteAdmin(aseq);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }

    public List<Admin> selectAdminList() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try{
            return sqlSession.getMapper(AdminMapper.class).selectAdminList();
        }finally {
            sqlSession.close();
        }
    }
}
