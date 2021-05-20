import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;

import javax.swing.JButton;
import javax.swing.*;

public class LoginPage implements ActionListener{

	JFrame frame = new JFrame();
	JButton loginButton = new JButton("Login");
	JButton resetButton = new JButton("Reset");
	JTextField userIDField = new JTextField();
	JPasswordField userPassword = new JPasswordField();
	JLabel userIDLabel = new JLabel("userID:");
	JLabel userPassLabel = new JLabel("Password:");
	JLabel messageLabel = new JLabel();
	
	HashMap<String,String> loginInfo= new HashMap<String,String>();
	
	LoginPage(HashMap<String,String> loginInfoOG){
		
		
		//Setting up the GUI
		
		userIDLabel.setBounds(50,100,75,25);
		userPassLabel.setBounds(50,150,75,25);
		
		messageLabel.setBounds(125, 250, 250, 35);
		messageLabel.setFont(new Font(null,Font.ITALIC, 25));
		
		userIDField.setBounds(125,100,200,25);
		userPassword.setBounds(125,150,200,25);
		
		loginButton.setBounds(125,200,100,25);
		loginButton.setFocusable(false);
		loginButton.addActionListener(this);
		
		
		frame.add(userIDField);
		frame.add(userPassword);
		frame.add(userIDLabel);
		frame.add(userPassLabel);
		frame.add(messageLabel);
		frame.add(loginButton);
		loginInfo= loginInfoOG;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setLayout(null);
		frame.setVisible(true);
		
	}
	//whenever button is pushed, this code runs to check if login credentials are valid
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource()==loginButton) {
			String userID=userIDField.getText();
			String password =String.valueOf(userPassword.getPassword());
			
			Timestamp timestamp = new Timestamp (new Date().getTime());
			System.out.println(timestamp + "   - login attempt -   ");
			
			String uidHash = hashDis(userID);
			String passHash = hashDis(password);
			System.out.println("Un-hashed Username: " + userID);
			System.out.println("Un-hashed Password: " + password);
			System.out.println("Hashed Username: " + uidHash);
			System.out.println("Hashed Password: " + passHash);
			
			if(loginInfo.containsKey(uidHash)) {
				if(loginInfo.get(uidHash).equals(passHash)) {
					messageLabel.setForeground(Color.green);
					messageLabel.setText("Login successful");
					
					frame.dispose();
					MainPage mainPage = new MainPage(userID);
				}
				else {
					userIDField.setText("");
					userPassword.setText("");
				}
			}
			else {
				userIDField.setText("");
				userPassword.setText("");
			}
		}
		
	}
	//Method that does the hashing of usernames and passwords using SHA-256
	static private String hashDis(String plaintext) {
		MessageDigest digest = null;
		try {
			 digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			digest.update(plaintext.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] hash = digest.digest();
		
		char[] HEX_CHARS= "0123456789ABCDEF".toCharArray();
		
		StringBuilder sb = new StringBuilder(hash.length*2);
		for(byte b : hash) {
			sb.append(HEX_CHARS[(b & 0xF0) >> 4]);
			sb.append(HEX_CHARS[b & 0x0F]);
		}
		String hex= sb.toString();
//		System.out.println(hex);
		return hex;
	}

}
