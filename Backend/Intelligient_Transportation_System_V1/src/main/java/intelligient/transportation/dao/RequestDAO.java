package intelligient.transportation.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import intelligient.transportation.models.Request;
import intelligient.transportation.models.User;

@Component
@Service
@Repository
public class RequestDAO {
	
	
	 @Autowired
		private SessionFactory sessionFactory;
	
		public Request createRequest(String request){
			Session session= null;
			Request reqObject = new Request();
			try {
				session = sessionFactory.openSession();
				session.beginTransaction();
				
				JSONObject req = new JSONObject(request);
				int customerId = req.getInt("customerId");
				
				User user = new User();
				user.setId(customerId);
				reqObject.setUser(user);
				Integer requestId =(Integer) session.save(reqObject);
				reqObject.setId(requestId);
				
				

				//create new request
				System.out.println("Request is created With Id::"+requestId);
				session.getTransaction().commit();
				session.close();
				
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return reqObject;
		}
	
	
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
	
	public List<Request> getall(){
		
		Session session;
		session = sessionFactory.openSession();
		Criteria criteria= session.createCriteria(Request.class);
	    List<Request> r = criteria.list();
	    session.close();
	    
		return r;
	}
	
	public void Delete(int id){
	   Session session=sessionFactory.openSession();
	   session.beginTransaction();
	   Request req =  (Request) session.get(Request.class, id); 
	   session.delete(req);
	   session.getTransaction().commit();
		session.close();	
		
	}
}
