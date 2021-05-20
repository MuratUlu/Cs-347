public class Gate {
     //data fields
    public boolean open;
    public int distance;
 
     //constructor
     public Gate(boolean open, int distance) {
        this.open = open;
        this.distance = distance;
     }

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String toString() {
		return " is open: "+this.open+" distance: "+this.distance+" ";
	}

}
