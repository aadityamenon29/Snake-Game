import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//JFrame , window in which game runs	
		
		JFrame window = new JFrame();
		Gameplay gameplay = new Gameplay();
		window.setBounds(10, 10, 905, 700);
		
		window.setBackground(Color.BLACK);
		//to restrict the user from resizing the window
		window.setResizable(false);
		
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.add(gameplay);
		
		

	}

}
