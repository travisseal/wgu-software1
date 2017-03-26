/*
 * Purpose: Modify the selected product.
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import Model.partsTableView;
import static Model.partsTableView.partsTableList;
import static Model.partsTableView.refreshTables;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Travis
 */
public class ModifyProductController implements Initializable {

    private Part thisPart;  
    private Product thisProduct = partsTableView.selectedProduct();
    
    private ObservableList<Part> inventoryParts = FXCollections.observableArrayList(Inventory.getParts());
    private ObservableList<Part> partsAssociated = FXCollections.observableArrayList(thisProduct.getParts());
    private int numberOfParts;
    
    @FXML TableView<Part> partsInInventory;
    @FXML TableView<Part> modProdTable;

    @FXML TableColumn<Part,Integer> tPartId;
    @FXML TableColumn<Part,String> tPartName;
    @FXML TableColumn<Part,Integer> tInventoryLevel;
    @FXML TableColumn<Part,Double> tPricePerUnit;
    
    @FXML TableColumn<Part,Integer> bPartid;
    @FXML TableColumn<Part,String> bPartName;
    @FXML TableColumn<Part,Integer> bInventoryLevel;
    @FXML TableColumn<Part,Double> bPricePerUnit;
  
    @FXML TextField addProductID;
    @FXML TextField addProductNameTxt;
    @FXML TextField addProductInvTxt;
    @FXML TextField addProductPriceTxt;
    @FXML TextField addProductMaxTxt;
    @FXML TextField addProductMinTxt;
    @FXML TextField addProductSearchTxt;
    
    @FXML Button addProductSearchBtn;
    @FXML Button addProductSaveBtn;
    @FXML Button addProductCancelBtn;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        //listener to get the selected part
        partsInInventory.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (partsInInventory.getSelectionModel().getSelectedItem() != null) {
                thisPart = (Part) partsInInventory.getSelectionModel().getSelectedItem();
                System.out.println(thisPart.toString());
            }
        });
        //listener on the bottom table for selected associated part
        modProdTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (modProdTable.getSelectionModel().getSelectedItem() != null) {           
                thisPart = (Part) modProdTable.getSelectionModel().getSelectedItem();
                System.out.println(partsAssociated.indexOf(thisPart));
            }
           
        });
        
        addProductSearchBtn.setOnAction((event) -> { 
        try {
           searchForPart();
        } catch(Exception e) {
           System.out.println(e.toString());
          }
       
    });
        addProductSaveBtn.setOnAction((event) -> { 
        try {
           saveBtn();
        } catch(Exception e) {
           System.out.println(e.toString());
          }
       
    });
        
       setTextBoxes(); 
       setTopTableFields();
       setBottomTableFields();
       numberOfParts = partsAssociated.size();
    }
    
     public void cancelBtn()
    {
        Stage stage = (Stage) addProductCancelBtn.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel...");
        alert.setHeaderText("Canceling...");
        alert.setContentText("Would you like to cancel now?");        
        alert.showAndWait()
                            .filter(response -> response == ButtonType.OK).ifPresent(response -> stage.close());
    }
    
    
    //set top table inventory
    private void setTopTableFields()
    {
        tPartId.setCellValueFactory(cellData -> cellData.getValue().getPartId());      
        tPartName.setCellValueFactory(cellData -> cellData.getValue().getPartName());
        tInventoryLevel.setCellValueFactory(cellData -> cellData.getValue().getInventoryLevel());
        tPricePerUnit.setCellValueFactory(cellData -> cellData.getValue().getPricePerUnit());

        partsInInventory.setItems(inventoryParts);
    }
    

    //sets the bottom table with partsAssociated associated to the selected product
    private void setBottomTableFields()
    {
        bPartid.setCellValueFactory(cellData -> cellData.getValue().getPartId());      
        bPartName.setCellValueFactory(cellData -> cellData.getValue().getPartName());
        bInventoryLevel.setCellValueFactory(cellData -> cellData.getValue().getInventoryLevel());
        bPricePerUnit.setCellValueFactory(cellData -> cellData.getValue().getPricePerUnit());

        modProdTable.setItems(partsAssociated);

    }
    
    
    public void deleteBtn()
    {
       try
       {
           //make sure we are working with good inventory
            reloadInventory();
           
            int index = partsAssociated.indexOf(thisPart);
            
            if(index == -1)
                throw new Exception("Error -1: Item does not exist in the list. Please make a selection");
            
            thisProduct.removePart(index);
            
            partsAssociated = FXCollections.observableArrayList(thisProduct.getParts());
            modProdTable.refresh();
            modProdTable.setItems(partsAssociated);
                
       }
       catch (Exception ex)
       {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("oops");
            alert.setHeaderText(ex.toString());
            alert.show();
       }
    }
    
    public void saveBtn()
    {
       
        try
        {
            thisProduct.setName(addProductNameTxt.getText());
            thisProduct.setInstock(Integer.parseInt(addProductInvTxt.getText()));
            thisProduct.setPrice(Double.parseDouble(addProductPriceTxt.getText()));
            thisProduct.setMax(Integer.parseInt(addProductMaxTxt.getText()));
            thisProduct.setMin(Integer.parseInt(addProductMinTxt.getText()));
            
            
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.toString());
            alert.show();
        }
        finally
        {
            partsTableView.refreshTables();
            partsTableView.updatePartsTableView();
            
            Stage stage = (Stage) addProductSaveBtn.getScene().getWindow();
            stage.close();
        }
    }
    
    public void addParts()
    { 
        try
        {
            if(thisPart != null)
                thisProduct.addPart(thisPart);
            else
                throw new Exception("Please select a part first");
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("oops");
            alert.setHeaderText(ex.toString());
            alert.show();   
        }
        finally
        {
            
            reloadInventory();
            
        }
    }
    
    private void reloadInventory()
    {
        try
        {
            partsAssociated = FXCollections.observableArrayList(thisProduct.getParts());
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("oops");
            alert.setHeaderText(ex.toString());
            alert.show();
        }
        finally
        {
            setBottomTableFields();
            partsInInventory.refresh();
            modProdTable.refresh();
        }
    }  
    
     
    private void setTextBoxes()
    {
         addProductID.setText(thisProduct.getProductId().getValue().toString());
         addProductNameTxt.setText(thisProduct.getName().getValue());
         addProductInvTxt.setText(thisProduct.getInstock().getValue().toString());
         addProductPriceTxt.setText(thisProduct.getPrice().getValue().toString());
         addProductMaxTxt.setText(thisProduct.getMax().toString());
         addProductMinTxt.setText(thisProduct.getMin().toString());
    }
       
    private void searchForPart()
    {
    
        try
        {
              
            partsTableList = FXCollections.observableList(Inventory.lookupPart(addProductSearchTxt.getText().trim().toLowerCase()));
        
        }
        catch (Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.toString());
            alert.show();
        }
        finally
        {
           refreshTables();
           updatePartsTableView();
        }
    
    }
   
   public void updatePartsTableView()
    {
        try
        {
            
            tPartId.setCellValueFactory(cellData -> cellData.getValue().getPartId());      
            tPartName.setCellValueFactory(cellData -> cellData.getValue().getPartName());
            tInventoryLevel.setCellValueFactory(cellData -> cellData.getValue().getInventoryLevel());
            tPricePerUnit.setCellValueFactory(cellData -> cellData.getValue().getPricePerUnit());
            
            partsInInventory.setItems(partsTableList);
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.toString());
            alert.show();
        }
    }
}
