package com.nespresso.heating;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * The system obtains temperature data from a remote source,
 * compares it with a given threshold and controls a remote heating
 * unit by switching it on and off. It does so only within a time
 * period configured on a remote service (or other source)
 * 
 * This is purpose-built crap
 *
 */
public class ScheduleManager {
	
	public static void manage(HeatingManagerImpl hM, String threshold) throws Exception {
		String temperature = stringFromURL("http://probe.home:9990/temp", 4);
		int hour_of_day = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		boolean activate = hour_of_day > startHour() && hour_of_day < endHour();
        hM.manageHeating(temperature, threshold, activate);
	}

	private static int endHour() throws NumberFormatException, MalformedURLException, IOException {
		return Integer.parseInt(stringFromURL("http://timer.home:9990/end", 2));
	}

	private static String stringFromURL(String urlString, int sizeOfString) throws MalformedURLException,
			IOException {
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		byte[] tempBuffer = new byte[sizeOfString];
		is.read(tempBuffer);
		String t = new String(tempBuffer);
		is.close();
		return t;
	}

	private static int startHour() throws NumberFormatException, MalformedURLException, IOException {
		return Integer.parseInt(stringFromURL("http://timer.home:9990/start", 2));
	}

}
