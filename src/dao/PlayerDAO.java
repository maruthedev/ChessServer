package dao;

import model.Player;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class PlayerDAO extends DAO {
    public PlayerDAO() {
        super();
    }

    public Player checkLogin(Player p) {
        try {
            Query query = session.createQuery("FROM Player WHERE username = :un AND password = :pw AND status = 'away'");
            query.setParameter("un", p.getUsername());
            query.setParameter("pw", p.getPassword());
            ArrayList<Player> re = new ArrayList<>(query.list());
            if (re.size() == 1) {
                Transaction trans = session.getTransaction();
                if (!trans.isActive()) trans.begin();
                re.get(0).setStatus("online");
                session.update(re.get(0));
                trans.commit();
                return re.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public ArrayList<Player> onlinePlayer() {
        ArrayList<Player> re = new ArrayList<>();
        try {
            Query query = session.createQuery("FROM Player WHERE status = :onl OR status =:inm");
            query.setParameter("onl", "online");
            query.setParameter("inm","in match");
            re = new ArrayList<Player>(query.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }

    public boolean logout(Player p) {
        boolean success = false;
        try {
            Query query = session.createQuery("FROM Player WHERE username = :un");
            query.setParameter("un", p.getUsername());
            ArrayList<Player> re = new ArrayList<>(query.list());
            if (re.size() == 1) {
                Transaction trans = session.getTransaction();
                if (!trans.isActive()) trans.begin();
                re.get(0).setStatus("away");
                session.update(re.get(0));
                trans.commit();
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }


    public boolean createAccount(Player p) {
        boolean success = false;
        try {
            String un = p.getUsername();
            String pw = p.getPassword();
            ArrayList<Player> re = (ArrayList<Player>) session.createQuery("from Player where username = un").list();
            if (re.size() == 0) {
                p.setStatus("Online");
                p.setWins(0);
                p.setLoses(0);
                session.createQuery("insert into Player(un,pw,'Online','0','0')" +
                        "select username, password, status, wins, loses from Player").executeUpdate();
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }
}
