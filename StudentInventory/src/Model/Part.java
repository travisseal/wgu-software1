/*
 * Base class implements Inhouse, Outsourced
 */
package Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Travis
 */
public abstract class Part{
    
   protected StringProperty name;
   protected ObservableValue<Integer> partID;
   protected ObservableValue price;
   protected ObservableValue inventoryLevel;
   protected ObservableValue min;
   protected ObservableValue max;
   //protected IntegerProperty partId;
    
   public void setName(String inName)
    {
        name = new SimpleStringProperty(inName);
    }
   public ObservableValue<String> getPartName()
   {
       return this.name;
   }

    public void setPricePerUnit(double inPrice)
    {
        price = new SimpleDoubleProperty(inPrice).asObject();
    }
   public ObservableValue<Double> getPricePerUnit()
    {
        return price;
    }
   public void setInventoryLevel(int addToStock)
    {
        try
        {
            inventoryLevel = new SimpleIntegerProperty(addToStock);
        }
        catch(Exception ex)
        {
            System.err.println(ex.toString());
        }
    }
   public ObservableValue<Integer> getInventoryLevel()
    {
        return inventoryLevel;
    }
    public void setMin(int inMin)
    {
        min = new SimpleIntegerProperty(inMin).asObject();
    }
    public ObservableValue<Integer> getMin()
    {
        return min;
    }
   public void setMax(int inMax)
    {
        max = new SimpleIntegerProperty(inMax).asObject();
    }
   public ObservableValue<Integer> getMax()
    {
        return max;
    }
   public void setPartId(int inPartID)
    {
       this.partID = new SimpleIntegerProperty(inPartID).asObject();
    }
    public ObservableValue<Integer> getPartId()
    {
        return partID;
    }
    
    @Override
    public String toString()
    {
        return this.name.get();
    }
  
}
