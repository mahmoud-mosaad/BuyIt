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
@CrossOrigin(origins = "*")
public class AdminController extends UserController{

	@Autowired
	private AdminDAO adminDAO;
	
	@RequestMapping(value="/logInAdmin", method=RequestMethod.POST)
	@ResponseBody
	public String logIn(@RequestBody Admin admin) {
		
		return adminDAO.logInAdmin(admin);
	}
}
