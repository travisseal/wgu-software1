/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Travis
 */
public class Inhouse extends Part {
    
    private ObservableValue<Integer> machineId;
    
    public void setMachineId(int machineId)
    {
        this.machineId = new SimpleIntegerProperty(machineId).asObject();
    }
    public ObservableValue<Integer> getMachineId()
    {
        return machineId;
    }
      
}
