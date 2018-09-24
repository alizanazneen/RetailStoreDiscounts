package retail.main;


import retail.core.Cart;
import retail.core.Item;
import retail.core.ItemType;
import retail.core.Product;
import retail.core.User;
import retail.core.UserType;
import retail.discount.DiscountPolicy;
import retail.discount.ThresholdDiscount;

class Main {
    public static void main (String[] args)
    {   
    	User employee = new User(UserType.EMPLOYEE, "John");
        Item groceryItem = new Product("Pulse", 30, ItemType.GROCERY);
        Item otherItem = new Product("Mobile", 200, ItemType.OTHER);
        DiscountPolicy discountPolicy = new ThresholdDiscount();
        
        Cart cart = new Cart(employee, discountPolicy);
        cart.add(groceryItem, 3);
        cart.add(otherItem, 3);
        
        System.out.println(cart.total());    
        //o/p -  485.0
    }
}