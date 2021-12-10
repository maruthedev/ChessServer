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
            query1.setParameter("i", p.getId());
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

    public boolean addFriend(BeFriend bf) {
        boolean success = false;
        try {
            Query query = session.createQuery("From BeFriend WHERE player.id = :i AND player1.id = :i1");
            query.setParameter("i", bf.getPlayer().getId());
            query.setParameter("i1", bf.getPlayer1().getId());
            ArrayList<Player> re = new ArrayList<>(query.list());
            if (re.size() == 0) {
                Transaction trans = session.getTransaction();
                if (!trans.isActive()) trans.begin();
                session.save(bf);
                trans.commit();
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean acceptFriend(BeFriend bf) {
        boolean success = false;
        try {
            Query query = session.createQuery("FROM BeFriend WHERE player.id = :i AND player1.id = :i1");
            query.setParameter("i", bf.getPlayer().getId());
            query.setParameter("i1", bf.getPlayer1().getId());
            ArrayList<BeFriend> list = new ArrayList<>(query.list());
            BeFriend beFriend = list.get(0);

            Transaction trans = session.getTransaction();
            if (!trans.isActive()) trans.begin();

            if (bf.getStatus().equals("accept")) {
                beFriend.setStatus("friend");
                session.update(beFriend);
                trans.commit();
                success = true;
            } else if (bf.getStatus().equals("refuse")){
                session.delete(beFriend);
                trans.commit();
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean deleteFriend(BeFriend bf) {
        boolean success = false;
        try {
            Query query = session.createQuery("FROM BeFriend " +
                    "WHERE player.id = :i AND player1.id = :i1");
            Query query1 = session.createQuery("FROM BeFriend " +
                    "WHERE player.id = :i1 AND player1.id = :i");
            query.setParameter("i", bf.getPlayer().getId());
            query.setParameter("i1", bf.getPlayer1().getId());

            query1.setParameter("i", bf.getPlayer().getId());
            query1.setParameter("i1", bf.getPlayer1().getId());

            ArrayList<BeFriend> re = new ArrayList<>(query.list());
            re.addAll(query1.list());
            BeFriend beFriend = re.get(0);
            Transaction trans = session.getTransaction();
            if (!trans.isActive()) trans.begin();

            session.delete(beFriend);
            trans.commit();
            success = true;
            System.out.println("delete");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
