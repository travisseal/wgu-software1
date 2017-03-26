/*
 Data model for Product
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Product {

    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private ObservableValue<Integer> productId;
    private StringProperty name;
    private ObservableValue<Double> price;
    private ObservableValue<Integer> inStock;
    private IntegerProperty min;
    private IntegerProperty max;
    
    public void setName(String inName)
    {
        name = new SimpleStringProperty(inName);
    }

    public ObservableValue<String> getName()
    {
        return this.name;
    }
     
    public void setInstock(int setStock)
    {
        this.inStock = new SimpleIntegerProperty(setStock).asObject();
    }
    public void setPrice(double setPrice)
    {
        this.price = new SimpleDoubleProperty(setPrice).asObject();
    }
    
    public ObservableValue<Double> getPrice()
    {
        return this.price;
    }
    public ObservableValue<Integer> getInstock()
    {
        return this.inStock;
    }
    
    public void setMin(int inMin)
    {
        this.min = new SimpleIntegerProperty(inMin);
    }
    
    public Integer getMin()
    {
        return min.get();
    }
    
    public void setMax(int inMax)
    {
        this.max = new SimpleIntegerProperty(inMax);
    }
   
    public Integer getMax()
    {
        return this.max.get();
    }
    
    public void addPart(Part inPart)
    {
        parts.add(inPart);
    }
    
    public boolean removePart(int index)
    {
        try
        {
            parts.remove(index);
            return true;
        }
        catch (Exception ex)
        {
            System.err.println(ex.toString());
            return false;
        }
    }
    
    public Part lookupPart(int index)
    {
        try
        {
          return parts.get(index);
        }
        catch (Exception ex)
        {
            System.err.println(ex.toString());
            return null;
        } 
    }
    
    
   public void updatePart(int index)
   {
       
   }
   
   public void setProductId(int inProdId)
   {
       this.productId = new SimpleIntegerProperty(inProdId).asObject();
   }
    
   public ObservableValue<Integer> getProductId()
   {
       return this.productId;
   } 
   public Integer getPartsStock()
   {
       return parts.size();
   }
   
   public ObservableList<Part> getParts()
   {
       return parts;
   }
   //provides an interface to give a list of parts to product.
   public void setPartsList(ObservableList<Part> tmpPartArray)
   {
       this.parts = tmpPartArray;
   }
}
