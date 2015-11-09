
/**package csc481.networking;

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
public class Server {
	private static final int NUM_OBJECTS = 12;
	public static final int PORT_NUMBER = 26036;
	public static final int timeout = 100;
	public static InetAddress host;
	private boolean isRunning;
	private static LinkedList<Socket> sockets;
	private static volatile LinkedList<ObjectOutputStream> outputStreams;
	private static volatile LinkedList<ObjectInputStream> inputStreams;

	public static void addClient(Socket sock) {
		//send all objects to the new client so the client knows about all game object state
		//TestObject object;
		//will this synchronize on the correct object? (since add() probably copies sock)
		//synchronized (sock) {
			try {
				ObjectOutputStream output = new ObjectOutputStream( sock.getOutputStream());
				outputStreams.add(output);
				inputStreams.add(new ObjectInputStream(sock.getInputStream()));
					output.reset();
					output.writeObject(ProcessingSketch.getObjects());
					output.flush();	
				sockets.add(sock);
			} catch (Exception e) {
				System.out.println("Error interacting with client: " + e);
				e.printStackTrace();
			}
		//}
	}
	
	public LinkedList<GameObject> update(LinkedList<GameObject> objects) {
		try {
			//update this client with new information from the server
			//(probably want to make this a new thread in the future)
			Player inObj;
			try {
    			//System.out.println("created input stream");//!
				int i = 0;
    			do {
    				i++;
    				for (int p = 0;  p < sockets.size(); p++) {
	    				ObjectInputStream input = inputStreams.get(p);
	    				inObj = (Player) input.readObject();
	    				if (inObj.getIndex() >= objects.size()) {
	    					objects.add(inObj);
	    				} else {
		    				//System.out.println("read object");//!
		    				//need to somehow put this in a loop so it keeps reading object until
		    				//the server no longer needs to send any more objects
		    				objects.set( inObj.getIndex(), inObj);
		    				System.out.println(">>>>A client RECEIVED a player with inded " + inObj.getIndex());//!
		    				if (i > 1) System.out.println("a client read multiple objects------------------------------" + i);
	    				}
    				}
    			} while (input.available() != 0);
    		} catch (SocketTimeoutException e) {
				return null;
			}

			ObjectOutputStream output = new ObjectOutputStream( sock.getOutputStream() );
			
			output.reset();
			output.writeObject(objects);
			output.flush();
			System.out.println("client written to server");//!

		
		} catch (Exception e) {
			System.out.println("Client exception.");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void init() {
		sockets = new LinkedList<Socket>();
		outputStreams = new LinkedList<ObjectOutputStream>();
		inputStreams = new LinkedList<ObjectInputStream>();
	}
}*/
