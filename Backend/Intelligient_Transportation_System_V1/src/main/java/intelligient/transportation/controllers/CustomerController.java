package intelligient.transportation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.dao.CustomerDAO;
import intelligient.transportation.models.Customer;

@RestController
@RequestMapping("/customer")
public class CustomerController extends UserController{
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@RequestMapping(value="/sign_up", method=RequestMethod.POST)
	@ResponseBody
	public Customer sign_up(@RequestBody Customer customer) {
	//	CustomerDAO customerDAO  = new CustomerDAO();
		this.customerDAO.createCustomer(customer);
		
		return customer;
	}
	
}
