package Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class Util {

	public static String[] generateTime(){
		String[] ret = {"00:00:00.0","01:00:00.0","02:00:00.0","03:00:00.0","04:00:00.0","05:00:00.0",
				"06:00:00.0","07:00:00.0","08:00:00.0","09:00:00.0","10:00:00.0","11:00:00.0",
				"12:00:00.0","13:00:00.0","14:00:00.0","15:00:00.0","16:00:00.0","17:00:00.0",
				"18:00:00.0","19:00:00.0","20:00:00.0","21:00:00.0","22:00:00.0","23:00:00.0"};
		return ret;
	}

	public static List<String> generateFutureDate(Date curr){
		List<String> dates = new ArrayList<String>();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(curr);
		for(int i=0;i<10;i++){
			Date result = calendar.getTime();
			dates.add(Format.parseDateTime(result).substring(0,10));
			calendar.add(Calendar.DATE, 1);
		} return dates;
	}
	public static Date getDate(String date){
		String year = date.substring(0,4);
		String month = date.substring(5,7);
		String day = date.substring(8,10);
		return new Date(Date.parse(""+year+"/"+month+"/"+day));
	}

	public static String formatDate(Date date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}
	
	public static Date getCurrentDate(){ 
		Calendar cal = Calendar.getInstance();
		return cal.getTime();

	}

	public static int getDifference(String date1,String date2,String time1,String time2){
		int year1 = Integer.parseInt(date1.substring(0,4));
		int month1 = Integer.parseInt(date1.substring(5,7));
		int day1 = Integer.parseInt(date1.substring(8,10));
		int year2 = Integer.parseInt(date2.substring(0,4));
		int month2 = Integer.parseInt(date2.substring(5,7));
		int day2 = Integer.parseInt(date2.substring(8,10));
		int hour1 = Integer.parseInt(time1.substring(0,2));
		int hour2 = Integer.parseInt(time2.substring(0,2));
		int ret = hour1-hour2;
		ret+=24*(day1-day2);
		ret+=5040*(month1-month2);
		ret+=24*365*(year1-year2);
		return ret;
	}
	
}
