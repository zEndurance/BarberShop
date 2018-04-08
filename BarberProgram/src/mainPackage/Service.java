package mainPackage;

public class Service {

	private int id;
	private String service;
	private double price;

	public Service(String[] args) {
		// If valid
		if(validate(args)){
			// Then set values
			if (args.length > 2) {
				this.id = Integer.parseInt(args[0]);
				this.service = args[1];
				this.price = Double.parseDouble(args[2]);
			}
		}else{
			throw new IllegalArgumentException("Incorrect constructor arguments!");
		}
	}
	
	public boolean validate(String[] args){
		return true;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString(){
		return "ID: " + this.id + " Service: " + this.service + " Price: " + this.price;
	}
}
