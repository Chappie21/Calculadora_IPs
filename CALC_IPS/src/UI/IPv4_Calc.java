package UI;

import javax.swing.*;
import DB.db;
import java.awt.Color;
import java.awt.event.*;
import java.util.regex.Pattern;
import java.util.ArrayList;
import clas_ip.Claf;
import clas_ip.Cipv4;

public class IPv4_Calc extends JPanel implements ActionListener{
	
	Vprincipal l = Vprincipal.Instancia();
	db basedd = db.Instancia();
	
	//ELEMENTOS
	private JLabel t1,t2,t3,red,tipo,clase,apipa,reservada,multifun,
	Dred,Dgat,Dbrod,rango;
	private JButton aceptar,back,borrar,host;
	private JTextField ip,mascara;
	private JOptionPane mensaje = new JOptionPane();
	
	//ALMACENAR ELEMTOS
	private ArrayList<JLabel> label = new ArrayList<>();
	private ArrayList<JButton> button = new ArrayList<>();
	
	//CONSTRUCTOR VACIO
	public IPv4_Calc() {
		setSize(400,400);
		setBackground(Color.gray);
		setLayout(null);
		Elementos();
		setVisible(true);
	}
	
	//ELEMENTOS
	private void Elementos() {
		//BOTONES
		this.back = new JButton("VOLVER");button.add(back);
		this.aceptar = new JButton("ACEPTAR");button.add(aceptar);
		this.borrar = new JButton("BORRAR");button.add(borrar);
		this.host = new JButton("HOST");;button.add(host);
		
		this.back.setBounds(10,330,100,30);
		this.aceptar.setBounds(165,40,100,30);
		this.borrar.setBounds(275,40,100,30);
		this.host.setBounds(155,330,100,30);
		
		//COLOR
		for(int i=0;i<button.size();i++) {
			button.get(i).setBackground(Color.BLACK);
			button.get(i).setForeground(Color.white);
			add(button.get(i));
		}
		//EVENTOS
		for(int i=0;i<button.size();i++) {
			button.get(i).addActionListener(this);
		}
		
		//CAMPO DE TEXTO
		this.ip = new JTextField();
		this.mascara = new JTextField();
		this.ip.setBounds(35,40,120,30);
		this.mascara.setBounds(35,75,120,30);
		
		TecladoEvento(ip);TecladoEvento(mascara);
		
		add(ip);add(mascara);
		
		//ETIQUETAS
		this.t1 = new JLabel("INFORMACION SOBRE IP");label.add(t1);
		this.t2 = new JLabel("IP:");label.add(t2);
		this.t3 = new JLabel("M:");label.add(t3);
		this.red = new JLabel("RED:  ?");label.add(red);
		this.tipo = new JLabel("TIPO:  ?");label.add(tipo);
		this.clase = new JLabel("CLASE:  ?");label.add(clase);
		this.apipa = new JLabel("APIPA:  ?");label.add(apipa);
		this.reservada = new JLabel("RESERVADA:  ?");label.add(reservada);
		this.multifun = new JLabel("DIFUSION:  ?");label.add(multifun);
		this.Dred = new JLabel("DIR. RED:  ?");label.add(Dred);
		this.Dgat = new JLabel("DIR. GATEWAY:  ?");label.add(Dgat);
		this.Dbrod = new JLabel("DIR. BROADCAST:  ?");label.add(Dbrod);
		this.rango = new JLabel("RANGO:  ?");label.add(rango);
		
		this.t1.setBounds(10,5,170,30);
		this.t2.setBounds(10,40,50,30);
		this.t3.setBounds(10,75,50,30);
		
		int j=20;
		for(int i=4;i<label.size();i++) {
			label.get(i).setBounds(10,110+j,340,30);
			j+=20;
		}
		
		//COLOR
		for(int i=0;i<label.size();i++) {
			label.get(i).setForeground(Color.black);
			add(label.get(i));
		}
		//EVENTO
		for(int i=3;i<label.size();i++) {
			EventoMouse(label.get(i));
		}
		//INVISIBLE
		Desap(false);
	}
	
	//TRNASFORMAR A ENTEROS
	private int[] Transformar(String u[]) {
		int datos[]= new int [4];
		for(int i=0;i<4;i++) {
			datos[i]=Integer.parseInt(u[i]);
		}
		return datos;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==back) {
			l.Volver();
		}
		if(e.getSource()==aceptar) {
			if(!ip.getText().isEmpty()&&!mascara.getText().isEmpty()) {
				String datosI[] = ip.getText().split(Pattern.quote("."));
				String datosM[] = mascara.getText().split(Pattern.quote("."));
				int dat[]=Transformar(datosI);
				int datM[]=Transformar(datosM);
				if(dat[0]<=255&&dat[1]<=255&&dat[2]<=255&&dat[3]<=255&&datM[0]<=255&&datM[1]<=255&&datM[2]<=255&&datM[3]<=255) {
					Cipv4 claf = new Cipv4(dat,datM);
					Mostrar(claf); Desap(true);
					basedd.Agregar_Ipv4(claf);
				}
				else {
					mensaje.showMessageDialog(null,"OCTETO MAYOR A 255");
				}
			}
			else {
				mensaje.showMessageDialog(null,"CAMPO VACIO");
			}
		}
		if(e.getSource()==host) {
			int n= Integer.parseInt(mensaje.showInputDialog("numero de host's:"));
			mensaje.showMessageDialog(null,"IP: "+Claf.Ip4_recomend(n));
		}
		if(e.getSource()==borrar) {
			this.ip.setText(null);
			this.mascara.setText(null);
			for(int i=3;i<label.size();i++) {
				label.get(i).setText(null);
			}
			Desap(false);
		}
	}
	
	//MOSTRAR DATOS DE LA IP DADA
	private void Mostrar(Cipv4 o) {
		this.red.setText("RED:  "+o.getIp());
		this.tipo.setText("TIPO: "+o.getTipo());
		this.clase.setText("CLASE: "+o.getClase());
		this.apipa.setText("APIPA: "+o.getApipa());
		this.reservada.setText("RESERVADA: "+o.getReservada());
		this.multifun.setText("DIFUSION: "+o.getDifusion());
		this.Dred.setText("DIR. RED: "+o.getDred());
		this.Dgat.setText("DIR. GATEWAY: "+o.getDgateway());
		this.Dbrod.setText("DIR. BROADCAST: "+o.getDbroadcast());
		this.rango.setText("RANGO: "+o.getRango());
	}
	
	/*MOSTRAR O NO LOS JLABEL DE INFOMACION DE LA IP*/
	private void Desap(boolean l) {
		for(int i=3;i<label.size();i++) {
			label.get(i).setVisible(l);
		}
	}
	
	//EVENTO DE MOUSE
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
					mensaje.showMessageDialog(null,"INGRESE SOLO CARACTERES");
				}
			}
		});
	}
}
