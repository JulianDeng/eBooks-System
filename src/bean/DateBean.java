package bean;

public class DateBean{
	private final int baseYear = 1970;
	private final long minutemills = 60*1000;
	private final long hourmills = 60*minutemills;
	private final long daymills = 24*hourmills;
	private final long commonyearmillis = 365*daymills;
	private final long leapyearmillis = 366*daymills;
	private final int[] monthIndex = {0,31,28,31,30,31,30,31,31,30,31,30,31};
	private final int[] leapMonthIndex = {0,31,28,31,30,31,30,31,31,30,31,30,31};
	private int timeZone;
	
	private int year, month, day, hour, minute, second, millisecond;
	
	public DateBean(DateBean date){
		this.year = date.year;
		this.month = date.month;
		this.day = date.day;
		this.hour = date.hour;
		this.minute = date.minute;
		this.second = date.second;
		this.millisecond = date.millisecond;
	}
	
	public DateBean(int timeZone, int year, int month, int day, int hour, int minute, int second,
			int millisecond) {
		this.timeZone = timeZone;
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.millisecond = millisecond;
	}

	public DateBean(int timeZone){
		long currentTime = System.currentTimeMillis(); //millisecond from 1970-01-01-00
		this.timeZone = timeZone;
		currentTime += timeZone*hourmills;
		long timeRemain = currentTime;
		int year = baseYear;
		long currentyearmill = isLeap(year) ? leapyearmillis : commonyearmillis;
		while(timeRemain-currentyearmill>0){
			timeRemain -= currentyearmill;
			year ++;
			currentyearmill = isLeap(year) ? leapyearmillis : commonyearmillis;
		}
		this.year = year;
		int month=1;
		long currentmonthmill = isLeap(year) ? leapMonthIndex[month]*daymills : monthIndex[month]*daymills;
		while(timeRemain-currentmonthmill>0){
			timeRemain -= currentmonthmill;
			month ++;
			currentmonthmill = isLeap(year) ? leapMonthIndex[month]*daymills : monthIndex[month]*daymills;
		}
		this.month = month;
		int day = 1;
		while(timeRemain-daymills>0){
			timeRemain -= daymills;
			day ++;
		}
		this.day = day;
		int hour = 0;
		while(timeRemain-hourmills>0){
			timeRemain -= hourmills;
			hour ++;
		}
		this.hour = hour;
		int minute = 0;
		while(timeRemain-minutemills>0){
			timeRemain -= minutemills;
			minute ++;
		}
		this.minute = minute;
		int second = 0;
		while(timeRemain-1000>0){
			timeRemain -= 1000;
			second ++;
		}
		this.second = second;
		this.millisecond = (int) timeRemain;
	}
	
	public int getTimeZone() {
		return timeZone;
	}

	public int getYear(){
		return this.year;
	}
	
	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

	public int getMillisecond() {
		return millisecond;
	}
	
	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append("Year: "+this.year+"\n");
		result.append("Month: "+this.month+"\n");
		result.append("Day: "+this.day+"\n");
		result.append("Hour: "+this.hour+"\n");
		result.append("Minute: "+this.minute+"\n");
		result.append("Second: "+this.second+"\n");
		result.append("Millisecond: "+this.millisecond+"\n");
		return result.toString();
	}
	
	private boolean isLeap(int year){
		if(year % 4 == 0){
			if(year % 100 == 0){
				if(year % 400 == 0){
					return true;
				} else {
					return false;
				}
			} else{
				return true;
			}
		} else return false;
	}
	
	/**
	 * format date to MMDDYYYY
	 * @param date
	 * @return
	 */
	public String formatDateToYYYYMMDD(){
		//format is MMDDYYYY;
		String yearStr, monthStr, dayStr;
		yearStr = year+"";
		monthStr = month<10 ? "0"+month : ""+month;
		dayStr = day<10 ? "0"+day : ""+day;
		return yearStr+monthStr+dayStr;
	}
}