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
public class    PostService {
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
                    //String image="/home/rang/yogidamayo/app/WEB-INF/classes/static/image/"+ imageFiter.get(i).getImage_name();
                    String image="/images/"+ imageFiter.get(i).getImage_name();
                    imageFiter.get(i).setImage_name(image);
                    imageList.add(imageFiter.get(i));
                }
                postList.setImageList(imageList);
            }
            String title= roomDao.postRumTitle(rum);
            postAll.put("content",postLists);
            postAll.put("title",title);
            //System.out.println(title);
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

            //String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";
            String UPDATE_PATH = "/home/rang/frontend/build/images/";
            List<Image> images = postDao.selectImage(pseq);

            int result = postDao.deletePost(pseq);
            int result2 = postDao.deleteImage(pseq);
            //System.out.println("!!!"+result2);
            if(result > 0){

                if(result2 >= 1){
                    // ????????? ????????????
                    if(images.size() != 0){
                        for(int i = 0; i < images.size(); i++){
                            File file = new File(UPDATE_PATH + File.separator + images.get(i).getImage_name());
                            boolean result_f = file.delete();
                        }
                    }
                }

                return true;
            }else {
                return false;
            }




        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    // ????????? Post ????????? ??????.
    public boolean insertPost(List<MultipartFile> files, Post post) throws Exception{

        // String UPDATE_PATH = "/home/rang/yogidamayo/app/WEB-INF/classes/static/image/";
        String UPDATE_PATH = "/home/rang/frontend/build/images/";
        // String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";

        List<String> imageName = new ArrayList<>();
        int buff = 0;
        int pseq = 0;

        // post ????????? ??????.
        int result  = postDao.insertpost(post);

        try{
            // ????????? ?????? ?????? ?????? ??????
            File destdir = new File(UPDATE_PATH);

            // ????????? ?????? ???????????? ????????? ??????(????????????) ??????.
            if(!destdir.exists()){
                destdir.mkdirs();

                Runtime.getRuntime().exec("chmod 777 " + UPDATE_PATH);
                destdir.setExecutable(true, false);
                destdir.setReadable(true, false);
                destdir.setWritable(true, false);

                destdir.createNewFile();

            }

            // post ?????? ?????? ??? , ???????????? ??????.
            if(result != 0){

                for (MultipartFile file : files) {
                    // ?????? ?????? ?????? ????????? ?????? ?????? (????????????+?????????)
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                    String fileId = (sdf1.format(System.currentTimeMillis()) + "" + (new Random().ints(1000, 9999).findAny().getAsInt()));
                    String orignalfileName = file.getOriginalFilename(); // ????????????

                    //if(!file.equals("")){
                    String fileExtension = orignalfileName.substring(orignalfileName.lastIndexOf(".") + 1); // .?????????
                    String finalName = fileId + "." + fileExtension; // DB??? ?????? ?????? ??????.
                    imageName.add(finalName); // ????????? ??????????????? ??????.
                    File dest = new File(UPDATE_PATH + fileId + "." + fileExtension);
                    file.transferTo(dest);
                    //}
                }

                pseq = postDao.selectpseq();

                // ???????????? ??????.
                for (int i = 0; i < imageName.size(); i++) {
                    buff = postDao.insertimage(imageName.get(i), pseq);
                    if (buff == 0) {// ????????? ?????? ?????? ???, post ?????? ??????
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



    public boolean insertPost(Post post) throws Exception{

        // post ????????? ??????.
        int result  = postDao.insertpost(post);

        if(result == 1){
            return true;
        }
        else return false;

    }



    // ????????? Post- Content ?????? ??? ??????.
    public boolean postupdate(List<MultipartFile> files, Post post,List<String> images) throws Exception{

        List<Image> originImages = new ArrayList<>();
        int ck = 0;

        //String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";
        //String UPDATE_PATH = "/home/rang/yogidamayo/app/WEB-INF/classes/static/image/";
        String UPDATE_PATH = "/home/rang/frontend/build/images/";

        // ?????? ????????? ??????
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


        // ????????? ????????? ?????? ??????
        originImages = postDao.selectImage(post.getPseq());

        for(int i = 0; i < originImages.size(); i++){
            for(int j = 0; j < images.size(); j++){
                if(originImages.get(i).getImage_name().equals(images.get(j))){
                    ck += 1;
                }
            }
        }

        // DB??? ?????? ??? ???????????? ?????? ??? ????????? ?????? ?????? ?????? ??????
        if(ck != images.size()) return false;

        // ????????? ????????????
        if(images.size() != 0){
            for(int i = 0; i < images.size(); i++){
                File file = new File(UPDATE_PATH + File.separator + images.get(i));
                boolean result = file.delete();
                int buff = postDao.deleteimage(post.getPseq(),images.get(i));
                if(buff == 0) return false;
            }
        }


        // post ????????????!
        int result = postDao.updatepost(post.getContent(),post.getPseq());
        if(result == 0)  return false;

        return true;
    }


    // ?????? ?????? ???????????? ????????? ????????? ??????
    public boolean postupdate(Post post,List<String> images) throws Exception{

        List<Image> originImages = new ArrayList<>();
        int ck = 0;

        //String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";
        //String UPDATE_PATH = "/home/rang/yogidamayo/app/WEB-INF/classes/static/image/";
        String UPDATE_PATH = "/home/rang/frontend/build/images/";
        // ????????? ????????? ?????? ??????
        originImages = postDao.selectImage(post.getPseq());

        for(int i = 0; i < originImages.size(); i++){
            for(int j = 0; j < images.size(); j++){
                if(originImages.get(i).getImage_name().equals(images.get(j))){
                    ck += 1;
                }
            }
        }

        // DB??? ?????? ??? ???????????? ?????? ??? ????????? ?????? ?????? ?????? ??????
        if(ck != images.size()) return false;


        // ????????? ????????????
        if(images.size() != 0){
            for(int i = 0; i < images.size(); i++){
                File file = new File(UPDATE_PATH + File.separator + images.get(i));
                boolean result = file.delete();
                int buff = postDao.deleteimage(post.getPseq(),images.get(i));
                if(buff == 0) return false;
            }
        }

        // post ????????????!
        int result = postDao.updatepost(post.getContent(),post.getPseq());
        if(result == 0)  return false;


        return true;
    }




    // ??????, ????????? ?????? ???????????? ??????.
    public boolean postupdate(Post post) throws Exception{

        // post ????????????!
        int result = postDao.updatepost(post.getContent(),post.getPseq());
        if(result == 0)  return false;

        return true;

    }


    // ?????? ?????? ????????? ?????? ??????
    public boolean postupdate(List<MultipartFile> files,Post post)throws Exception{

        //String UPDATE_PATH = "/home/rang/yogidamayo/app/WEB-INF/classes/static/image/";
        // String UPDATE_PATH = "D:\\f_project\\recyclingclon\\src\\main\\resources\\static\\image\\";
        String UPDATE_PATH = "/home/rang/frontend/build/images/";

        // ?????? ????????? ??????
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

        // post ????????????!
        int result = postDao.updatepost(post.getContent(),post.getPseq());
        if(result == 0)  return false;

        return true;
    }


}




//=================

//    // ????????? Post- Content ?????? ??? ??????.
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
//        // ?????? ???????????? ????????????.
//        imageName = postDao.selectImage(post.getPseq());
//
//        // ???????????? ??? ???????????? ??????. (basicName, UpdateName ??????)
//        for(MultipartFile file : files){
//            String orignalfileName = file.getOriginalFilename();
//
//            if(!orignalfileName.equals("")){
//                // ?????? ????????? ????????? ????????? ????????? ???????????? ???????????? ????????? ?????? ?????? ????????? ???????????? ??????.
//                for(int i  = 0; i < imageName.size(); i++){
//                    if(imageName.get(i).getImage_name().equals(orignalfileName)) {
//                        basicName.add(imageName.get(i).getImage_name());
//                        check = 1;
//                    }
//                }
//                if(check == 0){ // ?????? ?????? ??? ????????? ??????.
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
//            // ?????? ???????????? ?????? ???.
//            if(basicName.size() == 0){
//                for(int i = 0; i < imageName.size(); i++){
//                    File file = new File(UPDATE_PATH + File.separator + imageName.get(i).getImage_name());
//                    boolean result = file.delete();
//                    int buff = postDao.deleteimage(post.getPseq(),imageName.get(i).getImage_name());
//                    if(buff == 0) return false;
//                }
//            }
//            else{
//                // ????????? ????????? ??????.
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
//                // ????????? ????????????
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
//        // post ????????????!
//        int result = postDao.updatepost(post.getContent(),post.getPseq());
//        if(result == 0)  return false;
//
//        return true;
//    }
//
//}
