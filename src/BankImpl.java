import java.rmi.*;
import java.rmi.server.*;
import java.sql.*;
import java.util.Random;

public class BankImpl extends UnicastRemoteObject implements BankInt
{
     public BankImpl()throws RemoteException
    {
       super();
    }

    int custid=0,accountno=0,balance=0,mobileno,age;
    String name,password;
    Random random = new Random();
    int min = 8826843,max = 10378954;
    int min2 = 1001,max2 = 100000;
    static Connection con = Bankconnection.getConnection();
    //static String sql = "";


    public int create(int age,int mobileno, int blnce, String name,String password) throws RemoteException{
        int accno = (int) (Math.random()*(max-min+1)+min);
        int cid = (int) (Math.random()*(max2-min2+1)+min2);
        try {
            String query = " INSERT INTO BalanceInformation (Accountno, Balance) VALUES ('"+accno+"','"+blnce+"')";
            String query2 = " INSERT INTO CustomerInformation(CustID,CustName,CustAge,MobileNO,Accountno) VALUES ('"+cid+"','"+name+"','"+age+"','"+mobileno+"','"+accno+"')";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            PreparedStatement preparedStmt2 = con.prepareStatement(query2);
            preparedStmt.executeUpdate();
            preparedStmt2.executeUpdate();
            Statement stmt = con.createStatement();
            String query3 = " INSERT INTO Users (Password, CustID) VALUES ('"+password+"','"+cid+"')";
            PreparedStatement preparedStmt3 = con.prepareStatement(query3);
            preparedStmt3.executeUpdate();
           // con.close();
        }
        catch (Exception e) {
            System.out.println("Not working   " + e.getMessage());
        }
        return cid;
    }

    public int login(int uname, String password) throws RemoteException{

        int a = 0;
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users where CustID='"+uname+"' AND Password='"+password+"'");
            if(rs.next())
            {
                custid = rs.getInt("CustID");
                a=1;
            }else{
                a=0;
            }
        }
        catch (Exception e) {
            System.out.println("not working   " + e.getMessage());
        }
        return a;
    }


    public int deposit(int dp,int uname)throws RemoteException
    {
        try {

            Statement stmt = con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM customerinformation where CustID='"+uname+"'");
            while(rs2.next()) {
                accountno = rs2.getInt("Accountno");
            }
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM balanceinformation where Accountno='"+accountno+"'");
            while(rs3.next()) {
                balance = rs3.getInt("Balance");
                balance = balance + dp;
                String query = "UPDATE balanceinformation SET  Balance='" + balance + "' where Accountno='" + accountno + "'";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();
            }
           // con.close();
        }
        catch (Exception e) {
            System.out.println("not working   " + e.getMessage());
        }
        return balance;
    }


    public int withdraw(int a,int uname)throws RemoteException
    {
        int n=0;
        try {
            //custid= 12345;
            Statement stmt = con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM customerinformation where CustID='"+uname+"'");
            while(rs2.next()){
            accountno = rs2.getInt("Accountno");
            }
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM balanceinformation where Accountno='"+accountno+"'");
            while(rs3.next()){
            balance = rs3.getInt("Balance");
            if(a<balance){

                balance=balance-a;
                String query ="UPDATE balanceinformation SET  Balance='"+balance+"' where Accountno='"+accountno+"'";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();
                //con.close();
                n= balance;
            }
            else {
                n= 2;
            }}
        }
        catch (Exception e) {
            System.out.println("not working   " + e.getMessage());
        }
        return n;
    }


    public int balance(int uname)throws RemoteException
    {
        int amt=0;
        try{

            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery("SELECT * FROM BalanceInformation where Accountno='"+acct+"'");
//            while(rs.next()){
//                amt=rs.getInt("Balance");
//            }
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM customerinformation where CustID='"+uname+"'");
            while(rs2.next()) {
                accountno = rs2.getInt("Accountno");
            }
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM balanceinformation where Accountno='"+accountno+"'");
            while(rs3.next()){
                amt = rs3.getInt("Balance");

            }
        }
        catch (Exception e) {
            System.out.println("not working   " + e.getMessage());
        }
        return amt;

    }
    public int transfer(int tramt, int tracc, int uname)throws RemoteException{
        int a=0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM customerinformation where CustID='"+uname+"'");
            while(rs2.next()) {
                accountno = rs2.getInt("Accountno");
            }
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM balanceinformation where Accountno='"+accountno+"'");
            while(rs3.next()){
            balance = rs3.getInt("Balance");
            if(tramt<balance){
                balance=balance-tramt;
                String query ="UPDATE balanceinformation SET  Balance='"+balance+"' where Accountno='"+accountno+"'";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();
                ResultSet rs4 = stmt.executeQuery("SELECT * FROM balanceinformation where Accountno='"+tracc+"'");
                while(rs4.next()) {
                    int blnc = rs4.getInt("Balance");
                    blnc = blnc + tramt;
                    String query1 = "UPDATE balanceinformation SET  Balance='" + blnc + "' where Accountno='" + tracc + "'";
                    PreparedStatement preparedStmt1 = con.prepareStatement(query1);
                    preparedStmt1.executeUpdate();
                }
                //con.close();
                a=balance;
            }
            else {
                a=2;
            }}
        }
        catch (Exception e) {
            System.out.println("not working   " + e.getMessage());
        }
        return a;
    }


    public int transferother(int tramt,int uname)throws RemoteException{
        int a=0;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM customerinformation where CustID='"+uname+"'");
            while(rs2.next()) {
                accountno = rs2.getInt("Accountno");
            }
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM balanceinformation where Accountno='"+accountno+"'");
            while(rs3.next()){
            balance = rs3.getInt("Balance");
            if(tramt<balance){
                balance=balance-tramt;
                String query ="UPDATE balanceinformation SET  Balance='"+balance+"' where Accountno='"+accountno+"'";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();
               // conn.close();
                a=balance;
            }
            else {
                a=2;
            }}
        }
        catch (Exception e) {
            System.out.println("not working   " + e.getMessage());
        }
        return a;
    }


    public int utility(int amount,int uname)throws RemoteException{

        int a=0;
        try {

            Statement stmt = con.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM customerinformation where CustID='"+uname+"'");
            while(rs2.next()) {
                accountno = rs2.getInt("Accountno");
            }
            ResultSet rs3 = stmt.executeQuery("SELECT * FROM balanceinformation where Accountno='"+accountno+"'");
            while(rs3.next()){
            balance = rs3.getInt("Balance");
            if(amount<balance){
                balance=balance-amount;
                String query ="UPDATE balanceinformation SET  Balance='"+balance+"' where Accountno='"+accountno+"'";
                PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.executeUpdate();
                ResultSet rs4 = stmt.executeQuery("SELECT * FROM balanceinformation where Accountno='"+832865+"'");
               while(rs4.next()) {
                   int blce = rs4.getInt("Balance");
                   blce = blce + amount;
                   String query1 = "UPDATE balanceinformation SET  Balance='" + blce + "' where Accountno='" + 832865 + "'";
                   PreparedStatement preparedStmt1 = con.prepareStatement(query1);
                   preparedStmt1.executeUpdate();
               }
                //con.close();
                a=balance;
            }
            else {
                a=2;
            }
            }
        }
        catch (Exception e) {
            System.out.println("not working   " + e.getMessage());
        }
        return a;
    }
}
