package intelligient.transportation.models;

import javax.persistence.Entity;

@Entity

public class Salesman extends User {

	Salesman(){}
	
	
	private Boolean available;

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
	
}
