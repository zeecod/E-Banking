import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.Naming;

public class BankServer
{
    public static void main(String[] args)
    {
        try
        {
            BankImpl bankimpl=new BankImpl();
            Naming.rebind("rmi://localhost:1099/BankServer",bankimpl);
            System.out.println("Server is ready");
        }
        catch(Exception e)
        {
            System.out.println("Exception :"+e);
        }
    }
}


