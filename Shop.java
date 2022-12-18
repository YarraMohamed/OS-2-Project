
package com.mycompany.os_project;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public  class Shop {
    private final AtomicInteger totalCalls = new AtomicInteger(0);
	private final AtomicInteger customersLost = new AtomicInteger(0);
	int nchair, noOfEmployee, availableEmployees;
    List<Customer> listCustomer;
    
    Random r = new Random();	 
    
    public Shop(int noOfEmployee, int noOfChairs){
    
        this.nchair = noOfChairs;														
        listCustomer = new LinkedList<Customer>();						
        this.noOfEmployee = noOfEmployee;									
        availableEmployees = noOfEmployee;
    }
 
    public  AtomicInteger getTotalCalls() {
    	
    	totalCalls.get();
    	return totalCalls;
    }
    
    public AtomicInteger getCustomerLost() {
    	
    	customersLost.get();
    	return customersLost;
    }
    
    public void answerCall(int employeeId)
    {
        Customer customer;
        synchronized (listCustomer) {									
        															 	
            while(listCustomer.size()==0) {
            
                System.out.println("\nEmployee "+employeeId+" is free and can accept calls ");
                SleepingEmployee.txt += "\nEmployee "+employeeId+" s free and can accept calls";
                		             
                try {
                
                    listCustomer.wait();								
                }
                catch(InterruptedException iex) {
                
                    iex.printStackTrace();
                }
            }
            
            customer = (Customer)((LinkedList<?>)listCustomer).poll();	
            
            System.out.println("Customer "+customer.getCustomerId()+
            		" needs to make call and " +employeeId +" can answer");
            SleepingEmployee.txt += "\nCustomer "+customer.getCustomerId() + " needs to make call and " + employeeId + "can answer";
            
        }
        
        int millisDelay=0;
                
        try {
        	
        	availableEmployees--; 										
        																
            System.out.println("Employee "+employeeId+" answer the call of "+
            		customer.getCustomerId());
            SleepingEmployee.txt += "\nEmployee "+employeeId+" answer the call of "+
            		customer.getCustomerId();
            double val = r.nextGaussian() * 2000 + 4000;				
        	millisDelay = Math.abs((int) Math.round(val));				
        	Thread.sleep(millisDelay);
        	
        	System.out.println("\nCompleted serving "+
        			customer.getCustomerId()+" by employee " + 
        			employeeId +" in "+millisDelay+ " milliseconds.");
            SleepingEmployee.txt += "\nCompleted serving "+
        			customer.getCustomerId()+" by employee " + 
        			employeeId +" in "+millisDelay+ " milliseconds." ;
        	totalCalls.incrementAndGet();
            															
            if(listCustomer.size()>0) {									
            	System.out.println("Employee "+employeeId+					
            			" can answer the next customer from the waiting calls");					
                SleepingEmployee.txt += "\nEmployee "+employeeId+					
            			" can answer the next customer from the waiting calls "	;			
            			
            }
            
            availableEmployees++;											
        }
        catch(InterruptedException iex) {
        
            iex.printStackTrace();
        }
        
    }
 
    public void add(Customer customer) {
    
        System.out.println("\nCustomer "+customer.getCustomerId()+
        		" wants to make a call at "
        		+customer.getInTime());
        SleepingEmployee.txt += "\nCustomer "+customer.getCustomerId()+
        		" wants to make a call at "
        		+customer.getInTime() ;
 
        synchronized (listCustomer) {
        
            if(listCustomer.size() == nchair) {							
            
                System.out.println("\nNo employees are available for sometime "
                		+ "for customer "+customer.getCustomerId()+
                		" so customer ends the call");
                SleepingEmployee.txt += "\nNo employees are available for sometime "
                		+ "for customer "+customer.getCustomerId()+
                		" so customer ends the call" ;
              customersLost.incrementAndGet();
                
                return;
            }
            else if (availableEmployees > 0) {							
            															
            	((LinkedList<Customer>)listCustomer).offer(customer);
				listCustomer.notify();
			}
            else {														
            															
            	((LinkedList<Customer>)listCustomer).offer(customer);
                
            	System.out.println("All employee(s) are busy so "+
            			customer.getCustomerId()+
                		" takes a chair in the waiting calls");
                 SleepingEmployee.txt += "All employee(s) are busy so "+
            			customer.getCustomerId()+
                		" takes a chair in the waiting calls" ;
                if(listCustomer.size()==1)
                    listCustomer.notify();
            }
        }
    }
}
