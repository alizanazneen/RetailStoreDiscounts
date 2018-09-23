package retail.test;

import org.junit.Before;
import org.junit.Test;

import retail.core.Cart;
import retail.core.Item;
import retail.core.ItemType;
import retail.core.Product;
import retail.core.User;
import retail.core.UserType;
import retail.discount.DiscountPolicy;
import retail.discount.ThresholdDiscount;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

/*
 * Test Cases for no discount policy
 */
public class TestDiscounts {

    private Item groceryItem;
    private Item otherItem;
    private User employee;
    private User affiliate;
    private User simpleUser;
    private User simpleUserWith2Years;
    private DiscountPolicy discountPolicy;

    @Before
    public void setUp() {
    	employee = new User(UserType.EMPLOYEE, "Pual");
    	affiliate = new User(UserType.AFFILIATE, "Smith");
    	simpleUser = new User(UserType.SIMPLE, "Jojo");
    	simpleUserWith2Years = new User(UserType.SIMPLE, "Paddy", LocalDateTime.of(2014, 7, 19, 6, 40, 45));
        groceryItem = new Product("Pulse", 20, ItemType.GROCERY);
        otherItem = new Product("Mobile", 222, ItemType.OTHER);
        discountPolicy = new ThresholdDiscount();
    }

    @Test
    public void test_employeeWithGrocery() {
        Cart cart = new Cart(employee, discountPolicy);
        cart.add(groceryItem, 4);
        // No discount because of grocery item
        assertEquals(80, cart.total(), 0.01);
        
    }
    
    @Test
    public void test_employeeWithOtherItem() {
        Cart cart = new Cart(employee, discountPolicy);
        cart.add(otherItem, 4);
        /*
         *  30% discount then 5 dollars off of each 100 dollars of total price because of other item
         *  Total 222 * 4 = 888
         *  After 30% discount = 621.6
         *  After 30 dollars off due to price over $600 = 591.6 
         */
        assertEquals(591.6, cart.total(), 0.01);
        
    }
    
    @Test
    public void test_affiliateWithGrocery() {
        Cart cart = new Cart(affiliate, discountPolicy);
        cart.add(groceryItem, 4);
        // No discount because of grocery item
        assertEquals(80, cart.total(), 0.01);
        
    }
    
    @Test
    public void test_affiliateWithOtherItem() {
        Cart cart = new Cart(affiliate, discountPolicy);
        cart.add(otherItem, 4);
        /*
         *  10% discount then 5 dollars off of each 100 dollars of total price because of other item
         *  Total 222 * 4 = 888
         *  After 10% discount = 799.2
         *  After 35 dollars off due to price over $700 = 591.6 
         */
        assertEquals(764.2, cart.total(), 0.01);
        
    }
    
    @Test
    public void test_simpleUserWithGrocery() {
        Cart cart = new Cart(simpleUser, discountPolicy);
        cart.add(groceryItem, 4);
        // No discount because of grocery item
        assertEquals(80, cart.total(), 0.01);
        
    }
    
    @Test
    public void test_simpleUserWithOtherItem() {
        Cart cart = new Cart(simpleUser, discountPolicy);
        cart.add(otherItem, 4);
        /*
         *  Total 222 * 4 = 888
         *  No percentage discount because user is a customer for less than 2 years
         *  After 40 dollars off due to price over $800 = 848 
         */
        assertEquals(848, cart.total(), 0.01);        
    }
    
    @Test
    public void test_simpleUserWith2YearsWithGrocery() {
        Cart cart = new Cart(simpleUserWith2Years, discountPolicy);
        cart.add(groceryItem, 4);
        // No discount because of grocery item
        assertEquals(80, cart.total(), 0.01);
        
    }
    
    @Test
    public void test_simpleUserWith2YearsWithOtherItem() {
        Cart cart = new Cart(simpleUserWith2Years, discountPolicy);
        cart.add(otherItem, 4);
        /*
         *  5% discount then 5 dollars off of each 100 dollars of total price because of other item
         *  Total 222 * 4 = 888
         *  After 5% discount = 843.6
         *  After 40 dollars off due to price over $800 =803.6 
         */
        assertEquals(803.6, cart.total(), 0.01);
        
    }
    
    @Test
    public void test_employeeWithGroceryAndOtherItem() {
        Cart cart = new Cart(employee, discountPolicy);
        cart.add(groceryItem, 4);
        cart.add(otherItem, 4);
        /*
         *  Total (20 * 4) + (222 * 4) = 968
         *  No discount on grocery items = 968 still
         *  After 30% discount on 4 other items totalling 888 = 701.6
         *  After 35 dollars off due to price over $700 =666.6 
         */
        assertEquals(666.6, cart.total(), 0.01);
        
    }
}
