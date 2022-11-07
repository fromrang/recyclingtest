package demo.recycling.repository;

import demo.recycling.dto.Mail;
import demo.recycling.mapper.MailMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MailDao {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    public int insertMail(Mail mail) throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            int result = sqlSession.getMapper(MailMapper.class).insertMail(mail);
            sqlSession.commit();
            return result;
        }finally {
            sqlSession.close();
        }
    }
}
