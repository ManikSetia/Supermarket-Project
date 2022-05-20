package fact.it.supermarket;

import fact.it.supermarket.model.Customer;
import fact.it.supermarket.model.Department;
import fact.it.supermarket.model.Supermarket;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SupermarketTests {
    /**
     * Test of constructor en getters
     */
    @Test
    public void testConstructorEnGetters() {
        Supermarket supermarket = new Supermarket("Colruyt");
        assertEquals("Colruyt", supermarket.getName());
    }

    /**
     * Test of setName method, of class Supermarket.
     */
    @Test
    public void testSetName() {
        Supermarket supermarket = new Supermarket("Colruyt");
        supermarket.setName("Delhaize");
        assertEquals("Delhaize", supermarket.getName());
    }

    /**
     * check constructor of Supermarket + adding of department check if you can
     * find an department by name
     */
    @Test
    public void checkAddDepartmentsAndGetNumber() {
        Supermarket supermarket1 = new Supermarket("Colruyt");
        assertEquals(0, supermarket1.getNumberOfDepartments());
        Department department = new Department("Fruit");
        supermarket1.addDepartment(department);
        assertEquals(1, supermarket1.getNumberOfDepartments());
        Department department1 = new Department("Vegetables");
        supermarket1.addDepartment(department1);
        assertEquals(2, supermarket1.getNumberOfDepartments());
        Department[] array = new Department[2];
        array[0] = department;
        array[1] = department1;
        assertArrayEquals(array, supermarket1.getDepartmentList().toArray());
    }

    /**
     * Test of search by Name
     */
    @Test
    public void testSearchDepartmentbyName() {
        Supermarket supermarket2 = new Supermarket("Delhaize");
        assertEquals(0, supermarket2.getNumberOfDepartments());
        Department vegetables = new Department("Vegetables");
        supermarket2.addDepartment(vegetables);
        assertEquals(1, supermarket2.getNumberOfDepartments());
        Department fruit = new Department("Fruit");
        supermarket2.addDepartment(fruit);
        assertEquals(2, supermarket2.getNumberOfDepartments());
        assertNotNull(supermarket2.searchDepartmentByName("Fruit"));
        assertEquals(fruit.getName(), supermarket2.searchDepartmentByName("Fruit").getName());
        assertNull(supermarket2.searchDepartmentByName("blabla"));
    }

    /**
     * before a Customer can make his shopping, he has to register to the
     * Supermarket as result of this registration he will receive a card number
     */
    @Test
    public void testRegisterCustomer() {
        Customer customer = new Customer("Donald", "Duck");
        Supermarket colruyt = new Supermarket("Colruyt");
        colruyt.registerCustomer(customer);
        assertEquals(1, customer.getCardNumber());
        Customer customer1 = new Customer("Mickey", "Mouse");
        colruyt.registerCustomer(customer1);
        assertEquals(2, customer1.getCardNumber());
        assertEquals(2, colruyt.getNumberCustomers());
    }
}
