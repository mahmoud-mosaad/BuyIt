package intelligient.transportation.dao;

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

import hibernate.config.HibernateUtilConfig;
import intelligient.transportation.models.Customer;

@Component
@Service
@Repository

public class CustomerDAO {
	
	 @Autowired
	private SessionFactory sessionFactory;
	
	public void createCustomer(Customer customer){
	
		
		try {
			Session session;
			if(sessionFactory==null)
				System.out.println("Session Factory is null");
			else
				System.out.println("Session Factory is NOT null");
			
				
			session = sessionFactory.openSession();
			session.beginTransaction();
			Integer id =(Integer) session.save(customer);
			System.out.println("user is created With Id::"+id);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
