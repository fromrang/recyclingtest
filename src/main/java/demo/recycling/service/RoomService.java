package demo.recycling.service;

import com.zaxxer.hikari.metrics.prometheus.PrometheusMetricsTrackerFactory;
import demo.recycling.dto.Member;
import demo.recycling.dto.Room;
import demo.recycling.dto.Tag;
import demo.recycling.dto.Users;
import demo.recycling.repository.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    RoomDao roomDao;
    @Autowired
    Room room;


    public int insertRoom(Room room){
        try{
            String email = roomDao.selectUserInfo(room.getNickname());
            room.setUserEmail(email);
            return roomDao.insertRoom(room);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public int insertTag(Room room, String tags){
        try{
            String email = roomDao.selectUserInfo(room.getNickname());
            int rum = roomDao.selectRum(email);
            //tag.setRum(rum);
            return roomDao.insertTag(rum, tags);
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public int insertMember(String nickname){
        try{
            String email = roomDao.selectUserInfo(nickname);
            int rum = roomDao.selectRum(email);
            //member.setRum(rum);
            return roomDao.insertMember(rum, nickname);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public List<Room> selectMyRoom(String nickname) {
        try{
            return roomDao.selectMyRoom(nickname);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> selectTag(String nickname) {
        try{
            return roomDao.selectTag(nickname);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
