package csc481.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class ServerAccepter implements Runnable{
	public static final int PORT_NUMBER = 26036;
	public static final int timeout = 10;
	private boolean isRunning;
	ServerSocket serverSocket;
	
	public void run() {
		isRunning = true;
		serverSocket = null;
		Socket sock = null;
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			while( isRunning ){	    	
				//Thread.sleep(350);
				
				// Try to get a new client connection.
				try {
					sock = serverSocket.accept();
				} catch (SocketException e) {
					continue;
				}
				
				sock.setSoTimeout(timeout);
				
				Server.addClient(sock);
				
				//sock.close();

			}
			serverSocket.close();
	    } catch( IOException e ){
			System.err.println( "Failure accepting client " + e );
			e.printStackTrace();
		}
	    
	}
	
	public void stop() {
		isRunning = false;
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
