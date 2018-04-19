package intelligient.transportation.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
@Service
@Repository
public class UserDAO {
	
	
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
