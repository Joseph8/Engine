package csc481.networking;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Random;

public class Client implements Runnable {
	private static final int NUM_OBJECTS = 12;
	public static final int PORT_NUMBER = 26036;
	public static final int timeout = 100;
	public InetAddress host;
	private LinkedList<TestObject> objects;
	private static boolean isRunning;

	public void run() {
		objects = new LinkedList<TestObject>();
		isRunning = true;
		try {
			host  = InetAddress.getByName("127.0.0.1");
			Socket sock = new Socket(host, PORT_NUMBER);
			sock.setSoTimeout(timeout);
    		ObjectInputStream input = new ObjectInputStream( sock.getInputStream() );
    		TestObject object;
    		System.out.println("Client connected");//!

    		//populate the objects list with objects from the server until the server sends a null object
    		while ((object = (TestObject) input.readObject()).getIndex() != -1) {
        		objects.add(object);
    		}
    		
    		System.out.println("IN THE CLIENT :");//!
    		//! for testing purposes
    		for (int i = 0; i < NUM_OBJECTS; i++) {
    			System.out.println("TestObject " + i + " :: " + objects.get(i).getStringData() + " , " + objects.get(i).getIndex());//!
    		}
    		
    		System.out.println("Client initialized");//!
    		ObjectOutputStream output = new ObjectOutputStream( sock.getOutputStream() );
    		while (isRunning) {
    			Random rand = new Random();
    			Thread.sleep(rand.nextInt(2000));
    			
    			int stringData = rand.nextInt(1000);
    			int objIdx = rand.nextInt(NUM_OBJECTS);
    			
    			objects.get(objIdx).setStringData("" + stringData);
    			System.out.println(">>>>A client SET a TestObject stringData to " + stringData);//!
    			
    			//update the server (and thus other clients)
    			//ObjectOutputStream output = new ObjectOutputStream( sock.getOutputStream() );
    			
    			output.reset();
    			output.writeObject(objects.get(objIdx));
    			output.flush();
    			System.out.println("client written to server");//!
    			//update this client with new information from the server
    			//(probably want to make this a new thread in the future)
    			TestObject inObj;
    			try {
        			//System.out.println("created input stream");//!
    				int i = 0;
        			do {
        				i++;
        				inObj = (TestObject) input.readObject();
        				//System.out.println("read object");//!
        				//need to somehow put this in a loop so it keeps reading object until
        				//the server no longer needs to send any more objects
        				objects.set( inObj.getIndex(), inObj);
        				System.out.println(">>>>A client RECEIVED a TestObject with a stringData to " + inObj.getStringData());//!
        				if (i > 1) System.out.println("a client read multiple objects------------------------------" + i);
        			} while (input.available() != 0);
        		} catch (SocketTimeoutException e) {
    				continue;
    			}
    		}
    		
			sock.close();
			
		} catch (Exception e) {
			System.out.println("Client exception.");
			e.printStackTrace();
		}
		
		
	}

	public void stop() {
		for (int j = 0; j < NUM_OBJECTS; j++) {
			System.out.println("TestObject " + j + " has its stringData field set to " + objects.get(j).getStringData());//!
		}
		isRunning = false;
	}
	
}
