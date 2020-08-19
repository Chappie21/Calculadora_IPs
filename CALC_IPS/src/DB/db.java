package DB;

import java.sql.*;
import clas_ip.Cipv4;
import clas_ip.Cipv6;

public class db {
	//PATRON SINGLETON
	private static db datab = new db();
	
	private Connection conexion;
	private Statement st;
	private PreparedStatement pst;
	private ResultSet rs;
	
	//CONTRUCTOR VACIO, REALIZA LA CONEXION CON LA DB
	private db() {
		try {
			this.conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5433/Direcciones_IP","postgres","chappie2k18");
			System.out.println("CONEXION ESTABLECIDA");
		}catch(SQLException l) {
			System.out.println("CONEXION:"+l.getMessage());
		}
	}
	//OBTNER INSTANCIA
	public static db Instancia() {
		return datab;
	}
	//CERRA CONEXION
	public void Cerrar() {
		try {
			this.conexion.close();
		}catch(SQLException l) {
			System.out.println("CERRAR CONEXION:"+l.getMessage());
		}
	}
	//RETORNAR ID AUTOASIGNABLE
	private int Id_auto(String qery) {
		int id=1;/*EN CASO DE NO TENER OPERACIONES, ASIGNA EL PRIMER ID COMO 1*/
		try {
			/*REALIZA UNA CONSULTA SQL RETORNANDO EL MAYOR ID DE LA DB
			 * Y ALMACENDANDO EL OBJETO RESULSET EN "rs*/
			this.st = this.conexion.createStatement();
			this.rs = this.st.executeQuery(qery);
			while(rs.next()) {
				id=rs.getInt(1)+1;/*TOMA EL MAYOR ID Y EL SUMA UNO*/
			}
		}catch(Exception e) {
				
		}finally {
			try {
				//CIERRE DE OBJETOS
				this.st.close();
				this.rs.close();
			}catch(Exception l) {
			}
		}
		return id;/*RETORNA EL ID RESPECTIVO PARA LA NUEVA OPERACION*/
	}
	//AÑADIR AL REGISTRO DE IPV4
	public void Agregar_Ipv4(Cipv4 info) {
		try {
			this.pst = this.conexion.prepareStatement("INSERT INTO Ipv4 VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
			this.pst.setInt(1,Id_auto("SELECT MAX(id_op) FROM Ipv4"));
			this.pst.setString(2,info.getIp());
			this.pst.setString(3,info.getMascara());
			this.pst.setString(4,info.getTipo());
			this.pst.setString(5,info.getClase());
			this.pst.setString(6,info.getApipa());
			this.pst.setString(7,info.getDifusion());
			this.pst.setString(8,info.getReservada());
			this.pst.setString(9,info.getDred());
			this.pst.setString(10,info.getDgateway());
			this.pst.setString(11,info.getDbroadcast());
			this.pst.setString(12,info.getRango());
			this.pst.execute();/*EJECUTA EL QUERY Y ALAMCENA EN LA DB*/
		}catch(SQLException l) {
			System.out.println(" ERROR ALMACENAR IPV4:"+l.getMessage());
		}finally {
			try {
				this.pst.close();
			}catch(SQLException l){
				
			}
		}
	}
	//AGREGAR AL REGISTRO DE IPV6
	public void Agregar_Ipv6(Cipv6 info) {
		try {
			this.pst = this.conexion.prepareStatement("INSERT INTO Ipv6 VALUES (?,?,?,?,?)");
			this.pst.setInt(1,Id_auto("SELECT MAX(id_op) FROM Ipv6"));
			this.pst.setString(2,info.getIp());
			this.pst.setString(3,info.getIpcom());
			this.pst.setString(4,info.getDifusion());
			this.pst.setString(5,info.getReservada());
			this.pst.execute();/*EJECUTA EL QUERY Y ALAMCENA EN LA DB*/
		}catch(SQLException l) {
			System.out.println("ERROR ALMACENAR IPV6:"+l.getMessage());
		}finally {
			try {
				this.pst.close();
			}catch(SQLException l){
				
			}
		}
	}
	//MOSTRAR REGISTRO DE IPV4
	public String[][] Regis_Ipv4() {
		String[][] n = new String[Id_auto("SELECT MAX(id_op) FROM Ipv4")-1][12];
		try {
			this.st = this.conexion.createStatement();
			this.rs = this.st.executeQuery("SELECT*FROM Ipv4");
			int i=0;
			/*OBTIENE TODA LA INFO EN LA ABSE DE DATOS Y LO ALAMCENA EN UN
			 * ARRAY DE 12 COLUMNAS Y N FILAS SEGUN LA DB*/
			while(rs.next()) {
				n[i][0]=rs.getString(1);
				n[i][1]=rs.getString(2);
				n[i][2]=rs.getString(3);
				n[i][3]=rs.getString(4);
				n[i][4]=rs.getString(5);
				n[i][5]=rs.getString(6);
				n[i][6]=rs.getString(7);
				n[i][7]=rs.getString(8);
				n[i][8]=rs.getString(9);
				n[i][9]=rs.getString(10);
				n[i][10]=rs.getString(11);
				n[i][11]=rs.getString(12);
				i++;
			}
			return n;
		}catch(SQLException l) {
			System.out.println(" ERROR CONSULTA IPV4:"+l.getMessage());
		}finally {
			try {
				this.st.close();
				this.rs.close();
			}catch(SQLException l){
				
			}
		}
		return null;
	}
	//MOSTRAR REGISTRO DE IPV6
		public String[][] Regis_Ipv6() {
			String[][] n = new String[Id_auto("SELECT MAX(id_op) FROM Ipv6")-1][5];
			try {
				this.st = this.conexion.createStatement();
				this.rs = this.st.executeQuery("SELECT*FROM Ipv6");
				int i=0;
				/*OBTIENE TODA LA INFO EN LA ABSE DE DATOS Y LO ALAMCENA EN UN
				 * ARRAY DE 12 COLUMNAS Y N FILAS SEGUN LA DB*/
				while(rs.next()) {
					n[i][0]=rs.getString(1);
					n[i][1]=rs.getString(2);
					n[i][2]=rs.getString(3);
					n[i][3]=rs.getString(4);
					n[i][4]=rs.getString(5);
					i++;
				}
				return n;
			}catch(SQLException l) {
				System.out.println(" ERROR CONSULTA IPV6:"+l.getMessage());
			}finally {
				try {
					this.st.close();
					this.rs.close();
				}catch(SQLException l){
					
				}
			}
			return null;
		}
}