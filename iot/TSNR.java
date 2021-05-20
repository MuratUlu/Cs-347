import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;

public class TSNR {
    //data fields
    protected boolean isOn;
    protected boolean userIsLoggedIn;
    protected String currentUser;
    protected double temperatures[];
    protected double airPressures[];
    protected double rpms[];
    protected double gpsSpeed;
    protected boolean wifiWorking;
    protected ArrayList<DetectedObject> objectsDetected;
    protected ArrayList<Gate> gatesDetected;

    //constructor
    protected TSNR() {
        this.isOn = true;
        this.userIsLoggedIn = true;
        this.currentUser = "";
        this.temperatures = new double[2];
        this.airPressures = new double[2];
        this.rpms = new double[2];
        this.gpsSpeed = 0;
        this.wifiWorking = true;
        this.objectsDetected = new ArrayList<DetectedObject>();
        this.gatesDetected = new ArrayList<Gate>();
    }

    @Override
	public String toString() {
		return "TSNR [isOn=" + isOn + ", userIsLoggedIn=" + userIsLoggedIn + ", currentUser=" + currentUser
				+ ", temperatures=" + Arrays.toString(temperatures) + ", airPressures="
				+ Arrays.toString(airPressures) + ", rpms=" + Arrays.toString(rpms) + ", gpsSpeed=" + gpsSpeed
				+ ", wifiWorking=" + wifiWorking + ", objectsDetected=" + objectsDetected
				+ ", gatesDetected=" + gatesDetected + "]";
	}

	public ArrayList<DetectedObject> getObjectsDetected() {
		return objectsDetected;
	}

	public void setObjectsDetected(ArrayList<DetectedObject> objectsDetected) {
		this.objectsDetected = objectsDetected;
	}

	public ArrayList<Gate> getGatesDetected() {
		return gatesDetected;
	}

	public void setGatesDetected(ArrayList<Gate> gatesDetected) {
		this.gatesDetected = gatesDetected;
	}

	public void setOn(boolean isOn) {
		this.isOn = isOn;
	}

	public void setWifiWorking(boolean wifiWorking) {
		this.wifiWorking = wifiWorking;
	}

	//getters and setters
    public boolean isIsOn() {
        return this.isOn;
    }

    public boolean getIsOn() {
        return this.isOn;
    }

    public void setIsOn(boolean isOn) {
        this.isOn = isOn;
    }

    public boolean isUserIsLoggedIn() {
        return this.userIsLoggedIn;
    }

    public boolean getUserIsLoggedIn() {
        return this.userIsLoggedIn;
    }

    public void setUserIsLoggedIn(boolean userIsLoggedIn) {
        this.userIsLoggedIn = userIsLoggedIn;
    }

    public String getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public double[] getTemperatures() {
        return this.temperatures;
    }

    public void setTemperatures(double temperatures[]) {
        this.temperatures = temperatures;
    }

    public double[] getAirPressures() {
        return this.airPressures;
    }

    public void setAirPressures(double airPressures[]) {
        this.airPressures = airPressures;
    }

    public double[] getRpms() {
        return this.rpms;
    }

    public void setRpms(double rpms[]) {
        this.rpms = rpms;
    }

    public double getGpsSpeed() {
        return this.gpsSpeed;
    }

    public void setGpsSpeed(double gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }

    public boolean isWifiWorking() {
        return this.wifiWorking;
    }

    public boolean getWifiWorking() {
        return this.wifiWorking;
    }
}

