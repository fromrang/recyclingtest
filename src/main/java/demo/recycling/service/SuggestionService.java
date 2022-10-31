package demo.recycling.service;

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

}