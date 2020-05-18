package sample.serialization;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;;
import javafx.collections.ObservableList;
import sample.lists.ObjectWrapper;
import sample.model.PrintedEdition;

import java.io.File;
import java.util.*;

public class JsonSerialization implements Serialization {


    @Override
    public void serialize(ObservableList<PrintedEdition> list, String fileName) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        List<PrintedEdition> editions = new ArrayList<>(list);
        ObjectWrapper wrapper = new ObjectWrapper();
        wrapper.setAllEditions(editions);
        mapper.configure( SerializationFeature.INDENT_OUTPUT,true);
        mapper.writeValue(new File(fileName), wrapper);
    }

    @Override
    public ObservableList<PrintedEdition>  deserialize (String fileName) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping();
        ObjectWrapper wrapper = mapper.readValue(new File(fileName), ObjectWrapper.class);
        ObservableList<PrintedEdition> list = FXCollections.observableArrayList();
        list.setAll(wrapper.getAllEditions());
        return list;
    }
}

