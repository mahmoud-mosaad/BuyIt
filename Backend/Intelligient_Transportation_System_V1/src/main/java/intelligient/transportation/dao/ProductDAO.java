package intelligient.transportation.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import intelligient.transportation.models.Product;

@Component
@Service
@Repository
public class ProductDAO {
	
	  @Autowired
		
	  private SessionFactory sessionFactory;
	
	  public Boolean addProduct(Product product){
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
			return false;
		}
		return true;
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
		session.beginTransaction();
       Product p =  (Product) session.get(Product.class, product_id);
       session.getTransaction().commit();
       session.close();
       return p;
	}
	/*
	public Product updateProduct(int product_id, int quantity) {
		Session session;
		session = sessionFactory.openSession();
		session.beginTransaction();
       Product p =  (Product) session.get(Product.class, product_id);
       p.setQuantity(p.getQuantity() - quantity);
       session.getTransaction().commit();
       session.close();
       return p;
	}
	*/
	public List getAllProduct() {
		Session session;
		session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Product.class);
		List products=criteria.list();
       session.close();
		
		return products;
	}
	
}
