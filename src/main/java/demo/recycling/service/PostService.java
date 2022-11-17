package demo.recycling.service;

import demo.recycling.dto.Image;
import demo.recycling.dto.Post;
import demo.recycling.repository.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class PostService {
    @Autowired
    Post post;
    @Autowired
    PostDao postDao;

    public List<Post> selectPost(int rum){
        try{
            List<Post> postAll = postDao.selectPost(rum);
            for(Post postList:postAll){
                postList.setImageList(postDao.selectImage(postList.getPseq()));
            }
            return postAll;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public int roomEnter(int rum){
        try {
            int roomType = postDao.selectType(rum);
            if(roomType == 0){
                return 0;
            }else {
                return 1;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public boolean checkPwd(int rum, int password){
        try{
            int pwd = postDao.selectPwd(rum);
            if(pwd == password){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePost(int pseq){
        try {
            int result = postDao.deletePost(pseq);
            int result2 = postDao.deleteImage(pseq);
            if(result > 0 && result2 > 0){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    // 사진과 Post 정보를 저장.
    public boolean postinsert(List<MultipartFile> files, Post post) throws Exception{

        String UPDATE_PATH = "/home/rang/yogidamayo/app/WEB-INF/classes/static/image/";
        //String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";

        List<String> imageName = new ArrayList<>();
        int buff = 0;
        int pseq = 0;

        // post 데이터 저장.
        int result  = postDao.insertpost(post);

        try{
            // post 저장 성공 시 , 이미지들 저장.
            if(result != 0){

                for(MultipartFile file : files){
                    // 파일 이름 중복 없애기 위한 코드 (날짜시간+랜덤수)
                    String fileId = (new Date().getTime())+ "" + (new Random().ints(1000,9999).findAny().getAsInt());
                    String orignalfileName = file.getOriginalFilename(); // 파일이름
                    if(!orignalfileName.equals("")){
                        String fileExtension = orignalfileName.substring(orignalfileName.lastIndexOf(".")+1); // .확장명
                        String finalName = fileId+"."+fileExtension; // DB에 저장 시킬 이름.
                        imageName.add(finalName); // 이미지 리스트대로 넣기.
                        File dest = new File(UPDATE_PATH+fileId + "." + fileExtension);
                        file.transferTo(dest);
                    }
                }

                pseq = postDao.selectpseq();

                // 이미지들 저장.
                for(int i = 0; i < imageName.size(); i++){
                    buff = postDao.insertimage(imageName.get(i),pseq);
                    if(buff == 0){// 이미지 저장 실패 시, post 정보 삭제
                        int bol = postDao.deletePost(pseq);
                        return false;
                    }
                }

            }
            else return false;

            return true;
        }catch (Exception e){
            int bol = postDao.deletePost(pseq);
            return false;
        }

    }

    // 사진과 Post- Content 수정 및 삭제.
    public boolean postupdate(List<MultipartFile> files, Post post) throws Exception{

        List<Image> imageName = new ArrayList<>();
        List<String> basicName = new ArrayList<>();
        List<String> DeleteName = new ArrayList<>();
        int check = 0;

        //String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";
        String UPDATE_PATH = "/home/rang/yogidamayo/app/WEB-INF/classes/static/image/";

        // 해당 이미지들 불러오기.
        imageName = postDao.selectImage(post.getPseq());

        // 업데이트 할 이미지들 비교. (basicName, UpdateName 분류)
        for(MultipartFile file : files){
            String orignalfileName = file.getOriginalFilename();

            if(!orignalfileName.equals("")){
                // 기존 이미지 파일과 들어온 이미지 파일명을 비교해서 있으면 기존 있는 데이터 리스트에 분류.
                for(int i  = 0; i < imageName.size(); i++){
                    if(imageName.get(i).getImage_name().equals(orignalfileName)) {
                        basicName.add(imageName.get(i).getImage_name());
                        check = 1;
                    }
                }
                if(check == 0){ // 새로 저장 할 이미지 저장.
                    String fileId = (new Date().getTime())+ "" + (new Random().ints(1000,9999).findAny().getAsInt());
                    String fileExtension = orignalfileName.substring(orignalfileName.lastIndexOf(".")+1);
                    String finalName = fileId+"."+fileExtension;
                    File dest = new File(UPDATE_PATH+fileId + "." + fileExtension);
                    file.transferTo(dest);
                    int result = postDao.insertimage(finalName,post.getPseq());
                    if(result == 0) return false;
                }
                else check = 0;
            }
        }

        check = 0;


        if(imageName.size() != 0){

            // 기존 데이터가 없을 시.
            if(basicName.size() == 0){
                for(int i = 0; i < imageName.size(); i++){
                    File file = new File(UPDATE_PATH + File.separator + imageName.get(i).getImage_name());
                    boolean result = file.delete();
                    int buff = postDao.deleteimage(post.getPseq(),imageName.get(i).getImage_name());
                    if(buff == 0) return false;
                }
            }
            else{
                // 삭제할 이미지 찾기.
                for(int i = 0 ; i < imageName.size(); i++){
                    for(int j = 0; j < basicName.size(); j++){
                        if(imageName.get(i).getImage_name().equals(basicName.get(j))){
                            check = 1;
                        }
                    }
                    if(check == 0){
                        DeleteName.add(imageName.get(i).getImage_name());
                    }
                    else check = 0;
                }

                // 데이터 삭제하기
                if(DeleteName.size() != 0){
                    for(int i = 0; i < DeleteName.size(); i++){
                        File file = new File(UPDATE_PATH + File.separator + DeleteName.get(i));
                        boolean result = file.delete();
                        int buff = postDao.deleteimage(post.getPseq(),DeleteName.get(i));
                        if(buff == 0) return false;
                    }
                }
            }

        }




        // post 업데이트!
        int result = postDao.updatepost(post.getContent(),post.getPseq());
        if(result == 0)  return false;

        return true;
    }

}
