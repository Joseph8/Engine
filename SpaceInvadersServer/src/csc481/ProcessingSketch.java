package csc481;

import java.util.LinkedList;

import csc481.Time.Replay;
import csc481.Time.Timeline;
import csc481.eventhandlers.EventManager;
import csc481.events.EventType;
import csc481.events.InputEvent;
import csc481.events.InputType;
import csc481.networking.Server;
import csc481.networking.ServerAccepter;
import csc481.networking.ServerReceiver;
import csc481.objects.Alien;
import csc481.objects.GameObject;
import csc481.objects.MoverGravityJump;
import csc481.objects.MoverNoGravity;
import csc481.objects.Player;
import csc481.objects.MovingPlatform;
import csc481.objects.Shot;
import csc481.objects.SpawnPoint;
import csc481.objects.StaticPlatform;
import processing.core.*;

public class ProcessingSketch extends PApplet {
	private static final long serialVersionUID = 3466602171618270735L;
	//MovingPlatform[] aliens = new MovingPlatform[12];
	Player player1;
	int colors[] = new int[6];
	MovingPlatform ground;
	private static LinkedList<GameObject> objects;
	/**Params to use in collision() that give top/bottom bounds
	of rect positions before move().
	{obj1Left, obj1Right, obj2Left, obj2Right}*/
	float prevXBounds[] = new float[4];
	boolean stop;
	private static EventManager eventManager;
	private static Timeline gameTime;
	private static ProcessingSketch instance;
	private static float initWidth;
	private static float initHeight;
	private static boolean gameOver;
	
	ServerAccepter accepter;
	Thread accepterThread;
	
	ServerReceiver receiver = new ServerReceiver();
	Thread receiverThread = new Thread(receiver);

	public void setup() {
		gameOver = false;
		initWidth = width;
		initHeight = height;
		instance = this;
		gameTime = new Timeline();
		colors[0] = color(238, 233, 233);
		colors[1] = color(205, 192, 176);
		colors[2] = color(135, 206, 250);
		colors[3] = color(154, 205, 50);
		colors[4] = color(255, 99, 71);
		colors[5] = color(255, 215, 0);
		size(800, 800);
		background(100);
		objects = new LinkedList<GameObject>();
		eventManager = new EventManager();
		
		player1 = new Player((float) width / 2, (float) (height / 20) * 19,
				(float) width / 30, (float) height / 30, 0, 1);
		objects.add(player1);
		eventManager.register(player1.getGUID(), EventType.COLLISION);
		eventManager.register(player1.getGUID(), EventType.DEATH);
		eventManager.register(player1.getGUID(), EventType.SPAWN);
		eventManager.register(player1.getGUID(), EventType.INPUT);
		
		int alienRows = 3;
		int alienColumns = 10;
		float spacer = width/alienColumns;
		for (int i = 0; i < alienRows; i++) {
			for (int j = 0; j < alienColumns; j++) {
				objects.add(new Alien((float) spacer * j, (float) (height/alienRows/5) * i + (spacer/2),(float) width / 30, (float) height / 30, colors[i%colors.length],  spacer));
				//eventManager.register(platform.getGUID(), EventType.COLLISION);
			}
		}

		objects.add( new SpawnPoint(5, 17 * (height / 20)));
		objects.add( new SpawnPoint(width/2 + 5, 13 * (height / 20)));
		objects.add( new SpawnPoint(width/3 + 5, 6 * (height / 20)));
		
		Server.init();
		
		accepter = new ServerAccepter();
		accepterThread = new Thread(accepter);
		accepterThread.start();
		
		receiver = new ServerReceiver();
		receiverThread = new Thread(receiver);
		receiverThread.start();
		stop = false;
		
		
	}

	public void draw() {
		//PRIORITY COLLLISION
		/**
		//collide lowest to highest priority
		boolean firstForPriority = true;
		for (int curPri = 0; curPri < 3; curPri++) {
			firstForPriority = true;
			//process the current priority
			for (GameObject object1 : objects) {
				int priority = object1.getCollider().getPriority();
				if (priority != curPri) continue;
				
				if (firstForPriority) {
					//equalCollide with same priority objects
					for (GameObject object2 : objects) {
						if (priority == object2.getCollider().getPriority()) {
							
							
						}
					}
					firstForPriority = false;
				}
				//wallCollide with higher priority objects
				for (GameObject object2 : objects) { 
					if (priority < object2.getCollider().getPriority()) {
						object1.getCollider().wallCollide(object2);
					}
				}
			}
		}
		*/
		/**if (Replay.replaying) {
			if (gameTime.getGameTime() > Replay.replayEndTime) {
				Replay.stopReplay();
			}
		}*/
		if (stop) exitProgram();
		if (gameOver) {
			//textSize(40);
			 background(345);
			  stroke(175);
			  line(width/2,0,width/2,height);

			  textFont(createFont("Arial",16,true));       
			  fill(0);

			  textAlign(CENTER);
			  text("This text is centered.",width/2,60); 

			  textAlign(LEFT);
			  text("This text is left aligned.",width/2,100); 

			  textAlign(RIGHT);
			  text("This text is right aligned.",width/2,140); 
			/*textFont(createFont("Arial",16,true),16);
			fill(0, 102, 153);
			text("GAME OVER", width, height);*/
		}
		Server.updateClients(objects);
		
		//move
		//for (GameObject obj : objects) {
		for (int i = 0; i < objects.size(); i++) {
			GameObject obj = objects.get(i);
			obj.setPrevXBounds();
			if (obj.getMover() != null) {
				if(!obj.getMover().move()) {
					if (obj instanceof Shot) ((Shot)obj).remove();
					objects.remove(obj);
					i--;
				}
			}
		}
		
		//collide
		if (!Replay.replaying) {
			int i = 0;
			for (GameObject obj1 : objects) {
				if (obj1.getCollider() == null) continue;
				for (int j = 0; j < objects.size(); j++) {
					if (obj1.getGUID() == objects.get(j).getGUID()) continue;
					obj1.getCollider().collide(objects.get(j));
				}
				i++;
			}
		}
		
		eventManager.handleEvents();
		gameTime.incrementIterations();
		
		//render
		if (Replay.replaying) {
			background(3320);
		} else if (Replay.recording) {
			background(190);
		} else if (gameOver) {
			background(2342);
		} else {
			background(100);
		}
		for (GameObject obj : objects) {
			if (obj.getRenderer() != null) obj.getRenderer().render();
		}

		
//		player1.getCollider().setPrevOwnerXBounds();
//		player1.getMover().move();
//		player1.setOnGround(false);
//		for (GameObject obj : objects) {
//			player1.getCollider().setPrevObj2XBounds(obj);
//			if (obj.getMover() != null) obj.getMover().move();
//			player1.getCollider().collide(obj);
//		}
			
		/**
		player1.getMover().move();
		player1.setOnGround(false);
		for (int i = 0; i < rectangles.length; i++) {
//			prevXBounds[2] = rectangles[i].getxPos();
//			prevXBounds[3] = rectangles[i].getxPos() + rectangles[i].getWidth();
			player1.getCollider().setPrevObj2XBounds(rectangles[i]);
			rectangles[i].getMover().move();
			player1.getCollider().collide(rectangles[i]);
		}
		//prevXBounds[2] = ground.getxPos();
		//prevXBounds[3] = ground.getxPos() + ground.getWidth();
		player1.getCollider().setPrevObj2XBounds(ground);
		player1.getCollider().collide(ground);
		*/
	}

	public void keyPressed() {
		switch (key) {
		case 'a':
			if (!Replay.replaying) eventManager.raise(new InputEvent(InputType.MOVE_LEFT, getGameTimeline().getIterations()));
			break;
		case 'd':
			if (!Replay.replaying) eventManager.raise(new InputEvent(InputType.MOVE_RIGHT, getGameTimeline().getIterations()));
			break;
		case ' ':
			if (!Replay.replaying) eventManager.raise(new InputEvent(InputType.SHOOT, getGameTimeline().getIterations()));
			break;
		case 'z':
			exit();
			break;
		case 'r':
			if (Replay.replaying) break;
			if (!Replay.recording) {
				Replay.startRecording();
			} else {
				Replay.stopRecording();
				Replay.startReplay();
			}
			break;
		case 'q':
			if (Replay.replaying) Replay.changeSpeed(false);
			break;
		case 'e':
			if (Replay.replaying) Replay.changeSpeed(true);
			break;
		}
	}

	public void keyReleased() {
		switch (key) {
		case 'a':
			//player1.stopMoveLeft();
			if (!Replay.replaying) eventManager.raise(new InputEvent(InputType.STOP_MOVE_LEFT, getGameTimeline().getIterations()));
			break;
		case 'd':
			//player1.stopMoveRight();
			if (!Replay.replaying) eventManager.raise(new InputEvent(InputType.STOP_MOVE_RIGHT, getGameTimeline().getIterations()));
			break;
		case ' ':
			//player1.stopJump();
			if (!Replay.replaying) eventManager.raise(new InputEvent(InputType.STOP_SHOOT, getGameTimeline().getIterations()));
			break;
		}
	}

	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "ProcessingSketch" });
	}
	
	public static LinkedList<GameObject> getObjects() {
		return objects;
	}
	
	private void exitProgram() {
		accepter.stop();
		receiver.stop();
		try {
			accepterThread.join();
			receiverThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exit();
	}

	public static void setObjects(LinkedList<GameObject> objects) {
		ProcessingSketch.objects = objects;
	}

	public static Timeline getGameTimeline() {
		return gameTime;
	}

	public static void setGameTime(Timeline gameTime1) {
		gameTime = gameTime1;
	}

	public static EventManager getEventManager() {
		return eventManager;
	}

	public static void setEventManager(EventManager eventManager) {
		ProcessingSketch.eventManager = eventManager;
	}
	
	public static ProcessingSketch getInstance() {
		return instance;
	}
	
	public static void addGameObject(GameObject obj) {
			objects.add(obj);
	}
	
	public static void gameOver() {
		gameOver = true;
	}
	
	public static void removeObjByGUID(long GUID) {
		for (GameObject obj : objects) {
			if (obj.getGUID() == GUID) {
				objects.remove(obj);
				return;
			}
		}
	}
}
