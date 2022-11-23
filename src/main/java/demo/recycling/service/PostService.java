package demo.recycling.service;

import demo.recycling.dto.Image;
import demo.recycling.dto.Post;
import demo.recycling.dto.Room;
import demo.recycling.repository.PostDao;
import demo.recycling.repository.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PostService {
    @Autowired
    Post post;
    @Autowired
    PostDao postDao;

    @Autowired
    RoomDao roomDao;

    @Autowired
    Room room;

    public HashMap<String,Object> selectPost(int rum){
        try{
            HashMap<String,Object> postAll=new HashMap<>();
            List<Post> postLists = postDao.selectPost(rum);

            for(Post postList:postLists){
                List<Image> imageList=new ArrayList<>();
                List<Image> imageFiter=postDao.selectImage(postList.getPseq());
                for(int i=0;i<imageFiter.size();i++){
                    String image="/home/rang/yogidamayo/app/WEB-INF/classes/static/image"+ imageFiter.get(i).getImage_name();
                    imageFiter.get(i).setImage_name(image);
                    imageList.add(imageFiter.get(i));
                }
                postList.setImageList(imageList);
            }
            String title= roomDao.postRumTitle(rum);
            postAll.put("content",postLists);
            postAll.put("title",title);
            System.out.println(title);
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
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                    String fileId = (sdf1.format(System.currentTimeMillis())+""+(new Random().ints(1000,9999).findAny().getAsInt()));
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
    public boolean postupdate(List<MultipartFile> files, Post post,List<String> images) throws Exception{

        List<Image> imageName = new ArrayList<>();
        List<String> basicName = new ArrayList<>();
        List<String> DeleteName = new ArrayList<>();
        int check = 0;

        //String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";
        String UPDATE_PATH = "/home/rang/yogidamayo/app/WEB-INF/classes/static/image/";

        // 해당 이미지들 불러오기.
        imageName = postDao.selectImage(post.getPseq());

        // 업데이트 할 이미지들 비교.
        for(MultipartFile file : files){
            String orignalfileName = file.getOriginalFilename();

            if(!orignalfileName.equals("")){
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                    String fileId = (sdf1.format(System.currentTimeMillis())+""+(new Random().ints(1000,9999).findAny().getAsInt()));
                    String fileExtension = orignalfileName.substring(orignalfileName.lastIndexOf(".")+1);
                    String finalName = fileId+"."+fileExtension;
                    File dest = new File(UPDATE_PATH+fileId + "." + fileExtension);
                    file.transferTo(dest);
                    int result = postDao.insertimage(finalName,post.getPseq());
                    if(result == 0) return false;
            }
        }

        check = 0;

        // 해당 post에 넣은 사진이 있는경우
        if(imageName.size() != 0){
            // images
            // 기존 이미지 이름이 없을 시.
            if(images.size() == 0){
                for(int i = 0; i < imageName.size(); i++){
                    File file = new File(UPDATE_PATH + File.separator + imageName.get(i).getImage_name());
                    boolean result = file.delete();
                    int buff = postDao.deleteimage(post.getPseq(),imageName.get(i).getImage_name());
                    if(buff == 0) return false;
                }
            }
            else{ // 기존 이미지 이름이 있을 시.
                // 삭제할 이미지 찾기.
                for(int i = 0 ; i < imageName.size(); i++){
                    for(int j = 0; j < images.size(); j++){
                        if(imageName.get(i).getImage_name().equals(images.get(j))){
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




//=================

//    // 사진과 Post- Content 수정 및 삭제.
//    public boolean postupdate(List<MultipartFile> files, Post post) throws Exception{
//
//        List<Image> imageName = new ArrayList<>();
//        List<String> basicName = new ArrayList<>();
//        List<String> DeleteName = new ArrayList<>();
//        int check = 0;
//
//        String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";
//        //String UPDATE_PATH = "/home/rang/yogidamayo/app/WEB-INF/classes/static/image/";
//
//        // 해당 이미지들 불러오기.
//        imageName = postDao.selectImage(post.getPseq());
//
//        // 업데이트 할 이미지들 비교. (basicName, UpdateName 분류)
//        for(MultipartFile file : files){
//            String orignalfileName = file.getOriginalFilename();
//
//            if(!orignalfileName.equals("")){
//                // 기존 이미지 파일과 들어온 이미지 파일명을 비교해서 있으면 기존 있는 데이터 리스트에 분류.
//                for(int i  = 0; i < imageName.size(); i++){
//                    if(imageName.get(i).getImage_name().equals(orignalfileName)) {
//                        basicName.add(imageName.get(i).getImage_name());
//                        check = 1;
//                    }
//                }
//                if(check == 0){ // 새로 저장 할 이미지 저장.
//                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//                    String fileId = (sdf1.format(System.currentTimeMillis())+""+(new Random().ints(1000,9999).findAny().getAsInt()));
//                    String fileExtension = orignalfileName.substring(orignalfileName.lastIndexOf(".")+1);
//                    String finalName = fileId+"."+fileExtension;
//                    File dest = new File(UPDATE_PATH+fileId + "." + fileExtension);
//                    file.transferTo(dest);
//                    int result = postDao.insertimage(finalName,post.getPseq());
//                    if(result == 0) return false;
//                }
//                else check = 0;
//            }
//        }
//
//        check = 0;
//
//
//        if(imageName.size() != 0){
//
//            // 기존 데이터가 없을 시.
//            if(basicName.size() == 0){
//                for(int i = 0; i < imageName.size(); i++){
//                    File file = new File(UPDATE_PATH + File.separator + imageName.get(i).getImage_name());
//                    boolean result = file.delete();
//                    int buff = postDao.deleteimage(post.getPseq(),imageName.get(i).getImage_name());
//                    if(buff == 0) return false;
//                }
//            }
//            else{
//                // 삭제할 이미지 찾기.
//                for(int i = 0 ; i < imageName.size(); i++){
//                    for(int j = 0; j < basicName.size(); j++){
//                        if(imageName.get(i).getImage_name().equals(basicName.get(j))){
//                            check = 1;
//                        }
//                    }
//                    if(check == 0){
//                        DeleteName.add(imageName.get(i).getImage_name());
//                    }
//                    else check = 0;
//                }
//
//                // 데이터 삭제하기
//                if(DeleteName.size() != 0){
//                    for(int i = 0; i < DeleteName.size(); i++){
//                        File file = new File(UPDATE_PATH + File.separator + DeleteName.get(i));
//                        boolean result = file.delete();
//                        int buff = postDao.deleteimage(post.getPseq(),DeleteName.get(i));
//                        if(buff == 0) return false;
//                    }
//                }
//            }
//
//        }
//
//
//
//
//        // post 업데이트!
//        int result = postDao.updatepost(post.getContent(),post.getPseq());
//        if(result == 0)  return false;
//
//        return true;
//    }
//
//}
