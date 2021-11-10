package dao;

import model.BeFriend;
import model.Player;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class BeFriendDAO extends DAO {
    public BeFriendDAO() {
        super();
    }

    public ArrayList<Player> friendList(Player p) {
        ArrayList<Player> playerList = null;
        try {
            Query query = session.createQuery("FROM Player WHERE id " +
                    "IN(SELECT player1 FROM BeFriend WHERE player.id = :i AND status = 'friend')");
            Query query1 = session.createQuery("FROM Player WHERE id " +
                    "IN(SELECT player FROM BeFriend WHERE player1.id = :i AND status = 'friend')");
            query.setParameter("i", p.getId());
            query1.setParameter("i",p.getId());
            playerList = new ArrayList<>(query.list());
            playerList.addAll(query1.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerList;
    }

    public ArrayList<Player> requestList(Player p) {
        ArrayList<Player> playerList = null;
        try {
            Query query = session.createQuery("FROM Player WHERE id " +
                    "IN(SELECT player FROM BeFriend WHERE player1.id = :id AND status = 'request')");
            query.setParameter("id", p.getId());
            playerList = new ArrayList<>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerList;
    }

    public boolean addFriend(Player player, Player other) {
        boolean success = false;
        try {
            int i = player.getId();
            int i1 = other.getId();
            if(i != i1){
                Query query = session.createQuery("From BeFriend WHERE player.id = :i AND player1.id = :i1");
                query.setParameter("i",i);
                query.setParameter("i1",i1);
                ArrayList<Player> re = new ArrayList<>(query.list());
                if(re.size() == 0){
                    BeFriend bf = new BeFriend(0,"request",player, other);
                    Transaction trans = session.getTransaction();
                    if(!trans.isActive()) trans.begin();
                    session.save(bf);
                    trans.commit();
                    success = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean acceptFriend(ArrayList<Player> players){
        boolean success = false;
        try {
            Player player = players.get(0);
            Player other = players.get(1);
            Query query = session.createQuery("FROM BeFriend " +
                    "WHERE player.id = :i AND player1.id = :i1");
            query.setParameter("i",player.getId());
            query.setParameter("i1",other.getId());
            ArrayList<BeFriend> re = new ArrayList<>(query.list());
            BeFriend bf = re.get(0);
            Transaction trans = session.getTransaction();
            if(!trans.isActive()) trans.begin();

            if(players.size() == 2){
                bf.setStatus("friend");
                session.update(bf);
                trans.commit();
                success  = true;
                System.out.println("add");
            }else{
                session.delete(bf);
                trans.commit();
                System.out.printf("ref");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return success;
    }
}
