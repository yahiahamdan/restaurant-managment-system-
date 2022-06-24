package sample;

import java.sql.Connection;
import java.sql.DriverManager;

public class databaseconnect {

    public static  String dbname="cafeproject";
    public static String user ="root";
    public static String pass ="";
    static Connection con;
    private  databaseconnect(){

    }
    public  static  Connection getconnect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbname,user,pass);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return con;
    }

}

