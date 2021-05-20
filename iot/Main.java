

/*
Project: IoT Hugs the Rails System
Group: 9
Team Name: Topham Hats
Members: Eleanor Katsman, Alfredo Mendez, Murat Ulu, Simrun Heir 
*/

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			IDandPasswords idandPasswords= new IDandPasswords();
			
			LoginPage loginPage = new LoginPage(idandPasswords.getLoginInfo());
			
	}

}
