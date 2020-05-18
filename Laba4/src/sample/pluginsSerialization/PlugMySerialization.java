package sample.pluginsSerialization;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.*;
import sample.plugins.PluginOne;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Method;

public class PlugMySerialization implements PlugSerialization  {
    @Override
    public void serialize(ObservableList<PrintedEdition> list, String fileName, String key, Class plugClass, Method method) throws Exception{
        FileWriter writer = new FileWriter(fileName);
        for (PrintedEdition edition : list){
            byte[] plainText = edition.writeData().getBytes();
            byte[] cipherText = (byte[])method.invoke(plugClass.getDeclaredConstructor().newInstance(), plainText, key);
            String encodedString = new String(cipherText);
            writer.append(encodedString);
            writer.append('\n');
        }
        writer.close();
    }

    @Override
    public ObservableList<PrintedEdition>  deserialize (String fileName, String key) throws Exception{
        FileReader reader = new FileReader(fileName);
        BufferedReader bufReader = new BufferedReader(reader);
        ObservableList<PrintedEdition> list = FXCollections.observableArrayList();
        String line = bufReader.readLine();
        while(line != null){
            byte[] cipherText = line.getBytes();
            String[] tokens = fileName.split("[.]");
            String str = "sample.plugins."+tokens[tokens.length-2];
            Class<?> plugClass =Class.forName(str);
            Method method = plugClass.getDeclaredMethod("decrypt", byte[].class, String.class);
            byte[] plainText = (byte[]) method.invoke(plugClass.getDeclaredConstructor().newInstance(), cipherText, key);
            String newLine = new String(plainText);
            tokens = newLine.split(";");
            switch (tokens[0]) {
                case "Newspaper":{
                    Newspaper newspaper = new Newspaper(
                            tokens[3], tokens[4],
                            tokens[5], Boolean.parseBoolean(tokens[6]),
                            AdditionalInfo.Quality.valueOf(tokens[7]),
                            Newspaper.NewspaperType.valueOf(tokens[1]),
                            Boolean.parseBoolean(tokens[2])
                    );
                    list.add(newspaper);
                    break;
                }
                case "Magazine":{
                    Magazine magazine = new Magazine(
                            tokens[2], tokens[3],
                            tokens[4], Boolean.parseBoolean(tokens[5]),
                            AdditionalInfo.Quality.valueOf(tokens[6]),
                            Magazine.AgeCategory.valueOf(tokens[1])
                    );
                    list.add(magazine);
                    break;
                }
                case "FictionBook":{
                    FictionBook book = new FictionBook(
                            tokens[5], tokens[6],
                            tokens[7], Boolean.parseBoolean(tokens[8]),
                            AdditionalInfo.Quality.valueOf(tokens[9]),
                            tokens[2], Integer.parseInt(tokens[3]),
                            Integer.parseInt(tokens[4]),
                            FictionBook.Genre.valueOf(tokens[1])
                    );
                    list.add(book);
                    break;
                }
                case "NonFictionBook":{
                    NonFictionBook book = new NonFictionBook(
                            tokens[5], tokens[6],
                            tokens[7], Boolean.parseBoolean(tokens[8]),
                            AdditionalInfo.Quality.valueOf(tokens[9]),
                            tokens[2], Integer.parseInt(tokens[3]),
                            Integer.parseInt(tokens[4]),
                            NonFictionBook.Sphere.valueOf(tokens[1])
                    );
                    list.add(book);
                    break;
                }
            }
            line = bufReader.readLine();
        }
        reader.close();
        bufReader.close();
        return list;
    }
}
