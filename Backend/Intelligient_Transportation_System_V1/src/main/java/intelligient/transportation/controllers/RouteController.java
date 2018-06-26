package intelligient.transportation.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.dao.RouteDAO;
import intelligient.transportation.dao.TokenHandler;
import intelligient.transportation.models.Request;
import intelligient.transportation.models.Route;


@RestController
@RequestMapping("/route")
public class RouteController {

	@Autowired
	RouteDAO routeDAO ;
	
	@RequestMapping(value="/user/{id}", method=RequestMethod.GET)
	@ResponseBody
	public List<HashMap<String,Object>> getRouteBySalesman(@PathVariable int id,@RequestHeader String token, HttpServletResponse response) {
		
		int decodedUserId = TokenHandler.getUserIdFromToken(token);
		System.out.println(decodedUserId);
		if(decodedUserId==-1) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return null;
		}
		Route route = routeDAO.getById(id);
		List<HashMap<String,Object>> locations = new ArrayList<HashMap<String,Object>>();
		if(route!=null){
		List<Request> requests = (List<Request>) route.getRequests();
		requests.sort((Request r1, Request r2) -> r1.getRequestPriority()-r2.getRequestPriority());
		for(Request r :requests){
			HashMap<String,Object> json = new HashMap<String,Object>();
			json.put("lat", r.getUser().getLatitude());
			json.put("longi", r.getUser().getLongitude());
			json.put("name", r.getUser().getName());
			json.put("address", r.getUser().getAddress());
			json.put("phone", r.getUser().getPhone());
			locations.add(json);
		}
		routeDAO.delete(route.getId());
		}
		
		return locations;
	}
}
