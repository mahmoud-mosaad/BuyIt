package intelligient.transportation.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.errors.ApiException;

import intelligient.transportation.algorithms.Chromosome;
import intelligient.transportation.algorithms.Cluster;
import intelligient.transportation.algorithms.DistanceMatrixAPI;
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
	
	 public double EvaluateDistance(ArrayList<point> points,long[][] distanceMatrix) throws ApiException, InterruptedException, IOException {
	      
	      double totalDistance=0.0;
	      for(int j=1 ; j<points.size() ; j++){
	    /*   String origin =String.valueOf(points.get(j-1).latitude)+","+String.valueOf(points.get(j-1).longitude);
       	   String destination =String.valueOf(points.get(j).latitude)+","+String.valueOf(points.get(j).longitude);*/
           
	          totalDistance+=(distanceMatrix[points.get(j-1).index][points.get(j).index]);
	      }
	        
	     return totalDistance;
	    }
	
	public void runAlgorithms(ArrayList<ArrayList<point>> clusters, List<Salesman> salesman,long[][] distanceMatrix) throws ApiException, InterruptedException, IOException{
		ArrayList<point> points = new ArrayList<point>();
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
			
			points=solution.construct(clusters.get(i),distanceMatrix);
			
			double distanceGA = EvaluateDistance(points,distanceMatrix);
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
		
	}
	@RequestMapping(value="/run", method=RequestMethod.GET)
	public void run(){
	    List<Salesman> salesman =  salesmanDao.getAvailableUsers();
		List<Request> requests = requestDao.getRequests();
		System.out.println(salesman.size()+"   "+requests.size());
		if(salesman.size()==0 || requests.size()==0)return;
		System.out.println("After null");
		ArrayList<point> points = new ArrayList<point>();
		String[] origins=new String[requests.size()];
		String[] destinations=new String[requests.size()];
		
		for(int i=0 ; i<requests.size();i++){
		  point p=new point(requests.get(i).getUser().getLongitude(),requests.get(i).getUser().getLatitude(),requests.get(i).getId(),i);
		  origins[i]=String.valueOf(p.latitude)+","+String.valueOf(p.longitude);
		  destinations[i]=String.valueOf(p.latitude)+","+String.valueOf(p.longitude);
		  
		  points.add(p);	
		}
		System.out.println("After points");
		
		ArrayList<ArrayList<point>> clusters ;
		try{
			long[][] distanceMatrix = DistanceMatrixAPI.distanceMatrix(origins, destinations);
			System.out.println("After distanceMatrix");
			clusters = cluster.algorithm(points, salesman.size(),distanceMatrix);
			System.out.println("After clusters");
			runAlgorithms(clusters, salesman,distanceMatrix);
			return;
			
		}catch(Exception e){
		
			System.out.println("Catch");
			//List<Request> request =requestDao.getall();
			return; 
			//System.out.println("b4 return in run");
			//return null;
		}
		
		
		
		
		
	}
	
}
