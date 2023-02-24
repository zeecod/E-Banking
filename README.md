# E-Banking
Online banking using RMI 

Programming langauge: Java

Database: MySQL



Steps to run the application:

  	javac BankInt.java

  	javac BankImpl.java

  	javac Bankconnection.java

  	javac BankClient.java

	javac BankServer.java

	start rmiregistry

	java BankServer

	java BankClient


The BankClient class implements the main UI for the client side and connection with the server.
The BankServer class implements the remote connection with the client and calles the implementation class.
The BankInt class holds all the interface methods that are to be implemented on implementation class.
The BankImpl defines all interface methods also calls the databse connection class.
The Bankconnection class implements the database connection

