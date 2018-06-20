package intelligient.transportation.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import intelligient.transportation.models.Order;

@Component
@Service
@Repository
public class OrderDAO {
	
	
	  @Autowired
	private SessionFactory sessionFactory;
	public void createOrder(Order order){
		Session session= null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Integer id =(Integer) session.save(order);
			System.out.println("Order is created With Id::"+id);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	public List getOrderByUserId(int UserId) {
		Criteria criteria = sessionFactory.openSession().createCriteria(Order.class);
		return criteria.list();
	}
}
