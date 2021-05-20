public class DetectedObject {
    //data fields
    public int height;
    public int distance;
    public int speed;
    public boolean isRear;

    //constructor
    public DetectedObject(int height, int distance, int speed, boolean isRear) {
    	this.height = height;
    	this.distance = distance;
    	this.speed = speed;
    	this.isRear= isRear;
    }

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public boolean getIsRear() {
		return isRear;
	}
	public void setIsRear(boolean isRear) {
		this.isRear=isRear;
	}
	public String toString() {
		return " H: "+this.height+" D: "+ this.distance+" S: "+this.speed+" isRear: "+this.isRear;
	}

}
