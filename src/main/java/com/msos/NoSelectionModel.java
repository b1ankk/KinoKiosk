package com.msos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionModel;

public class NoSelectionModel<T> extends MultipleSelectionModel<T>
{
    @Override
    public ObservableList<Integer> getSelectedIndices()
    {
        return FXCollections.emptyObservableList();
    }
    
    @Override
    public ObservableList<T> getSelectedItems()
    {
        return FXCollections.emptyObservableList();
    }
    
    @Override
    public void selectIndices(int i, int... ints)
    {
    
    }
    
    @Override
    public void selectAll()
    {
    
    }
    
    @Override
    public void selectFirst()
    {
    
    }
    
    @Override
    public void selectLast()
    {
    
    }
    
    @Override
    public void clearAndSelect(int i)
    {
    
    }
    
    @Override
    public void select(int i)
    {
    
    }
    
    @Override
    public void select(T t)
    {
    
    }
    
    @Override
    public void clearSelection(int i)
    {
    
    }
    
    @Override
    public void clearSelection()
    {
    
    }
    
    @Override
    public boolean isSelected(int i)
    {
        return false;
    }
    
    @Override
    public boolean isEmpty()
    {
        return true;
    }
    
    @Override
    public void selectPrevious()
    {
    
    }
    
    @Override
    public void selectNext()
    {
    
    }
}
