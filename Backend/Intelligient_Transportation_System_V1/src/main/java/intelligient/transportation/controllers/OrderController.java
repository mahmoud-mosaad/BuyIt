package intelligient.transportation.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import intelligient.transportation.dao.OrderDAO;
import intelligient.transportation.dao.TokenHandler;
import intelligient.transportation.dao.UserDAO;
import intelligient.transportation.models.Product;

@RestController
@RequestMapping("Order")
public class OrderController {
	
	@Autowired
	OrderDAO orderRepository;
	
	@RequestMapping(method=RequestMethod.GET, path= "/getOrderByUserId/{UserId}")
	public List getAllOrder(@PathVariable int UserId, 
	    		@RequestHeader String token, HttpServletResponse response) {
		   
		    //System.out.println(token);
			int decodedUserId = TokenHandler.getUserIdFromToken(token);
			//System.out.println(decodedUserId);
			if(decodedUserId==-1) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return null;
			}
			
	        return orderRepository.getOrderByUserId(UserId);
	 }
}