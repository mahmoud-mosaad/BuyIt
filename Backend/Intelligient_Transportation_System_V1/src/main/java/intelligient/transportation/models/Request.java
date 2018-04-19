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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Request {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private Integer requestPriority;
	
	@OneToMany(mappedBy = "request", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private Collection<Order> orders = new ArrayList<Order>();
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn(name="customer_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private Route route;

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRequestPriority() {
		return requestPriority;
	}

	public void setRequestPriority(Integer requestPriority) {
		this.requestPriority = requestPriority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<Order> getOrders() {
		return orders;
	}

	public void setOrders(Collection<Order> orders) {
		this.orders = orders;
	}
	
}
