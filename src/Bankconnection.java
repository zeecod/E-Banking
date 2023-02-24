import java.sql.Connection;
import java.sql.DriverManager;

// Global connection Class
public class Bankconnection {
    static Connection con; // Global Connection Object
    public static Connection getConnection()
    {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/bankdatabse"; //mysql url
            String user = "root";	 //mysql username
            String pass = "zeki1234"; //mysql passcode
            System.out.println("DB username password and url initiated");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver registered");
            System.out.println ("driver.newInstance gotten.");
            con = DriverManager.getConnection(url, user, pass);
            System.out.println ("Connection gotten: " + con + ".");
        }
        catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            System.out.println("Exception :"+e);
        }

        return con;
    }
}
