package intelligient.transportation.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import intelligient.transportation.models.Request;

@Component
@Service
@Repository
public class RequestDAO {
	
	
	 @Autowired
		private SessionFactory sessionFactory;
	
	
	public List<Request> getRequests() {
		Session session;
		session = sessionFactory.openSession();
		Criteria criteria= session.createCriteria(Request.class);
	    criteria.add(Restrictions.isNull("route"));
	    List<Request> r = criteria.list();
	    session.close();
	    
	    
	    
		return r;
	}
	
	public Request getById(int request_id) {
		Session session;
		session = sessionFactory.openSession();
		session.beginTransaction();
		Request req =  (Request) session.get(Request.class, request_id);
		session.getTransaction().commit();
		session.close();
         return req;
	}
	
}
