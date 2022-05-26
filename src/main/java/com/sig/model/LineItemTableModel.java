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
public class LineItemTableModel extends AbstractTableModel {

    
    private InvoiceHeader invoice;
    private ArrayList<InvoiceLine> lines;
    private String[] columnNames
            = {
                "No.",
                "Item Name",
                "Item Price",
                "Count",
                "Total"
            };

    public LineItemTableModel (ArrayList<InvoiceLine> lines) {
        this.lines = lines;
    }

    public LineItemTableModel() {
    }

    public ArrayList<InvoiceLine> getInvoiceLinesList() {
        return lines;
    }

    public void setLinesList(ArrayList<InvoiceLine> lines) {
        this.lines =  lines;
    }

    public InvoiceHeader getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceHeader invoice) {
        this.invoice = invoice;
    }
    
    @Override
    public int getRowCount() {
        //System.out.println("getRowCount");
        if (this.lines != null) {
            return this.lines.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        //System.out.println("getColumnCount");
        return this.columnNames.length;
    }
    @Override
    public String getColumnName(int column) {
        // System.out.println("getColumnName");
        return this.columnNames[column];  
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       
       InvoiceLine line =  this.lines.get(rowIndex);
       switch (columnIndex) {
           case 0:
               return line.getInvoiceNumber();
           case 1:
               return line.getItemName();
           case 2:
               return line.getItemPrice();
           case 3:
               return line.getCount();
           case 4:
               return line.getTotal();
        default: return null;
    }

    }

    

    
}
