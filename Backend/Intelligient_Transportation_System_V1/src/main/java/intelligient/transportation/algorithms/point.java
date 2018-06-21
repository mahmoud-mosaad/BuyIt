package intelligient.transportation.algorithms;

public class point {
   public point(){}
   public point( double longitude,double latitude,int requestId,int index){
     this.longitude=longitude;
     this.latitude=latitude;
     this.requestId=requestId;
     this.index=index;
    }
    public double longitude;
    public double latitude;
    public int requestId;
    public int index;
    
}