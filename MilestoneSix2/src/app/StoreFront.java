package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author CESARTORRES
 *
 */
public class StoreFront {

	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public void start(int port) throws UnknownHostException, IOException {
		System.out.println("Waiting for a Client connection........");
		serverSocket = new ServerSocket(port);
		clientSocket = serverSocket.accept();
		System.out.println("Client has requested " + clientSocket.getLocalPort());
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String input;
		while ((input = in.readLine()) != null) {
			if ("E".equals(input)) {
				System.out.println("Message received for Server Shut Down");
				out.println("QUIT");
				break;
			} else {
				System.out.println("Got a message of: " + input);
				out.println("OK");
			}
		}
		System.out.println("System is Shut Down");
	}

	public String sendMessage(String msg) throws IOException {
		out.println(msg);
		return in.readLine();

	}

	public void cleanUp() throws IOException {

		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
	}
	

	private static void saveToFile(String filename, Product<Object> newItem, boolean append) {
		PrintWriter pw;
		try {
			File file = new File(filename);
			FileWriter fw = new FileWriter(file, append);
			pw = new PrintWriter(fw);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(newItem);
			pw.println(json);

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Items not appended.");
		}
	}
	@SuppressWarnings("rawtypes")
	private static ArrayList<Product> readFromFile(String filename) {
		ArrayList<Product> items = new ArrayList<>();
		{
			try {
				File file = new File(filename);
				Scanner s = new Scanner(file);

				while (s.hasNext()) {
					String json = s.nextLine();
					ObjectMapper objectMapper = new ObjectMapper();
					Product item1 = objectMapper.readValue(json, Product.class);
					items.add(item1);
				}
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Items not added.");
			}
			return items;
		}
	}


	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException, InterruptedException {
		StoreFront server = new StoreFront();
		server.start(6666);

		System.out.println("Welcome to CESAR'S STORE!!!!!");
		// Instance of a the inventory class
		Inventory item = new Inventory();
		// Creates a scanner objects to receive input from the user

		@SuppressWarnings("resource")
		Scanner myItem = new Scanner(System.in);
		// Initialize the choose action methods with a value as action
		int action = chooseAction();
		// While loop with the default value of 0
		while (action != 0) {
			System.out.println("You chose option " + action);
			// Switch and case to prompt the user for input and allow the program to respond
			// to the input
			switch (action) {
			case 1:
				try {
					System.out.println("You chose to add an item to your Inventory");
					String name = " ";
					String description = " ";
					int quantity = 0;
					double price = 0;

					System.out.println("Name: ");
					name = myItem.next();

					System.out.println("Description: ");
					description = myItem.next();

					System.out.println("Qty: ");
					quantity = myItem.nextInt();

					System.out.println("Price: $");
					price = myItem.nextDouble();

					Product<Object> newItem = new Product<Object>(name, description, quantity, price);
					item.StockList.add(newItem);
					// Collections.sort(newItem);
					printStock(item);
					// JSON File application
					for (int x = 0; x < item.StockList.size(); ++x)
						;
					{
						saveToFile("out.json", newItem, true);
					}
					List<Product> inventoryList = readFromFile("out.json");

					for (Product item1 : inventoryList) {
						String text = (item1.name + "" + item1.description + "" + item1.quantity + "" + item1.price);
						System.out.println(text);
					}
				} 
				catch (Exception e) {
					System.out.println("Invalid Character input");
				}
				break;

			case 2:
				try {
					System.out.println("Would you like to remove an Item(s) from your Inventory?");
					int remove;
					printStock(item);

					System.out.println("Which Item woudld you like to remove (number) ");
					remove = myItem.nextInt();
					item.StockList.remove(remove);
					System.out.println("The following items are in your cart.");
					printStock(item);
				} catch (Exception e) {
					System.out.println("Invalid Character input");
				}
				break;
			case 3:
				try {
					System.out.println("You would like to add the follwing Item(s)to the cart?");
					printStock(item);

					item.CartList.addAll(item.StockList);
					System.out.println("The following items are in your cart.");
					printCart(item);
				} catch (Exception e) {
					System.out.println("Invalid Character input");
				}
				break;
			case 4:
				try {
					System.out.println("Would you like to return an Item?");
					int rtrn;
					printCart(item);

					System.out.println("Which Item would you like to return (number) ");
					rtrn = myItem.nextInt();

					item.CartList.remove(rtrn);
					System.out.println("The following items are in your cart.");
					printCart(item);

					Product<?> newItem = new Product<Object>();
					System.out.println("The following items have been returned to stock.");
					item.StockList.add(newItem);
					printStock(item);

				} catch (Exception e) {
					System.out.println("Invalid Character input");
				}
				break;
			case 5:
				try {
					System.out.println("Your Item(s) have been cleared.");
					item.CartList.clear();
					printCart(item);

				} catch (Exception e) {
					System.out.println("Invalid Character input");
				}
				break;
			case 6:
				try {
					System.out.println("You chose to checkout.");
					printCart(item);
					System.out.println("The Total cost of your Item(s). $" + item.Checkout());

				} catch (Exception e) {
					System.out.println("Invalid Character input");
				}
				break;
			case 7:
				try {

					ArrayList<Product> inventoryList = readFromFile("out.json");
					for (Product item1 : inventoryList) {
						String text = (item1.name + "," + item1.description + "," + item1.quantity + "," + item1.price);
						System.out.printf(text);
					}
				} catch (Exception e) {
					System.out.println("JSON File cannot be displayed.");
				}
				break;

			}
			action = chooseAction();

		}
		server.cleanUp();
	}

	/**
	 * 
	 * @param Print Cart List
	 */
	private static void printCart(Inventory item) {
		for (int i = 0; i < item.CartList.size(); i++) {

			System.out.println("New Item added: #" + i + " " + item.CartList);

		}
	}

	/**
	 * 
	 * @param Print Stock List
	 */
	private static void printStock(Inventory item) {

		for (int i = 0; i < item.StockList.size(); i++) {
			System.out.println("New Item returned: #" + i + " " + item.StockList);

		}
	}

	/**
	 * 
	 * @return User input method
	 */

	public static int chooseAction() {
		@SuppressWarnings("resource")
		Scanner num = new Scanner(System.in);
		int choice = 0;
		try {
			System.out.println(
					"Choose action (0) TO QUIT! (1) Add new item to the Inventory List (2) Remove item from Inventory List ");
			System.out.println(
					"(3) Add item(s) to the cart (4) Remove Item(s) to the cart and return to the Invenory List ");
			System.out.println("(5) Clear Item(s) from the Cart (6) Checkout (7) Read JSON File");
			choice = num.nextInt();

		} catch (Exception e) {
			System.out.println("Invalid Character entry");
		}
		return choice;
	}

}
