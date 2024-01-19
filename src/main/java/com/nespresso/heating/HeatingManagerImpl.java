package com.nespresso.heating;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class HeatingManagerImpl {

	private final static int PORT = 9999;
	private final static String HOST = "heater.home";

	public void manageHeating(String temperature, String threshold, boolean active) {
		double dTemperature = new Double(temperature);
		double dThreshold = new Double(threshold);
		if (dTemperature < dThreshold && active) {
			writeToOutputStream("on");
		} else if (dTemperature > dThreshold && active) {
			writeToOutputStream("off");
		}
	}

	public void writeToOutputStream(String message) {
		try {
			Socket socket = new Socket(HOST, PORT);
			OutputStream os = socket.getOutputStream();
			os.write(message.getBytes());
			os.flush();
			os.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
