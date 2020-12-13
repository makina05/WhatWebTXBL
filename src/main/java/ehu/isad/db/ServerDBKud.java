package ehu.isad.db;

import ehu.isad.model.Lag2;
import ehu.isad.model.Laguntzailea;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerDBKud {
    String server;
    String se;
    private static final ServerDBKud instance = new ServerDBKud();

    public static ServerDBKud getInstance(){
        return instance;
    }

    private ServerDBKud(){
    }

    public List<Lag2> lortuEskaneatutakoak(){

        String query = "select distinct t.target, s.string from targets t inner join scans s on t.target_id=s.target_id where s.string like '%nginx%' or s.string like '%Apache%' or s.string like '%Node.js%' and s.string like '%(%'";
        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
        ResultSet rs = dbKudeatzaile.execSQL(query);

        List<Lag2> emaitza = new ArrayList<>();
        try{
            while (rs.next()){

                String target = rs.getString("target");
                if (rs.getString("string").contains(" ")) {
                    if (rs.getString("string").split(" ")[0] == null) {
                        this.server = "Unknown";
                        this.se = "Unknown";
                    } else if (rs.getString("string").split(" ")[1] == null) {
                        this.server = rs.getString("string").split(" ")[0];
                        this.se = "Unknown";
                    } else {
                        this.server = rs.getString("string").split(" ")[0];
                        this.se = rs.getString("string").split(" ")[1];
                    }
                }
                else{
                    this.server=rs.getString("string");
                    this.se = "Unknown";
                }

                emaitza.add(new Lag2(target,server,se));

            }
        } catch (SQLException throwables){
            throwables.printStackTrace();
        }
        return emaitza;
    }
//    public List<Lag2> lortuServerra(){
//        String query = "select s.string from scans s where s.string like '%Apache%'";
//        DBKudeatzaile dbKudeatzaile = DBKudeatzaile.getInstantzia();
//        ResultSet rs = dbKudeatzaile.execSQL(query);
//
//        List<Lag2> emaitza = new ArrayList<>();
//        try{
//            while (rs.next()){
//
//                String server = rs.getString("string");
//
//                emaitza.add(new Lag2(server));
//
//            }
//        } catch (SQLException throwables){
//            throwables.printStackTrace();
//        }
//        return emaitza;
//    }
}

