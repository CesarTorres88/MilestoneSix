package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread {


	public void run() {

		System.out.println("My Client thread is running");
	}
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	/**
	 * Initiates the client class to connect to the server thread
	 * @param ip
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void start(String ip, int port) throws UnknownHostException, IOException {
		clientSocket = new Socket(ip, port);
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public String sendMessage(String msg) throws IOException {
		out.println(msg);
		return in.readLine();

	}
/**
 * Clean up method closes the starting functions 
 * of this client. 
 * @throws IOException
 */
	public void cleanUp() throws IOException {

		out.close();
		in.close();
		clientSocket.close();
	}
/**
 * Initializes a client object and
 * determines its function.  
 * @param args
 * @throws IOException
 * @throws InterruptedException
 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException {

		Client client = new Client();
		client.start("127.0.0.1", 6666);
		String response;
		for (int count = 0; count < 2; ++count) {
			String message;
			if (count != 1) {
				message = "Hello from Client" + count;
				Thread.sleep(10000);
			} else
				message = "E";
			response = client.sendMessage(message);
			System.out.println("Server response was " + response);
			if (response.equals("q"))
				Thread.sleep(10000);
			break;
		}
		client.cleanUp();

	}

}
