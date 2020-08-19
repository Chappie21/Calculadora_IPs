package clas_ip;

public class Cipv6 {
	//ELEMENTOS
	private String ip,ipcom,difusion,reservada;
	//CONSTRUCTOR
	public Cipv6(int ipD[],String ip,String ipcom) {
		this.ip = ip;
		this.ipcom = ipcom;
		this.difusion = Difusion(ipD);
		this.reservada = reservada(ipD);
	}
	private String Difusion(int ip[]) {
		if(ip[0]>=8192&&ip[0]<=16383) {
			return "UNICAST GLOBAL";
		}else if(ip[0]>=65152&&ip[0]<=65215) {
			return "UNICAST LINK-LOCAL";
		}else if(ip[0]==0&&ip[1]==0&&ip[2]==0&&ip[3]==0&&ip[4]==0&&ip[5]==0&&ip[6]==0&&ip[7]==1) {
			return "UNICAST LOOPACK";
		}else if(ip[0]==0&&ip[1]==0&&ip[2]==0&&ip[3]==0&&ip[4]==0&&ip[5]==0&&ip[6]==0&&ip[7]==0) {
			return "UNICAST UNSPECIFIEF ADDRESS";
		}else if(ip[0]>=64512&&ip[0]<=65023) {
			return "UNICAST UNIQUE LOCAL";
		}else if(ip[0]>=65280&&ip[0]<=65535) {
			return "MULTICAST";
		}
		return "-";
	}
	private String reservada(int ip[]) {
		if(ip[0]==256&&ip[1]==0&&ip[2]==0&&ip[3]==0) {
			return "PREFIJO";
		}else if(ip[0]==8193&&ip[1]==0&&ip[2]==0&&ip[3]==0) {
			return "TUNEL TEREDO";
		}else if(ip[0]==8193&&ip[1]>=32&&ip[1]<=47) {
			return "ORCHIDV2";
		}else if(ip[0]==8193&&ip[1]==3512) {
			return "DOCUMENTACION Y CODIGO FUENTE";
		}else if(ip[0]==8193) {
			return "DIRECCIONAMIENTO 6to4";
		}
		return "RUTA POR DEFECTO";
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDifusion() {
		return difusion;
	}
	public void setDifusion(String difusion) {
		this.difusion = difusion;
	}
	public String getReservada() {
		return reservada;
	}
	public void setReservada(String reservada) {
		this.reservada = reservada;
	}
	public String getIpcom() {
		return ipcom;
	}
	public void setIpcom(String ipcom) {
		this.ipcom = ipcom;
	}
}
