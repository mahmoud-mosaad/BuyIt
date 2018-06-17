package intelligient.transportation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.dao.RequestDAO;
import intelligient.transportation.models.Customer;

import intelligient.transportation.models.Request;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "http://localhost:4200")
public class RequestController {
	@Autowired
	RequestDAO requestDao;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String getRequests(){
	
		return requestDao.getRequests().get(0).getUser().getName();
	}
	
	
	
}
