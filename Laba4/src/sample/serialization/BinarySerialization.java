package sample.serialization;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.PrintedEdition;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BinarySerialization implements Serialization {
    

    @Override
    public void serialize(ObservableList<PrintedEdition> list, String fileName) throws Exception{
        FileOutputStream fileStream = new FileOutputStream(fileName);
        ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
        objectStream.writeObject(new ArrayList<>(list));
        fileStream.close();
        objectStream.close();
    }

   @Override
    public ObservableList<PrintedEdition>  deserialize (String fileName) throws Exception{
       FileInputStream fileStream = new FileInputStream(fileName);
       ObjectInputStream objectStream = new ObjectInputStream(fileStream);
       Object newObj = objectStream.readObject();
       fileStream.close();
       objectStream.close();
       ObservableList<PrintedEdition> list = FXCollections.observableArrayList();
       list.setAll((ArrayList<PrintedEdition>)newObj);
       return list;
    }
}
