package csc481.networking;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import csc481.ProcessingSketch;
import csc481.events.Event;
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
	private ObjectOutputStream output;

	/**
	 * Send input events to the server and receive the updated GameObjects from the server.
	 * @param eventBuffer events to send
	 * @return the updated GameObjects
	 */
	public LinkedList<GameObject> update(ArrayList<Event> eventBuffer) {
		try {
			
			output.reset();
			output.writeObject(eventBuffer);
			output.flush();
			System.out.println("client written to server");//!
			//update this client with new information from the server
			//(probably want to make this a new thread in the future)
			Player inObj;
			LinkedList<GameObject> newObjects = null;
			while (true) {
				try {
					int i = 0;
    				newObjects = (LinkedList<GameObject>) input.readObject();
    				
//    				i++;
//    				inObj = (Player) input.readObject();
//    				//System.out.println("read object");//!
//    				//need to somehow put this in a loop so it keeps reading object until
//    				//the server no longer needs to send any more objects
//    				ProcessingSketch.getObjects().set( inObj.getIndex(), inObj);
//    				System.out.println(">>>>A client RECEIVED a player with inded " + inObj.getIndex());//!
    				if (i > 1) System.out.println("a client read multiple objects------------------------------" + i);
	    		} catch (SocketTimeoutException e) {
					return null;
				} catch (EOFException e) {
					return newObjects;
				}
			}
		
		} catch (Exception e) {
			System.out.println("Client exception.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public LinkedList<GameObject> init(Player player) {
		try {
			host  = InetAddress.getByName("127.0.0.1");
			sock = new Socket(host, PORT_NUMBER);
			sock.setSoTimeout(timeout);
			
			ObjectOutputStream output = new ObjectOutputStream( sock.getOutputStream() );
			output.reset();
			output.writeObject(player);
			output.flush();
			
			ObjectInputStream input = new ObjectInputStream( sock.getInputStream() );	
			//populate the objects list with objects from the server until the server sends a null object
			LinkedList<GameObject> objects = (LinkedList<GameObject>) input.readObject();	
	
			
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
