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

import intelligient.transportation.models.Admin;
import intelligient.transportation.models.Customer;
import intelligient.transportation.models.User;

@Component
@Service
@Repository
public class AdminDAO {
	
	
	 @Autowired
	private SessionFactory sessionFactory;
		
		public Admin findByEmaiAndPassword(String e ,String p){
		
			
			try {
				Session session;
				if(sessionFactory==null)
					System.out.println("Session Factory is null");
				else
					System.out.println("Session Factory is NOT null");
				
					
				session = sessionFactory.openSession();
				session.beginTransaction();
				Criteria criteria = session.createCriteria(Admin.class);
				
				criteria.add(Restrictions.eq("email", e))
				         .add(Restrictions.eq("password", p));	
			    List<Admin> u = (List<Admin>) criteria.list();
		
				//System.out.println("user is created With Id::"+id);
				session.getTransaction().commit();
				session.close();
				return u.size()>0?u.get(0):null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return null;
			
		}
	/*
	  @Autowired
	private SessionFactory sessionFactory;
	public void createEmployee(user user){
		Session session= null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Integer id =(Integer) session.save(user);
			System.out.println("user is created With Id::"+id);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
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
