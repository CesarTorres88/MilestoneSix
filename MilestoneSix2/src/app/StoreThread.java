package app;

import java.io.IOException;

public class StoreThread extends Thread {
	/**
	 * Run method is initialized once 
	 * we extend the class. Functions
	 * are defined within the method to
	 * identify the actions it will perform 
	 * once it is invoked. 
	 */
	@Override
	public void run() {
		StoreFront server = new StoreFront();
		try {
			server.start(6666);
			server.cleanUp();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns a string message 
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	 public String sendMessage(String msg) throws IOException {
		System.out.println(msg + "");
		return msg;

	}
}