package intelligient.transportation.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Route {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE )
	@JoinColumn(name="salesman_id")
	private Salesman user;
	
	@OneToMany(mappedBy = "route", fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	private Collection<Request> requests = new ArrayList<Request>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Salesman getUser() {
		return user;
	}

	public void setUser(Salesman user) {
		this.user = user;
	}

	public Collection<Request> getRequests() {
		return requests;
	}

	public void setRequests(Collection<Request> requests) {
		this.requests = requests;
	}
}
