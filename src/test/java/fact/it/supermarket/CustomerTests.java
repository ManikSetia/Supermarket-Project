package fact.it.supermarket;

import fact.it.supermarket.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomerTests {
    /**
     * Test of constructor en getters
     */
    @Test
    public void testConstructorEnGetters() {

        Customer customer = new Customer("Donald", "Duck");
        assertEquals("Donald", customer.getFirstName());
        assertEquals("Duck", customer.getSurName());
        assertEquals(-1, customer.getCardNumber());
        assertEquals(0, customer.getYearOfBirth());
    }

    /**
     * Test of setFirstName method, of class Customer.
     */
    @Test
    public void testSetFirstName() {
        Customer customer = new Customer("Donald", "Duck");
        customer.setFirstName("Mickey");
        assertEquals("Mickey", customer.getFirstName());
    }

    /**
     * Test of setSurName method, of class Customer.
     */
    @Test
    public void testSetSurName() {
        Customer customer = new Customer("Donald", "Duck");
        customer.setSurName("Mouse");
        assertEquals("Mouse", customer.getSurName());
    }

    /**
     * Test of setCardNumber method, of class Customer.
     */
    @Test
    public void testSetCardNumber() {
        Customer customer = new Customer("Donald", "Duck");
        customer.setCardNumber(11);
        assertEquals(11, customer.getCardNumber());
    }

    /**
     * Test of setYearOfBirth of class Customer.
     */
    @Test
    public void testSetYearOfBirth() {
        Customer customer = new Customer("Donald", "Duck");
        customer.setYearOfBirth(2015);
        assertEquals(2015, customer.getYearOfBirth());
    }

    /**
     * check basic functionality of Customer: constructor, setters/getters and
     * adding product to shoppinglist
     */
    @Test
    public void testShoppingListBasics() {
        Customer customer = new Customer("Donald", "Duck");
        assertEquals(0, customer.getNumberOnShoppingList());
        assertTrue(customer.addToShoppingList("Butter"));
        assertEquals(1, customer.getNumberOnShoppingList());
    }

    /**
     * Visitor can add maximum 5 products names to shoppinglist
     */
    @Test
    public void testShoppingListMax5() {
        Customer customer = new Customer("Donald", "Duck");
        assertEquals(0, customer.getNumberOnShoppingList());
        assertTrue(customer.addToShoppingList("Butter"));
        assertEquals(1, customer.getNumberOnShoppingList());
        assertTrue(customer.addToShoppingList("Cheese"));
        assertEquals(2, customer.getNumberOnShoppingList());
        assertTrue(customer.addToShoppingList("Fish"));
        assertEquals(3, customer.getNumberOnShoppingList());
        assertTrue(customer.addToShoppingList("Bread"));
        assertEquals(4, customer.getNumberOnShoppingList());
        assertTrue(customer.addToShoppingList("Apple"));
        assertEquals(5, customer.getNumberOnShoppingList());
        assertFalse(customer.addToShoppingList("Banana"));
        assertEquals(5, customer.getNumberOnShoppingList());
    }

    /**
     * Test of toString method, of class Customer.
     */
    @Test
    public void testToString() {
        Customer donald = new Customer("Donald", "Duck");
        donald.setCardNumber(9);
        assertEquals("Customer DUCK Donald with card number 9", donald.toString());
    }
}
