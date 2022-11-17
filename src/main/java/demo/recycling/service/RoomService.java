package demo.recycling.service;

import com.zaxxer.hikari.metrics.prometheus.PrometheusMetricsTrackerFactory;
import demo.recycling.dto.Member;
import demo.recycling.dto.Room;
import demo.recycling.dto.Tag;
import demo.recycling.dto.Users;
import demo.recycling.repository.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomDao roomDao;
    @Autowired
    Room room;


    public int insertRoom(Room room){
        try{
            //System.out.println("!!!"+room.getNickname());
            String email = roomDao.selectUserInfo(room.getNickname());
            String tagString = "";
            if(room.getTags() != null || !room.getTags().isEmpty()) {
                tagString = String.join("$", room.getTags());
            }
            room.setTag(tagString);
            room.setUserEmail(email);

            roomDao.insertRoom(room);

            int rum = roomDao.selectRum(email);

            if(rum < 0){
                return rum;
            }

            roomDao.insertMember(rum, room.getNickname());
            roomDao.insertTag(rum, tagString);

            return rum;
        }catch(Exception e){
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
        try{
            List<Room> rooms = roomDao.selectMyRoom(nickname);
            if(rooms.isEmpty())return rooms;
            for(Room room:rooms){
                //System.out.println(room.getTag());

                room.setTags(Arrays.asList(room.getTag().split("\\$")));
                room.setTag(null);
            }
            //return roomDao.selectMyRoom(nickname);
            return rooms;
        }catch (Exception e) {
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

    public boolean joinRoom(Member member) {
        try {
            List<Room> roomList = roomDao.selectRoom(member.getRum());
            Member members = roomDao.nicknameCheck(member.getNickname() , member.getRum());
            if (roomList.get(0).getCount() >= roomList.get(0).getMaxnum()) {
                return false;
            } else {

                if(members != null){
                    return false;
                }else {
                    int result = roomDao.joinRoom(member.getRum());
                    roomDao.insertMember(member.getRum(), member.getNickname());
                    if (result < 1) {
                        return false;
                    }
                    return true;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
