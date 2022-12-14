package demo.recycling.service;

import com.zaxxer.hikari.metrics.prometheus.PrometheusMetricsTrackerFactory;
import demo.recycling.dto.Member;
import demo.recycling.dto.Room;
import demo.recycling.dto.Tag;
import demo.recycling.dto.Users;
import demo.recycling.repository.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomDao roomDao;
    @Autowired
    Room room;


    public int insertRoom(Room room) {
        try {
            //System.out.println("!!!"+room.getNickname());
            String email = roomDao.selectUserInfo(room.getNickname());
            if (email == null) {
                return -1;
            }
            String tagString = "";
            if (room.getTags() != null || !room.getTags().isEmpty()) {
                tagString = String.join("$", room.getTags());
            }
            room.setTag(tagString);
            room.setUserEmail(email);

            roomDao.insertRoom(room);

            int rum = roomDao.selectRum(email);

            if (rum < 0) {
                return rum;
            }

            roomDao.insertMember(rum, room.getNickname());
            roomDao.insertTag(rum, tagString);

            return rum;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//    public int insertTag(Room room, String tags){
//        try{
//            String email = roomDao.selectUserInfo(room.getNickname());
//            int rum = roomDao.selectRum(email);
//            //tag.setRum(rum);
//            return roomDao.insertTag(rum, tags);
//        }catch(Exception e){
//            e.printStackTrace();
//            return 0;
//        }
//    }

//    public int insertMember(String nickname){
//        try{
//            String email = roomDao.selectUserInfo(nickname);
//            int rum = roomDao.selectRum(email);
//            //member.setRum(rum);
//            return roomDao.insertMember(rum, nickname);
//        }catch (Exception e){
//            e.printStackTrace();
//            return 0;
//        }
//    }

    public List<Room> selectMyRoom(String nickname) {
        try {
            List<Room> rooms = roomDao.selectMyRoom(nickname);
            if (rooms.isEmpty()) return rooms;
            for (Room room : rooms) {
                //System.out.println(room.getTag());

                room.setTags(Arrays.asList(room.getTag().split("\\$")));
                room.setTag(null);
            }
            //return roomDao.selectMyRoom(nickname);
            return rooms;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    public List<String> selectTag(String nickname) {
        try{
            List<String> tags = roomDao.selectTag(nickname);
            System.out.println(tags.toString());
            return roomDao.selectTag(nickname);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    */


    // post
    public String joinRoom(Member member) {
        try {
            List<Room> roomList = roomDao.selectRoom(member.getRum());
            Member members = roomDao.nicknameCheck(member.getNickname(), member.getRum());
            if (members != null) {
                return "existsUser";
            } else {
                if (roomList.get(0).getCount() >= roomList.get(0).getMaxnum()) {
                    return "countOver";
                } else {
                    return "countOK";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
    public String insertRoomMember(Member member){
        try {
            List<Room> roomList = roomDao.selectRoom(member.getRum());
            Member members = roomDao.nicknameCheck(member.getNickname() , member.getRum());
            if (members != null) {
                return "existsUser";
            } else {
                if(roomList.get(0).getCount() >= roomList.get(0).getMaxnum()){
                    return "countOver";
                }else {
                    int result = roomDao.joinRoom(member.getRum());
                    roomDao.insertMember(member.getRum(), member.getNickname());
                    if (result < 1) {
                        return "false";
                    }
                    return "true";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

//    public boolean insertRoomMember(Member member){
//        try{
//            roomDao.insertMember(member.getRum(), member.getNickname());
//            roomDao.joinRoom(member.getRum());
//            return true;
//        }catch(Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    // keyword??? ?????? TagList ??????
    public List<Room> selectTag(String keyword) {
        try {
            List<Room> searchList = new ArrayList<Room>();
            List<Room> rooms = roomDao.selectTag();
            String tagName = "";
            for (int i = 0; i < rooms.size(); i++) {
                if ((tagName = rooms.get(i).getTag()) != null) {
                    //System.out.println(rooms.get(i).getTag());
                    //String[] tag_name = StringUtils.split(room.getTag(), "\\$");
                    //List<String> tags = Arrays.asList(tag_name);
                    List<String> tags =new ArrayList<>();
                    tags.addAll(Arrays.stream(tagName.split("\\$")).toList());
                    tags.removeAll(Arrays.asList("",null));
                    rooms.get(i).setTags(tags);
                    if (tags.contains(keyword)) {
                        searchList.add(rooms.get(i));
                    }
                }
            }

            return searchList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // keyword??? ?????? TitleList ??????
    public List<Room> selectTitle(String keyword) {
        try {
            List<Room> rooms = roomDao.selectTitle(keyword);
            return rooms;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tag,Title List ???????????? ??? ??????
    public List<Room> searchRoom(String keyword) {
        try {

            List<Room> roomListAll = new ArrayList<>(); // ?????? ?????????
            List<Integer> intersection = new ArrayList<>(); // ????????? ?????????


                //???????????? ????????? tag list,title list
                List<Room> tagList = selectTag(keyword);
                List<Room> titleList = selectTitle(keyword);

                //title??? ????????? tag??? split
                for (int i = 0; i < titleList.size(); i++) {
                    String tagName = null;

                    if ((tagName = titleList.get(i).getTag())!=null) {

                        List<String> tags = new ArrayList<>();
                        tags.addAll(Arrays.stream(tagName.split("\\$")).toList());
                        // System.out.println(tags.size());
                        if(tags.contains("")){
                            if(tags.size()==1){
                                List<String> notags=new ArrayList<>();
                                titleList.get(i).setTags(notags);
                            }else{
                                tags.removeAll(Arrays.asList("", null));
                                titleList.get(i).setTags(tags);
                            }
                        }else{
                            titleList.get(i).setTags(tags);
                        }

                    }
                }

                //System.out.println(tagList.size());
                //System.out.println(titleList.size());
                //????????? ???????????? ?????? ??? ?????? ????????? list??? ?????? (rum????????? ??????)
                for (int i = 0; i < tagList.size(); i++) {
                    for (int j = 0; j < titleList.size(); j++) {
                        if (tagList.get(i).getRum() == titleList.get(j).getRum()) {
                            roomListAll.add(tagList.get(i));
                            intersection.add(tagList.get(i).getRum());
                        }
                    }
                }
                //tag list ?????? ??????
                for (int i = 0; i < tagList.size(); i++) {
                    if (!intersection.contains(tagList.get(i).getRum())) {
                        roomListAll.add(tagList.get(i));
                    }
                }
                //title list ?????? ??????
                for (int i = 0; i < titleList.size(); i++) {
                    if (!intersection.contains(titleList.get(i).getRum())) {
                        roomListAll.add(titleList.get(i));
                    }
                }
                Collections.sort(roomListAll,Collections.reverseOrder());

            return roomListAll;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}