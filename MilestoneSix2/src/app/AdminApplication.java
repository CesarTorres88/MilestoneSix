package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AdminApplication {
/**
 * Main class used to initiate the server thread invoking the store front application and the client application.
 * @param args
 * @throws InterruptedException
 * @throws IOException
 */
	public static void main(String[] args) throws InterruptedException, IOException {
		StoreThread storeThread = new StoreThread();
		storeThread.start();
		Runnable runnable = new Client();
		Thread thread2 = new Thread(runnable);
		thread2.start();
/**
 * For loop that reiterates the program only one time during its execution.
 */
		for (int x = 0; x < 1; ++x) {
			System.out.println("Welcome to CESAR'S STORE!!!!!");
			try {

				// Instance of a the inventory class
				Inventory item = new Inventory();
				// Creates a scanner objects to receive input from the user

				@SuppressWarnings("resource")
				Scanner myItem = new Scanner(System.in);
				// Initialize the choose action methods with a value as action
				String action = chooseAction();
				/**
				 * While loop that will receive input from the user
				 */
				while (action != "e") {
					System.out.println("You chose option " + action);
					// Switch and case to prompt the user for input and allow the program to respond
					// to the input
					switch (action) {
					case "U":
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
							for (int x1 = 0; x1 < item.StockList.size(); ++x1)
								;
							{
								saveToFile("out.json", newItem, true);
							}
							List<Inventory> inventoryList = readFromFile1("out.json");

							for (Inventory item1 : inventoryList) {
								String text = (item1.name + "" + item1.description + "" + item1.quantity + "" + item1.price);
								System.out.println(text);
							}
						
						} catch (Exception e) {
							System.out.println("Invalid Character input");
						}
						break;

					case "R":
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
					}
					action = chooseAction();

				}

				Thread.sleep(500);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
	}

/**
 * Read from file should return the items in the 
 * JSON file. 
 * @param filename
 * @return
 */
	private static ArrayList<Inventory> readFromFile1(String filename) {
		ArrayList<Inventory> items = new ArrayList<>();
		{
			try {
				File file = new File(filename);
				Scanner s = new Scanner(file);

				while (s.hasNext()) {
					String json = s.nextLine();
					ObjectMapper objectMapper = new ObjectMapper();
					Inventory item1 = objectMapper.readValue(json, Inventory.class);
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
/**
 * Save file method that return the following objects
 * @param filename
 * @param newItem
 * @param append
 */
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
	/**
	 * Print cart method that return the inventory array list created in the inventory class
	 * @param item
	 */
	private static void printCart(Inventory item) {
		for (int i = 0; i < item.CartList.size(); i++) {

			System.out.println("New Item added: #" + i + " " + item.CartList);

		}
	}
	/**
	 * Returns the stock list array that was created in the inventory class
	 * @param Print Stock List
	 */
	private static void printStock(Inventory item) {

		for (int i = 0; i < item.StockList.size(); i++) {
			System.out.println("New Item returned: #" + i + " " + item.StockList);

		}
	}
/**
 * Choose action method that allows the while loop to receive string input from 
 * the user and within specific parameters.
 * @return
 */
	public static String chooseAction() {
		@SuppressWarnings("resource")
		Scanner num = new Scanner(System.in);
		String choice = "e";
		try {
			System.out.println(
					"Choose action (e) to --QUIT-- (U) Add new item to the Inventory List (R) Remove Item(s) to the cart and return to the Invenory List ");
			choice = num.next();

		} catch (Exception e) {
			System.out.println("Invalid Character entry");
		}
		return choice;
	}

}
