/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.model;

/**
 *
 * @author sara
 */
public class InvoiceLine {

   
    
    private int invoiceNumber;        
    private String itemName;
    private Double itemPrice;
    private int count;
    private Double total;
    
    public InvoiceLine() {
    }
    
    public InvoiceLine(int invoiceNumber, String itemName, Double itemPrice, int count) {
        this.invoiceNumber = invoiceNumber;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.count = count;
    }

    
    
    // ----------- Getter -------
    
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public int getCount() {
        return count;
    }
    public Double getTotal() {
        return itemPrice * count;
    }
    // ----------- Setter -------
    
    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    
    /*  Parsing line from file  */
      public boolean  parseInvoiceLine(String[] attr) {
        if( attr != null && attr.length == 4){ // check the format of file
            try {
              this.invoiceNumber = Integer.parseInt(attr[0]);
              this.itemName = attr[1];
              this.itemPrice = Double.parseDouble(attr[2]);
              this.count = Integer.parseInt(attr[3]);
              return true;
            } catch (NumberFormatException nfe) {
                System.out.println("Error in paring Invoice Line number  ");
                return false;
            }
        }
           return false;
    }

      
    /* Check if data is valid */
      
       public boolean  isValidInvoiceLine(){
          if ( (this.invoiceNumber < 0 )  || (this.itemPrice < 0) || (this.count < 0)){
          return false;
        }else {
              return true;
              }
      }
    @Override
    public String toString() {
        return invoiceNumber+","+itemName + ","+ itemPrice+ ","+ count;
    }
    
  
}
