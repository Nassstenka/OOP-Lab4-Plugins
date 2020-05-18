package sample.serialization;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.*;

import java.io.*;
import java.time.LocalDate;

public class MySerialization implements Serialization {


    @Override
    public void serialize(ObservableList<PrintedEdition> list, String fileName) throws Exception{
        FileWriter writer = new FileWriter(fileName);
        for (PrintedEdition edition : list){
            writer.append(edition.writeData());
            writer.append('\n');
        }
        writer.close();
    }

    @Override
    public ObservableList<PrintedEdition>  deserialize (String fileName) throws Exception{
        FileReader reader = new FileReader(fileName);
        BufferedReader bufReader = new BufferedReader(reader);
        ObservableList<PrintedEdition> list = FXCollections.observableArrayList();
        String line = bufReader.readLine();
        while(line != null){
            String[] tokens = line.split(";");
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
