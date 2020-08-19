package UI;

import javax.swing.*;

import DB.db;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;

public class Vprincipal extends JFrame implements ActionListener{
	//singleton
	private static Vprincipal principal = new Vprincipal();
	//CONEXION CON DB
	db basedd = db.Instancia();
	//ELEMENTOS DE LA VENTANA
	private JPanel menu = new JPanel();
	private JButton b1,b2,b3;
	private JLabel text;
	
	//Lista de botones
	ArrayList<JButton> botones = new ArrayList<JButton>(); 
	//CONSTRUCTOR VACIO
	private Vprincipal() {
		setSize(200,200);
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);/*CERRA PROCESO*/
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);/*LIBERAR RECURSOS DEL PROCESO*/
		Elementos();
		setContentPane(menu);
		setVisible(true);
		addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				basedd.Cerrar();
				dispose();
				System.exit(0);
			}
			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	//ELEMENTOS
	private void Elementos() {
		menu.setLayout(null);
		menu.setBackground(Color.gray);
		//TITULO
		this.text = new JLabel("CALCULAR TU IP");
		this.text.setBounds(45,10,130,30);
		this.text.setForeground(Color.white);
		menu.add(text);
		
		//BOTONES
		this.b1 = new JButton("IPv4");botones.add(b1);
		this.b2 = new JButton("IPv6");botones.add(b2);
		this.b3 = new JButton("REGIS");botones.add(b3);
		
		int j=40;
		for(int i=0;i<botones.size();i++) {
			botones.get(i).setBounds(50,j,100,40);
			botones.get(i).setBackground(Color.black);
			botones.get(i).setForeground(Color.WHITE);
			botones.get(i).addActionListener(this);
			menu.add(botones.get(i));
			j+=40;
		}
	}
	
	//EVENTOS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==b1) {
			IPv4_Calc q = new IPv4_Calc();
			setSize(400,400);
			setContentPane(q);
		}
		if(e.getSource()==b2) {
			IPv6_Calc q = new IPv6_Calc();
			setSize(400,330);
			setContentPane(q);
		}
		if(e.getSource()==b3) {
			Registro q = new Registro();
			setBounds(50,200,1200,500);/*POSICION DE LA VENTANA*/
			setContentPane(q);
		}
	}
	
	//INSTANCIA
	public static Vprincipal Instancia() {
		return principal;
	}
	
	//VOLVER
	public void Volver() {
		setBounds(500,400,200,200);
		setContentPane(menu);
	}
}
