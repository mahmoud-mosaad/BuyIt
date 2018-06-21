package intelligient.transportation.algorithms;

import java.io.IOException;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

public class DistanceMatrixAPI {

public static long getDistance(String origin, String destination) throws ApiException, InterruptedException, IOException{
		
		//set up key
	   	GeoApiContext distCalcer = new GeoApiContext.Builder()
			    .apiKey("AIzaSyCgpuUQt8Wsqjr8mqMuzCPq_R-WQXZRpBo")
			    .build();
	   	
	   	DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(distCalcer); 
	       DistanceMatrix result = req.origins(origin)
	               .destinations(destination)
	               .mode(TravelMode.DRIVING)
	               .units(Unit.METRIC)
	               .language("en-US")
	               .await();
	       
				long distApart = result.rows[0].elements[0].distance.inMeters;
		
		return distApart;
	}

  public static long[][] distanceMatrix(String[] origins,String[] destinations) throws ApiException, InterruptedException, IOException{
	/*=new String[]{"30.039451,31.202534","31.267296,29.989697"};
	String[] destinations=new String[]{"31.292534,30.040523","30.321361,31.226865"};*/
	GeoApiContext context = new GeoApiContext.Builder()
		    .apiKey("AIzaSyCgpuUQt8Wsqjr8mqMuzCPq_R-WQXZRpBo")
		    .build();
		
		DistanceMatrixApiRequest req=DistanceMatrixApi.newRequest(context);
		DistanceMatrix t=req.origins(origins).destinations(destinations).mode(TravelMode.DRIVING).await();
		long[][] array=new long[origins.length][destinations.length];
		
		for(int i=0;i<origins.length;i++){
			for(int j=0;j<destinations.length;j++){
				//System.out.println(t.rows[i].elements[j].distance.inMeters);
				array[i][j]=t.rows[i].elements[j].distance.inMeters;
			}
		}
		return array;
	
}
}

