package intelligient.transportation.controllers;

import java.util.HashMap;

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
	public HashMap<String, String> logInUser(@RequestBody User user) {
		
		HashMap<String, String> data = new HashMap<String, String>();
		User newUser = userDAO.logInUser(user);
		data.put( "token",  newUser.getApiToken());
		data.put("userId", newUser.getId().toString());
		data.put("userType", newUser.getRole());
		return data;
	}
	
}
