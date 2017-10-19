package loginApplication;

public enum Option {
	Admin, Employee;
	
	private Option(){
		
	}
	
	public String value(){
		return name();
	}
	public static Option fromvalue(String value){
		return valueOf(value);
	}

}
