/*
 Controller for modifyPart
 */
package Controller;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import Model.partsTableView;
import static Model.partsTableView.partsTableList;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Travis
 */
public class ModifyPartController implements Initializable {

    private boolean inHouseIsChecked;
    private boolean isOutSourcedIsChecked;
    
    @FXML ToggleGroup myGroup; 
    @FXML TextField companyNameTextBoxID;
    @FXML TextField modifyPartTextFieldID;
    @FXML TextField nameTextFieldID;
    @FXML TextField invTextFieldID;
    @FXML TextField priceCostTextFieldID;
    @FXML TextField maxAmountTextFieldID;
    @FXML TextField minAmountTextFieldID;
    @FXML Button modPartCancelBtn;
    
    final Part selectedPart = partsTableView.selectedPart();
    private int selectedPartId;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //selectedPartId = Inventory.getIndexOfSelectedPart();
       
       setFields();
    }
    
    public void cancelBtn()
    {
        Stage stage = (Stage) modPartCancelBtn.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel...");
        alert.setHeaderText("Canceling...");
        alert.setContentText("Would you like to cancel now?");        
        alert.showAndWait()
            
                    .filter(response -> response == ButtonType.OK).ifPresent(response -> stage.close());
    }
    
    private void setFields()
    {
        
       // modifyPartTextFieldID.setText(Inventory.getPart(selectedPart).getPartId().getValue().toString());
        modifyPartTextFieldID.setText(selectedPart.getPartId().getValue().toString());
        nameTextFieldID.setText(selectedPart.getPartName().getValue());
        invTextFieldID.setText(selectedPart.getInventoryLevel().getValue().toString());
        priceCostTextFieldID.setText(selectedPart.getPricePerUnit().getValue().toString());
        maxAmountTextFieldID.setText(selectedPart.getMax().getValue().toString());
        minAmountTextFieldID.setText(selectedPart.getMin().getValue().toString());
    }
    public void saveBtn()
    {
                
        if(selectedPart instanceof Inhouse)
        {
            Inhouse newHouse = new Inhouse();
            newHouse = (Inhouse) selectedPart;
            
            newHouse.setName(nameTextFieldID.getText());
            newHouse.setInventoryLevel(Integer.parseInt(invTextFieldID.getText()));
            newHouse.setPricePerUnit(Double.parseDouble(priceCostTextFieldID.getText()));
            newHouse.setMax(Integer.parseInt(maxAmountTextFieldID.getText()));
            newHouse.setMin(Integer.parseInt(minAmountTextFieldID.getText()));
            //newHouse.setMachineId(Integer.parseInt(companyNameTextBoxID.getText()));
            
//            Inventory.removePart(selectedPartId);
//            Inventory.addPart(newHouse);
            
            partsTableView.refreshTables();
          
            
        }
        else if(selectedPart instanceof Outsourced)
        {
        
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText("You need to make a valid option first.");
            alert.show();
        }
        
       
        
        
       // Inventory.addPart(selectedPart);
    }
     
    public void myGroupAction(ActionEvent action)
    {
      try
      {
       if(myGroup.getSelectedToggle().toString().contains("In-House"))
       {
           //clearAllTextFields();
           
           inHouseIsChecked = true;
           isOutSourcedIsChecked = false;
           
           modifyPartTextFieldID.setEditable(true);
           companyNameTextBoxID.setEditable(false);
           nameTextFieldID.setEditable(true);
           invTextFieldID.setEditable(true);
           priceCostTextFieldID.setEditable(true);
           maxAmountTextFieldID.setEditable(true);
           minAmountTextFieldID.setEditable(true);

               
                
           
           
           System.out.println("In house is checked: " + inHouseIsChecked);
       }
       else
       {
          // clearAllTextFields();
           
           isOutSourcedIsChecked = true;
           inHouseIsChecked = false;
           
           modifyPartTextFieldID.setEditable(false);
           companyNameTextBoxID.setEditable(false);
           nameTextFieldID.setEditable(true);
           invTextFieldID.setEditable(true);
           priceCostTextFieldID.setEditable(true);
           maxAmountTextFieldID.setEditable(true);
           minAmountTextFieldID.setEditable(true);
           companyNameTextBoxID.setEditable(true);

       }
       
      }
      catch (Exception e)
      {
          System.out.println(e.toString());
      }
       
    }
    //clears text fields contents.
    private void clearAllTextFields()
    {
        modifyPartTextFieldID.setText("");
        companyNameTextBoxID.setText("");
        nameTextFieldID.setText("");
        invTextFieldID.setText("");
        priceCostTextFieldID.setText("");
        maxAmountTextFieldID.setText("");
        minAmountTextFieldID.setText("");
    }

           
}    
    
    
