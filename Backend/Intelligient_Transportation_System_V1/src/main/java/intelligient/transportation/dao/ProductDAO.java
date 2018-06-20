package intelligient.transportation.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import intelligient.transportation.models.Product;

@Component
@Service
@Repository
public class ProductDAO {
	
	  @Autowired
		
	  private SessionFactory sessionFactory;
	
	  public void addProduct(Product product){
		Session session= null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Integer id =(Integer) session.save(product);
			System.out.println("Product is created With Id::"+id);
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
	*/
	public Product getById(int product_id) {
		Session session;
		session = sessionFactory.openSession();
       Product p =  (Product) session.get(Product.class, product_id);
       return p;
	}
	
	public List getAllProduct() {
		Criteria criteria = sessionFactory.openSession().createCriteria(Product.class);
		return criteria.list();
	}
	
}
