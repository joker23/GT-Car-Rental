package Data;
import java.util.*;
public class Format {

//	public static void main(String[] args){
//		Date dt = new Date();
//		System.out.println(parseDateTime(dt));
//	}
	/**
	 * format to YYYY-MM-DD HH:MM:SS
	 * @param dt
	 * @return
	 */
	public static String parseDateTime(Date dt){
		String ret="";
		ret+=dt.getYear()+1900+ "-";
		ret+=dt.getMonth()<10?"0"+(1+dt.getMonth()):(1+dt.getMonth());
		ret+="-"+(dt.getDate()<10?"0"+(dt.getDate()):dt.getDate())+" ";
		ret+=dt.getHours()+":"+dt.getMinutes()+":"+dt.getSeconds();
		return ret;
	}
}
