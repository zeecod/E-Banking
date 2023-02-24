import java.rmi.Naming;
import java.util.*;

public class BankClient
{
    public static void main(String[] args)
    {
        try
        {

            BankInt bankintf=(BankInt)Naming.lookup("rmi://localhost:1099/BankServer");
            Scanner br=new Scanner(System.in);
            int ch,login;
            System.out.println("\n****** WELCOME TO E-BANKING ******");
            do{
                System.out.println("\n\t1.Login\n\t2.Create new account\n\t3.Exit");
                System.out.println("\nEnter your choice:");
                System.out.println("\n**********************************");
                login = br.nextInt();

                //Create New User

                if(login==2){
                    System.out.println("\n**********CREATE ACCOUNT**********");
                    System.out.println("\n**********************************");
                    System.out.println("\nEnter your Name:");
                    String name=br.next();
                    //int custID=br.nextInt();
                    System.out.println("\nEnter new Password:");
                    String pass=br.next();
                    System.out.println("\nEnter your Mobile Number:");
                    int mobileno= br.nextInt();
                    System.out.println("\nEnter your Age:");
                    int age = br.nextInt();
              System.out.println("\nEnter your Initial Amount:");
              int balance=br.nextInt();
                    int in =bankintf.create(age,mobileno, balance,name,pass);
                   // if (in == 1) {
                        System.out.println("Account Created Succeccfully!!");
                        System.out.println("Please use your Customer ID and your Password to login!!");
                        System.out.println("Your Username / Customer ID is  "  + in);
                        System.out.println("\n**********************************");
                   // }else{
                       // System.out.println("Account Not Created Try Again!!!");
                       // System.out.println("\n**********************************");

                   // }

                }

                //Login
                else if(login==1){
                    System.out.println("\n************* LOG-IN *************");
                    System.out.println("\n**********************************");
                    System.out.println("\nEnter your UserName:");
                    int uname= br.nextInt();
                    System.out.println("\nEnter your Password:");
                    String password= br.next();
                    int in =bankintf.login(uname,password);
                   // System.out.println("you have: "+ in);
                    //Logged in successfully
                    if(in==0)
                     {
                         System.out.println("Invalid Username or Password!! Try Again");
                         System.out.println("\n**********************************");
            }
                    else{
                        System.out.println("\n************* LOGGED-IN *************");
                        System.out.println("\n****** WELCOME "+uname+" ******");

                    do
                    {
                        System.out.println("\n**********************************");
                        System.out.println("\n\t1.Withdraw\n\t2.Deposit\n\t3.Balance\n\t4.Transfer\n\t5.Pay Utility\n\t6.Exit");
                        System.out.println("\nEnter your choice:");
                        ch= br.nextInt();

                        switch(ch)
                        {
                            //Withdraw money
                            case 1:
                                System.out.println("\n************** WITHDRAW *************");
                                System.out.println("\nEnter amount of Withdraw:");
                                int a=br.nextInt();
                                int wd=bankintf.withdraw(a,uname);
                                if(wd==2){
                                    System.out.println("\nBalance is less! Unable to proceed withdraw");
                                    System.out.println("\n**********************************");
                                }else if (wd==0){
                                    System.out.println("\nProblem occur!! Unable to proceed withdraw");
                                    System.out.println("\n**********************************");
                                }
                                else
                                {
                                    System.out.println("\nYou account is debited" + a+ " birr ");
                                    System.out.println("\nCurrent Balance is :" + wd);
                                    System.out.println("\n**********************************");
                                }
                                break;
                            //Deposit money to account
                            case 2:
                                System.out.println("\n************** DEPOSIT *************");
                                System.out.println("\nEnter amount of deposit:");
                                int dp=br.nextInt();
                                int depo=bankintf.deposit(dp,uname);
                                System.out.println("\n Your account is credited: " +dp+" birr. \nCurrent Balance: "+depo);
                                System.out.println("\n**********************************");
                                break;
                            //To check remaning balance
                            case 3:
                                System.out.println("\n************** BALANCE *************");
//                                System.out.println("\nEnter your account no:");
//                                int acct=br.nextInt();
                               int bal=bankintf.balance(uname);
                                System.out.println("\nYour Balance is : "+bal);
                                System.out.println("\n**********************************");
                                break;
                            //Transfer to other account
                            case 4:
                                System.out.println("\n************** TRANSFER *************");
                                System.out.println("\nChoose transfer type:");
                                System.out.println("\n\t1.This Bank\n\t2.Other Bank");
                                int tra= br.nextInt();
                                if(tra==1){
                                    System.out.println("\n************** THIS BANK *************");
                                    System.out.println("\nEnter account no. to transfer:");
                                    int tracc=br.nextInt();
                                    System.out.println("\nEnter amount of money to transfer:");
                                    int tramt=br.nextInt();
                                  int  bank = bankintf.transfer(tramt,tracc,uname);
                                    if(bank==2) {
                                        System.out.println("\nBalance less unable to proceed transfer");
                                        System.out.println("\n**********************************");
                                    }
                                    else if (bank==0){
                                        System.out.println("\nProblem occur!! Unable to proceed transfer");
                                        System.out.println("\n**********************************");
                                    }
                                    else {
                                        System.out.println("\nYou have transfered:" + tramt + " birr for account number:" + tracc);
                                        System.out.println("\nCurrent Balance is :" + bank);
                                        System.out.println("\n**********************************");
                                    }
                                } else if (tra==2) {
                                    System.out.println("\n************** OTHER BANK *************");
                                    System.out.println("\nEnter bank name of the account to transfer:");
                                    String trabankname=br.nextLine();
                                    System.out.println("\nEnter account no. to transfer:");
                                    int tracc=br.nextInt();
                                    System.out.println("\nEnter amount of money to transfer:");
                                    int tramt=br.nextInt();
                                    int other = bankintf.transferother(tramt,uname);
                                    if(other==2) {
                                        System.out.println("\nBalance less unable to proceed transfer");
                                        System.out.println("\n**********************************");
                                    }else if (other==0){
                                        System.out.println("\nProblem occur!! Unable to proceed transfer");
                                        System.out.println("\n**********************************");
                                    }
                                    else {
                                        System.out.println("\nYou have transfered:" + tramt + " birr for account number:" + tracc+" in "+trabankname+" bank");
                                        System.out.println("\nCurrent Balance is :" + other);
                                        System.out.println("\n**********************************");
                                    }

                                }else{
                                    System.out.println("\nInvalid entry!! Try Again");
                                    System.out.println("\n**********************************");
                                }

                                break;
                            //to pay utility
                            case 5:
                                System.out.println("\n************** UTILITY *************");
                                System.out.println("\nChoose utility type to make payment:");
                                System.out.println("\n\t1.Water bill\n\t2.School payment");
                                int utility= br.nextInt();
                                if(utility==1){
                                    System.out.println("\n************** WATER BILL *************");
                                    System.out.println("\nEnter amount of money to pay water bill:");
                                    int watamt=br.nextInt();
                                    int water = bankintf.utility(watamt,uname);
                                    if(water==2) {
                                        System.out.println("\nBalance less unable to proceed water bill payment payment");
                                        System.out.println("\n**********************************");
                                    }
                                    else if (water==0){
                                        System.out.println("\nProblem occur!! Unable to proceed water bill payment");
                                        System.out.println("\n**********************************");
                                    }
                                    else {
                                        System.out.println("\nYou have payed:" + watamt + " birr for water bill Payment");
                                        System.out.println("\nCurrent Balance is :" + water);
                                        System.out.println("\n**********************************");
                                    }
                                } else if (utility==2) {
                                    System.out.println("\n************** SCHOOL PAYMENT *************");
                                    System.out.println("\nEnter school name you want to make payment for:");
                                    String schname=br.next();
                                    System.out.println("\nEnter account no. for school payment:");
                                    int schacc=br.nextInt();
                                    System.out.println("\nEnter amount of money to pay:");
                                    int schamt=br.nextInt();
                                    int school = bankintf.transfer(schamt,schacc,uname);
                                    if(school==2) {
                                        System.out.println("\nBalance less unable to proceed school payment");
                                        System.out.println("\n**********************************");
                                    }
                                    else if (school==0){
                                        System.out.println("\nProblem occur!! Unable to proceed school payment");
                                        System.out.println("\n**********************************");
                                    }
                                    else {
                                        System.out.println("\nYou have payed:" + schamt + " birr for " +schname+ "with account number:" + schacc);
                                        System.out.println("\nCurrent Balance is :" + school);
                                        System.out.println("\n**********************************");
                                    }
                                }else{
                                    System.out.println("\nInvalid entry!! Try Again");
                                    System.out.println("\n**********************************");
                                }
                                break;
                        }
                    }while(ch<6);

                    }
                }
            }while(login<3);
        } catch(Exception e)
        {
            System.out.println("Exception :"+e);
        }
    }
}