package dao;

import model.Player;
import model.Tournament;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ParticipationDAO extends DAO{
    public ParticipationDAO() {
        super();
    }

    public boolean register(Player p, Tournament t){
        boolean success = false;
        int tid = t.getId();
        int pid = p.getId();
        try{
            ArrayList<Player> re = (ArrayList<Player>) session.createQuery("from ParticipateATournament" +
                    "where idTournament = tid and idPlayer = pid").list();
            if(re.size() == 0){
                LocalDateTime st = t.getStartTime();
                LocalDateTime et = t.getEndTime();
                session.createQuery("insert into ParticipateATournament(st,et,pid,tid)" +
                        "select startTime, endTime, idPlayer, idTournament from ParticipateATournament").executeUpdate();
                success = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
}
