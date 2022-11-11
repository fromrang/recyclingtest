package demo.recycling.service;

import demo.recycling.dto.RecylingMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {
    @Autowired
    RecylingMap recylingMap;

    final int EARTH_RADIUS = 6371;

    //폐건지&형광등 list
    List<RecylingMap> CsvWasteBattery(){
        List<RecylingMap> recylingMaps=new ArrayList<>();
        File csv = new File("/home/ubuntu/test/WEB-INF/classes/static/WasteBattery.csv");
        //System.out.println("!!"+csv.getPath());
        BufferedReader br = null;
        String line = "";
        int count=0;
        try {
            br = new BufferedReader(new FileReader(csv));
            while((line = br.readLine()) != null) {
                //WasteBattery를 new 해서 list안에 담음
                RecylingMap WasteBattery=new RecylingMap();
                List<String> aLine = new ArrayList<String>();
                String[] linArr = line.split(",");
                if(!linArr[0].equals("name")&&!linArr[1].equals("adderess")&&!linArr[2].equals("number")&&
                        !linArr[3].equals("lat")&&!linArr[4].equals("long")){
                    WasteBattery.setName(linArr[0]);
                    WasteBattery.setAdderess(linArr[1]);
                    WasteBattery.setNumber(linArr[2]);
                    System.out.println(count+"  :  "+linArr[1]);
                    WasteBattery.setLat(Double.valueOf(linArr[3]));
                    WasteBattery.setLon(Double.valueOf(linArr[4]));
                    WasteBattery.setCode(1);
                    recylingMaps.add(WasteBattery);
                    count+=1;
                }
            }
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null) {
                    br.close();
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        return recylingMaps;

    }

    // 쓰레기통 list
    List<RecylingMap> CsvWastebasket(){

        List<RecylingMap> recylingMaps=new ArrayList<>();
        File csv = new File("/home/ubuntu/test/WEB-INF/classes/static/TrashBin.csv");
        //System.out.println("!!"+csv.getPath());
        BufferedReader br = null;
        String line = "";

//        System.out.println("쓰레기통이야");
        int count = 1;

        try {
            br = new BufferedReader(new FileReader(csv));
            while((line = br.readLine()) != null) {
                RecylingMap wastebasket=new RecylingMap();
                List<String> aLine = new ArrayList<String>();
                String[] linArr = line.split(",");
                //  System.out.println(count+"번째 , 이름 : "+linArr[0]);
                //  System.out.println(count+"번째 , 주소 : "+linArr[1]);
                //  System.out.println(count+"번째 , 전화번호 : "+linArr[2]);
                // System.out.println(count+"번째 , lat : "+linArr[3]);
                //  System.out.println(count+"번째 , long : "+linArr[4]);
                if(!linArr[0].equals("name")&&!linArr[1].equals("adderess")&&!linArr[2].equals("number")&& !linArr[3].equals("lat")&&!linArr[4].equals("long")){
                    wastebasket.setName(linArr[0]);
                    wastebasket.setAdderess(linArr[1]);
                    wastebasket.setNumber(linArr[2]);
                    wastebasket.setLat(Double.valueOf(linArr[3]));
                    wastebasket.setLon(Double.valueOf(linArr[4]));
                    wastebasket.setCode(2);
                    recylingMaps.add(wastebasket);

                }
                count++;
            }


        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null) {
                    br.close();
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        return recylingMaps;

    }


    //센터 list
    List<RecylingMap> CsvWasteCenter(){
        List<RecylingMap> recylingMaps=new ArrayList<>();
        File csv = new File("/home/ubuntu/test/WEB-INF/classes/static/RecyclingCenter.csv");
        //System.out.println("!!"+csv.getPath());
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while((line = br.readLine()) != null) {
                //WasteBattery를 new 해서 list안에 담음
                RecylingMap WasteCenter=new RecylingMap();
                List<String> aLine = new ArrayList<String>();
                String[] linArr = line.split(",");
                if(!linArr[0].equals("name")&&!linArr[1].equals("adderess")&&!linArr[2].equals("number")&&!linArr[3].equals("lat")&&!linArr[4].equals("long")){
                    WasteCenter.setName(linArr[0]);
                    WasteCenter.setAdderess(linArr[1]);
                    WasteCenter.setNumber(linArr[2]);
                    WasteCenter.setLat(Double.valueOf(linArr[3]));
                    WasteCenter.setLon(Double.valueOf(linArr[4]));
                    WasteCenter.setCode(0);
                    recylingMaps.add(WasteCenter);
                }
            }
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(br != null) {
                    br.close();
                }
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        return recylingMaps;

    }


    //m떨어진 지점의 두 지점의 거리 계산
    double getDistance(double lat1, double lon1, double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat/2)* Math.sin(dLat/2)+ Math.cos(Math.toRadians(lat1))* Math.cos(Math.toRadians(lat2))* Math.sin(dLon/2)* Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = EARTH_RADIUS* c * 1000;    // Distance in m
        return d;
    }

    //사각형 형태로 된 주변 리스트 찾기
    List<RecylingMap> getAroundRecyclingList(double maxY, double maxX, double minY, double minX, List<RecylingMap> data){

        List<RecylingMap> Filter = new ArrayList<RecylingMap>();

        for(int i = 0; i < data.size(); i++) {
            if(data.get(i).getLat() >= minY && data.get(i).getLat() < maxY && data.get(i).getLon() >= minX && data.get(i).getLon() < maxX) {
                Filter.add(data.get(i));
            }
        }


        return Filter;
    }



    // 원 형태로 된 주변 리스트 찾기 (사각형 형태로 된 리스트에서 한번더 거름)
    public List<RecylingMap> TotalAroundRecyclingMap(double nowLatitude,double nowLongitude,int code){

        List<RecylingMap> TotalAroundRecycling = new ArrayList<RecylingMap>();

        //설정한 거리 값
        int meters = 500;

        //m당 y 좌표 이동 값
        double mForLatitude =(1 /(EARTH_RADIUS* 1 *(Math.PI/ 180)))/ 1000;
        //m당 x 좌표 이동 값
        double mForLongitude =(1 /(EARTH_RADIUS* 1 *(Math.PI/ 180)* Math.cos(Math.toRadians(nowLatitude))))/ 1000;

        //현재 위치 기준 검색 거리 좌표
        double maxY = nowLatitude +(meters* mForLatitude);
        double minY = nowLatitude -(meters* mForLatitude);
        double maxX = nowLongitude +(meters* mForLongitude);
        double minX = nowLongitude -(meters* mForLongitude);

        // 센터 0, 배터리 1, 쓰레기통 2
        if(code == 0) TotalAroundRecycling = getAroundRecyclingList(maxY, maxX, minY, minX,CsvWasteCenter());
        else if(code == 1) TotalAroundRecycling = getAroundRecyclingList(maxY, maxX, minY, minX,CsvWasteBattery());
        else if(code == 2) TotalAroundRecycling = getAroundRecyclingList(maxY, maxX, minY, minX,CsvWastebasket());

        List<RecylingMap> Filter = new ArrayList<RecylingMap>();

        for(int i = 0; i < TotalAroundRecycling.size(); i++) {
            double distance = getDistance(nowLatitude,nowLongitude,TotalAroundRecycling.get(i).getLat(),TotalAroundRecycling.get(i).getLon());
            if(distance < meters) {
                Filter.add(TotalAroundRecycling.get(i));
            }
        }

        if(Filter.size()==0) {
            RecylingMap noReturn =new RecylingMap();
            if(code==0) noReturn.setName("주위에 재활용센터가 없습니다.");
            else if (code==1) noReturn.setName("주위에 폐건전지&형광등이 없습니다");
            else noReturn.setName("주위에 쓰레기통&재활용이 없습니다.");
            Filter.add(noReturn);
            return Filter;
        }
        System.out.println("총 사이즈"+Filter.size());

        return Filter;
    }

}
