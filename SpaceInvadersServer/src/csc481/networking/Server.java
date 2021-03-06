package csc481.networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Random;

import csc481.ProcessingSketch;
import csc481.objects.GameObject;
import csc481.objects.Player;

public class Server {
	private static final int NUM_OBJECTS = 12;
	/**The sockets representing client connections. */
	private static volatile LinkedList<Socket> sockets;
	private static volatile LinkedList<ObjectInputStream> inputStreams;
	private static volatile LinkedList<ObjectOutputStream> outputStreams;


	public static LinkedList<Socket> getSockets() {
		return sockets;
	}

	public static LinkedList<ObjectInputStream> getInputStreams() {
		return inputStreams;
	}
	
	public static void init() {
		sockets = new LinkedList<Socket>();
		inputStreams = new LinkedList<ObjectInputStream>();
		outputStreams = new LinkedList<ObjectOutputStream>();
	}
	
	//must synchronize on the last socket in sockets
	//could we put that synchronize{} in this method instead?
	public static void addClient(Socket sock) {
			try {
				ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
				Player newPlayer = null;
				try {
					newPlayer = (Player) input.readObject();
					System.out.println("Player added to server with randNum: " + newPlayer.randNum);
				} catch (SocketTimeoutException e) {
					e.printStackTrace();
				}
				
				//add the new player to the object list by raising an event to be handled later
				//ProcessingSketch.getHandler().raise(new NewObjectEvent(newPlayer));
				outputStreams.add(new ObjectOutputStream(sock.getOutputStream()));
				inputStreams.add(input);
				sockets.add(sock);
				
			} catch (Exception e) {
				System.out.println("Error interacting with client: " + e);
				e.printStackTrace();
			}
		//}
		System.out.println("New client " + (sockets.size()-1) + " has connected");//! Testing code
	}
	
	/**
	 * Send all clients an updated version of all game objects
	 * @param objects 
	 * @param object the object that has been changed on the server side 
	 * to send to the clients to update the clients' object
	 * @param skipIdx skip sending object info to this object at this index in objects
	 *  (if this object sent the update object in the first place).
	 */
	public static void updateClients(LinkedList<GameObject> objects) {
	      try {
		    for (int i = 0;  i < sockets.size(); i++) {
		    	//Only need to synchronize here if the server could call this method
		    	//(since it could be called also from a client)
		    	synchronized (sockets.get(i)) {
		    		//ObjectOutputStream output = new ObjectOutputStream( sockets.get(i).getOutputStream() );
		    		ObjectOutputStream output = outputStreams.get(i);
		    		//don't need to synchronize object since its a copy of the actual object (Java is pass by value)
		    		output.reset();
		    		output.writeObject(objects);
				    output.flush();
				    
		    	}
		    }
	      } catch ( Exception e ) {
	    	  System.out.println( "Error interacting with client: " + e );
	    	  e.printStackTrace();
	      }		
	}




//	public static LinkedList<TestObject> getObjects() {
//		return objects;
//	}
//	
//	/**
//	 * Updates
//	 * @param object
//	 */
//	public static void updateObject(TestObject object) {
//		int objIndex = object.getIndex();
//		
//  	    //get exclusive control to the object this will change before it changes it
//		synchronized (objects.get(objIndex)) {
//			objects.set(objIndex, object);
//		}
//
//		System.out.println("Object " + objIndex + " has updated in the server");//! Testing code
//		System.out.println("Passed in obj value: " + object.getStringData() + ", New obj value: " + objects.get(object.getIndex()).getStringData());
//		System.out.println("LINE");
//	}

//	public static void main(String args[]) throws Exception {
//		objects = new LinkedList<TestObject>();
//		sockets = new LinkedList<Socket>();
//		inputStreams = new LinkedList<ObjectInputStream>();
//		outputStreams = new LinkedList<ObjectOutputStream>();
//		
//		for (int i = 0; i < NUM_OBJECTS; i++) {
//			objects.add(new TestObject("init", i));
//		}
//		
//		ServerAccepter accepter = new ServerAccepter();
//		Thread accepterThread = new Thread(accepter);
//		accepterThread.start();
//		
//		//Random rand = new Random();		
//		int numClients = 3;//rand.nextInt(4) + 2;//2 to 6 clients //!
//		
//		//Initialize clients. Should be done on client side.
//		Client[] clients = new Client[numClients];
//		Thread[] threads = new Thread[numClients];
//		System.out.println(numClients + " clients created.");
//		for (int i = 0; i < numClients; i++) {
//			clients[i] = new Client();
//			threads[i] = new Thread(clients[i]);
//			threads[i].start();
//		}
//		
//
//		
//		//Let them do some work
//		Thread.sleep(20000);
//	
//		
//		for (int i = 0; i < numClients; i++) {
//			System.out.println("IN CLIENT " + i + " :");//!
//			clients[i].stop();
//			threads[i].join();
//
//			//sockets.get(i).close();
//		}
//		
////		for (int i = 0; i < numClients; i++) {
////			sockets.get(i).close();
////		}
//		
//		System.out.println("IN THE SERVER :::::");//!
//		//! for testing purposes
//		for (int i = 0; i < NUM_OBJECTS; i++) {
//			System.out.println("TestObject " + i + " has its stringData field set to " + objects.get(i).getStringData());//!
//		}
//		
//	}


}
