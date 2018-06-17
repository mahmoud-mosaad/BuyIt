package intelligient.transportation.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import intelligient.transportation.models.Route;

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
	/*
	public List getUserDetails() {
		Criteria criteria = sessionFactory.openSession().createCriteria(user.class);
		return criteria.list();
	}
	
	public user getById(int user_id) {
		Session session;
		session = sessionFactory.openSession();
         user User =  (user) session.get(user.class, user_id);
         return User;
	}
	*/
}
