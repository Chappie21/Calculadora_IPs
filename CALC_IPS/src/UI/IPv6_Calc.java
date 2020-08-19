package UI;

import javax.swing.*;

import DB.db;

import java.awt.Color;
import java.awt.event.*;
import java.util.ArrayList;
import clas_ip.Cipv6;

public class IPv6_Calc extends JPanel implements ActionListener{
	
	Vprincipal l = Vprincipal.Instancia();
	db basedd = db.Instancia();
	
	//ELEMENTOS
	private JLabel t1,t2,t3,Nota,ipn,ipv6,adorno,difusion,reservada;
	private JButton aceptar,back,borrar;
	private JTextField ip,mascara;
	private JOptionPane mensaje = new JOptionPane();
	
	//LISTAS DE ELEMENTOS
	ArrayList<JLabel> label = new ArrayList<JLabel>();
	ArrayList<JButton> boton = new ArrayList<JButton>();
	
	//CONSTRUCTOR VACIO
	public IPv6_Calc(){
		setSize(400,330);
		setBackground(Color.gray);
		setLayout(null);
		l.setLocationRelativeTo(null);
		Elementos();
		setVisible(true);
	}
	
	//CREACION DE ELEMENTOS
	private void Elementos() {
		
		//CAMPOS DE TEXTO
		this.ip = new JTextField();
		this.mascara = new JTextField();
		this.ip.setBounds(35,40,150,30);
		this.mascara.setBounds(210,40,30,30);
		
		TecladoEvento(mascara);
		
		add(ip);add(mascara);
		//ETIQUETAS
		this.t1 = new JLabel("INFORMACION SOBRE IPv6");label.add(t1);
		this.t2 = new JLabel("IP:");label.add(t2);
		this.t3 = new JLabel("/");label.add(t3);
		this.adorno = new JLabel("------------------------------ IPv6 INFO ------------------------------");
		label.add(adorno);
		this.ipn = new JLabel();label.add(ipn);
		this.ipv6 = new JLabel();label.add(ipv6);
		this.difusion = new JLabel();label.add(difusion);
		this.reservada = new JLabel();label.add(reservada);
		this.Nota = new JLabel("INGRESE EN NOTACION CIDR  :)!!!");
		
		this.t1.setBounds(10,5,180,30);
		this.t2.setBounds(10,40,50,30);
		this.t3.setBounds(195,40,20,30);
		this.Nota.setBounds(10,74,230,30);
		this.adorno.setBounds(10,130,400,30);
		
		this.Nota.setForeground(Color.white);
		add(Nota);
		
		for(int i=0;i<label.size();i++) {
			label.get(i).setForeground(Color.BLACK);
			add(label.get(i));
		}
		
		int j=120;
		for(int i=3;i<label.size();i++) {
			label.get(i).setBounds(10,j,400,30);
			label.get(i).setVisible(false);
			EventoMouse(label.get(i));
			j+=20;
		}
	
		//BOTONES
		this.back = new JButton("VOLVER");boton.add(back);
		this.aceptar = new JButton("ACEPTAR");boton.add(aceptar);
		this.borrar = new JButton("BORRAR");boton.add(borrar);
		
		this.back.setBounds(10,260,100,30);
		this.aceptar.setBounds(260,40,100,30);
		this.borrar.setBounds(260,73,100,30);
		
		for(int i=0;i<boton.size();i++) {
			boton.get(i).setBackground(Color.black);
			boton.get(i).setForeground(Color.white);
			boton.get(i).addActionListener(this);
			add(boton.get(i));
		}
	}
	
	//EVENTOS
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==back) {
			l.Volver();
		}
		if(e.getSource()==aceptar) {
			if(!ip.getText().isEmpty()&&!mascara.getText().isEmpty()) {
				String datos[] = ip.getText().split(":");
				if(Obtener_ipv6(datos)!=null) {
					String ipv6 = Obtener_ipv6(datos);
					String IPn[] = ipv6.split(":");
					int IPde[] = new int[8];
					boolean verificar=true;
					for(int i=0;i<8;i++) {
						IPde[i]=Integer.parseInt(IPn[i], 16);
						if(IPde[i]>65535) {
							verificar=false;
							i=8;
						}
					}
					if(verificar) {
						Cipv6 claf = new Cipv6(IPde,ip.getText()+"/"+mascara.getText(),ipv6);
						Mostrar(claf);basedd.Agregar_Ipv6(claf);
					}
					else {
						mensaje.showMessageDialog(null,"ERROR: BLOQUE CON MÁS 16 BITS");
					}
				}
				else {
					mensaje.showMessageDialog(null,"ERROR: FALLO DE ESCRITURA DE IPV6");
				}
			}
			else {
				mensaje.showMessageDialog(null,"ERROR: CAMPO(S) DE TEXTO VACIO(S)");
			}
		}
		if(e.getSource()==borrar) {
			Borrar();
		}
	}
	
	private String Obtener_ipv6(String datos[]) {
		StringBuilder IP = new StringBuilder();
	
		int a=0,b=0;
		for(int i=0;i<datos.length;i++) {
			if(!datos[i].equals("")) {
				a++;
			}
			else {
				b++;
			}
		}
		/*EN CASO DE ENCONTRAR UN ESPACIO "" INICIA EL AUTO COMPLETADO*/
		if(b!=0) {
			int flag=0;
			for(int i=0;i<datos.length;i++) {
				if(datos[i].equals("")) {
					if(flag==0) {
						int o=8-a;
						int l=0;
						while(l<o) {
							IP.append("0000:");
							l++;
						}
						flag++;/*EN CASO DE AUTO COMPLETAR, CONTADOR SERÁ =1
									* Y NO SE REPETIRÁ ESTE PROCESO*/
					}
				}
				else {
					/*EN CASO DE QUE NO ENCUENTRE ESPACIO ""
					 * TOMARA LA DATA CORRESPONDIENTE A LA AÑADIRÁ 
					 * AL NUEVO STRING, EN CASO DE SER UN "0" COMPLETARÁ
					 * EL BLOQUE CON LOS 0's FALTANTES*/
					if(datos[i].contentEquals("0")) {
						IP.append("0000");
					}
					else {
						IP.append(datos[i]);
					}
					/*AÑADE LA SEPARACION POR BLOQUES SIEMPRE Y CUANDO
					 * NO SEA EL ULTIMO DATO*/
					if(i!=(datos.length-1)) {
						IP.append(":");
					}
				}
			}
		}
		/*EM EL CASO DE NO POSSER ESPACIOS ""
		 * SE CUENTA LOS HEXTETOS FALTANTES Y SE COMPLETA CON 
		 * TANTOS 0's SEA NECESARIO*/
		else {
			int o=8-a;
			for(int i=0;i<datos.length;i++) {
				if(datos[i].equals("0")) {
					IP.append("0000:");
				}
				else {
					IP.append(datos[i]+":");
				}
			}
			for(int l=0;l<o;l++) {
				IP.append("0000");
				if(l!=o-1) {
					IP.append(":");
				}
			}
		}
		return IP.toString();/*SE RETORNA LA IP COMPLETA*/
	}
	
	//BORRAR
	private void Borrar() {
		ip.setText(null); 
		mascara.setText(null);
		adorno.setVisible(false);
		for(int i=4;i<label.size();i++) {
			label.get(i).setText(null);
			label.get(i).setVisible(false);
		}
	}
	
	//MOSTRAR INFO
	private void Mostrar(Cipv6 datos) {
		
		this.ipn.setText("IP: "+datos.getIp());
		this.ipv6.setText("IP COMP: "+datos.getIpcom()+"/"+mascara.getText());
		this.difusion.setText("DIFUSION: "+datos.getDifusion());
		this.reservada.setText("RESERVADA: "+datos.getReservada());
		
		for(int i=3;i<label.size();i++) {
			label.get(i).setVisible(true);
		}
	}
	
	//EVENTO
	private void EventoMouse(JLabel text) {
		text.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				text.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				text.setForeground(Color.black);
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}); 
	}
	
	//EVENTO DE TECLADO
		private void TecladoEvento(JTextField tex) {
			tex.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent ev) {
					char dato=ev.getKeyChar();
					if(Character.isLetter(dato)) {
						getToolkit().beep();
						ev.consume();
						mensaje.showMessageDialog(null,"INGRESE SOLO NUMEROS");
					}
				}
			});
		}
}
