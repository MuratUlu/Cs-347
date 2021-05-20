import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MainPage extends JFrame implements ActionListener , ChangeListener{
	
	static final int AP_MIN=0;
	static final int AP_MAX=10;
	static final int AP_INIT=1;
	
	JList<String> doList;
	JList<String> gateList;
	
	JButton logoutButt;
	
	JButton addButton;
	JButton deleteButton;
	
	JButton gaddButton;
	JButton gdeleteButton;
	
	JButton downloadButton;
	
	JSpinner distanceSpinner;
	SpinnerNumberModel dSpinModel;
	
	JSpinner heightSpinner;
	SpinnerNumberModel hSpinModel;
	
	JSpinner speedSpinner;
	SpinnerNumberModel sSpinModel;
	
	JSpinner gdistanceSpinner;
	SpinnerNumberModel gdSpinModel;
	
	
	JPanel apSliderPanel = new JPanel();
	JPanel rpmPanel = new JPanel();
	JPanel listPanel = new JPanel();
	JPanel glistPanel = new JPanel();
	JPanel warningPanel=new JPanel();
	JPanel tempPanel= new JPanel();
	JPanel speedPanel= new JPanel();
	JPanel wifiPanel= new JPanel();
	
	//fix air preassure slider
	JSlider apSlider= new JSlider(JSlider.HORIZONTAL, 0, 700, 580);
	JSlider tempSlider= new JSlider(JSlider.HORIZONTAL, -100, 140,70);
	
	JSlider apSlider2= new JSlider(JSlider.HORIZONTAL,0, 700, 580);
	JSlider tempSlider2= new JSlider(JSlider.HORIZONTAL, -100, 140,70);
	
	JSlider rpmSlider= new JSlider(JSlider.HORIZONTAL, 0, 1000,500);
	JSlider rpmSlider2= new JSlider(JSlider.HORIZONTAL, 0,1000,500);
	
	JSlider gpsSlider= new JSlider(JSlider.HORIZONTAL, 0, 200 ,100);
	
	JCheckBox isRearCB = new JCheckBox("Object is behind");
	
	JCheckBox wifiIsOnCB= new JCheckBox("Wifi is working");
	JCheckBox gpsIsOnCB= new JCheckBox("GPS is On");
	
	JCheckBox isOpenCP = new JCheckBox("Gate is open");
	
	
	JFrame frame = new JFrame("main");
	JFrame tsnrFrame = new JFrame("controller");
	
	JLabel welcomeLabel = new JLabel();
	JLabel apLabel = new JLabel("Air Pressure");
	JLabel tempLabel = new JLabel("Temperature");
	JLabel rpmLabel = new JLabel("    RPM    ");
	JLabel gpsLabel = new JLabel("GPS Speed");
	JLabel distLabel = new JLabel("Distance");
	JLabel speedLabel = new JLabel("Speed");
	JLabel heightLabel= new JLabel("Height");
	JLabel weatherLabel= new JLabel("weather");
	JLabel mainSpeedLabel= new JLabel("speed");
	JLabel mainWIfiLabel= new JLabel("Wi-Fi");
	
	JLabel warningLabel= new JLabel("warning warning");
	JTextArea warningTA= new JTextArea("holder");
	TSNR tsnr = new TSNR();
	iot_system iotsys= new iot_system();
	
	String currentUser;
	double [] temps= new double[2];
	double [] aps= new double[2];
	double[] rpms= new double[2];
	
	MainPage(String userID){
		
		currentUser=userID;
		tsnr.setCurrentUser(currentUser);
		
		downloadButton= new JButton("download log");
		downloadButton.setBounds(550, 10, 300, 30);
		
		logoutButt= new JButton ("Log Out");
		logoutButt.setBounds(350,10 , 150, 30);

		addButton= new JButton("Add");
		deleteButton= new JButton("delete");
		
		gaddButton= new JButton("Add");
		gdeleteButton= new JButton("delete");
//		MainPage a= new MainPage();
		welcomeLabel.setBounds(0,0,300,35);
		welcomeLabel.setFont(new Font(null,Font.PLAIN,25));
		welcomeLabel.setText("User: "+userID);
		
		apLabel.setAlignmentX(CENTER_ALIGNMENT);
		apLabel.setBounds(0,25,300,50);
		apLabel.setFont(new Font(null,Font.PLAIN,25));

		tempLabel.setAlignmentX(CENTER_ALIGNMENT);
		tempLabel.setBounds(0,25,300,50);
		tempLabel.setFont(new Font(null,Font.PLAIN,25));
		
		rpmLabel.setAlignmentX(CENTER_ALIGNMENT);
		rpmLabel.setBounds(0,25,300,50);
		rpmLabel.setFont(new Font(null,Font.PLAIN,25));
		
		gpsLabel.setAlignmentX(CENTER_ALIGNMENT);
		gpsLabel.setBounds(0,25,300,50);
		gpsLabel.setFont(new Font(null,Font.PLAIN,25));
		
		warningLabel.setAlignmentX(CENTER_ALIGNMENT);
		warningLabel.setBounds(0,25,300,50);
		warningLabel.setFont(new Font(null,Font.PLAIN,35));
		
		weatherLabel.setAlignmentX(CENTER_ALIGNMENT);
		weatherLabel.setBounds(0,25,300,50);
		weatherLabel.setFont(new Font(null,Font.PLAIN,35));
		
		mainSpeedLabel.setAlignmentX(CENTER_ALIGNMENT);
		mainSpeedLabel.setBounds(0,25,300,50);
		mainSpeedLabel.setFont(new Font(null,Font.PLAIN,35));
		
		mainWIfiLabel.setAlignmentX(CENTER_ALIGNMENT);
		mainWIfiLabel.setBounds(0,25,300,50);
		mainWIfiLabel.setFont(new Font(null,Font.PLAIN,35));
		
		warningTA.setFont(new Font(null,Font.PLAIN,15));
		
		gpsIsOnCB.isVisible();
		gpsIsOnCB.setBounds(100,100,100,100);
		
		
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("25.00") );
		labelTable.put( new Integer( 350 ), new JLabel("28.50") );
		labelTable.put( new Integer( 700 ), new JLabel("32.00") );
		//apSlider.addChangeListener((ChangeListener) this);
		apSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source=(JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					aps[0]=(2500+(int)source.getValue())*.01;
				}
				
			}
			
		});
		
//		apSlider.setMinorTickSpacing(50);
//		apSlider.setMajorTickSpacing(150);
		apSlider2.setLabelTable( labelTable );
		apSlider.setBounds(25, 0, 300, 50);
		apSlider.setPaintTicks(true);
		apSlider.setPaintLabels(true);
		apSlider.setFont(new Font(null, Font.PLAIN,15));
		apSlider.setVisible(true);
		
		tempSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source=(JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					temps[0]=(int)source.getValue();
					weatherLabel.setText(""+iotsys.calculateAverage(temps)+"\u00B0 F");
				}
				
			}
			
		});
		tempSlider.setMinorTickSpacing(10);
		tempSlider.setMajorTickSpacing(50);
		tempSlider.setBounds(25,0, 300, 50);
		tempSlider.setPaintTicks(true);
		tempSlider.setPaintLabels(true);
		tempSlider.setFont(new Font(null, Font.PLAIN,15));
		tempSlider.setVisible(true);
		
		apSlider2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source=(JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					aps[1]=(2500+(int)source.getValue())*.01;
				}
				
			}
			
		});
		apSlider2.setMinorTickSpacing(50);
		apSlider2.setMajorTickSpacing(150);
		
		
		apSlider2.setLabelTable( labelTable );
		apSlider2.setBounds(25, 0, 300, 50);
		apSlider2.setPaintTicks(true);
		apSlider2.setPaintLabels(true);
		apSlider2.setFont(new Font(null, Font.PLAIN,15));
		apSlider2.setVisible(true);
		
		tempSlider2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source=(JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					temps[1]=(int)source.getValue();
					weatherLabel.setText(""+iotsys.calculateAverage(temps)+"\u00B0 F");
				}
				
			}
			
		});
		tempSlider2.setMinorTickSpacing(10);
		tempSlider2.setMajorTickSpacing(50);
		tempSlider2.setBounds(25, 0, 300, 50);
		tempSlider2.setPaintTicks(true);
		tempSlider2.setPaintLabels(true);
		tempSlider2.setFont(new Font(null, Font.PLAIN,15));
		tempSlider2.setVisible(true);
//		
		rpmSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source=(JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					rpms[0]=(int)source.getValue();
				}
				
			}
			
		});
		rpmSlider.setMinorTickSpacing(50);
		rpmSlider.setMajorTickSpacing(250);
		rpmSlider.setBounds(25, 100, 300, 50);
		rpmSlider.setPaintTicks(true);
		rpmSlider.setPaintLabels(true);
		rpmSlider.setFont(new Font(null, Font.PLAIN,15));
		rpmSlider.setVisible(true);
//		
		rpmSlider2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source=(JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					rpms[1]=(int)source.getValue();
				}
				
			}
			
		});
		rpmSlider2.setMinorTickSpacing(50);
		rpmSlider2.setMajorTickSpacing(250);
		rpmSlider2.setBounds(25, 100, 300, 50);
		rpmSlider2.setPaintTicks(true);
		rpmSlider2.setPaintLabels(true);
		rpmSlider2.setFont(new Font(null, Font.PLAIN,15));
		rpmSlider2.setVisible(true);
		
		gpsSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider source=(JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					tsnr.setGpsSpeed(source.getValue());
					mainSpeedLabel.setText(""+tsnr.getGpsSpeed()+" m/s");
				}
				
			}
			
		});
		gpsSlider.setMinorTickSpacing(25);
		gpsSlider.setMajorTickSpacing(100);
		gpsSlider.setBounds(25, 100, 300, 50);
		gpsSlider.setPaintTicks(true);
		gpsSlider.setPaintLabels(true);
		gpsSlider.setFont(new Font(null, Font.PLAIN,15));
		gpsSlider.setVisible(true);
		
		
		wifiIsOnCB.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				JCheckBox source=(JCheckBox)e.getSource();
				tsnr.setWifiWorking(source.isSelected());
				if(source.isSelected()) {
					wifiPanel.setBackground(Color.green);
				}
				else {
					wifiPanel.setBackground(Color.red);
				}
			}
			
		});
		
		//JPanel apSliderPanel = new JPanel();
		apSliderPanel.setBounds(25, 30, 300, 550);
		apSliderPanel.setBackground(Color.cyan);
		
		apSliderPanel.add(apLabel);
		apSliderPanel.add(apSlider);
		apSliderPanel.add(apSlider2);
		apSliderPanel.add(tempLabel);
		apSliderPanel.add(tempSlider);
		apSliderPanel.add(tempSlider2);
		//apSliderPanel.add(rpmLabel);
//		apSliderPanel.add(rpmSlider);
//		apSliderPanel.add(rpmSlider2);
		
		
		rpmPanel.setBounds(25, 600, 300, 300);
		rpmPanel.setBackground(Color.red);
	
		apSliderPanel.add(rpmLabel);
		apSliderPanel.add(rpmSlider);
		apSliderPanel.add(rpmSlider2);
		apSliderPanel.add(gpsLabel);
		apSliderPanel.add(gpsSlider);
		
		//rpmPanel.add(gpsIsOnCB);
		rpmPanel.add(wifiIsOnCB);
		
		listPanel.setBounds(350,25,450,300);
		listPanel.setBackground(Color.GREEN);
		
		glistPanel.setBounds(350, 350, 450, 300);
		glistPanel.setBackground(Color.PINK);
		
		warningPanel.setBounds(50,50,700,400);
		warningPanel.setBackground(Color.PINK);
		
		tempPanel.setBounds(50,450,150,150);
		tempPanel.setBackground(Color.LIGHT_GRAY);
		
		
		speedPanel.setBounds(200,450,150,150);
		speedPanel.setBackground(Color.MAGENTA);
		
		speedPanel.add(mainSpeedLabel);
		
		wifiPanel.setBounds(350,450,150,150);
		wifiPanel.setBackground(Color.GREEN);
		
		wifiPanel.add(mainWIfiLabel);
		ArrayList<DetectedObject> detObjects= new ArrayList<DetectedObject>();
		ArrayList<Gate> gates = new ArrayList<Gate>();
		
		tsnr.setObjectsDetected(detObjects);
		tsnr.setGatesDetected(gates);
		
		DetectedObject do1 = new DetectedObject(3,4,6,true);
		DetectedObject do2 = new DetectedObject(500,500,500,false);
		
		Gate gate1 = new Gate(true, 100);
		Gate gate2 = new Gate(false, 234);
		
		doList= new JList<String>();
		gateList= new JList<String>();

		//speed in MPH
		//distance in meters
		//height in feet
		dSpinModel= new SpinnerNumberModel(500,0,10000,1);
		distanceSpinner = new JSpinner(dSpinModel);
		
		sSpinModel= new SpinnerNumberModel(500,0,500,1);
		speedSpinner = new JSpinner(sSpinModel);
		
		hSpinModel= new SpinnerNumberModel(50,0,50,1);
		heightSpinner = new JSpinner(hSpinModel);
		
		
		distanceSpinner.setBounds(100,100,100,100);
		speedSpinner.setBounds(100,100,100,100);
		heightSpinner.setBounds(100,100,100,100);
		
		listPanel.add(new JScrollPane(doList));
		listPanel.add(distLabel);
		listPanel.add(distanceSpinner);
		listPanel.add(heightLabel);
		listPanel.add(heightSpinner);
		listPanel.add(speedLabel);
		listPanel.add(speedSpinner);
		listPanel.add(addButton);
		listPanel.add(deleteButton);
		listPanel.add(isRearCB);
		
		
		gdSpinModel= new SpinnerNumberModel(500,0,10000,1);
		gdistanceSpinner= new JSpinner(gdSpinModel);
		
		gdistanceSpinner.setBounds(100,100,100,100);
		
		glistPanel.add(new JScrollPane(gateList));
		glistPanel.add(gdistanceSpinner);
		glistPanel.add(isOpenCP);
		glistPanel.add(gaddButton);
		glistPanel.add(gdeleteButton);
		
//		warningPanel.add(warningLabel);
		warningPanel.add(warningTA);
		tempPanel.add(weatherLabel);
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		DefaultListModel<String> model2 = new DefaultListModel<String>();

		
		doList.setModel(model);
		gateList.setModel(model2);
		
		detObjects.add(do1);
		detObjects.add(do2);
		
		gates.add(gate1);
		gates.add(gate2);
		
		model.addElement(do1.toString());
		model.addElement(do2.toString());
		
		model2.addElement(gate1.toString());
		model2.addElement(gate2.toString());
		
		downloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				iot_system.getLogFile();
				System.out.println("download initiated");
			}
			
		});
		
		logoutButt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("User " + currentUser  + " logged out.");
				iot_system.logMsg("\nUser " + currentUser  + " logged out.\n");
				frame.dispose();
				tsnrFrame.dispose();
				IDandPasswords idandPasswords= new IDandPasswords();
				LoginPage lp= new LoginPage(idandPasswords.getLoginInfo());
			}
			
		});
		
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int dist= (Integer)distanceSpinner.getValue();
				int height=(Integer) heightSpinner.getValue();
				int speed=(Integer) speedSpinner.getValue();
				boolean rear=(boolean) isRearCB.isSelected();
				DetectedObject newDO= new DetectedObject(height,dist,speed,rear);
				detObjects.add(newDO);
				model.addElement(newDO.toString());
				
				doList.revalidate();
				
			}
			
		});
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String selectedVal=doList.getSelectedValue();
				if(selectedVal !=null) {
					
//					System.out.println("delButt: "+selectedVal);
					for(int i=0;i<detObjects.size();i++) {
						if(detObjects.get(i).toString().equals(selectedVal)) {
							detObjects.remove(i);
							break;
						}
					}
					model.remove(doList.getSelectedIndex());
					
				}
			}
			
		});
		
		gaddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int dist= (Integer)gdistanceSpinner.getValue();
				boolean isOpen= isOpenCP.isSelected();
				Gate newGate= new Gate(isOpen,dist);
				gates.add(newGate);
				model2.addElement(newGate.toString());
				
				gateList.revalidate();
				
			}
			
		});
		
		gdeleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String selectedVal=gateList.getSelectedValue();
				if(selectedVal !=null) {
//					System.out.println("delButt: "+selectedVal);
					for(int i=0;i<gates.size();i++) {
						if(gates.get(i).toString().equals(selectedVal)) {
							gates.remove(i);
							break;
						}
					}
					
					model2.remove(gateList.getSelectedIndex());
				
				}
			}
			
		});
		
		Timestamp timestamp = new Timestamp (new Date().getTime());
		System.out.println(timestamp + "   - new login -   Current User: " + currentUser);
		iot_system.logMsg(timestamp + "   - new login -   Current User: " + currentUser + "\n");
		if(currentUser.equals("admin")) {
			frame.add(downloadButton);
		}
		//frame.add(downloadButton);
		frame.add(welcomeLabel);
		frame.add(warningPanel);
		frame.add(tempPanel);
		frame.add(speedPanel);
		frame.add(wifiPanel);
		frame.add(logoutButt);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,900);
		frame.setLayout(null);
		frame.setVisible(true);
//		
		tsnrFrame.add(apSliderPanel);
		tsnrFrame.add(rpmPanel);
		tsnrFrame.add(listPanel);
		tsnrFrame.add(glistPanel);
		//tsnrFrame.add(welcomeLabel);
		tsnrFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tsnrFrame.setSize(900,900);
		tsnrFrame.setLayout(null);
		tsnrFrame.setVisible(true);	
	
		
		
		temps[0]=tempSlider.getValue();
		temps[1]=tempSlider2.getValue();
		tsnr.setTemperatures(temps);
	
		
		
		
		aps[0]= (apSlider.getValue()+2500)*.01;
		aps[1]= (apSlider2.getValue()+2500)*.01;
		tsnr.setAirPressures(aps);
		
		rpms[0]= rpmSlider.getValue();
		rpms[1]= rpmSlider2.getValue();
		tsnr.setRpms(rpms);
		tsnr.setGpsSpeed(gpsSlider.getValue());
		//tsnr.setIsGpsOn(gpsIsOnCB.isSelected());
		tsnr.setWifiWorking(wifiIsOnCB.isSelected());
		
		weatherLabel.setText(""+iotsys.calculateAverage(temps)+"\u00B0 F");
		mainSpeedLabel.setText(""+tsnr.getGpsSpeed()+"m/s");
		
		if(tsnr.getWifiWorking()) {
			wifiPanel.setBackground(Color.green);
		}
		else {
			wifiPanel.setBackground(Color.red);
		}
	
		//Warnings are calculated every .5 seconds
	   ActionListener taskPerformer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String warnString="";
			for(int i=0;i<gates.size();i++) {
				warnString+=iotsys.calculateWarningGate(gates.get(i));
			}
			
			for(int i=0;i<detObjects.size();i++) {
				warnString+=iotsys.calculateWarningObject(detObjects.get(i),tsnr.gpsSpeed);
			}

			warnString+=iotsys.calculateWarningOther(tsnr.getRpms(), tsnr.getGpsSpeed(), tsnr.getAirPressures());

			//System.out.println(warnString);
			warningTA.setText(warnString);
			
			Timestamp timestamp = new Timestamp (new Date().getTime());
			iot_system.logMsg(timestamp + "\n" + warnString);
			
			//System.out.println("air pressure"+tsnr.getAirPressures()[0]+" and " +tsnr.getAirPressures()[1]);
		}
	   };
	   new Timer(500, taskPerformer).start();
       
	}
	
	private static void DoTheTask() {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

