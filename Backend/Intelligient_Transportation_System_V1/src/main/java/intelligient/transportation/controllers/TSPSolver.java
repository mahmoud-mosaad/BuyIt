package intelligient.transportation.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import intelligient.transportation.algorithms.Chromosome;
import intelligient.transportation.algorithms.Cluster;
import intelligient.transportation.algorithms.GA;
import intelligient.transportation.algorithms.Haversine;
import intelligient.transportation.algorithms.Solution;
import intelligient.transportation.algorithms.point;
import intelligient.transportation.dao.RequestDAO;
import intelligient.transportation.dao.RouteDAO;
import intelligient.transportation.dao.SalesmanDAO;
import intelligient.transportation.models.Request;
import intelligient.transportation.models.Route;
import intelligient.transportation.models.Salesman;

@RestController
@RequestMapping("/tspsolver")
@CrossOrigin(origins = "http://localhost:4200")
public class TSPSolver {

	@Autowired
	RequestDAO requestDao;
	
	@Autowired
	SalesmanDAO salesmanDao;
	
	@Autowired
	RouteDAO routeDao;
	
	Cluster cluster = new Cluster();
	Solution solution;
	
	 public double EvaluateDistance(ArrayList<point> points) {
	      
	          double totalDistance=0.0;
	          for(int j=1 ; j<points.size() ; j++){
	              totalDistance+=(Haversine.HaversineInKM(points.get(j-1).latitude,points.get(j-1).longitude, points.get(j).latitude,points.get(j).longitude));
	          }
	        
	        return totalDistance;
	    }
	
	 
	@RequestMapping(value="/run", method=RequestMethod.GET)
	public ArrayList<point> run(){
	    List<Salesman> salesman =  salesmanDao.getAvailableUsers();
		List<Request> requests = requestDao.getRequests();
		if(salesman.size()==0 || requests.size()==0)return null;
		
		ArrayList<point> points = new ArrayList<point>();
		for(int i=0 ; i<requests.size();i++){
		  point p=new point(requests.get(i).getUser().getLongitude(),requests.get(i).getUser().getLatitude(),requests.get(i).getId());
		  points.add(p);	
		}
		
		
		ArrayList<ArrayList<point>> clusters = cluster.algorithm(points, salesman.size());
		
		for(int i=0 ; i<clusters.size() ;i++){
			/*if(clusters.get(i).size()<=12)
				Call Exact ->Zeyad*/
			/*else{
			 * Solution=new Assel();
			 * Solution=new Hamed();
			 * Solution=new Mahmoud();
			 * }
			 */
			solution = new GA();
			points=solution.construct(clusters.get(i));
			double distanceGA = EvaluateDistance(points);
			//Check the minimum distance and take the tour from it
			
			Route route =new Route();
			
			route.setUser(salesman.get(i));
			
			for(int j=0 ; j<clusters.get(i).size() ;j++){
				Request request = requestDao.getById(clusters.get(i).get(j).requestId);
				request.setRequestPriority(j+1);
				request.setRoute(route);
				route.getRequests().add(request);
				
			}
			routeDao.createRoute(route);
			salesman.get(i).setAvailable(false);
			salesmanDao.update(salesman.get(i));
		}
		return points;
		
		
		
	}
	
}
