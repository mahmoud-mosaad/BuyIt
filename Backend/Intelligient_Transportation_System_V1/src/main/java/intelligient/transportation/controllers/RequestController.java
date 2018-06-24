package intelligient.transportation.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.dao.OrderDAO;
import intelligient.transportation.dao.RequestDAO;
import intelligient.transportation.dao.TokenHandler;
import intelligient.transportation.dao.UserDAO;
import intelligient.transportation.models.Customer;
import intelligient.transportation.models.Order;
import intelligient.transportation.models.Product;
import intelligient.transportation.models.Request;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins = "*")
public class RequestController {

	@Autowired 
	RequestDAO requestDAO;
	@Autowired
	OrderDAO orderDAO;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public List getRequests(@RequestHeader String token, HttpServletResponse response){
	
		int decodedUserId = TokenHandler.getUserIdFromToken(token);
		System.out.println(decodedUserId);
		if(decodedUserId==-1) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
		List<Request> requests = requestDAO.getall();
	 List<HashMap<String, String>> entities = new ArrayList<HashMap<String, String>>();
		 
		// entity.put("id", request.getId());
	      for (Request request : requests) {
	            HashMap<String,String> entity = new HashMap<String,String>();
	            entity.put("id", String.valueOf(request.getId()));
	            entity.put("customer", request.getUser().getName());
	            entity.put("lat", String.valueOf(request.getUser().getLatitude()));
	            entity.put("long", String.valueOf(request.getUser().getLongitude()));
	            entity.put("priority", String.valueOf(request.getRequestPriority()));
	            if(request.getRoute()!=null){
	            entity.put("salesman", request.getRoute().getUser().getName());
	            }else{
		            entity.put("salesman", "null");
	            }
	            
	            entities.add(entity);
	        }
	   return entities;
		
	}
	
	
	
	@RequestMapping(value="/submitRequest", method=RequestMethod.POST)
	@ResponseBody
	public String submitRequest(@RequestBody String request,@RequestHeader String token, HttpServletResponse response) {
		
		
		System.out.println(token);
		int decodedUserId = TokenHandler.getUserIdFromToken(token);
		System.out.println(decodedUserId+"  aaaa");
		if(decodedUserId==-1) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return "Request rejected Wrong token, token can't be verified";
		}
		
		System.out.println("Submit Request");
		Request reqObj = requestDAO.createRequest(request);
		JSONObject req = new JSONObject(request);
		JSONArray products = req.getJSONArray("products");
		
		for (int i = 0; i < products.length(); i++) {
		    JSONObject prod = products.getJSONObject(i);
		    int prodId = prod.getInt("productId");
		    int quantity = prod.getInt("productQuantity");
		    
		    //get product from table by id
		    Product p = new Product();
		    p.setId(prodId);
		    Order order = new Order();
		    order.setQuantity(quantity);
		    order.setProduct(p);
		    order.setRequest(reqObj);
		    
		    orderDAO.createOrder(order);
		}
		
		
		return request;
	}
	
}
