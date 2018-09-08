package model;

public class Profile {
	
	private String id;
	private String first_name;
	private String last_name;
	private String age;
	private String profile;
	private String mob;
	private String account_id;
	
	public Profile(String[] args){
		this.id = args[0];
		this.first_name = args[1];
		this.last_name = args[2];
		this.age = args[3];
		this.mob = args[4];
		this.profile = args[5];
		this.account_id = args[6];
	}
	
	public String getAge(){
		return this.age;
	}
	
	
	public String getName(){
		return this.getFirstName() + " " + this.getLastName();
	}
	
	
	public String getFirstName(){
		return this.first_name;
	}
	
	public String getLastName(){
		return this.last_name;
	}
	
	public String getID(){
		return this.id;
	}
	
	public String getProfile(){
		return this.profile;
	}
	
	public String getAccountID(){
		return this.account_id;
	}
	
	public String getMobileNumber(){
		return this.mob;
	}
}
