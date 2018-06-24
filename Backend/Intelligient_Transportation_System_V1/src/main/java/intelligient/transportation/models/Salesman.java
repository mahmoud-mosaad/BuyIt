package intelligient.transportation.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Entity

public class Salesman extends User {

	Salesman(){}
	
	
	private Boolean available;

	@OneToOne(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL )
	private Route route;
	
	
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
	
}
