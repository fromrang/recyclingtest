package demo.recycling.service;

import demo.recycling.dto.SearchDetail;
import demo.recycling.repository.SearchDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    @Autowired
    SearchDao searchDao;

    @Autowired
    SearchDetail Search;

    //검색 키워드를 입력했을때 나오는 리스트 (글자 하나씩 입력할때마다 반영)
    public List<SearchDetail> getSearchdata(String data){

        List<SearchDetail> datas = new ArrayList<>();
        List<Integer> splitdata=new ArrayList<>();

        try {


            if(!"".equals(data)){
                List<String> d_no=searchDao.selectResultsearch(data);
            //result의 문자를 불러서 split하여 detail에 일치하는 번호 찾기
                for(String split:d_no){
                    String[] split_d_no=split.split(",");
                    for(String split_no:split_d_no){
                        //System.out.println(split_no);
                        splitdata.add(Integer.valueOf(split_no));
                    }
                }

                List<Integer> noduplication=splitdata.stream().distinct().collect(Collectors.toList());

                for(Integer detail:noduplication){
                    datas.add(searchDao.selectDetailSearch(detail));
                }

                //검색결과가 없을 시에 아래의 문구 표시
                if(datas.size()==0){
                    Search.setNo(999);
                    Search.setD_name("미등록 쓰레기, 요기담아요에 요청을 남겨주시면 빠르게 반영하겠습니다!");
                    datas.add(Search);
                }
            }else {
                //공백일 경우에 표시
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
