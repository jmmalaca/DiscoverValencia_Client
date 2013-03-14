package rmiConnections;

/**
 * Class User for the System
 * */
public class Object_User implements java.io.Serializable{
	
	/**
	 * class Object_User
	 */
	private static final long serialVersionUID = 1L;
	
	private String Name;
	private String Email;
	private String Country;
	private String Password;
	private String Facebook_Account;
	private Interface_Client_Methods Methods;
    
	/**
	 * Class constructor
	 * */
	Object_User(String userName, Interface_Client_Methods m){
		System.out.println("\nRegisto: ");
		
		//set Methods
		Methods=m;
		
		Name=userName;
		System.out.println("-Name: "+Name);
		
		System.out.println("Insert some info in our account: ");
		
		System.out.println("-Email: ");
		Email=Read_Keyboard.readString();
		
		System.out.println("-Country: ");
		Country=Read_Keyboard.readString();
		
		System.out.println("-Password: ");
		Password=Read_Keyboard.readString();
		
		//protection for password insertion
		String pass="";
		do{
			System.out.println("-Repeat the Password: ");
			pass=Read_Keyboard.readString();
		}while(pass.equals(Password) != true);
	}
	
	/**
	 * set Methods of the Client
	 * */
	public void setMetodos(Interface_Client_Methods m){
		Methods=m;
	}
	
	/**
	 * set the Facebook account ID
	 * */
	public void setFacebook_Account(String account){
		Facebook_Account=account;
	}
	
	/**
	 * get Methods of the Client
	 * */
	public Interface_Client_Methods getMetodos(){
		return Methods;
	}
	
	/**
	 * get User Name
	 * */
	public String getNome(){
		return Name;
	}
	
	/**
	 * get User Email
	 * */
	public String getMail(){
		return Email;
	}
	
	/**
	 * get User Country
	 * */
	public String getCountry(){
		return Country;
	}
	
	/**
	 * get User Facebook_Account
	 * */
	public String getFacebook_Accout(){
		return Facebook_Account;
	}
	
	/**
	 * get User Password
	 * */
	public String getPassword(){
		return Password;
	}
	
	/**
	 * print User Data
	 * */
	public void printCliente(){
		System.out.println("\nUser Data: ");
		System.out.println("User: "+Name+" ("+Country+")");
		System.out.println("E-mail: "+Email);
		System.out.println("Facebook ID: "+Facebook_Account);
		System.out.println("Password: "+Password);
	}
	
	/**
	 * method to give some protection, the User must repeat the password correctely
	 * */
	public int protect(){
		String pass="";
		System.out.println("Insert the Password: ");
		pass=Read_Keyboard.readString();
		if (pass.equals(Password) != true){
			return 0;
		}else{
			return 1;
		}
	}
	
	/**
	 * method for edit the User Data
	 * */
	public boolean editCliente(){
		System.out.println("\nEdit: ");
		System.out.println("(1)Name");
		System.out.println("(2)EMail");
		System.out.println("(3)Country");
		System.out.println("(4)Password");
		System.out.println("Exit(0)");
		System.out.println("Opcao: ");
		int opcao=Read_Keyboard.readInt();
		
		if (opcao>=0 && opcao<=4){
			if (opcao == 1){
				String n="";
				System.out.println("new Name:");
				n=Read_Keyboard.readString();
				if (protect() != 0){
					Name=n;
				}
				System.out.println("new Name set.");
				return true;
				
			}else if (opcao == 2){
				String m="";
				System.out.println("new e-mail: ");
				m=Read_Keyboard.readString();
				if (protect() != 0){
					Email=m;
				}
				System.out.println("new E-Mail set.");
				return true;
				
			}else if (opcao == 3){
				String c="";
				System.out.println("new Country: ");
				c=Read_Keyboard.readString();
				if(protect() != 0){
					Country=c;
				}
				System.out.println("new Country.");
				return true;
				
			}else if (opcao == 4){
				String passActual="";
				System.out.println("actual Password: ");
				passActual=Read_Keyboard.readString();
				
				if (passActual.equals(Password) == true){
					System.out.println("new Password?: ");
					Password=Read_Keyboard.readString();
					//proteccao da passord
					String pass="";
					do{
						System.out.println("repeat the Password: ");
						pass=Read_Keyboard.readString();
					}while(pass.equals(Password) != true);
					System.out.println("new Password set.");
					return true;
				}else{
					System.out.println("actual password wrong.");
				}
			}
		}
		return false;
	}

}
