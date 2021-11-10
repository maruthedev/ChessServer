package dao;

import model.Player;

import java.util.ArrayList;

public class RankingTableDAO extends DAO{
    public RankingTableDAO() {
        super();
    }

    public ArrayList<Player> showRankingTable(String s){
        ArrayList<Player> re = (ArrayList<Player>)session.createQuery("from RankingTable where scope = s").list();
        return re;
    }
}
