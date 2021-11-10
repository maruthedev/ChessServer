package dao;

import model.Tournament;

import java.util.ArrayList;

public class TournamentDAO extends DAO{
    public TournamentDAO() {
        super();
    }

    public ArrayList<Tournament> showTournamentList(){
        ArrayList<Tournament> re = (ArrayList<Tournament>)session.createQuery("from Tournament").list();
        return re;
    }
}
