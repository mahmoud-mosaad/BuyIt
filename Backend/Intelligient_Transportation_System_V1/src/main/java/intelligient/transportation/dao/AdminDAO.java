package intelligient.transportation.dao;

import java.util.List;

import org.bouncycastle.asn1.isismtt.x509.Restriction;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
	 
	 public String logInAdmin(Admin admin){
			
			Admin retrievedAdmin= new Admin();
			try {
				Session session;
				session = sessionFactory.openSession();
				session.beginTransaction();
				
				Criteria cc = session.createCriteria(Admin.class);
				cc.add(Restrictions.eq("email", admin.getEmail()))
				.add(Restrictions.eqOrIsNull("password", admin.getPassword()));
				
				List<Admin> adminList = (List<Admin>) cc.list();
			     if(adminList.size()==0)
			    	 return null;
			     
			     retrievedAdmin = adminList.get(0);
			     String apiToken = TokenHandler.createToken(retrievedAdmin.getId());
			     retrievedAdmin.setApiToken(apiToken);
			     
			     session.getTransaction().commit();
			     session.close();
			     System.out.println(retrievedAdmin.toString());
			     
			} catch (Exception e) {
				e.printStackTrace();
			}
			return retrievedAdmin.getApiToken();
		}
	 
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
