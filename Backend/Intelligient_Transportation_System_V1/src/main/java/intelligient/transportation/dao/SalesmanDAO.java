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

import intelligient.transportation.models.Salesman;

@Component
@Service
@Repository
public class SalesmanDAO {
	
		@Autowired
		private SessionFactory sessionFactory;  
		  
		public void createSalesman(Salesman salesman){
			
			salesman.setAvailable(true);
				
			try {
				Session session;
				session = sessionFactory.openSession();
				session.beginTransaction();
				
				Integer id =(Integer) session.save(salesman);
				String apiToken = TokenHandler.createToken(id);
				salesman.setApiToken(apiToken);
				salesman.setRole("Salesman");
				System.out.println("salesman is created With Id:"+ id);
				
				session.getTransaction().commit();
				session.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	  public List<Salesman> getAvailableUsers(){
		 Session session = sessionFactory.openSession();
		  //session.beginTransaction();
		  Criteria criteria = session.createCriteria(Salesman.class);
		  criteria.add(Restrictions.eq("available", true));
		  List<Salesman>Salesman = (List<Salesman>) criteria.list();
		  session.close();
		  return Salesman;
		  
		  
	  }
	  public void update(Salesman s){
			 Session session = sessionFactory.openSession();
			  session.beginTransaction();
			  session.update(s);
			  session.getTransaction().commit();
			  session.close();
		  }
	  public Salesman checkIn(int id){
		  Session session = sessionFactory.openSession();
		  session.beginTransaction();
		  Salesman s = (Salesman)session.get(Salesman.class ,id);
		  s.setAvailable(true);
		  session.getTransaction().commit();
		  session.close();
		  return s;
	  }
	
	/*public List getUserDetails() {
		Criteria criteria = sessionFactory.openSession().createCriteria(user.class);
		return criteria.list();
	}
	
	public user getById(int user_id) {
		Session session;
		session = sessionFactory.openSession();
         user User =  (user) session.get(user.class, user_id);
         return User;

	
}*/
}