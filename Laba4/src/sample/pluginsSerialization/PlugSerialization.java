package sample.pluginsSerialization;

import javafx.collections.ObservableList;
import sample.model.PrintedEdition;

import java.lang.reflect.Method;

public interface PlugSerialization {
    void serialize(ObservableList<PrintedEdition> list, String fileName, String key, Class plugClass, Method method) throws Exception;
    ObservableList<PrintedEdition> deserialize(String fileName, String key) throws Exception;
}
