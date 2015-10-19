package csc481.networking;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;

public class Server {
	private static final int NUM_OBJECTS = 12;
	/**The sockets representing client connections. */
	private static LinkedList<Socket> sockets;
	private static LinkedList<TestObject> objects;

	public static LinkedList<Socket> getSockets() {
		return sockets;
	}
	
	//must synchronize on the last socket in sockets
	//could we put that synchronize{} in this method instead?
	public static void addClient(Socket sock) {
		//send all objects to the new client so the client knows about all game object state
		//TestObject object;
		//will this synchronize on the correct object? (since add() probably copies sock)
		//synchronized (sock) {
			try {
				ObjectOutputStream output = new ObjectOutputStream( sock.getOutputStream());
				for (int i = 0;  i < objects.size(); i++) {
					synchronized (objects.get(i)) {
						output.writeObject(objects.get(i));
					}
					output.flush();	
				}
				//send terminating TestObject to tell the client that the server is done sending objects
				output.writeObject(new TestObject("init", -1));
				output.flush();
			} catch (Exception e) {
				System.out.println("Error interacting with client: " + e);
				e.printStackTrace();
			}
			sockets.add(sock); //changed to add sockets afterwards
		//}
		System.out.println("New client " + (sockets.size()-1) + " has connected");//! Testing code
	}
	/**
	 * Send all clients an updated version of an object
	 * @param object the object that has been changed on the server side 
	 * to send to the clients to update the clients' object
	 * @param skipIdx skip sending object info to this object at this index in objects
	 *  (if this object sent the update object in the first place).
	 */
	public static void sendObjectToClients(Object object, int skipIdx) {
	      try {
		    for (int i = 0;  i < sockets.size(); i++) {
		    	if (i == skipIdx) continue;
		    	//Only need to synchronize here if the server could call this method
		    	//(since it could be called also from a client)
		    	synchronized (sockets.get(i)) {
		    		ObjectOutputStream output = new ObjectOutputStream( sockets.get(i).getOutputStream() );
		    		//don't need to synchronize object since its a copy of the actual object (Java is pass by value)
		    		output.writeObject(object);
				    output.flush();
				      
		    	}
		    }
	      } catch ( Exception e ) {
	    	  System.out.println( "Error interacting with client: " + e );
	    	  e.printStackTrace();
	      }
	}


	public static LinkedList<TestObject> getObjects() {
		return objects;
	}
	
	/**
	 * Updates
	 * @param object
	 */
	public static void updateObject(TestObject object) {
		int objIndex = object.getIndex();
		
  	    //get exclusive control to the object this will change before it changes it
		synchronized (objects.get(objIndex)) {
			objects.set(objIndex, object);
		}

		System.out.println("Object " + objIndex + " has updated in the server");//! Testing code
	}

	public static void main(String args[]) throws Exception {
		objects = new LinkedList<TestObject>();
		sockets = new LinkedList<Socket>();
		
		for (int i = 0; i < NUM_OBJECTS; i++) {
			objects.add(new TestObject("init", i));
		}
		
		ServerAccepter accepter = new ServerAccepter();
		(new Thread(accepter)).start();
		
		//Random rand = new Random();		
		int numClients = 3;//rand.nextInt(4) + 2;//2 to 6 clients //!
		
		//Initialize clients. Should be done on client side.
		Client[] clients = new Client[numClients];
		Thread[] threads = new Thread[numClients];
		System.out.println(numClients + " clients created.");
		for (int i = 0; i < numClients; i++) {
			clients[i] = new Client();
			threads[i] = new Thread(clients[i]);
			threads[i].start();
		}
		
		ServerReceiver receiver = new ServerReceiver();
		(new Thread(receiver)).start();
		
		//Let them do some work
		Thread.sleep(10000);
		
		for (int i = 0; i < numClients; i++) {
			System.out.println("IN CLIENT " + i + " :");//!
			clients[i].stop();
			threads[i].join();

			//sockets.get(i).close();
		}
		
//		for (int i = 0; i < numClients; i++) {
//			sockets.get(i).close();
//		}
		accepter.stop();
		receiver.stop();
		
		System.out.println("IN THE SERVER :::::");//!
		//! for testing purposes
		for (int i = 0; i < NUM_OBJECTS; i++) {
			System.out.println("TestObject " + i + " has its stringData field set to " + objects.get(i).getStringData());//!
		}
		
	}
}
