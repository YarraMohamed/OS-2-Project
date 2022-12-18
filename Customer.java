package com.mycompany.os_project;

import java.util.Date;

public class Customer implements Runnable {
    int customerId;
    Date inTime;
    Shop shop;
 
    public Customer(Shop shop) {
    
        this.shop = shop;
    }
 
    public int getCustomerId() {										
        return customerId;
    }
 
    public Date getInTime() {
        return inTime;
    }
 
    public void setcustomerId(int customerId) {
        this.customerId = customerId;
    }
 
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
 
    public void run() {													
    
        askForCall();
    }
    
    private synchronized void askForCall() {							
        shop.add(this);
    }

}
