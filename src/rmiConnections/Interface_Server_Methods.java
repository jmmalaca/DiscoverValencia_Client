package rmiConnections;
import java.rmi.*;
import java.util.*;

/**
 * Interface to the Server Methods
 * */
public interface Interface_Server_Methods extends Remote {
	
	/**
	 * Check if the User exists
	 * */
	public boolean Check_User(String user) throws java.rmi.RemoteException;
	
	/**
	 * Register a new user
	 * */
	public boolean Register_User(Object_User user) throws java.rmi.RemoteException;
	
	/**
	 * check if the User Password is correct
	 * */
	public Object_User Check_Password(String nome, String pass) throws java.rmi.RemoteException;
	
	/**
	 * Edit the User Data
	 * */
	public boolean Edit_User(Object_User user, String nome) throws java.rmi.RemoteException;
	
	/**
	 * Get List of Users connected to the Server
	 * */
	public ArrayList <Object_User> Users_OnLine() throws java.rmi.RemoteException;
	
	/**
	 * Add New User
	 * */
	public void Add_User(Object_User obj) throws java.rmi.RemoteException;
	
	/**
	 * User Exit the Server
	 * */
	public void Exit(Object_User obj) throws java.rmi.RemoteException;
	
	/**
	 * User Send a Msg
	 * */
	public boolean Send_Msg(String nome, String msg, String deQuem) throws java.rmi.RemoteException;
	
}