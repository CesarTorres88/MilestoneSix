package app;
/**
 * 
 * @author Cesar Torres
 *A generic class is created to allow the inventory class 
 *to enable generic arrays data parameterized in the inventory class.
 * @param <T>
 */
public class Product <T> 
{
private T obj;


	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public T getData()
	{
		return this.obj;
	}	
	public void add(T obj) {
		this.obj = obj;
	
	}
/**
 * Data type and reference 
 * that will be used in the
 * store front application to
 * print out the items in the 
 * inventory list array as well 
 * as the values collected in 
 * the checkout method.
 */
public String name;
public String description;
public int quantity;
public double price;

/**
 * 
 * @return
 */
public String getName() {
	return name;
}
/**
 * 
 * @param name
 */
public void setName(String name) {
	this.name = name;
}
/**
 * 
 * @return
 */
public String getDescription() {
	return description;
}
/**
 * 
 * @param description
 */
public void setDescription(String description) {
	this.description = description;
}
/**
 * 
 * @return
 */
public int getQuantity() {
	return quantity;
}
/**
 * 
 * @param quantity
 */
public void setQuantity(int quantity) {
	this.quantity = quantity;
}
/**
 * 
 * @return
 */
public double getPrice() {
	return price;
}
/**
 * 
 */
public void setPrice(double price) {
	this.price = price;
}
/**
 * 
 */
Product() {
	System.out.println();
	name = this.getName();
	description = this.getDescription();
	quantity = this.getQuantity();
	price = this.getPrice();
}
/**
 * 
 * @param name
 * @param description
 * @param quantity
 * @param price
 */
Product(String name, String description, int quantity, double price) {
	this.name = name;
	this.description = description;
	this.quantity = quantity;
	this.price = price;
}
/**
 * 
 */
@Override
public String toString() {
	String item = "Name: " + name + " Description: " + description + " Qty: " + quantity + " Price: $" + price;
	return item;
}

/**
 * 
 */
}

