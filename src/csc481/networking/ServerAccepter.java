package csc481.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerAccepter implements Runnable{
	public static final int PORT_NUMBER = 26036;
	private boolean isRunning;
	
	public void run() {
		isRunning = true;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(PORT_NUMBER);
			while( isRunning ){	    	
				//Thread.sleep(350);
				
				// Try to get a new client connection.
				Socket sock = serverSocket.accept();
				sock.setSoTimeout(500);
				
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
	}

}
