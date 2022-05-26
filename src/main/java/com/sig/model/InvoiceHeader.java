/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.model;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sara
 */
public class InvoiceHeader {
    
    
    private int invoiceNumber;
    private Date invoiceDate;
    private String customerName;
    private Double invoiceTotal;
    private ArrayList<InvoiceLine> invoiceLines;
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
         
    public InvoiceHeader() {
    }

    public InvoiceHeader(int invoiceNumber, Date invoiceDate, String customerName) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
    }
    
    public boolean  parseInvoiceHeader(String[] attr) {
        if( attr != null && attr.length == 3){ // check the format of file
            try {
              this.invoiceNumber = Integer.parseInt(attr[0]);
              
            } catch (NumberFormatException nfe) {
                System.out.println("Error in paring Invoice header number  ");
                return false;
            }
             
             try{
                 if(this.isValidDate(attr[1])){
                    this.invoiceDate = dateFormatter.parse(attr[1]);
                 } else {
                     return false;
                 }
                 
            } catch (ParseException ex) {
               // Logger.getLogger(InvoiceHeader.class.getName()).log(Level.SEVERE, null, ex);
                 System.out.println("Error in paring Invoice header date ");
                  return false;
            }
             this.customerName = attr[2];
             return true;
            }
        
            return false;
    }
    
   public boolean isValidInvoice(){
       if (this.invoiceNumber <= 0   ) {
           return false;
       }else {
           return true;
       }
   } 
    
    public int getInvoiceNumber() {
        return invoiceNumber;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }
    public String getInvoiceDateString() {
       if( this.invoiceDate == null)
           return "";
       else {
          return dateFormatter.format(invoiceDate);
       }
    }
    public void setInvoiceDateString(String strDate) {
        
        try {
            this.invoiceDate= this.dateFormatter.parse(strDate);
        } catch (DateTimeParseException e) {
            Logger.getLogger(InvoiceHeader.class.getName()).log(Level.SEVERE, null, e );
             
        } catch (ParseException ex) {
           Logger.getLogger(InvoiceHeader.class.getName()).log(Level.SEVERE, null, ex);
             
        }
    }
    public boolean isValidDate(String strDate){
         try {
             dateFormatter.setLenient(false);
            this.dateFormatter.parse(strDate);
           
             return true;
        } catch (DateTimeParseException | ParseException e) {
            return false;
        }
           
    }
  public boolean isValidDateString(String s){
       boolean valid ;
      try {
   
            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(s,
                    DateTimeFormatter.ofPattern("dd-mm-yyyy")
                            .withResolverStyle(ResolverStyle.STRICT)
            );

            valid = true;

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            valid = false;
        }
      return valid;
  }
    public String getCustomerName() {
        return customerName;
    }

    public Double getInvoiceTotal() {
        this.invoiceTotal = 0.0; 
       if (this.invoiceLines != null) {
            for(InvoiceLine line: this.invoiceLines){
                 this.invoiceTotal += line.getCount() * line.getItemPrice();
            }
        } 
            return this.invoiceTotal;
         
        
    }

    public ArrayList<InvoiceLine> getInvoiceLines() {
        if (invoiceLines != null )
             return invoiceLines;
        else {
            invoiceLines = new ArrayList<>();
            return invoiceLines;
        }
    }

    public void setInvoiceNumber(int invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setInvoiceTotal(Double invoiceTotal) {
        this.invoiceTotal = invoiceTotal;
    }

    public void setInvoiceLines(ArrayList<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }

    @Override
    public String toString() {
        return invoiceNumber +"," +getInvoiceDateString()+"," +  customerName;
    }
}
