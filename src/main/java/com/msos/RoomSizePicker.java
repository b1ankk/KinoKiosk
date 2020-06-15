package com.msos;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RoomSizePicker extends VBox
{
    public static class RoomSize
    {
        private int rows;
        private int columns;
    
        public int getRows()
        {
            return rows;
        }
    
        public void setRows(int rows)
        {
            this.rows = rows;
        }
    
        public int getColumns()
        {
            return columns;
        }
    
        public void setColumns(int columns)
        {
            this.columns = columns;
        }
    }
    
    @FXML
    private TextField rowsTestField;
    
    @FXML
    private TextField columnsTestField;
    
    @FXML
    private Button okButton;
    
    @FXML
    private Button cancelButton;
    
    
    private final RoomSize roomSize;
    
    public RoomSizePicker(RoomSize roomSize)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/room_size_picker.fxml"));
        loader.setRoot(this);
        loader.setController(this);
    
        this.roomSize = roomSize;
        
        try
        {
            loader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private boolean isValidNumber(String s)
    {
        if (s.length() > 2)
            return false;
        
        char[] chars = s.toCharArray();
        for (char c : chars)
            if (!Character.isDigit(c))
                return false;
            
        return true;
    }
    
    @FXML
    private void initialize()
    {
        rowsTestField.textProperty().addListener(
            (observableValue, oldText, newText) ->
            {
                if (!isValidNumber(newText))
                    rowsTestField.setText(oldText);
            }
        );
        columnsTestField.textProperty().addListener(
            (observableValue, oldText, newText) ->
            {
                if (!isValidNumber(newText))
                    columnsTestField.setText(oldText);
            }
        );
        
        
        
        cancelButton.setOnAction(
            e -> ((Node) e.getSource()).getScene().getWindow().hide()
        );
        
        
        okButton.setOnAction(
            e ->
            {
                if (rowsTestField.getText().equals("0"))
                    rowsTestField.setText("");
                if (rowsTestField.getText().equals(""))
                    return;
                
                if (columnsTestField.getText().equals("0"))
                    rowsTestField.setText("");
                if (columnsTestField.getText().equals(""))
                    return;
                
                int rows = Integer.parseInt(rowsTestField.getText());
                int columns = Integer.parseInt(columnsTestField.getText());
                
                roomSize.setRows(rows);
                roomSize.setColumns(columns);
    
                ((Node) e.getSource()).getScene().getWindow().hide();
            }
        );
    }
    
    
}
