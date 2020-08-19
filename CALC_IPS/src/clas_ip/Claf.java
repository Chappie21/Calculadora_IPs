package clas_ip;

public class Claf {
	
	/*CALCULAR LA MASCARA SEGÚN UN HOSTO DADO, Y DAR UNA
	 * IP RECOMENDADA ADECUADA*/
	public static String Ip4_recomend(int host) {
		int n=(int) (32-(Math.log(host)/(Math.log(2))));/*MASCARA*/
		String ip="";
		if(n>=8&&n<=12) {
			ip+="10.0.0.0/"+n;
		}else if(n>=13&&n<=16) {
			ip+="172.16.0.0/"+n;
		}else if(n>=16&&n<=30) {
			ip+="192.168.0.0/"+n;
		}
		return ip;
	}
	
}
