package demo.recycling.service;

import demo.recycling.dto.Notice;
import demo.recycling.dto.Suggestion;
import demo.recycling.repository.SuggestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionService {
    @Autowired
    Suggestion suggestion;
    @Autowired
    Notice notice;

    @Autowired
    SuggestionDao suggestionDao;

    public List<Suggestion> allSuggestion(){
        try{
            return suggestionDao.selectSuggestion();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public boolean insertSuggestion(Suggestion suggestion){
        try {
            suggestionDao.insertSuggestion(suggestion);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Notice> serviceNoticeAll(){
        try{
            return suggestionDao.selectNoticeAllDao();
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Boolean serviceNoticeInsert(Notice notice){
        try {
            int result = suggestionDao.insertNoticeDao(notice);
            return result >= 1;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public Notice serviceNoticeSelectOne(int nseq){
        try {
            Notice notice = suggestionDao.selectNoticeOneDao(nseq);
            return notice;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    //문의하기 삭제
    public boolean serviceSuggestionDelete(int sseq){
        try{
            int result = suggestionDao.updateSuggestionStatusDao(sseq);
            return result >= 1;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //공지사항 수정
    public boolean serviceNoticeUpdate(Notice notice){
        try{
            int result = suggestionDao.updateNoticeOneDao(notice);
            return result >= 1;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //공지사항 삭제
    public boolean serviceNoticeDelete(int nseq){
        try{
            int result = suggestionDao.updateNoticeStatusDao(nseq);
            return result >= 1;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}