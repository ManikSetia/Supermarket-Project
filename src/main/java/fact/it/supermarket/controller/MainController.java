package fact.it.supermarket.controller;

/*
    Name: Manik Setia
    Student number: r0912182
 */

import fact.it.supermarket.model.Customer;
import fact.it.supermarket.model.Department;
import fact.it.supermarket.model.Staff;
import fact.it.supermarket.model.Supermarket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Controller
public class MainController {

    private ArrayList<Staff> staffArrayList;
    private ArrayList<Customer> customerArrayList;
    private ArrayList<Supermarket> supermarketArrayList;

    @PostConstruct
    private void initialFillingOfLists() {
        staffArrayList = fillStaffMembers();
        customerArrayList = fillCustomers();
        supermarketArrayList = fillSupermarkets();
    }

    @RequestMapping("/")
    public String showHomePage() {
        return "index";
    }

    @RequestMapping("/customer/new")
    public String newCustomer(Model model) {
        model.addAttribute("supermarketArrayList", supermarketArrayList);
        return "1_newcustomer";
    }

    @RequestMapping("/customer/show")
    public String showCustomer(HttpServletRequest request, Model model) {

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        //If user specifies full date of birth mistakenly instead of just year.
        if (request.getParameter("yearOfBirth").length() > 4) {
            model.addAttribute("errormessage", "You've to enter only YEAR of your birth!");
            return "error";
        }

        int yearOfBirth = Integer.parseInt(request.getParameter("yearOfBirth"));

        //Registration of customer at the supermarket
        Customer customer1 = new Customer(firstName, lastName);
        customer1.setYearOfBirth(yearOfBirth);
        int superMarketIndex = Integer.parseInt(request.getParameter("supermarketIndex"));

        Supermarket supermarket1 = supermarketArrayList.get(superMarketIndex);
        supermarket1.registerCustomer(customer1);

        //Adding new customer to the ArrayList of customers
        customerArrayList.add(customer1);

        model.addAttribute("customer1", customer1);
        model.addAttribute("firstName", firstName);
        return "2_showcustomer";
    }

    @RequestMapping("/staffmember/new")
    public String newStaffMember(Model model) {
        Staff staffMember = new Staff("", "");
        model.addAttribute("staff", staffMember);
        return "3_newstaffmember";
    }

    @RequestMapping("/staffmember/show")
    public String showStaffMember(HttpServletRequest request, Model model) {
        String firstName = request.getParameter("firstName");
        String surName = request.getParameter("surName");
        String employedSince = request.getParameter("employedSince");
        boolean student = Boolean.parseBoolean(request.getParameter("student"));

        Staff staffMember1 = new Staff(firstName, surName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        staffMember1.setStartDate(LocalDate.parse(employedSince, formatter));
        staffMember1.setStudent(student);

        //Saving the newly created staff member into the array list
        staffArrayList.add(staffMember1);

        model.addAttribute("staffMember1", staffMember1);
        return "4_showstaffmember";
    }

    @RequestMapping("/staffmembers/show")
    public String showStaffMembers(Model model) {
        model.addAttribute("staffmembers", staffArrayList);
        return "5_showstaffmembers";
    }

    @RequestMapping("/customers/show")
    public String showCustomers(Model model) {
        model.addAttribute("customers", customerArrayList);
        return "6_showcustomers";
    }

    @RequestMapping("/supermarket/new")
    public String newSupermarket() {
        return "7_newsupermarket";
    }

    @RequestMapping("/supermarkets/show")
    public String showSupermarkets(HttpServletRequest request, Model model) {
        String supermarketName = request.getParameter("supermarketName");

        //When the user enters a name for supermarket, only then it'll be added to the list of supermarkets.
        if (supermarketName != null) {
            Supermarket newSupermarket = new Supermarket(supermarketName);
            supermarketArrayList.add(newSupermarket);
        }

        model.addAttribute("supermarkets", supermarketArrayList);
        return "8_showsupermarkets";
    }

    @RequestMapping("/department/new")
    public String newDepartment(Model model) {
        Department department = new Department("");
        model.addAttribute("department", department);
        model.addAttribute("supermarkets", supermarketArrayList);
        model.addAttribute("staffmembers", staffArrayList);

        return "9_newdepartment";
    }

    @RequestMapping("/departments/show")
    public String showDepartments(HttpServletRequest request, Model model) {
        String departmentName = request.getParameter("departmentName");

        Department newDepartment = new Department(departmentName);

        int supermarketIndex = Integer.parseInt(request.getParameter("supermarketIndex"));

        if (departmentName == "" || departmentName == null) {
            //when we click on any of supermarkets
            Supermarket newSupermarket = supermarketArrayList.get(supermarketIndex);
            model.addAttribute("supermarket", newSupermarket);

            return "10_showdepartments";
        }

        int staffMemberIndex = Integer.parseInt(request.getParameter("staffMemberIndex"));

        if (supermarketIndex == -1) {
            //that means user has not selected anything from the dropdown of supermarket
            model.addAttribute("errormessage", "You didn't choose a supermarket!");
            return "error";
        }
        if (staffMemberIndex == -1) {
            //that means user has not selected anything from the dropdown of staff members
            model.addAttribute("errormessage", "You didn't choose a staff member!");
            return "error";
        }

        String photo = request.getParameter("photo");
        newDepartment.setPhoto(photo);
        boolean refrigerated = Boolean.parseBoolean(request.getParameter("refrigerated"));
        newDepartment.setRefrigerated(refrigerated);

        Staff newStaffMember = staffArrayList.get(staffMemberIndex);
        newDepartment.setResponsible(newStaffMember);

        Supermarket newSupermarket = supermarketArrayList.get(supermarketIndex);
        newSupermarket.addDepartment(newDepartment);

        model.addAttribute("supermarket", newSupermarket);

        return "10_showdepartments";
    }

    @RequestMapping("/department/show")
    public String showDepartment(HttpServletRequest request, Model model) {
        String keyword = request.getParameter("search");

        for (Supermarket supermarket : supermarketArrayList) {
            Department departmentName = supermarket.searchDepartmentByName(keyword);

            if (departmentName != null) {
                model.addAttribute("department", departmentName);
                return "11_showdepartment";
            }
        }

        //if we reach here, that means the searched department is not present in our supermarkets
        model.addAttribute("errormessage", "There is no department with the name '" + keyword + "'");
        return "error";
    }


    private ArrayList<Staff> fillStaffMembers() {
        ArrayList<Staff> staffMembers = new ArrayList<>();

        Staff staff1 = new Staff("Johan", "Bertels");
        staff1.setStartDate(LocalDate.of(2002, 5, 1));
        Staff staff2 = new Staff("An", "Van Herck");
        staff2.setStartDate(LocalDate.of(2019, 3, 15));
        staff2.setStudent(true);
        Staff staff3 = new Staff("Bruno", "Coenen");
        staff3.setStartDate(LocalDate.of(1995, 1, 1));
        Staff staff4 = new Staff("Wout", "Dayaert");
        staff4.setStartDate(LocalDate.of(2002, 12, 15));
        Staff staff5 = new Staff("Louis", "Petit");
        staff5.setStartDate(LocalDate.of(2020, 8, 1));
        staff5.setStudent(true);
        Staff staff6 = new Staff("Jean", "Pinot");
        staff6.setStartDate(LocalDate.of(1999, 4, 1));
        Staff staff7 = new Staff("Ahmad", "Bezeri");
        staff7.setStartDate(LocalDate.of(2009, 5, 1));
        Staff staff8 = new Staff("Hans", "Volzky");
        staff8.setStartDate(LocalDate.of(2015, 6, 10));
        staff8.setStudent(true);
        Staff staff9 = new Staff("Joachim", "Henau");
        staff9.setStartDate(LocalDate.of(2007, 9, 18));
        staffMembers.add(staff1);
        staffMembers.add(staff2);
        staffMembers.add(staff3);
        staffMembers.add(staff4);
        staffMembers.add(staff5);
        staffMembers.add(staff6);
        staffMembers.add(staff7);
        staffMembers.add(staff8);
        staffMembers.add(staff9);
        return staffMembers;
    }

    private ArrayList<Customer> fillCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();

        Customer customer1 = new Customer("Dominik", "Mioens");
        customer1.setYearOfBirth(2001);
        Customer customer2 = new Customer("Zion", "Noops");
        customer2.setYearOfBirth(1996);
        Customer customer3 = new Customer("Maria", "Bonetta");
        customer3.setYearOfBirth(1998);
        Customer customer4 = new Customer("Manik", "Setia");
        customer4.setYearOfBirth(1999);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customer4);
        customers.get(0).addToShoppingList("Butter");
        customers.get(0).addToShoppingList("Bread");
        customers.get(1).addToShoppingList("Apple");
        customers.get(1).addToShoppingList("Banana");
        customers.get(1).addToShoppingList("Grapes");
        customers.get(1).addToShoppingList("Oranges");
        customers.get(2).addToShoppingList("Fish");
        customers.get(3).addToShoppingList("Milk");
        customers.get(3).addToShoppingList("Ghee");
        customers.get(3).addToShoppingList("Pulses");
        customers.get(3).addToShoppingList("Rice");


        return customers;
    }

    private ArrayList<Supermarket> fillSupermarkets() {
        ArrayList<Supermarket> supermarkets = new ArrayList<>();

        Supermarket supermarket1 = new Supermarket("Delhaize");
        Supermarket supermarket2 = new Supermarket("Colruyt");
        Supermarket supermarket3 = new Supermarket("Albert Heyn");
        Department department1 = new Department("Fruit");
        Department department2 = new Department("Bread");
        Department department3 = new Department("Vegetables");
        department1.setPhoto("/img/fruit.jpg");
        department2.setPhoto("/img/bread.jpg");
        department3.setPhoto("/img/vegetables.jpg");
        department1.setResponsible(staffArrayList.get(0));
        department2.setResponsible(staffArrayList.get(1));
        department3.setResponsible(staffArrayList.get(2));
        supermarket1.addDepartment(department1);
        supermarket1.addDepartment(department2);
        supermarket1.addDepartment(department3);
        supermarket2.addDepartment(department1);
        supermarket2.addDepartment(department2);
        supermarket3.addDepartment(department1);
        supermarket3.addDepartment(department3);
        supermarkets.add(supermarket1);
        supermarkets.add(supermarket2);
        supermarkets.add(supermarket3);
        return supermarkets;
    }


}
