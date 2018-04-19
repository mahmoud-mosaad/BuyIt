package intelligient.transportation.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Component
@Service
@Repository
public class ProductDAO {
	
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
