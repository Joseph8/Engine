/**package csc481.networking;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
/**
 * Receives objects from the clients and updates them in the server.
 * @author Joseph Gregory
 *
 *
public class ServerReceiver implements Runnable{

	public static final int PORT_NUMBER = 26036;
	private boolean isRunning;
	
	public void run() {
	      try {
	    	  isRunning = true;
	    	  Socket sock;
	    	  System.out.println("000000000000000000000000000000");//!
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
		    		  	  TestObject object;
				    	  
		    		  	  try {
		    		  		  object = (TestObject) input.readObject();
		    		  	  } catch (SocketTimeoutException e) {
		    		  		  continue;
		    		  	  }
		    		  	  
				    	  //System.out.println("111111111111111111111111111111" + p);//!
				    	  System.out.println(">:??>>> Passed in obj value: " + object.getStringData() + ", New obj value: " + Server.objects.get(object.getIndex()).getStringData());
				    	  Server.updateObject(object);
				    	  System.out.println("222222222222222222222222222222");//!
				  		  //must tell all clients that this object has been updated
				    	  Server.sendObjectToClients(object, object.getIndex());
				    	  System.out.println("333333333333333333333333333333");//!
				    	  
					      
				    	  //For future assignments
//				    	  int guid = (GameObject)obj.getGUID();
//					      synchronize(Server.GameObjects[guid]) {
//					   			Server.updateObject(guid, (GameObject)obj);
//					      }
					      
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

}*/