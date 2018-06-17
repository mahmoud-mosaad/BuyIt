package intelligient.transportation.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.dao.AdminDAO;
import intelligient.transportation.models.Admin;
import intelligient.transportation.models.Customer;
import intelligient.transportation.models.User;
@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController extends UserController{

	@Autowired
	private AdminDAO adminDAO;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Integer> login(
			@RequestBody Admin a
			) {
		
		Admin admin = adminDAO.findByEmaiAndPassword(a.getEmail(),a.getPassword());
		 HashMap<String, Integer> map = new HashMap<String, Integer> ();
		if(admin!=null){
		 map.put("statusCode", 200);
		 map.put("token",admin.getId());
		 return map;
		}
		map.put("statusCode", 404);
		 return map;
		
		
	//	CustomerDAO customerDAO  = new CustomerDAO();
		//this.customerDAO.createCustomer(customer);
		
	}
	
}
