package dao;

import model.Group;

import java.util.ArrayList;

public class GroupDAO extends DAO {
    public GroupDAO() {
        super();
    }

    public ArrayList<Group> showGroupList() {
        ArrayList<Group> re = (ArrayList<Group>) session.createQuery("from Group").list();
        return re;
    }
}
