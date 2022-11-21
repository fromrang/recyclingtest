package demo.recycling.service;

import demo.recycling.dto.SearchDetail;
import demo.recycling.repository.SearchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    SearchDao searchDao;

    @Autowired
    SearchDetail Search;

    //검색 키워드를 입력했을때 나오는 리스트 (글자 하나씩 입력할때마다 반영)
    public List<SearchDetail> getSearchdata(String data){

        List<SearchDetail> datas = new ArrayList<>();

        try {

            //겁색결과가 없을 시에 아래의 문구 표시
            if(!"".equals(data)){
                datas = searchDao.selectDetailSearch(data);

                if(datas.size()==0){
                    Search.setNo(999);
                    Search.setD_name("미등록 쓰레기, 요기담아요에 요청을 남겨주시면 빠르게 반영하겠습니다!");
                    datas.add(Search);
                }
            }else {
                Search.setNo(1);
                Search.setD_name("");
                datas.add(Search);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return datas;
    }

}
