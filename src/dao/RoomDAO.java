package dao;

import model.Player;
import model.Room;

import java.util.ArrayList;

public class RoomDAO extends DAO{
    public RoomDAO(){
        super();
    }

    public ArrayList<Room> showRoomList(){
        ArrayList<Room> re = (ArrayList<Room>)session.createQuery("from Room").list();
        return re;
    }

    public void createRoom(Player p){
        try{
            String status = "ready";
            session.createQuery("insert into Room(status)" +
                    "select status from Room").executeUpdate();
            p.setStatus("in room");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
