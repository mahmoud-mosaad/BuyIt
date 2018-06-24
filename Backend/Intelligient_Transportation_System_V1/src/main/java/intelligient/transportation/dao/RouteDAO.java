package intelligient.transportation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import intelligient.transportation.models.Route;
import intelligient.transportation.models.Salesman;
import intelligient.transportation.models.User;

@Component
@Service
@Repository
public class RouteDAO {
	
	
	@Autowired
	private SessionFactory sessionFactory;
	

	
	public Integer createRoute(Route route){
		Session session= null;
		try {
		
		
			session = sessionFactory.openSession();
			session.beginTransaction();
			int id =(Integer) session.save(route);
			System.out.println("route is created With Id::"+id);
			session.getTransaction().commit();
			session.close();
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Route getById(int id) {
		try{
		Session session;
		session = sessionFactory.openSession();
        Salesman user =  (Salesman) session.get(Salesman.class, id);
        session.close();
        return user.getRoute();
		}catch(Exception e){
			return null;
		}
	}
	
	public void delete(int id) {
		
		Session session;
		session = sessionFactory.openSession();
		session.beginTransaction();
		Route route =  (Route) session.get(Route.class, id);
        route.setUser(null);
        System.out.println(route.getId());
        session.getTransaction().commit();
        session.close();
        
	}
	
}
