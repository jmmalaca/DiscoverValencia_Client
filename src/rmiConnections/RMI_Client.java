package rmiConnections;
//package hello;
import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Class for the program for a Client
 */
public class RMI_Client extends UnicastRemoteObject implements Interface_Client_Methods{
	
	private static final long serialVersionUID = 1L;
	private static Object_User User_obj=null;
	private static Interface_Server_Methods m;
	private static ArrayList <Object_User> List_Users_OnLine=new ArrayList<Object_User>();
	
	/**
	 * Class constructor
	 */
	public RMI_Client() throws RemoteException {
		super();
	}
	
	/**
	 * Method to show the menu of the system
	 */
	private static int menu(){
		//menu da aplicacao
		System.out.println("\n  .Menu.");
		System.out.println("(1)Show Data account");
		System.out.println("(2)Edit Account");
		System.out.println("(3)Show Online Users");
		System.out.println("(4)Message User");
		System.out.println("(5)Message All");
		System.out.println("(0)Exit");
		int opcao=Read_Keyboard.readInt();
		return opcao;
	}
	
	/**
	 * Method to Edit some User Data
	 */
	private static void edit(){
		
		String nome=User_obj.getNome();
		User_obj.editCliente();
		try{
			boolean verif=m.Edit_User(User_obj, nome);
			if(verif!=false){
				System.out.println("\nChanges on the user data sucessfuly");
			}else{
				System.out.println("\nChanges on the user data error");
			}
		}catch (Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * Print list of the users on-line
	 */
	private static void imprime_users(){
		System.out.println("\n.:: Users Online ::.");
		for(int i=0;i<List_Users_OnLine.size();i++){
			String nome = List_Users_OnLine.get(i).getNome();
			System.out.println("> "+nome);
		}
	}
	
	/**
	 * Method to send a msg to some user
	 */
	private static void Send_Msg(String deQuem){
		System.out.println("Name of the user to send msg: ");
		String nome=Read_Keyboard.readString();
		System.out.println("Msg to send: ");
		String msg=Read_Keyboard.readString();
		
		try {
			boolean resu=m.Send_Msg(nome,msg,deQuem);
			if (resu!=false){
				System.out.println("\nMSG Send.");
			}else{
				System.out.println("\nMSG not Send.");
			}
		} catch (RemoteException e) {
			System.out.println("\nERRO: sending msg"+e.getMessage());
		}
	}
	
	/**
	 * Method to send a msg all the users on-line
	 */
	private static void enviarMsgAll(String deQuem){
		String nome="all";
		System.out.println("Msg to send: ");
		String msg=Read_Keyboard.readString();
		
		try {
			boolean resu=m.Send_Msg(nome,msg,deQuem);
			if (resu!=false){
				System.out.println("\nMSG sended.");
			}else{
				System.out.println("\nMSG not sended.");
			}
		} catch (RemoteException e) {
			System.out.println("\nERROR: sending msg all"+e.getMessage());
		}
	}
	
	/**
	 * Method to receive a msg from the server that someone sends
	 */
	public void Receive_Msg(String msg, String nome) throws RemoteException {
		System.out.println("\n>MSG["+nome+"]: "+msg);
	}
	
	/**
	 * Method to receive user data from the server
	 */
	public void recebeDados(Object_User dados){
		User_obj=dados;
	}
	
	/**
	 * Method of the SYSTEM control
	 */
	private static void sistema(){
		
	    try {
			int opcao=0;
			do{
				//escolher uma opcao do menu
				opcao=menu();
				if (opcao >= 0 && opcao < 6){
					if (opcao == 0){
						m.Exit(User_obj);
					}else if (opcao == 1){
						User_obj.printCliente();
					}else if(opcao == 2){
						//edit
						edit();
					}else if(opcao == 3){
						//online users
						List_Users_OnLine=m.Users_OnLine();
						imprime_users();
					}else if (opcao == 4){
						//message user
						Send_Msg(User_obj.getNome());
					}else if(opcao == 5){
						//message all
						enviarMsgAll(User_obj.getNome());
					}
				}
			}while(opcao != 0);
			
		}catch (Exception e) {
			//System.out.println(">Exception(menu): " + e.getMessage());
			boolean start = connect();
			if (start != true){
				System.exit(0);
			}else{
				exec();
			}
		}
	}
	
	/**
	 * Method of the SYSTEM start and connect to the server
	 */
	public static void exec(){
		String user,pass;
		RMI_Client utilizador;
		try {
			utilizador = new RMI_Client();
			do{
				System.out.println("\n\nEnter name of user:  ");
				user=Read_Keyboard.readString();
				boolean verif = m.Check_User(user);
				if(verif==false){
					User_obj=new Object_User(user,(Interface_Client_Methods) null);
					verif=m.Register_User(User_obj);
					if(verif){
						System.out.println("Registered User");
						User_obj.printCliente();
					}
					else{
						verif=m.Register_User(User_obj);
					}

				}
				else{
					System.out.println("Enter the Password: ");
					pass=Read_Keyboard.readString();
					User_obj=m.Check_Password(user, pass);
					User_obj.setMetodos((Interface_Client_Methods) utilizador);
				}
			}while(User_obj==null);

			System.out.println("\n\n.:: WELCOME ::.");
			List_Users_OnLine=m.Users_OnLine();
			
			//adicionar utilizador aos Onlines(array)
			User_obj.setMetodos(utilizador);
			List_Users_OnLine.add(User_obj);
			m.Add_User(User_obj);

			//START the SYSTEM
			sistema();

			//chegou aqui - saiu
			List_Users_OnLine.remove(User_obj);
			//terminar programa
			System.out.println("\nApp terminou.");
			System.exit(0);//!!!!!!!
			
		} catch (Exception e) {
			System.out.println("Exec ERROR");
		}
	}
	
	/**
	 * Method to connect the client system to the user
	 */
	private static boolean connect(){
		//tentar 3 tentativas...
		for (int i=0; i<3; i++){
			//ligar ao server
			try {
				if (i == 0){
					System.out.print("\n>connecting...");
				}
				m = (Interface_Server_Methods) LocateRegistry.getRegistry(7001).lookup("Discover_Valencia_Server");

				if (m != null){
					return true;
				}

			} catch (Exception e) {
				//System.out.println(">Exception(connect): " + e.getMessage());
				System.out.print(".");
			}
		}

		System.out.println("\n>connection off.\n");
		return false;
	}

	/**
	 * Main Method of the Class RMI_Client 
	 */
	public static void main(String args[]) {

		/* This might be necessary if you ever need to download classes:
		System.getProperties().put("java.security.policy", "policy.all");
		System.setSecurityManager(new RMISecurityManager());
		*/
		try {
			boolean start = connect();
			if (start != true){
				System.exit(0);
			}
			
			exec();
			
		} catch (Exception e) {
			System.out.println("Exception in main: " + e);
			e.printStackTrace();
		}
	}
	
}