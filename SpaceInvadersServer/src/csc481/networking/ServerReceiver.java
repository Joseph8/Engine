package csc481.networking;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;

import csc481.ProcessingSketch;
import csc481.Time.Timeline;
import csc481.events.Event;

/**
 * Receives objects from the clients and updates them in the server.
 * @author Joseph Gregory
 *
 */
public class ServerReceiver implements Runnable{

	public static final int PORT_NUMBER = 26036;
	private boolean isRunning;
	
	public void run() {
	      try {
	    	  isRunning = true;
	    	  Socket sock;
	    	  while (isRunning) {
		    	  for (int i = 0; i < Server.getSockets().size(); i++) {
		    		  System.out.flush();
		    		  //Thread.sleep(100);
		    		  //Could these nested synchronized blocks create deadlock?
		    		  //Do we need this synchronized block?
		    		  //synchronized (sock = Server.getSockets().get(i)) {
		    		  	  sock = Server.getSockets().get(i);
		    		  	  ObjectInputStream input = Server.getInputStreams().get(i);
		    		  	  //ObjectInputStream input = new ObjectInputStream(sock.getInputStream());
		    		  	  //if (input == null) System.out.println("NULL\n\n\n\n\n\n\n\n\n\n");
		    		  	  ArrayList<Event> eventBuffer;
				    	  
		    		  	  try {
		    		  		  eventBuffer = (ArrayList<Event>) input.readObject();
		    		  		  for (Event event : eventBuffer) {
		    		  			  event.timestamp = ProcessingSketch.getGameTimeline().getIterations();
		    		  		  }
		    		  		  System.out.println("Server received event 1 timestamp:" + eventBuffer.get(0).timestamp);
		    		  	  } catch (SocketTimeoutException e) {
		    		  		  System.out.println("ServerReceiver timeout");
		    		  		  continue;
		    		  	  }
		    		  	  
				    	  for (int j = 0; j < eventBuffer.size(); j++) {
				    		  //EventHandler.raise(eventBuffer.get(j));
					    	  System.out.println("SERVER received Event at timestamp " + i + ": " + eventBuffer.get(i).timestamp);
				    	  }

				    	  System.out.println("222222222222222222222222222222");//!
				  		  //must tell all clients that this object has been updated
				    	  //Server.sendObjectToClients(object, object.getIndex());
				    	  
					      
				    	  //For future assignments
				    	  /*int guid = (GameObject)obj.getGUID();
					      synchronize(Server.GameObjects[guid]) {
					   			Server.updateObject(guid, (GameObject)obj);
					      }
					      */
		    		  //}
		    	  }
	    	  }
	      } catch ( Exception e ) {
	    	  System.out.println( "Error receiveing from client: " + e );
	    	  e.printStackTrace();
	      }
	    
	}
	
	public void stop() {
		isRunning = false;
	}

}