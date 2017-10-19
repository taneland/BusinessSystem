package admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductData {
	

	private final StringProperty name;
	private final StringProperty lenght;
	
	public ProductData(String name, String lenght) {
		
	
		this.name = new SimpleStringProperty(name);
		this.lenght = new SimpleStringProperty(lenght);
	}
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}

	public String getLenght() {
		return lenght.get();
	}
	public void setLenght(String lenght) {
		this.lenght.set(lenght);
	}
	
	
	

}
