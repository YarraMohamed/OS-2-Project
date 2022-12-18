/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.os_project;


public class Employee implements Runnable {
    Shop shop;
    int employeeId;
 
    public Employee(Shop shop, int employeeId) {
    
        this.shop = shop;
        this.employeeId = employeeId;
    }
    
    public void run() {
    
        while(true) {
        
            shop.answerCall(employeeId);
        }
    }

}
