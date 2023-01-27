import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginForm extends JFrame implements ActionListener{

	private JTextField txtid;
	private Main_View mv;

	public LoginForm() {
		Font mB = new Font("휴먼매직체", Font.BOLD, 30);
		Font mR = new Font("한컴 고딕", Font.BOLD, 20);
		// TODO Auto-generated constructor stub
		setSize(400, 200);
		setTitle("로그인");
		setLayout(null);
//		setUndecorated(true);
		setLocationRelativeTo(null);
		setBackground(new Color(255,255,255));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		JLabel lbltitle = new JLabel("인하경매장");
		JLabel lblid = new JLabel("아이디");
		txtid = new JTextField();
		JButton btnLogin = new JButton("입장하기");
		
		
		add(lbltitle);
		add(lblid);
		add(txtid);
		add(btnLogin);
		
		lbltitle.setFont(mB);		
		lblid.setFont(mR);
		btnLogin.setFont(mR);
		lbltitle.setBounds(120,5,200,50);
		lblid.setBounds(50,50,70,30);
		txtid.setBounds(150,50,200,30);
		btnLogin.setBounds(50, 100, 300,40);
		
		btnLogin.setBackground(new Color(28,79,116));
		btnLogin.setForeground(Color.white);
		lblid.setForeground(new Color(28,79,116));
		lbltitle.setForeground(new Color(28,79,116));
		setVisible(true);
		
		btnLogin.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(mv == null) {
			mv = new Main_View(txtid.getText());
			this.dispose();
//			MultiClient ms = new MultiClient(mv);
//			Thread th = new Thread(ms);
//			th.start();
		}else {
			JOptionPane.showMessageDialog(this, "이미 로그인 중입니다!.", "계정 오류!", JOptionPane.ERROR_MESSAGE);				
		}
	}
	
}

