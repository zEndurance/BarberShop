package mainPackage;

public class Profile {
	
	private String name;
	private String mob;
	
	public Profile(String[] args){
		this.name = "Test";
		this.mob = "04545454454";
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getMobileNumber(){
		return this.mob;
	}
}
