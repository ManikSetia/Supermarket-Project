package fact.it.supermarket;

import fact.it.supermarket.model.Department;
import fact.it.supermarket.model.Staff;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DepartmentTests {
    /**
     * Test of constructor en getters
     */
    @Test
    public void testConstructorEnGetters() {
        Department department = new Department("Fruit");
        assertEquals("Fruit", department.getName());
        Department department1 = new Department();
        assertEquals(null,department1.getName());
        Department department2 = new Department("Meat");
        assertEquals("Meat", department2.getName());
    }

    /**
     * Test of setName method, of class Department.
     */
    @Test
    public void testSetNaam() {
        Department department = new Department();
        department.setName("Fruit");
        assertEquals("Fruit", department.getName());
    }


    /**
     * Test of setPhoto method, of class Department.
     */
    @Test
    public void testSetPhoto() {
        Department department = new Department("Fruit");
        department.setPhoto("testphoto.jpg");
        assertEquals("testphoto.jpg", department.getPhoto());
    }

    /**
     * Test of setResponsible method, of class Department.
     */
    @Test
    public void testResponsible() {
        Department department = new Department("Fruit");
        Staff Mickey = new Staff("Mickey", "Mouse");
        department.setResponsible(Mickey);
        assertEquals(Mickey, department.getResponsible());
    }
}
