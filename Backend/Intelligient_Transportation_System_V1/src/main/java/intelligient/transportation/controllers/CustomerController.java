package intelligient.transportation.controllers;

import javax.servlet.http.HttpServletResponse;

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
	CustomerDAO customerDAO;
	
	@RequestMapping(value="/signUp", method=RequestMethod.POST)
	@ResponseBody
	public Customer signUp(@RequestBody Customer customer , HttpServletResponse response) {
	    if(customer.getEmail().trim().equals(""))
	    	customer.setEmail(null);
	    if(customer.getPassword().trim().equals(""))
	    	customer.setPassword(null);
	    
		boolean save = customerDAO.createCustomer(customer);
		if(!save){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		 return customer;
		
	}
	

}
