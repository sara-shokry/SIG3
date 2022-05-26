/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sig.controller;

import com.sig.model.FileOperations;
import com.sig.model.InvoiceHeader;
import com.sig.model.InvoiceLine;
import com.sig.model.InvoiceTableItemModel;
import com.sig.model.LineItemTableModel;
import com.sig.view.CreateInvoiceDialog;
import com.sig.view.CreateInvoiceLineDialog;
import com.sig.view.InvoicesFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author sara
 */
public class Controller implements ActionListener, ListSelectionListener {

    public FileOperations fileOpreations = new FileOperations();
    public ArrayList<InvoiceHeader> invoiceHeaderList;
    public ArrayList<InvoiceLine> linesList;
    private InvoiceTableItemModel invoicesTableModel;
    private LineItemTableModel linesTableModel;
    private InvoiceHeader selectedInvoice;
    private int invoiceSelectedIndx = -1;

    private CreateInvoiceDialog invoiceDialog;
    private CreateInvoiceLineDialog invoiceLineDialog;
    
    
    public void setSelectedInvoice(InvoiceHeader selectedInvoice) {
        this.selectedInvoice = selectedInvoice;
    }

    public InvoiceHeader getSelectedInvoice() {
        return selectedInvoice;
    }

    public void setInvoiceHeaderList(ArrayList<InvoiceHeader> invoiceHeaderList) {
        this.invoiceHeaderList = invoiceHeaderList;
    }

    public void setLinesList(ArrayList<InvoiceLine> linesList) {
        this.linesList = linesList;
    }

    public void setInvoicesTableModel(InvoiceTableItemModel invoicesTableModel) {
        this.invoicesTableModel = invoicesTableModel;
        this.invoicesTableModel.setInvoicesList(this.invoiceHeaderList);
    }

    public LineItemTableModel getLinesTableModel() {
        return linesTableModel;
    }

    public void setLinesTableModel(LineItemTableModel linesTableModel) {
        this.linesTableModel = linesTableModel;
    }

    public ArrayList<InvoiceHeader> getInvoiceHeaderList() {
        return invoiceHeaderList;
    }

    public ArrayList<InvoiceLine> getLinesList() {
        return linesList;
    }

    public InvoiceTableItemModel getInvoicesTableModel() {
        return invoicesTableModel;
    }

    public Controller(InvoicesFrame frame) {

        this.frame = frame;
        this.invoicesTableModel = new InvoiceTableItemModel();
        this.invoicesTableModel.setInvoicesList(this.invoiceHeaderList);
        this.linesTableModel = new LineItemTableModel();
        this.linesTableModel.setLinesList(linesList);
    }

    public Controller() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action : " + e.getActionCommand());

        switch (e.getActionCommand()) {
            case "Load file":
                loadFile();
                break;
            case "Save file":
                saveFile();
                break;
            case "Create New Invoice":
                createNewInvoiceDialog();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Save Invoice changes":
                saveInvoice();
                break;
            case "delete Invoice Line":
                deleteInvoiceLine();
                break;
            case "createNewInvoiceDialog":
                createNewInvoiceData();
                break;
            case "cancelNewInvoice":
                cancelNewInvoice();
                break;
            case "createNewInvoiceLineDialog":
                createNewLine();
                break;
            case "cancelNewLineInvoice":
                cancelNewLineInvoice();
                break;
            case "createNewInvoiceLineData":
                createNewLineData();
                break;
                
            default:
                throw new AssertionError();
        }
    }

    /*
    Menu Action 
    Load/Save files in cvs format to read/write invoices and lines 
     */
    public void loadFile() {

        showMessageDialog(this.frame, "Please select the first file for invoice header ");
        JFileChooser fileChooser = new JFileChooser();

        int returnValue = fileChooser.showOpenDialog(this.frame);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            this.invoiceHeaderList = this.fileOpreations.readFile(selectedFile.getPath());
            if (this.invoiceHeaderList == null){
                showMessageDialog(this.frame, "There's format error in this file ");
                return;
            }
            showMessageDialog(this.frame, "Please select the second file for invoice line ");
            returnValue = fileChooser.showOpenDialog(this.frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                this.linesList = this.fileOpreations.readLinesFile(selectedFile.getPath());
                 if (this.linesList == null){
                    showMessageDialog(this.frame, "There's format error in this file ");
                    return;
                }
                setupHeaderList(invoiceHeaderList, linesList);

            }

        }
    }

    public void saveFile() {
try {
    showMessageDialog(this.frame, "Please select the first file for invoice header ");
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                String invoiceFilePath = "";
                String lineFilePath ="";
               invoiceFilePath = fileChooser.getSelectedFile().getPath();
                  showMessageDialog(this.frame, "Please select the second file for lines ");
                result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                     lineFilePath = fileChooser.getSelectedFile().getPath();
                     this.fileOpreations.witeFile(invoiceHeaderList,invoiceFilePath, lineFilePath);
                }
            }
        } catch (Exception ex) {
showMessageDialog(this.frame, "There's an error in savign the files ");
    
        }
    }

    /*
    Buttons Actions  
     */
     

    public void deleteInvoice() {
        int selectedInvoiceIndex = frame.getInvoicesTable().getSelectedRow();
        if (selectedInvoiceIndex == -1) {
            return;
        } else {
            InvoiceHeader selectedInvoice = this.invoiceHeaderList.remove(selectedInvoiceIndex);
            this.invoiceSelectedIndx = --selectedInvoiceIndex;
            selectedInvoice.getInvoiceLines().clear();
            if (this.invoiceSelectedIndx == -1 && !this.invoiceHeaderList.isEmpty()) {
                this.invoiceSelectedIndx++;
            }
            updateUIByInvoice(this.invoiceSelectedIndx);
            this.invoicesTableModel.fireTableDataChanged();
        }
    }

    public void saveInvoice() {
        if (this.selectedInvoice == null) {
            return;
        }
        String newDate = frame.getInvoiceDateField().getText();
        String newCustomerName = frame.getCustomerNameField().getText();
        if (this.selectedInvoice.isValidDate(newDate)) {
            System.out.println("Valid "+ newDate);
            this.selectedInvoice.setInvoiceDateString(newDate);
            this.selectedInvoice.setCustomerName(newCustomerName);
            this.invoicesTableModel.fireTableDataChanged();
            createNewLine();
         }else {
            System.out.println("Please enter valid invoice date ( ex. 01-12-2022 )");
              showMessageDialog(this.invoiceDialog, "Please enter valid invoice date ( ex. 01-12-2022 ) ");
           
        }
       

    }

    public void deleteInvoiceLine() {
        int n = JOptionPane.showConfirmDialog(
                frame, "Are you sure you want to delete this line ?",
                "Confirmation ",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            int selectedLineIndex = frame.getLinesTable().getSelectedRow();
            System.out.println("selectedLineIndex " + selectedLineIndex);
            if (selectedLineIndex == -1 || invoiceSelectedIndx == -1) {
                return;
            }
            selectedInvoice = invoiceHeaderList.get(invoiceSelectedIndx);
            selectedInvoice.getInvoiceLines().remove(selectedLineIndex);
            updateUIByInvoice(this.invoiceSelectedIndx);
            this.invoicesTableModel.fireTableDataChanged();
        } else if (n == JOptionPane.NO_OPTION) {

        } else {

        }

    }

    public ArrayList<InvoiceHeader> setupHeaderList(ArrayList<InvoiceHeader> invoicesList, ArrayList<InvoiceLine> linesList) {

        for (InvoiceLine line : linesList) {
            for (InvoiceHeader invoice : invoicesList) {
                if (line.getInvoiceNumber() == invoice.getInvoiceNumber()) {
                    invoice.getInvoiceLines().add(line);
                    break;
                }
            }
        }

        this.invoiceHeaderList = invoicesList;
        this.invoicesTableModel.setInvoicesList(this.invoiceHeaderList);
        this.frame.setInvoicesList(this.invoiceHeaderList);
        this.frame.getInvoicesTable().setModel(invoicesTableModel);
        this.invoicesTableModel.fireTableDataChanged();

        return invoicesList;
    }

    private InvoicesFrame frame;

    public void setFrame(InvoicesFrame frame) {
        this.frame = frame;
    }

    public InvoicesFrame getFrame() {
        return frame;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Value changed  ");
        invoiceSelectedIndx = frame.getInvoicesTable().getSelectedRow();
        updateUIByInvoice(invoiceSelectedIndx);

    }

    public void updateUIByInvoice(int selectedInvoiceIndex) {
        if (selectedInvoiceIndex == -1) {
            return;
        }
        System.out.println("selectedInvoiceIndex " + selectedInvoiceIndex);
        this.selectedInvoice = this.invoiceHeaderList.get(selectedInvoiceIndex);

        this.linesTableModel = new LineItemTableModel();
        this.linesTableModel.setInvoice(selectedInvoice);
        this.linesTableModel.setLinesList(selectedInvoice.getInvoiceLines());

        this.frame.updateInvoiceUI(selectedInvoice);
        this.frame.getLinesTable().setModel(linesTableModel);
        this.linesTableModel.fireTableDataChanged();
    }

    
    /* Create New invoice UI and Actions  */
    private void createNewInvoiceDialog() {
        this.invoiceDialog =  new CreateInvoiceDialog(this.frame, true);
         invoiceDialog.getCancelBtn().addActionListener(this);
         invoiceDialog.getCreateBtn().addActionListener(this);
        this.invoiceDialog.setVisible(true);
     }

    private void createNewInvoiceData() {
        if(invoiceDialog != null){
            InvoiceHeader newInvoice = new InvoiceHeader();
            try {
                 int invoiceNumber = Integer.parseInt(invoiceDialog.getInvoiceNumberField().getText());
            if  (newInvoice.isValidDate(invoiceDialog.getDateField().getText())){
               
                newInvoice.setInvoiceDateString(invoiceDialog.getDateField().getText());
                newInvoice.setCustomerName(invoiceDialog.getCustomerNameField().getText());
                newInvoice.setInvoiceNumber(invoiceNumber);
               
              this.invoiceHeaderList.add(newInvoice);
                invoiceDialog.clearUI();
                this.invoiceDialog.setVisible(false);
                this.invoicesTableModel.fireTableDataChanged();
            }else {
                showMessageDialog(this.invoiceDialog, "Please enter valid invoice date ( ex. 01-12-2022 ) ");
            
            }
            } catch (NumberFormatException nfe) {
               showMessageDialog(this.invoiceDialog, "Please enter valid invoice number  ");
             
            }
                
        }
        
     }
    
    private void cancelNewInvoice() {
        invoiceDialog.clearUI();
        this.invoiceDialog.setVisible(false);
     }

    
    /* Create New Line UI and Actions  */
    private void createNewLine() {
         this.invoiceLineDialog =  new CreateInvoiceLineDialog(this.frame, true);
         invoiceLineDialog.getCancelBtn().addActionListener(this);
         invoiceLineDialog.getCreateBtn().addActionListener(this);
        this.invoiceLineDialog.setVisible(true);
      }
    
    private void createNewLineData() {
         if(invoiceLineDialog != null && selectedInvoice != null){
             try {
                 int itemCount= Integer.parseInt(invoiceLineDialog.getItemCountField().getText());
                 
                 double itemPrice = Double.parseDouble(invoiceLineDialog.getItemPriceField().getText());
            if  (itemPrice >= 0){
               if (itemCount >= 0){
                    InvoiceLine newLine = new InvoiceLine(selectedInvoice.getInvoiceNumber(),invoiceLineDialog.getItemNameField().getText() , itemPrice, itemCount);
                    selectedInvoice.getInvoiceLines().add(newLine);
                    invoiceLineDialog.clearUI();
                    this.invoiceLineDialog.setVisible(false);
                    this.linesTableModel.fireTableDataChanged();
                    this.invoicesTableModel.fireTableDataChanged();
                }else {
                  showMessageDialog(this.invoiceLineDialog, "Please enter valid item count ");
            
                }
            }else {
                showMessageDialog(this.invoiceLineDialog, "Please enter valid item price ");
            
            }
            } catch (NumberFormatException nfe) {
               showMessageDialog(this.invoiceLineDialog, "Please enter valid item count/price  ");
             
            }
                
        }
        
      }

    private void cancelNewLineInvoice() {
          invoiceLineDialog.clearUI();
        this.invoiceLineDialog.setVisible(false);
    }

}
