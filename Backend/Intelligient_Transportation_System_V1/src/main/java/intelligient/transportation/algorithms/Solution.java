package intelligient.transportation.algorithms;

import java.io.IOException;
import java.util.ArrayList;

import com.google.maps.errors.ApiException;

public interface Solution {
	public ArrayList<point> construct( ArrayList<point> requests,long[][]distanceMatrix)throws ApiException, InterruptedException, IOException;
}
