package intelligient.transportation.dao;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import intelligient.transportation.models.Customer;
import intelligient.transportation.models.User;

@Component
@Service
@Repository
public class UserDAO {
	
	  @Autowired
	private SessionFactory sessionFactory;
	public void createEmployee(User user){
		 

		Session session= null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Integer id =(Integer) session.save(user);
			System.out.println("User is created With Id::"+id);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public User logInUser(User user){
		
		User retrievedUser= new User();
		try {
			Session session;
			session = sessionFactory.openSession();
			 Query qry = session.createQuery
					 ("from User where email=:p1 and password =:p2 ");
			 qry.setParameter("p1",user.getEmail());
			 qry.setParameter("p2",user.getPassword());
		     List<Customer> usersList= qry.list();
		     if(usersList.size()==0)
		    	 return null;
		     
		     
		     /*
		     for(Object[] cust: customersList){
		         retrievedCustomer.setId((Integer)cust[0]);
		         retrievedCustomer.setEmail((String)cust[4]);
		         retrievedCustomer.setName((String)cust[7]);
		     }
		     */
		     retrievedUser = usersList.get(0);
		     System.out.println(retrievedUser.toString());
		     
		     session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrievedUser;
	}
	
  
  
	public List getUserDetails() {
		Criteria criteria = sessionFactory.openSession().createCriteria(User.class);
		return criteria.list();
	}
	
	public User getById(int user_id) {
		Session session = null;
		System.out.println(user_id);
		session = sessionFactory.openSession();
       User user =  (User) session.get(User.class, user_id);
       return user;
	}
	
}
