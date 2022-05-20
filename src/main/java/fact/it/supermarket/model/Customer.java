package fact.it.supermarket.model;

/*
    Name: Manik Setia
    Student number: r0912182
 */

import java.util.ArrayList;

public class Customer extends Person{

    private int cardNumber;
    private int yearOfBirth;
    private ArrayList<String> shoppingList;

    public Customer(String firstName, String surName) {
        super(firstName, surName);
        shoppingList=new ArrayList<>();
        this.cardNumber=-1;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public ArrayList<String> getShoppingList() {
        return shoppingList;
    }

    public boolean addToShoppingList(String productName){
        if(this.shoppingList.size()<5) {
            shoppingList.add(productName);
            return true;
        }
        return false;
    }

    public int getNumberOnShoppingList(){
        return this.shoppingList.size();
    }

    @Override
    public String toString() {
        return "Customer "+super.toString()+" with card number "+this.getCardNumber();
    }
}
