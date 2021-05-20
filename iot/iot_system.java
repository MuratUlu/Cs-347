import java.io.*;
import java.io.IOException;
import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class iot_system {
	
    //constructor
    protected iot_system() {
        
    }
    
    //getters and setters
    public static void logMsg(String msg) {
		//Our log file is located in "src/iot/log-trip.txt", change this if we change the file location
		
		//SYNTAX FOR ADDING LOG MESSAGES
		//   logMsg(msg);
		//   logMsg("text here");
		//It automatically makes each entry a new line
    	boolean newFile = false;
		try
	    {
			File file = new File ("log.txt");
			if(!file.exists()) {
				file.createNewFile();
				newFile = true;
			}
	       	FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); 
	       	//the second parameter (true) means it appends to this text file, not overwrite
	       	if(newFile == true) {
	       		fw.write("New log file created - previous log in 'admin-logs' folder\n\n\n");
	       	}
	        fw.write(msg+"\n");
	        fw.close();
	        newFile = false;
	    }
	    catch(IOException e)
	    {
	        throw new RuntimeException("Error opening file: log.txt");
	    }
	}

	//To-Do
	public static void getLogFile(){
		//create a copy of log.txt, name it admin-log.txt, will be in iot folder
		//append "Admin request of logs" to admin-log.txt
		//delete log.txt file
		try {
		File oldLogFile = new File ("log.txt");
		Reader oldLog = new FileReader(oldLogFile.getAbsoluteFile());
		
		String fileName = new SimpleDateFormat("yyyMMddHHmmss'.txt'").format(new Date());
		File newAdminLog = new File ("admin-logs/admin_log-" + fileName + ".txt");
		newAdminLog.createNewFile();
		Writer newAdministratorLog = new FileWriter(newAdminLog.getAbsoluteFile());
		
		int c;
		while((c = oldLog.read()) != -1) {
			newAdministratorLog.write(c);
		}
		oldLog.close();
		newAdministratorLog.close();
		oldLogFile.delete();
		}
		catch(IOException e)
	    {
	        throw new RuntimeException("Error creating admin log.");
	    }
	}

//================== functions ==================

//input: double array (calculates average of everything in array)
	public double calculateAverage(double [] array) { //input double array
		int sum = 0;
		for(int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		DecimalFormat numberFormat = new DecimalFormat("#.00");
		return Double.parseDouble(numberFormat.format(sum / array.length));
	}

//input: double avgRpm, double speed (speed is gps speed, radius of wheel is assumed to be .46 meters)
	public int calculateSlipRatio(double avgRPM, double gpsSpeed) {
		double angularV = avgRPM*6;
		int num = (int)(((angularV * .46) / gpsSpeed) -1);
		return num;
	}

	//input: int trainSpeed, int objSpeed, int objDistance, boolean isRear
	public int timeUntilCollision(int trainSpeed, int objSpeed, int objDistance, boolean isRear) {
		if (isRear == false) {
			return (objDistance/(trainSpeed - objSpeed));
		}
		else {
			return (objDistance/(objSpeed - trainSpeed));
		}
	}
	
	//calculates warnings for each object
	public String calculateWarningObject(DetectedObject obj, double gpsSpeed) {
		String s = "";
		
		int timeUntilCollision = timeUntilCollision((int)gpsSpeed, obj.getSpeed(), obj.getDistance(), obj.getIsRear());
		
		//Object Detection warning
		if(timeUntilCollision < 200 && obj.getIsRear() == false && obj.getHeight() > 1.5) {
			s = s.concat("RED WARNING: OBJECT DETECTED AHEAD - Stop train. TUC: " + timeUntilCollision + "sec\n");
		}
		else if(timeUntilCollision < 260 && timeUntilCollision >= 200 && obj.getIsRear() == false && obj.getHeight() > 1.5) {
			s = s.concat("ORANGE WARNING: OBJECT DETECTED AHEAD - Apply break to half-speed.\n");
		}
		else if(timeUntilCollision < 260 && obj.getIsRear() == true && obj.getHeight() > 1.5 && obj.getSpeed() > gpsSpeed) {
			s = s.concat("YELLOW WARNING: OBJECT DETECTED BEHIND - Match speed of rear object - " + obj.getSpeed() + "m/s.\n");
		}
		
		return s;
	}
	
	//HORN ASSIGNMENT IS THIS FUNCTION
	//calculates warnings for each gate
	public String calculateWarningGate(Gate gate) {
		String s = "";		
		//Gate Detection warning
		if(gate.isOpen() == true && gate.getDistance() > 0) {
			s = s.concat("OPEN GATE DETECTED: Please use horn and apply breaks.\n");
		}
		else if(gate.isOpen() == false && gate.getDistance() < 1650 && gate.getDistance() > 800) {
			s = s.concat("CLOSED GATE AHEAD: Honk horn for 15 seconds.\n");
		}
		else if(gate.isOpen() == false && gate.getDistance() <= 400) {
			s = s.concat("PASSING THROUGH CLOSED GATE: Honk horn for 6 seconds.\n");
		}	
		return s;
	}
	
	//Calculates warnings based on parameter values
	public String calculateWarningOther(double[] rpms,  double gpsSpeed, double[] airPressures) {
		String s = "";
		
		//Slip-Ratio warning
		double slipRatio = calculateSlipRatio(calculateAverage(rpms), gpsSpeed);
		if(slipRatio < 10 && slipRatio > 7.5) {
			s = s.concat("SLIP-RATIO < 10% (" + slipRatio + ") : Put sand on tracks.\n");
		}
		else if(slipRatio <= 7.5 && slipRatio > 5) {
			s = s.concat("SLIP-RATIO < 7.5% (" + slipRatio + "): Put sand on track & SLOW DOWN.\n");
		}
		else if(slipRatio <= 5) {
			s = s.concat("SLIP-RATIO < 5% (" + slipRatio + "): Put sand on tracks & SLOW DOWN aggressively.\n");
		}
		
		//Weather Warning
		double avgHg = calculateAverage(airPressures);
		if(avgHg <= 29.8) {
			s = s.concat("WEATHER WARNING: Heavy rain / snow expected.\n");
		}
		else if(avgHg < 30.2) {
			s = s.concat("WEATHER WARNING: Rain is incoming.\n");
		}

		return s;
	}
	
}
