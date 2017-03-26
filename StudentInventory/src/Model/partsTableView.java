/*
Purpose:
This is the driving class, main screen once the application starts.
 */
package Model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class partsTableView extends Application {

    private static final TableView partsTable = new TableView();
    private static final TableView productsTable = new TableView();
    private Stage stage;
    
    Button partsAddButton = new Button("      Add       ");
    Button partsModifyButton = new Button("   Modify   ");
    Button partsDeleteButton = new Button("    Delete    ");
    Button partsSearchButton = new Button("  Search  ");
    
    Button productsAddButton = new Button("      Add       ");
    Button productsModifyButton = new Button("   Modify   ");
    Button productsDeleteButton = new Button("    Delete    ");
    Button productsSearchButton = new Button("  Search  ");
    Button exitButton = new Button("  Exit  ");
    
    TextField productsTextField = new TextField();
    TextField partsTextField = new TextField();
    
    static TableColumn <Part,Integer> partsPartId;
    static TableColumn <Part,String>  partsPartName;
    static TableColumn <Part,Integer> partsInventoryLevel;
    static TableColumn <Part,Double>  partsPriceCostPerUnit;
    
    static TableColumn <Product,Integer> productsProductID;
    static TableColumn <Product,String>  productsProductName;
    static TableColumn <Product,Integer> productsInventoryLevel;
    static TableColumn <Product,Double>  productsPricePerUnit;
    
    
    private static Part selectedPart = null;
    private static Product selectedProd = null;
   
    
    public static ObservableList<Part> partsTableList = FXCollections.observableList(Inventory.getParts());
    public static ObservableList<Product> productsTableList = FXCollections.observableList(Inventory.getPartsAndProducts());
    
    public static void main(String[] args) {
        launch(args);
    }
    //refreshes the tables
    public static void refreshTables()
    {
        partsTable.refresh();
        productsTable.refresh();
    }
    
    public static Part selectedPart()
    {
       return selectedPart;
    }
    public static Product selectedProduct()
    {
       return selectedProd;
    }

    @Override
    public void start(Stage stage) {
     
    //set pats table listeners
    setTableViewListeners();
        
    this.stage = stage;
    
    GridPane grid = new GridPane();
    
    
    partsTextField.setPromptText("Search For Part");
    productsTextField.setPromptText("Search for Product");
    
    Label partsLable = new Label("Parts");
    partsLable.setStyle("-fx-font-size: 20; "
                      + "-fx-font-weight:bold;");
    Label productsLable = new Label("Products");
    productsLable.setStyle("-fx-font-size: 20; "
                      + "-fx-font-weight:bold;");
            
    grid.setAlignment(Pos.TOP_LEFT);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25,25,25,25));

    Text programTitle = new Text("Inventory Management System");
    programTitle.setFont(Font.font("Calibri",FontWeight.NORMAL,20));

    partsPartId = new TableColumn("Part ID");
    partsPartName = new TableColumn("Part Name");
    partsInventoryLevel = new TableColumn("Inventory Level");
    partsPriceCostPerUnit = new TableColumn("Price/Cost per Unit");

    productsProductID = new TableColumn("Product Id");
    productsProductName = new TableColumn("Product Name");
    productsInventoryLevel = new TableColumn("Inventory Level");
    productsPricePerUnit = new TableColumn("Price per Unit");
    
    partsPartId.setMinWidth(100);
    partsPartName.setMinWidth(100);
    partsInventoryLevel.setMinWidth(100);
    partsPriceCostPerUnit.setMinWidth(150);
    
    productsProductID.setMinWidth(100);
    productsProductName.setMinWidth(100);
    productsInventoryLevel.setMinWidth(100);
    productsPricePerUnit.setMinWidth(150);
    
    partsTable.getColumns().addAll(partsPartId, partsPartName, partsInventoryLevel,partsPriceCostPerUnit);
    productsTable.getColumns().addAll(productsProductID, productsProductName, productsInventoryLevel,productsPricePerUnit); 
    
    grid.setHgap(40);
    grid.setVgap(20);
    grid.add(partsTable,0,2,12,10);
    grid.add(productsTable,15,2,12,10);
    grid.add(programTitle,0,0,30,1);
    
    grid.add(partsLable,1,1,30,1);
    grid.add(partsSearchButton,5,1,30,1);
    grid.add(partsTextField, 7, 1, 5,1);
    grid.add(partsAddButton,3,12,30,1);
    grid.add(partsModifyButton,6,12,30,1);
    grid.add(partsDeleteButton,9,12,30,1);
    
    
    grid.add(productsLable,17,1,30,1);
    grid.add(productsSearchButton,20,1,30,1);
    grid.add(productsTextField, 22, 1, 5,1);
    grid.add(productsAddButton,18,12,30,1);
    grid.add(productsModifyButton,21,12,30,1);
    grid.add(productsDeleteButton,24,12,30,1);
    grid.add(exitButton,25,15,30,1);
       
    Scene scene = new Scene(grid,1100,500);

    stage.setTitle("Inventory Management System");
    stage.setScene(scene);
    stage.show();

    //event listener for Add Parts Button
    partsAddButton.setOnAction((event) -> { 
        
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/addPart.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root1));  
                stage1.show();
                
        } catch(Exception e) 
        {
           System.out.println(e.toString());
        }
       
    });
    
    //event listener for exit button
    exitButton.setOnAction((event) -> { 
        try {
           this.stop();
        } catch(Exception e) {
           System.out.println(e.toString());
          }
       
    });
    
    partsModifyButton.setOnAction((event) -> { 
        try {
                if(selectedPart == null)
                    throw new Exception("You have to select a part fist.");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/modifyPart.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root1));  
                stage1.show();
        } catch(Exception e) 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText(e.toString());
            alert.show();
        }
       
    });
    productsModifyButton.setOnAction((event) -> { 
        try {
            if(selectedProd == null)
                    throw new Exception("You have to select a product fist.");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/modifyProduct.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root1));  
                stage1.show();
        } catch(Exception e) 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops");
            alert.setHeaderText(e.toString());
            alert.show();
        }
       
    });
    productsSearchButton.setOnAction((event) -> { 
        try {
               searchForProduct();
        } catch(Exception e) 
        {
           System.out.println(e.toString());
        }
       
    });
    partsSearchButton.setOnAction((event) -> { 
        try {
               System.out.println("searching");
               searchForPart();
        } catch(Exception e) 
        {
           System.out.println(e.toString());
        }
       
    });
    productsAddButton.setOnAction((event) -> { 
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/addProduct.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage1 = new Stage();
                stage1.setScene(new Scene(root1));  
                stage1.show();
                
        } catch(Exception e) 
        {
           System.out.println(e.toString());
        }
       
    });
    
    //exit button
    exitButton.setOnAction((event) -> stage.close());
    
    }
    
    //update parts table 
    public static void updatePartsTableView()
    {
        try
        {
            partsPartId.setCellValueFactory(cellData -> cellData.getValue().getPartId());      
            partsPartName.setCellValueFactory(cellData -> cellData.getValue().getPartName());
            partsInventoryLevel.setCellValueFactory(cellData -> cellData.getValue().getInventoryLevel());
            partsPriceCostPerUnit.setCellValueFactory(cellData -> cellData.getValue().getPricePerUnit());
            
            partsTable.setItems(partsTableList);
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.toString());
            alert.show();
        }
    }
    //update products table 
    public static void updateProductsTableView()
    {
        try
        {
            productsProductID.setCellValueFactory(cellData -> cellData.getValue().getProductId());      
            productsProductName.setCellValueFactory(cellData -> cellData.getValue().getName());
            productsInventoryLevel.setCellValueFactory(cellData -> cellData.getValue().getInstock());
            productsPricePerUnit.setCellValueFactory(cellData -> cellData.getValue().getPrice());

            productsTable.setItems(productsTableList);
            
            
        }
        catch(Exception ex)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(ex.toString());
            alert.show();
        }
    }
    
    //defines how part or product is selected
    public void mouseClick(Product productSelection)
    {
        refreshTables();
        
       try
       {
           selectedProd = (Product)(productsTable.getSelectionModel().getSelectedItem());
       }
       catch(Exception ex)
       {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must made a selection! " + "\n" + ex.toString());
            alert.show();
       }

    }
    public void mouseClick(Part selectedPart)
    {
       try
       {
           selectedPart = (Part)(partsTable.getSelectionModel().getSelectedItem()); 
       }
       catch (Exception ex)
       {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must made a selection!");
            alert.show();
       }
       finally
       {
            updateProductsTableView();
            refreshTables();
       }
    
    }
    
    private void deletePartBtn()
    {
        try
        {
            if(selectedPart == null)
                throw new Exception("Please select a part");
            
            Stage stage = (Stage) partsDeleteButton.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete...");
            alert.setHeaderText("Delete...");
            alert.setContentText("Would you like to delete now?");        
            alert.showAndWait().filter(response -> response == ButtonType.OK)
                               .ifPresent(response -> partsTableList.remove((Part)selectedPart));
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
            updateProductsTableView();
            refreshTables();
        }
     
    }
    
    private void deleteProdBtn()
    {
        try
        {
            
            if(selectedProd == null)
                throw new Exception("Please select a product");
            
            Stage stage = (Stage) partsDeleteButton.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete...");
            alert.setHeaderText("Delete...");
            alert.setContentText("Would you like to delete now?");        
            alert.showAndWait().filter(response -> response == ButtonType.OK)
                               .ifPresent(response -> productsTableList.remove(selectedProd));
            
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
            updateProductsTableView();
            refreshTables();
        }    
    }
    
    public void setTableViewListeners()
    {
        partsTable.setOnMouseClicked(new EventHandler<MouseEvent>(){

          @Override
          public void handle(MouseEvent arg0) {
            selectedPart = (Part)(partsTable.getSelectionModel().getSelectedItem()); 
            mouseClick(selectedPart);
           
          }

      });
    
    //listener to get the selected part
        partsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (partsTable.getSelectionModel().getSelectedItem() != null) {
                selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
            }
        });
    
    //listener to make the deletion
    partsDeleteButton.setOnAction((ActionEvent e) -> {
        deletePartBtn();
        });
    
    //listener to get the selected product
        productsTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (productsTable.getSelectionModel().getSelectedItem() != null) {
                selectedProd = (Product) productsTable.getSelectionModel().getSelectedItem();
                mouseClick(selectedProd);
            }
        });
    
    //listener to make the deletion
    productsDeleteButton.setOnAction((ActionEvent e) -> {
        deleteProdBtn();
        });
    
    }
    /*
        Search function for products.
        called by the listener for this button.
    */
    private void searchForProduct()
    {
    
        try
        {
              
            productsTableList = FXCollections.observableList(Inventory.lookupProduct(productsTextField.getText().trim().toLowerCase()));
        
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
           updateProductsTableView();
        }
    
    }
    /*
        Search function for parts.
        called by the listener for this button.
    */
    private void searchForPart()
    {
    
        try
        {
              
            partsTableList = FXCollections.observableList(Inventory.lookupPart(partsTextField.getText().trim().toLowerCase()));
        
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
}
