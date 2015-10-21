package csc481.networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Random;

import csc481.ProcessingSketch;
import csc481.objects.GameObject;
import csc481.objects.Player;

public class Client {
	private static final int NUM_OBJECTS = 12;
	public static final int PORT_NUMBER = 26036;
	public static final int timeout = 100;
	public static InetAddress host;
	private boolean isRunning;
	private Socket sock;
	private ObjectInputStream input;

	public LinkedList<GameObject> update(Player player) {
		try {

			ObjectOutputStream output = new ObjectOutputStream( sock.getOutputStream() );
			
			output.reset();
			output.writeObject(player);
			output.flush();
			System.out.println("client written to server");//!
			//update this client with new information from the server
			//(probably want to make this a new thread in the future)
			Player inObj;
			try {
    			//System.out.println("created input stream");//!
				int i = 0;
    			do {
    				return (LinkedList<GameObject>) input.readObject();
    				
//    				i++;
//    				inObj = (Player) input.readObject();
//    				//System.out.println("read object");//!
//    				//need to somehow put this in a loop so it keeps reading object until
//    				//the server no longer needs to send any more objects
//    				ProcessingSketch.getObjects().set( inObj.getIndex(), inObj);
//    				System.out.println(">>>>A client RECEIVED a player with inded " + inObj.getIndex());//!
//    				if (i > 1) System.out.println("a client read multiple objects------------------------------" + i);
    			} while (input.available() != 0);
    		} catch (SocketTimeoutException e) {
				return null;
			}
		
		} catch (Exception e) {
			System.out.println("Client exception.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public LinkedList<GameObject> init() {
		try {
			host  = InetAddress.getByName("127.0.0.1");
			sock = new Socket(host, PORT_NUMBER);
			sock.setSoTimeout(timeout);
			
			ObjectInputStream input = new ObjectInputStream( sock.getInputStream() );
	
			//populate the objects list with objects from the server until the server sends a null object
			LinkedList<GameObject> objects = (LinkedList<GameObject>)input.readObject();	
	
			
	//		System.out.println("IN THE CLIENT :");//!
			//! for testing purposes
	//		for (int i = 0; i < NUM_OBJECTS; i++) {
	//			System.out.println("TestObject " + i + " :: " + objects.get(i).getStringData() + " , " + objects.get(i).getIndex());//!
	//		}
			
			System.out.println("Client initialized");//!
			return objects;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}