package dao;

import model.Group;
import model.Player;

import java.util.ArrayList;

public class PlayerInGroupDAO extends DAO{
    public PlayerInGroupDAO() {
        super();
    }

    public boolean joinGroup(Player p, Group g){
        boolean success = false;
        int pid = p.getId();
        int gid = g.getId();
        try{
            ArrayList<Player> re = (ArrayList<Player>) session.createQuery("from PlayerInGroup" +
                    "where idPlayer = pid and idGroup = gid").list();
            if(re.size() == 0){
                session.createQuery("insert into PlayerInGroup(pid,gid)" +
                        "select idPlayer, idGroup from PlayerInGroup").executeUpdate();
                success = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
}
