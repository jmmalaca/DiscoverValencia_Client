package rmiConnections;
import java.rmi.*;

public interface Interface_Client_Methods extends Remote{
	
	/**
	 * Method Receive Msg
	 * */
	public void Receive_Msg(String msg, String nome) throws java.rmi.RemoteException;
	
}