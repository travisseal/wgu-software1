/*
Purpose: Inventory class holds all inventory, and temp search results.
 */
package Model;

import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Travis
 */
public class Inventory {
    
    //temp arrays for holding search results
    private static ObservableList<Product> resultProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> resultParts = FXCollections.observableArrayList();
    
    
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> partsList = FXCollections.observableArrayList();
    
    private static int index;
   static public void addProduct(Product inProduct)
    {
       products.add(inProduct);  
    }
    static public void addPart(Part inPart)
    {
        partsList.add(inPart);
      
       System.out.println("added to the list: " + partsList.size());
    }
   static public boolean removeProduct(int rmProductID)
    {
        try
        {
            products.remove(rmProductID);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
       
    }
   static public boolean removePart(int rmPartid)
    {
        try
        {
            partsList.remove(rmPartid);
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }
       
    }
    
   static public Product lookupProduct(int productID)
    {
       try
       {
           return products.get(productID);
       }
       catch(Exception ex)
       {
           return null;
       }
    }
   /*
    
   */
   static public ObservableList<Product> lookupProduct(String productName)
    {
       try
       {
         resultProducts.remove(0, resultProducts.size());
           
         for(Product p : products)
         {
             if(p.getName().getValue().contains(productName))
             {
                 resultProducts.add(p);
             }
             else{}
         }
          
          return resultProducts;
       }
       catch(Exception ex)
       {
           System.err.println("Inventory did not find it." + ex.toString());
           return null;
       }
    }
   static public ObservableList<Part> lookupPart(String partName)
    {
       try
       {
         resultParts.remove(0, resultParts.size());
           
         for(Part p : partsList)
         {
             if(p.getPartName().getValue().contains(partName))
             {
                 resultParts.add(p);
             }
             else{}
         }
          
          return resultParts;
       }
       catch(Exception ex)
       {
           System.err.println("Inventory did not find it." + ex.toString());
           return null;
       }
    }
   
   static public ObservableList<Part> getParts()
   {
       return partsList;
   }
  
   /*
        This is the backing datastucture for the tableview that shows association between products and parts
   */
   public static ObservableList<Product> getPartsAndProducts()
   {
       return products;
   }
   public static int getPartsCount()
   {
       return partsList.size();
   }
   public static Part getPart(Part part)
   {
     if(partsList.indexOf(part) == -1)
         return null;
      else
        index = partsList.indexOf(part);
     
      
    return partsList.get(index);
   }
   public static int getIndexOfSelectedPart()
   {
       return index;
   }
}

