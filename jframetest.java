import java.awt.event.*;
import javax.swing.*;
import java.sql.*;


public class jframetest extends JFrame{
	
	static String b;
	static JTextField userIn;
	static JTextField passIn;
	static String user;
	
	static JFrame window;
	static JFrame loginWindow;
	static JFrame opt;
	
	static boolean login;
	public static void main(String args[]) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		
		
		final int WINDOW_WIDTH = 300, 
				WINDOW_HEIGHT =200;
		
		//---------sign up stuff----------//
		//username. 
		JLabel message2;
		String label2 = "Enter Username: ";
		message2 = new JLabel(label2);
		message2.setSize(200, 30);
		message2.setLocation(100,20);
		//input field.
		userIn = new JTextField("");
		userIn.setSize(100,20);
		userIn.setLocation(100,45);
		//password.
		JLabel passmessage;
		passmessage = new JLabel("Enter Password: ");
		passmessage.setSize(200, 30);
		passmessage.setLocation(100, 55);
		//inputfield 2. 
		passIn = new JTextField("");
		passIn.setSize(100, 20);
		passIn.setLocation(100, 80);
		// button
		JButton button;
		button = new JButton("Submit");
		button.setSize(100, 20);
		button.setLocation(100, 110);
		button.addActionListener(new Button1Clicked());
		//back button
		JButton back = new JButton("back");
		back.setSize(100, 20);
		back.setLocation(100, 140);
		back.addActionListener(new BackClicked());
		
		
		
		
		//------menu stuff------//
		JButton login = new JButton("Log in");
		login.setSize(100, 40);
		login.setLocation(100, 80);
		login.addActionListener(new LogInClicked());
		
		JButton signup = new JButton("Sign up");
		signup.setSize(100, 40);
		signup.setLocation(100, 30);
		signup.addActionListener(new SignUpClicked());
		
		//menu window. 
		opt = new JFrame ("Menu");
		opt.setSize(300, 200);
		opt.setLocation(700, 300);
		opt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		opt.setLayout(null);
		opt.add(login);
		opt.add(signup);
		opt.setVisible(true);
		
		//signup window.
		window = new JFrame("Sign Up");
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setLocation(700, 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(null);
		window.add(message2);
		window.add(passmessage);
		window.add(userIn);
		window.add(passIn);
		window.add(button);
		window.add(back);
		window.setVisible(false);

	}
	
	 static private class Button1Clicked implements ActionListener{
		 
		@Override
		public void actionPerformed(ActionEvent e) {

			
			boolean exists = false;
			String user = userIn.getText();
			String pass = passIn.getText();
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testInputs", "root", "@shes20237657");
				Statement st = conn.createStatement();
				
				ResultSet rs = st.executeQuery("SELECT * FROM users");
				while (rs.next()) {
					if (rs.getString(1).equals(user))
						exists = true;
				}
				
				//dont allow user to sign up if user exists already.
				if (window.getTitle().equals("Sign up")) {
					if (!exists && !user.isEmpty() && !pass.isEmpty()) {
						st.executeUpdate("INSERT INTO users VALUES ('" + user + "', " + "'" + pass + "')");
						JOptionPane.showMessageDialog(null, "User added succesfully!", "Success", 1);
					}
					else 
						JOptionPane.showMessageDialog(null, "Username is taken", "Error", 0);
				}
				//allow user to log in if information is valid. 
				else if (!window.getTitle().equals("Sign up")) {
					rs = st.executeQuery("select * from users");
					while(rs.next()) {
						if (rs.getString(1).equals(user) && rs.getString(2).equals(pass)) {
							JOptionPane.showMessageDialog(null, "Login successful");
						}
					}
				}
			} catch(Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error adding user", "Error", 0);
			}
			
			JLabel m = new JLabel(b);
			m.setSize(100,100);
			m.setLocation(150,100);
			
		}
	}
	 
	 static private class BackClicked implements ActionListener{
		 public void actionPerformed(ActionEvent e) {
			 window.setVisible(false);
			 opt.setVisible(true);
		 }
	 }
	 
	 static private class LogInClicked implements ActionListener{
		 public void actionPerformed(ActionEvent e) {
			 loginWindow = window;
			 loginWindow.setTitle("LogIn");
			 window.setVisible(true);
			 opt.setVisible(false);
		 }
	 }
	 static private class SignUpClicked implements ActionListener{
		 public void actionPerformed(ActionEvent e) {
			 window.setTitle("Sign up");
			 window.setVisible(true);
			 opt.setVisible(false);
		 }
	 }
	 
	 
}

