package csc481.networking;

import java.io.Serializable;

public class TestObject implements Serializable{

	private static final long serialVersionUID = 156321565;
	private String stringData;
	private int index;
	
	public TestObject(String stringData, int index) {
		this.stringData = stringData;
		this.index = index;
	}

	public String getStringData() {
		return stringData;
	}

	public void setStringData(String stringData) {
		this.stringData = stringData;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
