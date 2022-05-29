/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.model;

import com.sig.controller.Controller;
import java.awt.print.Book;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
 

/**
 *
 * @author sara
 */
public class FileOperations {
    String headerFilePath ="";
    String lineFilePath= "";
    /* Read file for Invoice Heaser */
    public ArrayList<InvoiceHeader> readFile(String pathStr) {
        
        System.out.println("File Path : "+ pathStr);
         ArrayList<InvoiceHeader> invoicesList = new ArrayList<>();
        if ( !pathStr.endsWith(".csv") ) {
            return null;
        } else {
           
            List<String> lines = Collections.emptyList();
            try
            {
                lines =
                        Files.readAllLines(Paths.get(pathStr), StandardCharsets.UTF_8);
                
                Iterator<String> itr = lines.iterator();
                while (itr.hasNext())  {
                    String[] attributes = itr.next().split(",");
                    InvoiceHeader invoice = new InvoiceHeader();
                    if (invoice.parseInvoiceHeader(attributes)){
                        invoicesList.add(invoice);
                        System.out.println( invoice.toString() );
                    }else {
                        // There's an issue in parsing this line
                        return null;
                        
                    }
                }
                
                
                
            }catch (IOException e)
            {
                // do something
                e.printStackTrace();
                System.out.println("File opreations Error "+ e.getLocalizedMessage());
                return null;
            }
            
        }
        
      return invoicesList;

    }

    public void witeFile(ArrayList<InvoiceHeader> invoicesList,String invoicesPathStr, String linesPathStr ){
      
         
        String headers = "";
        String lines = "";
        for (InvoiceHeader invoice : invoicesList) {
            String invCSV = invoice.toString();
            headers += invCSV;
            headers += "\n";

            for (InvoiceLine line : invoice.getInvoiceLines()) {
                String lineCSV = line.toString();
                lines += lineCSV;
                lines += "\n";
            }
        }
        
        try {
             
                File headerFile = Paths.get(invoicesPathStr).toFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                
                    File lineFile = Paths.get(linesPathStr).toFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
              
            
 
        } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {

        }
    }
        
 /*
    public void witeFile(ArrayList<InvoiceHeader> invoicesList,String invoicesPathStr, String linesPathStr ){
      
        String invoices = "";
        String lines = "";
        for (InvoiceHeader invoice : invoicesList) {
            //String invoicesCSV = invoice.toString();
            invoices += invoice.toString();
            invoices += "\n";

            for ( InvoiceLine line : invoice.getInvoiceLines()) {
                String lineCSV = line.toString();
                lines += lineCSV;
                lines += "\n";
            }
        }
        
        try {
            File invoicesFile = Paths.get(invoicesPathStr).toFile();
            FileWriter invoicesFileWriter = new FileWriter(invoicesFile) ;
                invoicesFileWriter.write(invoices);
                invoicesFileWriter.flush();
                invoicesFileWriter.close();
                
                
                    File lineFile =  Paths.get(linesPathStr).toFile();
                    FileWriter lineFileWriter = new FileWriter(lineFile);
                    lineFileWriter.write(lines);
                    lineFileWriter.flush();
                    lineFileWriter.close();
                } catch (IOException ex) {
            Logger.getLogger(FileOperations.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {

        }
    }
        
  */
    





    public void writeHeaderFile(ArrayList<InvoiceHeader> list) {

    }

    /* Read file for Invoice Heaser */
    public ArrayList<InvoiceLine> readLinesFile(String pathStr) {
        
        System.out.println("File Path : "+ pathStr);
        ArrayList<InvoiceLine> linesList = new ArrayList<>();
         if (!pathStr.endsWith(".csv")){
            return null;
        } else {
        List<String> lines = Collections.emptyList();
        try
        {
          lines =
           Files.readAllLines(Paths.get(pathStr), StandardCharsets.UTF_8);
          
          Iterator<String> itr = lines.iterator();
           while (itr.hasNext())  {
                String[] attributes = itr.next().split(",");
                 InvoiceLine line = new InvoiceLine();
                  if (line.parseInvoiceLine(attributes)){
                      linesList.add(line);
                      System.out.println(line.toString());;
                    }else {
                      // There's an issue in parsing this line
                      return null;
                  }
        }
  
         

        }catch (IOException e)
        {
         // do something
          e.printStackTrace();
            System.out.println("File opreations Error "+ e.getLocalizedMessage());
             return null;
        }
         }
      return linesList;

    }


    public void writeLinesFile(ArrayList<InvoiceHeader> list) {

    }

    public void testFunction(String headerPathStr, String linePathstr){
       Controller con =  new Controller();
        ArrayList<InvoiceHeader> invoicesList = readFile(headerPathStr);
        ArrayList<InvoiceLine> lineList = readLinesFile(linePathstr);
        if (invoicesList == null ||lineList == null ) {
            System.out.println("There's a format error in one of the files");
            return;
        }
        /* Setup iinvoices list with its line list */
        for (InvoiceLine line : lineList) {
            for (InvoiceHeader invoice : invoicesList) {
                if (line.getInvoiceNumber() == invoice.getInvoiceNumber()) {
                    invoice.getInvoiceLines().add(line);
                    break;
                }
            }
        }
        
        /*  after setup the invoices list loop on each invoice and print it with its lines */ 
        for(InvoiceHeader invoice : invoicesList ){
            System.out.println(invoice.getInvoiceNumber()+"\n{");
            System.out.println(invoice.getInvoiceDateString()+","+invoice.getCustomerName());
            for(InvoiceLine line : invoice.getInvoiceLines()){
                System.out.println(line.getItemName()+","+line.getCount()+","+line.getItemPrice());
            }
            System.out.println("}");
    }
    }
    public static void main(String[] args) {
        String headerFilePath= "/Users/sara/Desktop/Udacity/webinar projecect/Sales Invoice Generator/InvoiceHeader.csv";
        String lineFilePath= "/Users/sara/Desktop/Udacity/webinar projecect/Sales Invoice Generator/InvoiceLine.csv";
        
      FileOperations test = new FileOperations();
      test.testFunction(headerFilePath, lineFilePath);
        
    }

}
