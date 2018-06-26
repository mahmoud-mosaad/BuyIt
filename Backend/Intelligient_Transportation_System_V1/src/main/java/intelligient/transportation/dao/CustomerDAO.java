package intelligient.transportation.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.math.BigInteger;

import hibernate.config.HibernateUtilConfig;
import intelligient.transportation.models.Customer;
import intelligient.transportation.models.User;

@Component
@Service
@Repository
public class CustomerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	@Transactional
	public boolean createCustomer(Customer customer){
	
		
		try {
			Session session;
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			Integer id =(Integer) session.save(customer);
			String apiToken = TokenHandler.createToken(id);
			customer.setApiToken(apiToken);
			customer.setRole("Customer");
			System.out.println("user is created With Id::"+id);
			
			session.getTransaction().commit();
			session.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public Customer logInCustomer(Customer customer){
		
		Customer retrievedCustomer= new Customer();
		try {
			Session session;
			session = sessionFactory.openSession();
			 Query qry = session.createQuery
					 ("from User where role = 'Customer' and email=:p1 and password =:p2 ");
			 qry.setParameter("p1",customer.getEmail());
			 qry.setParameter("p2",customer.getPassword());
		     List<Customer> customersList= qry.list();
		     if(customersList.size()==0)
		    	 return null;
		     /*
		     for(Object[] cust: customersList){
		         retrievedCustomer.setId((Integer)cust[0]);
		         retrievedCustomer.setEmail((String)cust[4]);
		         retrievedCustomer.setName((String)cust[7]);
		     }
		     */
		     retrievedCustomer = customersList.get(0);
		     System.out.println(retrievedCustomer.toString());
		     
		     
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retrievedCustomer;
	}
	/*
	public CustomerDAO() throws Exception {
		try {
		System.out.println("Trying to create a test connection with the database.");
        Configuration configuration = new Configuration();
        configuration.configure("hibernate_sp.cfg.xml");
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(ssrb.build());
        System.out.println("Test connection with the database created successfuly.");
		}
		 catch (Exception e) {
				e.printStackTrace();
			}
	}
*/
}
