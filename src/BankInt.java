import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BankInt extends Remote{

    int withdraw(int a, int uname)throws RemoteException;
    int deposit(int dp,int uname)throws RemoteException;
    int balance(int amt)throws RemoteException;
    int transfer(int amount, int balance, int uname)throws RemoteException;
    int transferother(int tramt,int uname)throws RemoteException;
    int utility(int amount, int uname)throws RemoteException;
    int login(int uname, String password) throws RemoteException;
    int create(int age,int mobileno, int balance, String name,String password) throws RemoteException;
}