package dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
    public static Session session;
//    public static Connection con;

    public DAO(){
        if(session == null){
            try {
                session = new Configuration().configure(new File("resources/Hibernate.config.xml"))
                        .buildSessionFactory().openSession();
            }catch (HibernateException ex) {
                ex.printStackTrace();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

//        if(con == null){
//            String dbUrl = "jdbc:mysql://localhost:3306/chess?autoReconnect=true&useSSL=false";
//            String dbClass = "com.mysql.jdbc.Driver";
//
//            try {
//                Class.forName(dbClass);
//                con = DriverManager.getConnection (dbUrl, "root", "notapw128.");
//            }catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
    }
}