/*
 * Add product: Controller class for addProduct.
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Travis
 */
public class AddProductController implements Initializable {

    @FXML TableView<Part> partsInInventoryTableView;
    @FXML TableView<Part> productsPartsView;
    
    @FXML TableColumn<Part,Integer> partId;
    @FXML TableColumn<Part,String> partName;
    @FXML TableColumn<Part,Integer> inventoryLevel;
    @FXML TableColumn<Part,Double> pricePerUnit;
    @FXML AnchorPane addProductWindow;
    
    //text boxes
    @FXML TextField addProductIDTxt;
    @FXML TextField addProductName;
    @FXML TextField addProductInv;
    @FXML TextField addProductPriceTxt;
    @FXML TextField addProductMax;
    @FXML TextField addProductMin;
    @FXML TextField productSearchTxt;
    
    //bottom table view
    @FXML TableColumn <Part,Integer> bottomPartId;
    @FXML TableColumn <Part,String> connectedPartName;
    @FXML TableColumn <Part,Integer> bottomInventoryLevel;
    @FXML TableColumn <Part,Double>bottomPricePerUnit;
    @FXML Button closeButton;
    @FXML Button productCancelBtn;
    @FXML Button deleteBtn;
    
    @FXML Button productSearchBtn;

    
    
    private Stage stage;
    
    //top window
    private final ObservableList<Part> observableList = FXCollections.observableList(Inventory.getParts());
    private Product myProduct = null;
    private Part selectedPart = null;
    private final ObservableList<Part> tmpPartArray = FXCollections.observableArrayList();
    private int selectedIndex;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  
       myProduct = new Product();

       partId.setCellValueFactory(cellData -> cellData.getValue().getPartId());
       
       partName.setCellValueFactory(cellData -> cellData.getValue().getPartName());
       
       inventoryLevel.setCellValueFactory(cellData -> cellData.getValue().getInventoryLevel());

       pricePerUnit.setCellValueFactory(cellData -> cellData.getValue().getPricePerUnit());

       partsInInventoryTableView.setItems(observableList);
       
        productSearchBtn.setOnAction((event) -> { 
        try {
           searchForPart();
        } catch(Exception e) {
           System.out.println(e.toString());
          }
       
    });
       

    }
 
    public void cancelBtn()
    {
        Stage stage = (Stage) productCancelBtn.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Cancel...");
        alert.setHeaderText("Canceling...");
        alert.setContentText("Would you like to cancel now?");        
        alert.showAndWait().filter(response -> response == ButtonType.OK).ifPresent(response -> stage.close());
    
    }
  
    //grabs the selected item.
  public void mouseClick()
  {
     try
     {
        selectedPart = (partsInInventoryTableView.getSelectionModel().getSelectedItem());
        System.out.println(("Part : " + partsInInventoryTableView.getSelectionModel().getSelectedItem()));
       
     }
     catch(Exception ex)
     {
         System.out.println("mouse failed");
     }
         
  }
  public void bottomWindowMouseClick()
  {
    selectedPart = partsInInventoryTableView.getSelectionModel().getSelectedItem();
    
  }
 
  
  //price of the product cannot be less than the total cost of the parts.
  private boolean calculateValue()
  {
      double priceOfParts = 0; 
      
      for(Part p : Inventory.getParts())
      {
          priceOfParts = priceOfParts + p.getPricePerUnit().getValue();
      }
      System.out.println("Price of parts =  " + priceOfParts + " price of product is " + myProduct.getPrice().getValue());
      
      
      if(myProduct.getPrice().getValue() <= priceOfParts)
          return false;
      else
          return true;
  }
  
  /*
    
  Purpose: save new product + part
  Inputs: all text fields in add product
  
  */
  public void saveBtn()
  {

    try
    {
        

        myProduct.setProductId(Integer.parseInt(addProductIDTxt.getText()));

        myProduct.setName(addProductName.getText());
        
        myProduct.setInstock(Integer.parseInt(addProductInv.getText()));

        myProduct.setPrice(Double.parseDouble(addProductPriceTxt.getText()));
   
        myProduct.setMax(Integer.parseInt(addProductMax.getText()));

        myProduct.setMin(Integer.parseInt(addProductMin.getText()));
        
        myProduct.addPart(selectedPart);
             
        if(myProduct == null)
            throw new Exception("Your product was not created");
     
        if(calculateValue() == false)
            throw new Exception("Cost of the product cannot be less than the parts.");
        
        //set the parts added to the product
        myProduct.setPartsList(tmpPartArray);
        
        Inventory.addProduct(myProduct);
     
        
        //update the inventory
        partsTableView.productsTableList = FXCollections.observableList(Inventory.getPartsAndProducts());
        
        stage = (Stage) closeButton.getScene().getWindow();
        
        stage.close();

    }
    catch (Exception ex)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Please see: ");
        alert.setHeaderText(ex.toString());
        alert.show();
    }
    finally
    {
        //update products table view on main screen
        partsTableView.updateProductsTableView();
       
        partsTableView.refreshTables();
    }
    
  }
  
 
  public void addPartToProduct()
  {
     
      try
      {
        if(this.selectedPart == null)
            throw new Exception("You must select a part first");
        
        tmpPartArray.add(selectedPart);
        
        updateBottomTableView();
      
      }
      catch (Exception ex)
      {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(ex.toString());
        alert.show();
      }
             
  }
  
  
   /*
        This is the bottom tableview
    */
    public void updateBottomTableView()
    {
       try
       {
            bottomPartId.setCellValueFactory(cellData -> cellData.getValue().getPartId());      
            connectedPartName.setCellValueFactory(cellData -> cellData.getValue().getPartName());
            bottomInventoryLevel.setCellValueFactory(cellData -> cellData.getValue().getInventoryLevel());
            bottomPricePerUnit.setCellValueFactory(cellData -> cellData.getValue().getPricePerUnit());
       
            pricePerUnit.setCellValueFactory(
            new PropertyValueFactory<>("pricePerUnit"));
            
            productsPartsView.setItems(tmpPartArray);
       }
       catch(Exception ex)
       {
            System.err.println("updateBottomTableView failed: " + ex.toString());
       }
    }
    
   public boolean removePart()
   {
       try
       {
           if(selectedPart ==null)
               throw new Exception("Please select a part");
           
            selectedPart = productsPartsView.getSelectionModel().getSelectedItem();
            
            Stage stage = (Stage) deleteBtn.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete...");
            alert.setHeaderText("Delete...");
            alert.setContentText("Would you like to delete now?");        
            alert.showAndWait().filter(response -> response == ButtonType.OK)
                               .ifPresent(response -> tmpPartArray.remove(selectedPart));


            return true;
       }
       catch (Exception ex)
       {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.toString());
            alert.show();
            
            return false;
       }
   }
   
   private void searchForPart()
    {
    
        try
        {
              
            partsTableList = FXCollections.observableList(Inventory.lookupPart(productSearchTxt.getText().trim().toLowerCase()));
        
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
            partId.setCellValueFactory(cellData -> cellData.getValue().getPartId());      
            partName.setCellValueFactory(cellData -> cellData.getValue().getPartName());
            inventoryLevel.setCellValueFactory(cellData -> cellData.getValue().getInventoryLevel());
            pricePerUnit.setCellValueFactory(cellData -> cellData.getValue().getPricePerUnit());
            
            partsInInventoryTableView.setItems(partsTableList);
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