package intelligient.transportation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.dao.CustomerDAO;
import intelligient.transportation.dao.UserDAO;
import intelligient.transportation.models.Customer;
import intelligient.transportation.models.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired 
	UserDAO userDAO;
	
	@RequestMapping(value="/logIn", method=RequestMethod.POST)
	@ResponseBody
	public String logInUser(@RequestBody User user) {
		
		return userDAO.logInUser(user);
	}
	
}
