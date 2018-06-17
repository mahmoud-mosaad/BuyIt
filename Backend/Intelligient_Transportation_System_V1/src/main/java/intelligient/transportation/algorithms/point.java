package intelligient.transportation.algorithms;

public class point {
   public point(){}
   public point( double longitude,double latitude,int requestId){
     this.longitude=longitude;
     this.latitude=latitude;
     this.requestId=requestId;
    }
    public double longitude;
    public double latitude;
    public int requestId;
    
}