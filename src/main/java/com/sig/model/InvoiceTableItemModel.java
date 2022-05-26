/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author sara
 */
public class InvoiceTableItemModel extends AbstractTableModel {

    

    private ArrayList<InvoiceHeader> invoicesList;
    private String[] columnNames
            = {
                "No.",
                "Date",
                "Customer",
                "Total"
            };

    public InvoiceTableItemModel(ArrayList<InvoiceHeader> invoicesList) {
        this.invoicesList = invoicesList;
    }

    public InvoiceTableItemModel() {
    }

    public ArrayList<InvoiceHeader> getInvoicesList() {
        return invoicesList;
    }

    public void setInvoicesList(ArrayList<InvoiceHeader> invoicesList) {
        this.invoicesList = invoicesList;
    }
    
    @Override
    public int getRowCount() {
        //System.out.println("getRowCount");
        if (this.invoicesList != null) {
            return this.invoicesList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
       // System.out.println("getColumnCount");
        return this.columnNames.length;
    }
    @Override
    public String getColumnName(int column) {
         //System.out.println("getColumnName");
        return this.columnNames[column];  
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
       InvoiceHeader invoice =  this.invoicesList.get(rowIndex);
       switch (columnIndex) {
           case 0:
               return invoice.getInvoiceNumber();
           case 1:
               return invoice.getInvoiceDateString();
           case 2:
               return invoice.getCustomerName();
           case 3:
               return invoice.getInvoiceTotal();
           default: return null;
    }

    }

     
    

}
