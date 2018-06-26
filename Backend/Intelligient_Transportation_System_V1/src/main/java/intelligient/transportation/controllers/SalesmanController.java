package intelligient.transportation.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.dao.SalesmanDAO;
import intelligient.transportation.dao.TokenHandler;
import intelligient.transportation.models.Salesman;

@RestController
@RequestMapping("/salesman")
public class SalesmanController {

	@Autowired 
	SalesmanDAO salesmanDAO;
	
	@RequestMapping(value="/signUp", method=RequestMethod.POST)
	@ResponseBody
	public Salesman signUp(@RequestBody Salesman salesman, HttpServletResponse response) {
		if(salesman.getEmail().trim().equals(""))
			salesman.setEmail(null);
	    if(salesman.getPassword().trim().equals(""))
	    	salesman.setPassword(null);
	    
		boolean save = salesmanDAO.createSalesman(salesman);
		if(!save){
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		 return salesman;
		
	}
	
	
	@RequestMapping(value="/checkIn/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public Boolean checkIn(@PathVariable int id,@RequestHeader String token, @RequestHeader String type,HttpServletResponse response) {
		
		int decodedUserId = TokenHandler.getUserIdFromToken(token);
		System.out.println(decodedUserId);
		if(decodedUserId==-1 || !type.equals("Salesman") || decodedUserId!=id) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
		Salesman s = salesmanDAO.checkIn(id);
		return s.getAvailable();
	}
	
	
}
