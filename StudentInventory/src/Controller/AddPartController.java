/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.Product;
import Model.partsTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.stage.Stage;


public class AddPartController implements Initializable {

    @FXML TextField partIdTextBox;
    @FXML TextField nameTextFieldID;
    @FXML ToggleGroup addPartToggleGroup;
    @FXML TextField addPartCompanyNameTextBoxID;
    //@FXML TextField getMachineIdTxt;
    @FXML TextField invTextFieldID;
    @FXML TextField priceCostTextFieldID;
    @FXML TextField maxAmountTextFieldID;
    @FXML TextField minAmountTextFieldID;
    @FXML Label addPartCompanyNameLabel;
    @FXML Button addPartCancelBtn;
    
    private Model.Product product;
    private int _partId = 1;
    
    private boolean isInHouse;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                
        addPartCompanyNameLabel.setText("");
  
    }
    
    public void cancelBtn()
    {
        Stage stage = (Stage) addPartCancelBtn.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel...");
        alert.setHeaderText("Canceling...");
        alert.setContentText("Would you like to cancel now?");        
        alert.showAndWait()
            
                    .filter(response -> response == ButtonType.OK).ifPresent(response -> stage.close());
        
    }
    
    public void addPartToggleGroupAction(ActionEvent action)
    {
       
        try
        {
         if(addPartToggleGroup.getSelectedToggle().toString().contains("In-House"))
            {
                isInHouse = true;
                
                clearAllTextFields();            
                partIdTextBox.setText("");
                partIdTextBox.setEditable(false);
                nameTextFieldID.setPromptText("Part Name");
                invTextFieldID.setPromptText("Inventory");
                priceCostTextFieldID.setPromptText("Price");
                maxAmountTextFieldID.setPromptText("Max");
                minAmountTextFieldID.setPromptText("Min");
                addPartCompanyNameTextBoxID.setPromptText("Mach Id");
                
                addPartCompanyNameLabel.setText("   Machine Id");

                nameTextFieldID.setEditable(true);
                invTextFieldID.setEditable(true);
                priceCostTextFieldID.setEditable(true);
                maxAmountTextFieldID.setEditable(true);
                minAmountTextFieldID.setEditable(true);
                addPartCompanyNameTextBoxID.setEditable(true);
                
            }
         else //outsourced
            {
                clearAllTextFields();
                
                partIdTextBox.setText("");
                partIdTextBox.setEditable(false);
                nameTextFieldID.setPromptText("Part Name");
                invTextFieldID.setPromptText("Inventory");
                priceCostTextFieldID.setPromptText("Price");
                maxAmountTextFieldID.setPromptText("Max");
                minAmountTextFieldID.setPromptText("Min");
                addPartCompanyNameTextBoxID.setPromptText("Company Name");
                
                //change labels
                addPartCompanyNameLabel.setText("Company Name");
                
               
                nameTextFieldID.setEditable(true);
                //getMachineIdTxt.setEditable(false);
                invTextFieldID.setEditable(true);
                priceCostTextFieldID.setEditable(true);
                maxAmountTextFieldID.setEditable(true);
                minAmountTextFieldID.setEditable(true);
                addPartCompanyNameTextBoxID.setEditable(true);

            }
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText(ex.toString());
            alert.show();
        }

    }
    
    /*
        Save button 
        Purpose: Create new object (item)
        Inputs: all text fields
        create new object, add textfields
    */
    
    public void addPartBtn()
    {
        if(isInHouse)
        {
            try
            {
                Inhouse inhousePart = new Inhouse();
                
                inhousePart.setPartId(_partId);
                inhousePart.setMachineId(Integer.parseInt(addPartCompanyNameTextBoxID.getText()));
                inhousePart.setName(nameTextFieldID.getText());
                inhousePart.setInventoryLevel(Integer.parseInt(invTextFieldID.getText()));
                inhousePart.setPricePerUnit(Double.parseDouble(priceCostTextFieldID.getText()));
                inhousePart.setMax(Integer.parseInt(maxAmountTextFieldID.getText()));
                inhousePart.setMin(Integer.parseInt(minAmountTextFieldID.getText()));
                
                //check if inventory is within range.
                if(!checkInv())
                    throw new Exception("Inventory must be between min and max amounts.");
                
                _partId++;
                
                Inventory.addPart(inhousePart);
           
            }
            catch (Exception ex)
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Oops");
                alert.setHeaderText(ex.toString());
                alert.show();
            }
        }
        else
        {
            try
            {
                Outsourced oSourcedPart = new Outsourced();
                
                //check if inventory is within range.
                if(!checkInv())
                    throw new Exception("Inventory must be between min and max amounts.");
                
                oSourcedPart.setPartId(_partId);
                oSourcedPart.setName(nameTextFieldID.getText());
                oSourcedPart.setInventoryLevel(Integer.parseInt(invTextFieldID.getText()));
                oSourcedPart.setPricePerUnit(Double.parseDouble(priceCostTextFieldID.getText()));
                oSourcedPart.setMax(Integer.parseInt(maxAmountTextFieldID.getText()));
                oSourcedPart.setMin(Integer.parseInt(minAmountTextFieldID.getText()));
                oSourcedPart.setCompanyName(addPartCompanyNameTextBoxID.getText());

                 _partId++;
                
                Inventory.addPart(oSourcedPart);

            }
            catch (Exception ex)
            {
                System.err.println(ex.toString());
            }
        }
        partsTableView.partsTableList = FXCollections.observableList(Inventory.getParts());
        partsTableView.updatePartsTableView();

    }
    //clears text fields contents.
    private void clearAllTextFields()
    {
        partIdTextBox.setText("");
        addPartCompanyNameTextBoxID.setText("");   
        nameTextFieldID.setText("");
        invTextFieldID.setText("");
        priceCostTextFieldID.setText("");
        maxAmountTextFieldID.setText("");
        minAmountTextFieldID.setText("");
        addPartCompanyNameTextBoxID.setText("");
    }
    
    /*
        function ensures the constraints on add parts
    */
    private boolean checkInv()
    {
        int inv = Integer.parseInt(invTextFieldID.getText());
        int min = Integer.parseInt(minAmountTextFieldID.getText());
        int max = Integer.parseInt(maxAmountTextFieldID.getText());
     
        if((max <= inv) && (max >= min) && (min <= inv))
            return true;
        else
            return false;
            
    }
   
}
