package clas_ip;

public class Cipv4 {
	//ELEMENTOS
	private String ip,mascara,tipo,clase,apipa,reservada,difusion,dred,
	dgateway,dbroadcast,rango;
	
	//ALMACENAR TEMPORALMENTE
	private String alm[] = new String[2];
	private String alm2[] = new String[4];
	
	//CONSTRUCTOR
	public Cipv4(int ip[],int masc[]) {
		//SE ASIGNA CADA UNO DE LOS DATOS DE LA IP
		this.ip = convertir(ip);
		this.mascara = convertir(masc);
		alm = Tipo(ip);
		this.tipo = alm[0];
		this.apipa = alm[1];
		this.clase = Clase(ip);
		this.reservada = Reservada(ip);
		alm2 = Dred_getway(ip,masc);
		this.dred = alm2[0];
		this.dgateway = alm2[1];
		this.dbroadcast = alm2[2];
		this.rango = alm2[3];
		this.difusion = Multifuncion(ip);
	}
	
	/*COMPROBAR SI ES PUBLICA O PRIVADA
	 * TAMBIEN DETERMIAN SI PERTENECE AL PROTOCOLO APIPA*/
	public String[] Tipo(int u[]) {
		if(u[0]==10||((u[0]==172)&&(u[1]>=16&&u[1]<=31))||((u[0]==192)&&(u[1]==168))) {
			String n[]= {"PRIVADA","NO PERTENECE"};
			return n;
		}else if((u[0]==169)&&(u[1]==254)&&(u[2]>=0)&&(u[2]<=255)&&(u[3]>=1)&&(u[2]<=254)) {
			String n[]= {"PRIVADA","PERTENECE"};
			return n;
		}else if(u[0]>=0&&u[0]<=255) {
			String n[]= {"PUBLICA","NO PERTENECE"};
			return n;
		}
		return null;/*EN CASO SUPUESTO DE QUE LA IP SEA INCORRECTA*/
	}
	
	/*DETERMINA A QUE CLASE PERTENECE*/
	private String Clase(int u[]) {
		if(u[0]>=0&&u[0]<=127) {
			return "A";
		}else if(u[0]>=128&&u[0]<=191) {
			return "B";
		}else if(u[0]>=192&&u[0]<=223) {
			return "C";
		}else if(u[0]>=224&&u[0]<=239) {
			return "D";
		}else if(u[0]>=240&&u[0]<=255) {
			return "E";
		}
		return "-";/*EN EL CASO DE QUE NO PERTENEZCA A ALGUNA CLASE*/
	}
	
	/*DETERMINA SI LA IP ES RESERVADA*/
	private String Reservada(int u[]) {
		if(u[0]==0) {
			return "RED ACUTAL";
		}else if(u[0]==10) {
			return "RED PRIVADA";
		}else if(u[0]==100&&u[1]>=64&&u[1]<=127) {
			return "COMUNICACION PROVEEDOR Y SUBSCRIPTORES";
		}else if(u[0]==127) {
			return "DIR LOOPBACK";
		}else if(u[0]==169&&u[1]==254) {
			return "DIR ENLACE LOCAL";
		}else if(u[0]==172&&u[1]>=16&&u[1]<=31) {
			return "COMUNICAION EN UNA RED LOCAL";
		}else if(u[0]==192&&u[1]==0&&u[2]==0) {
			return "PROTOCOLO IETF";
		}else if(u[0]==192&&u[1]==0&&u[2]==2) {
			return "TEST-NET-1 DOCUMENTACION";
		}else if(u[0]==192&&u[1]==88&&u[2]==99) {
			return "RELAY IPV6 A IPV4";
		}else if(u[0]==192&&u[1]==168) {
			return "COMUNICACIONES LOCALES";
		}else if(u[0]==198&&(u[1]==18||u[1]==19)) {
			return "PRUEBA ENTRE SUBREDES SEPARADAS";
		}else if(u[0]==198&&u[1]==51&&u[2]==100) {
			return "TEST-NET-2";
		}else if(u[0]==203&&u[1]==0&&u[2]==113) {
			return "TEST-NET-3";
		}else if(u[0]==224||u[0]==239) {
			return "USADO PARA MULTICAST";
		}else if((u[0]>=240&&u[0]<=255)&&u[3]<=254) {
			return "USOS FUTUROS";
		}else if(u[0]==255&&u[1]==255&&u[2]==255&&u[3]==255) {
			return "DESTINOS DE MULTIDIFUSION";
		}
		return "-";/*EN CASO DE NO SER RESERVADA*/
	}
	
	/*DETERMINAR SU MULTIFUNCION*/
	public String Multifuncion(int u[]) {
		int broad[]=Transformar(getDbroadcast().split("[.]"));
		if((u[0]==169)&&(u[1]==254)&&(u[2]>=0)&&(u[2]<=255)&&(u[3]>=1)&&(u[2]<=254)) {
			return "UNICAST";
		}else if(u[0]>=224&&u[0]<=239) {
			return "MULTICAST";
		}else if(u[0]==broad[0]&&u[1]==broad[1]&&u[2]==broad[2]&&u[3]==broad[3]) {
			return "BROADCAST";/*SI LA IP COINCIDE CON LA DIR. BROADCAS ES BROADCAST*/
		}
		return "-";/*EN CASO DE NO POSEER FUNCION*/
	}
	
	/*DETERMINAR DIRECCION DE RED Y GETWAY*/
	public String[] Dred_getway(int u[],int o[]) {
		String datos[] = new String[4];
		//OBTENER LA DIRECCION DE RED
		int dire[] = {u[0]&o[0],u[1]&o[1],u[2]&o[2],u[3]&o[3]};
		datos[0]=convertir(dire);
		//OBTENER DIRECCION DEL GATWAY
		dire[3]=dire[3]+1;
		datos[1]=convertir(dire);
		//OBTENER LA DIRECCION DEL BROADCAST
		for(int i=0;i<4;i++) {
			o[i]=255-o[i];
		}
		int Bdire[] = {u[0]|o[0],u[1]|o[1],u[2]|o[2],u[3]|o[3]};
		datos[2]=convertir(Bdire);
		//RANGO
		Bdire[3]=Bdire[3]-1;
		datos[3]=datos[1]+" - "+convertir(Bdire);
		return datos;
	}
	
	/*CONVERTIR A STRING UNA DIRECCION*/
	private String convertir(int u[]) {
		String n="";
		n+=u[0];
		for(int i=1;i<4;i++) {
			n+="."+u[i];
		}
		return n;
	}
	
	//TRNASFORMAR A ENTEROS
	private int[] Transformar(String u[]) {
		int datos[]= new int [4];
		for(int i=0;i<4;i++) {
			datos[i]=Integer.parseInt(u[i]);
		}
		return datos;
	}
		
	
	//GETTERS AND SETTERS
	public String getIp() {
		return ip;
	}
	public String getMascara() {
		return mascara;
	}
	public String getTipo() {
		return tipo;
	}
	public String getClase() {
		return clase;
	}
	public String getApipa() {
		return apipa;
	}
	public String getReservada() {
		return reservada;
	}
	public String getDifusion() {
		return difusion;
	}
	public String getDred() {
		return dred;
	}
	public String getDgateway() {
		return dgateway;
	}
	public String getDbroadcast() {
		return dbroadcast;
	}
	public String getRango() {
		return rango;
	}
}
