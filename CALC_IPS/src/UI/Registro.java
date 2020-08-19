package UI;

import javax.swing.*;
import DB.db;
import java.awt.Color;
import java.awt.event.*;

public class Registro extends JPanel implements ActionListener{
	Vprincipal l = Vprincipal.Instancia();
	db basedd = db.Instancia();
	//ELEMENTOS
	private JButton back;
	private JTable tabla1,tabla2;
	private JScrollPane scroll1,scroll2;
	private JRadioButton b1,b2;
	private ButtonGroup grupo = new ButtonGroup();
	public Registro() {
		setSize(1200,500);
		setLayout(null);
		setBackground(Color.gray);
		Elmentos();
		setVisible(true);
	}
	
	private void Elmentos() {
		//BOTONES
		this.back = new JButton("VOLVER");
		this.back.setBackground(Color.black);
		this.back.setForeground(Color.white);
		this.back.setBounds(10,430,100,30);
		this.back.addActionListener(this);
		add(back);
		//RADIO BOTONES
		this.b1 = new JRadioButton("Ipv4");
		this.b2 = new JRadioButton("Ipv6");
		
		this.b1.setBounds(120,430,70,30);
		this.b2.setBounds(190,430,70,30);
		this.b1.setBackground(Color.gray);
		this.b2.setBackground(Color.gray);
		this.b1.setForeground(Color.black);
		this.b2.setForeground(Color.black);
		this.b1.setSelected(true);
		
		this.b1.addActionListener(this);
		this.b2.addActionListener(this);
		
		add(b1);add(b2);
		grupo.add(b1);grupo.add(b2);
		//TABLA
		Tabla_Ipv4();
		Tabla_Ipv6();
		//SCROLL
		this.scroll1 = new JScrollPane(tabla1);
		this.scroll2 = new JScrollPane(tabla2);
		this.scroll1.setBounds(10,40,1170,300);
		this.scroll2.setBounds(10,40,770,300);
		this.scroll2.setVisible(false);
		this.scroll1.setVisible(true);
		add(scroll1);add(scroll2);
	}
	private void Tabla_Ipv4() {
		String colm[] = {"Num OP","IP","MASCARA","TIPO","CLASE","APIPA","RESERVADA",
				"DIFUSION","DIR. RED","DIR. GATEWAY","DIR. BROADCAST","RANGO"};
		String filad[][] =  basedd.Regis_Ipv4();
		this.tabla1 = new JTable(filad,colm);
		this.tabla1.setEnabled(false);
	}
	private void Tabla_Ipv6() {
		String colm[] = {"Num OP","IP","IPCOM","DIFUSION","RESERVADA"};
		String filad[][] =  basedd.Regis_Ipv6();
		this.tabla2 = new JTable(filad,colm);
		this.tabla2.setEnabled(false);
	}
	//EVENTOS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==back) {
			l.Volver();
		}
		if(e.getSource()==b1) {
			l.setSize(1200,500);
			setSize(1200,500);
			this.scroll2.setVisible(false);
			this.scroll1.setVisible(true);
		}
		if(e.getSource()==b2) {
			l.setSize(800,500);
			setSize(800,500);
			this.scroll1.setVisible(false);
			this.scroll2.setVisible(true);
		}
	}
}
