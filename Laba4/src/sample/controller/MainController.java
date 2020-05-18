package sample.controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import sample.model.*;
import sample.pluginsSerialization.PlugBinarySerialization;
import sample.pluginsSerialization.PlugJsonSerialization;
import sample.pluginsSerialization.PlugMySerialization;
import sample.serialization.BinarySerialization;
import sample.serialization.JsonSerialization;
import sample.serialization.MySerialization;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

import static sample.model.AdditionalInfo.Quality.*;
import static sample.model.FictionBook.Genre.*;
import static sample.model.Magazine.AgeCategory.*;
import static sample.model.Newspaper.NewspaperType.*;
import static sample.model.NonFictionBook.Sphere.*;

public class MainController {
    private ObservableList<PrintedEdition> editionData = FXCollections.observableArrayList();

    @FXML
    private TableView<PrintedEdition> tableEditions;
    @FXML
    private TableColumn<PrintedEdition, String> nameColumn;
    @FXML
    private TableColumn<PrintedEdition, String> companyColumn;
    @FXML
    private TableColumn<PrintedEdition, Boolean> takeawayColumn;
    @FXML
    private TableColumn<PrintedEdition, AdditionalInfo.Quality> qualityColumn;
    @FXML
    private TableColumn<PrintedEdition, LocalDate> dateColumn;
    @FXML
    Label publDateLabel;

    @FXML
    Label ageLabel;

    @FXML
    Label typeLabel;

    @FXML
    Label colorLabel;

    @FXML
    Label authorLabel;

    @FXML
    Label pageNumLabel;

    @FXML
    Label rageLabel;

    @FXML
    Label genreLabel;

    @FXML
    Label sphereLabel;

    @FXML
    DatePicker publDatePicker;

    @FXML
    ComboBox<String> plugComboBox;

    @FXML
    ComboBox<Magazine.AgeCategory> ageComboBox;

    @FXML
    ComboBox<Newspaper.NewspaperType> typeComboBox;

    @FXML
    ComboBox<FictionBook.Genre> genreComboBox;

    @FXML
    ComboBox<NonFictionBook.Sphere> sphereComboBox;

    @FXML
    TextField rageTextField;

    @FXML
    TextField pageNumTextField;

    @FXML
    CheckBox colorCheckBox;

    @FXML
    TextField authorTextField;

    @FXML
    ComboBox<String> classComboBox;

    @FXML
    ComboBox<AdditionalInfo.Quality> qualityComboBox;

    @FXML
    TextField nameTextField;

    @FXML
    TextField companyTextField;

    @FXML
    TextField keyTextField;

    @FXML
    CheckBox takeawayCheckBox;

    @FXML
    private ObservableList<String> classes = FXCollections.observableArrayList("Newspaper", "Magazine", "Fiction book", "Non-fiction book");

    @FXML
    private ObservableList<AdditionalInfo.Quality> qualities = FXCollections.observableArrayList(Good, Normal, Bad);

    @FXML
    private ObservableList<Magazine.AgeCategory> ages = FXCollections.observableArrayList(forKids, forTeenagers, forAdults);

    @FXML
    private ObservableList<Newspaper.NewspaperType> types = FXCollections.observableArrayList(Local, Regional, State, International);

    @FXML
    private ObservableList<FictionBook.Genre> genres = FXCollections.observableArrayList(fairyTail, loveStory, novel, poetry);

    @FXML
    private ObservableList<NonFictionBook.Sphere> spheres = FXCollections.observableArrayList(programming, business, science);

    Class<?>[] plugins;


    public void CheckStr(String oldValue, String newValue, TextField d) {
            String regDate = "[a-zA-Z]*";
            Pattern pattern = Pattern.compile(regDate);
            if (!pattern.matcher(newValue).matches())
                d.setText(oldValue);
            else
                d.setText(newValue);
    }

    public void CheckInt(String oldValue, String newValue, TextField d) {
        String regDate = "([1-9]+[0-9]*)|(^)";
        Pattern pattern = Pattern.compile(regDate);
        if (!pattern.matcher(newValue).matches())
            d.setText(oldValue);
        else
            d.setText(newValue);
    }

    @FXML
    private void deserializeList(ActionEvent actionEvent) throws Exception{
        String key = keyTextField.getText();
        ObservableList<String> pluginList = plugComboBox.getItems();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Document");
        if (plugComboBox.getValue().equals("None")) {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Binary files", "*.dat"),
                    new FileChooser.ExtensionFilter("JSON Documents", "*.json"),
                    new FileChooser.ExtensionFilter("My Serialization files", "*.txt")
            );
            File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());

            if (file != null) {
                String fileName = file.getPath();
                int index = fileName.lastIndexOf(".");
                String extension = fileName.substring(index + 1);
                if (extension.equals("dat")) {
                    BinarySerialization serialization = new BinarySerialization();
                    editionData.setAll(serialization.deserialize(fileName));
                    tableEditions.refresh();
                } else if (extension.equals("json")) {
                    JsonSerialization serialization = new JsonSerialization();
                    editionData.setAll(serialization.deserialize(fileName));
                    tableEditions.refresh();
                } else if (extension.equals("txt")) {
                    MySerialization serialization = new MySerialization();
                    editionData.setAll(serialization.deserialize(fileName));
                    tableEditions.refresh();
                }
            }
        }
        else{
            if (!key.isEmpty()) {
                ObservableList<FileChooser.ExtensionFilter> filters = FXCollections.observableArrayList();
                for (int i = 1; i < pluginList.size(); i++) {
                    filters.add(new FileChooser.ExtensionFilter("My Serialization files", "*." + pluginList.get(i) + ".txt"));
                    filters.add(new FileChooser.ExtensionFilter("Binary Files", "*." + pluginList.get(i) + ".dat"));
                    filters.add(new FileChooser.ExtensionFilter("JSON Documents", "*." + pluginList.get(i) + ".json"));
                }
                fileChooser.getExtensionFilters().addAll(filters);
                File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());

                if (file != null) {
                    String fileName = file.getPath();
                    int index = fileName.lastIndexOf(".");
                    String extension = fileName.substring(index + 1);
                    if (extension.equals("dat")) {
                        PlugBinarySerialization serialization = new PlugBinarySerialization();
                        editionData.setAll(serialization.deserialize(fileName, key));
                        tableEditions.refresh();
                    } else if (extension.equals("json")) {
                        PlugJsonSerialization serialization = new PlugJsonSerialization();
                        editionData.setAll(serialization.deserialize(fileName, key));
                        tableEditions.refresh();
                    } else if (extension.equals("txt")) {
                        PlugMySerialization serialization = new PlugMySerialization();
                        editionData.setAll(serialization.deserialize(fileName, key));
                        tableEditions.refresh();
                    }
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please, enter the key!");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void serializeList(ActionEvent actionEvent) throws Exception {
        ObservableList<String> pluginList = plugComboBox.getItems();
        int selectedPlugin = pluginList.indexOf(plugComboBox.getValue());
        String key = keyTextField.getText();
        if (!editionData.isEmpty()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Document");
            if (plugComboBox.getValue().equals("None")) {
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Binary files", "*.dat"),
                        new FileChooser.ExtensionFilter("JSON Documents", "*.json"),
                        new FileChooser.ExtensionFilter("My Serialization files", "*.txt")
                );
                File file = fileChooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());
                if (file != null) {
                    String fileName = file.getPath();
                    int index = fileName.lastIndexOf(".");
                    String extension = fileName.substring(index + 1);
                    if (extension.equals("dat")) {
                        BinarySerialization serialization = new BinarySerialization();
                        serialization.serialize(editionData, fileName);
                    } else if (extension.equals("json")) {
                        JsonSerialization serialization = new JsonSerialization();
                        serialization.serialize(editionData, fileName);
                    } else if (extension.equals("txt")) {
                        MySerialization serialization = new MySerialization();
                        serialization.serialize(editionData, fileName);
                    }
                }
            }
            else{
                if (!key.isEmpty()) {
                    Class<?> plugClass = plugins[selectedPlugin - 1];
                    Method method = plugClass.getDeclaredMethod("encrypt", byte[].class, String.class);
                    String secExt = plugComboBox.getValue();
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("Binary files", "*." + secExt + ".dat"),
                            new FileChooser.ExtensionFilter("JSON Documents", "*." + secExt + ".json"),
                            new FileChooser.ExtensionFilter("My Serialization files", "*." + secExt + ".txt")
                    );
                    File file = fileChooser.showSaveDialog(((Node) actionEvent.getSource()).getScene().getWindow());
                    if (file != null) {
                        String fileName = file.getPath();
                        int index = fileName.lastIndexOf(".");
                        String extension = fileName.substring(index + 1);
                        if (extension.equals("dat")) {
                            PlugBinarySerialization serialization = new PlugBinarySerialization();
                            serialization.serialize(editionData, fileName, key, plugClass, method);
                        } else if (extension.equals("json")) {
                            PlugJsonSerialization serialization = new PlugJsonSerialization();
                            serialization.serialize(editionData, fileName, key, plugClass, method);
                        } else if (extension.equals("txt")) {
                            PlugMySerialization serialization = new PlugMySerialization();
                            serialization.serialize(editionData, fileName, key, plugClass, method);
                        }
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter the key!");
                    alert.showAndWait();
                }
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("List is empty!");
            alert.showAndWait();
        }
    }

    @FXML
    private void initialize() {
        initData();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        companyColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getAdditionalInfo().getPublishingCompany()));
        takeawayColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getAdditionalInfo().isTakeawayPermission()));
        qualityColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getAdditionalInfo().getQuality()));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("publishingDate"));
        tableEditions.setItems(editionData);
        classComboBox.setItems(classes);
        qualityComboBox.setItems(qualities);
        genreComboBox.setItems(genres);
        sphereComboBox.setItems(spheres);
        typeComboBox.setItems(types);
        ageComboBox.setItems(ages);
        classComboBox.getSelectionModel().select("Newspaper");
        plugComboBox.getSelectionModel().select("None");
        typeLabel.setVisible(true);
        colorLabel.setVisible(true);
        typeComboBox.setVisible(true);
        colorCheckBox.setVisible(true);
        {
            nameTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckStr(oldValue, newValue, nameTextField);
            });
            companyTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckStr(oldValue, newValue, companyTextField);
            });
            authorTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckStr(oldValue, newValue, authorTextField);
            });
            pageNumTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckInt(oldValue, newValue, pageNumTextField);
            });
            rageTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                CheckInt(oldValue, newValue, rageTextField);
            });
        }
    }

    private void initData()
    {
        editionData.add(new Magazine("Eralash", LocalDate.of(1995, 4, 15).toString(),"SomeCompany", true,  Good, forTeenagers));
    }

    @FXML
    private void showRow()
    {
        PrintedEdition edition = tableEditions.getSelectionModel().getSelectedItem();
        if (edition.getClass() == Newspaper.class){
            classComboBox.setValue("Newspaper");
            nameTextField.setText(edition.getName());
            companyTextField.setText(edition.getAdditionalInfo().getPublishingCompany());
            takeawayCheckBox.setSelected(edition.getAdditionalInfo().isTakeawayPermission());
            qualityComboBox.setValue(edition.getAdditionalInfo().getQuality());
            publDatePicker.setValue(LocalDate.parse(edition.getPublishingDate()));
            ageLabel.setVisible(false);
            ageComboBox.setVisible(false);
            typeLabel.setVisible(true);
            typeComboBox.setVisible(true);
            colorLabel.setVisible(true);
            colorCheckBox.setVisible(true);
            authorLabel.setVisible(false);
            authorTextField.setVisible(false);
            pageNumLabel.setVisible(false);
            pageNumTextField.setVisible(false);
            rageLabel.setVisible(false);
            rageTextField.setVisible(false);
            genreLabel.setVisible(false);
            genreComboBox.setVisible(false);
            sphereLabel.setVisible(false);
            sphereComboBox.setVisible(false);
            colorCheckBox.setSelected(((Newspaper)edition).isColorful());
            typeComboBox.setValue(((Newspaper)edition).getType());
        }
        if (edition.getClass() == Magazine.class){
            classComboBox.setValue("Magazine");
            nameTextField.setText(edition.getName());
            companyTextField.setText(edition.getAdditionalInfo().getPublishingCompany());
            takeawayCheckBox.setSelected(edition.getAdditionalInfo().isTakeawayPermission());
            qualityComboBox.setValue(edition.getAdditionalInfo().getQuality());
            publDatePicker.setValue(LocalDate.parse(edition.getPublishingDate()));
            ageLabel.setVisible(true);
            ageComboBox.setVisible(true);
            typeLabel.setVisible(false);
            typeComboBox.setVisible(false);
            colorLabel.setVisible(false);
            colorCheckBox.setVisible(false);
            authorLabel.setVisible(false);
            authorTextField.setVisible(false);
            pageNumLabel.setVisible(false);
            pageNumTextField.setVisible(false);
            rageLabel.setVisible(false);
            rageTextField.setVisible(false);
            genreLabel.setVisible(false);
            genreComboBox.setVisible(false);
            sphereLabel.setVisible(false);
            sphereComboBox.setVisible(false);
            ageComboBox.setValue(((Magazine)edition).getAgeCategory());
        }
        if (edition.getClass() == FictionBook.class){
            classComboBox.setValue("Fiction Book");
            nameTextField.setText(edition.getName());
            companyTextField.setText(edition.getAdditionalInfo().getPublishingCompany());
            takeawayCheckBox.setSelected(edition.getAdditionalInfo().isTakeawayPermission());
            qualityComboBox.setValue(edition.getAdditionalInfo().getQuality());
            publDatePicker.setValue(LocalDate.parse(edition.getPublishingDate()));
            ageLabel.setVisible(false);
            ageComboBox.setVisible(false);
            typeLabel.setVisible(false);
            typeComboBox.setVisible(false);
            colorLabel.setVisible(false);
            colorCheckBox.setVisible(false);
            authorLabel.setVisible(true);
            authorTextField.setVisible(true);
            pageNumLabel.setVisible(true);
            pageNumTextField.setVisible(true);
            rageLabel.setVisible(true);
            rageTextField.setVisible(true);
            genreLabel.setVisible(true);
            genreComboBox.setVisible(true);
            sphereLabel.setVisible(false);
            sphereComboBox.setVisible(false);
            authorTextField.setText(((FictionBook)edition).getAuthorName());
            pageNumTextField.setText(Integer.toString(((FictionBook)edition).getPageNumber(), 10));
            rageTextField.setText(Integer.toString(((FictionBook)edition).getRage(), 10));
            genreComboBox.setValue(((FictionBook)edition).getGenre());
        }
        if (edition.getClass() == NonFictionBook.class){
            classComboBox.setValue("Non-fiction book");
            nameTextField.setText(edition.getName());
            companyTextField.setText(edition.getAdditionalInfo().getPublishingCompany());
            takeawayCheckBox.setSelected(edition.getAdditionalInfo().isTakeawayPermission());
            qualityComboBox.setValue(edition.getAdditionalInfo().getQuality());
            publDatePicker.setValue(LocalDate.parse(edition.getPublishingDate()));
            ageLabel.setVisible(false);
            ageComboBox.setVisible(false);
            typeLabel.setVisible(false);
            typeComboBox.setVisible(false);
            colorLabel.setVisible(false);
            colorCheckBox.setVisible(false);
            authorLabel.setVisible(true);
            authorTextField.setVisible(true);
            pageNumLabel.setVisible(true);
            pageNumTextField.setVisible(true);
            rageLabel.setVisible(true);
            rageTextField.setVisible(true);
            genreLabel.setVisible(true);
            genreComboBox.setVisible(true);
            sphereLabel.setVisible(false);
            sphereComboBox.setVisible(false);
            authorTextField.setText(((NonFictionBook)edition).getAuthorName());
            pageNumTextField.setText(Integer.toString(((NonFictionBook)edition).getPageNumber(), 10));
            rageTextField.setText(Integer.toString(((NonFictionBook)edition).getRage(), 10));
            sphereComboBox.setValue(((NonFictionBook)edition).getSphere());
        }
    }

    @FXML
    private void deleteRow()
    {
        PrintedEdition edition = tableEditions.getSelectionModel().getSelectedItem();
        if (edition == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing selected!");
            alert.showAndWait();
        }
        else{
            int selectedIndex = tableEditions.getSelectionModel().getSelectedIndex();
            editionData.remove(selectedIndex);
            tableEditions.refresh();
        }
    }

    @FXML
    private void generateFields() {
        switch (classComboBox.getValue()) {
            case "Newspaper": {
                nameTextField.setText("");
                companyTextField.setText("");
                takeawayCheckBox.setSelected(false);
                qualityComboBox.setValue(null);
                publDatePicker.setValue(null);
                ageLabel.setVisible(false);
                ageComboBox.setVisible(false);
                typeLabel.setVisible(true);
                typeComboBox.setVisible(true);
                colorLabel.setVisible(true);
                colorCheckBox.setVisible(true);
                authorLabel.setVisible(false);
                authorTextField.setVisible(false);
                pageNumLabel.setVisible(false);
                pageNumTextField.setVisible(false);
                rageLabel.setVisible(false);
                rageTextField.setVisible(false);
                genreLabel.setVisible(false);
                genreComboBox.setVisible(false);
                sphereLabel.setVisible(false);
                sphereComboBox.setVisible(false);
                typeComboBox.setValue(null);
                break;
            }
            case "Magazine": {
                nameTextField.setText("");
                companyTextField.setText("");
                takeawayCheckBox.setSelected(false);
                qualityComboBox.setValue(null);
                publDatePicker.setValue(null);
                ageLabel.setVisible(true);
                ageComboBox.setVisible(true);
                typeLabel.setVisible(false);
                typeComboBox.setVisible(false);
                colorLabel.setVisible(false);
                colorCheckBox.setVisible(false);
                authorLabel.setVisible(false);
                authorTextField.setVisible(false);
                pageNumLabel.setVisible(false);
                pageNumTextField.setVisible(false);
                rageLabel.setVisible(false);
                rageTextField.setVisible(false);
                genreLabel.setVisible(false);
                genreComboBox.setVisible(false);
                sphereLabel.setVisible(false);
                sphereComboBox.setVisible(false);
                ageComboBox.setValue(null);
                break;
            }
            case "Fiction book": {
                nameTextField.setText("");
                companyTextField.setText("");
                takeawayCheckBox.setSelected(false);
                qualityComboBox.setValue(null);
                publDatePicker.setValue(null);
                ageLabel.setVisible(false);
                ageComboBox.setVisible(false);
                typeLabel.setVisible(false);
                typeComboBox.setVisible(false);
                colorLabel.setVisible(false);
                colorCheckBox.setVisible(false);
                authorLabel.setVisible(true);
                authorTextField.setVisible(true);
                pageNumLabel.setVisible(true);
                pageNumTextField.setVisible(true);
                rageLabel.setVisible(true);
                rageTextField.setVisible(true);
                genreLabel.setVisible(true);
                genreComboBox.setVisible(true);
                sphereLabel.setVisible(false);
                sphereComboBox.setVisible(false);
                authorTextField.setText("");
                pageNumTextField.setText("");
                rageTextField.setText("");
                genreComboBox.setValue(null);
                break;
            }
            case "Non-fiction book": {
                nameTextField.setText("");
                companyTextField.setText("");
                takeawayCheckBox.setSelected(false);
                qualityComboBox.setValue(null);
                publDatePicker.setValue(null);
                ageLabel.setVisible(false);
                ageComboBox.setVisible(false);
                typeLabel.setVisible(false);
                typeComboBox.setVisible(false);
                colorLabel.setVisible(false);
                colorCheckBox.setVisible(false);
                authorLabel.setVisible(true);
                authorTextField.setVisible(true);
                pageNumLabel.setVisible(true);
                pageNumTextField.setVisible(true);
                rageLabel.setVisible(true);
                rageTextField.setVisible(true);
                genreLabel.setVisible(false);
                genreComboBox.setVisible(false);
                sphereLabel.setVisible(true);
                sphereComboBox.setVisible(true);
                authorTextField.setText("");
                pageNumTextField.setText("");
                rageTextField.setText("");
                sphereComboBox.setValue(null);
                break;
            }
        }
    }

    @FXML
    public void saveNewRec()
    {
        PrintedEdition edition = tableEditions.getSelectionModel().getSelectedItem();
        if (edition == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nothing selected!");
            alert.showAndWait();
        }
        else {
            String name = nameTextField.getText();
            String publishingCompany = companyTextField.getText();
            boolean takeawayPermission = takeawayCheckBox.isSelected();
            AdditionalInfo.Quality quality = qualityComboBox.getValue();
            LocalDate publishingDate = publDatePicker.getValue();
            switch (classComboBox.getValue()) {
                case "Newspaper": {
                    Newspaper.NewspaperType type = typeComboBox.getValue();
                    boolean isColorful = colorCheckBox.isSelected();
                    if ((name.equals("")) || (publishingCompany.equals("")) || (quality == null) || (publishingDate == null) || (type == null)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please, enter all information!!!");
                        alert.showAndWait();
                    }
                    else {
                        edition.setName(name);
                        edition.getAdditionalInfo().setPublishingCompany(publishingCompany);
                        edition.getAdditionalInfo().setTakeawayPermission(takeawayPermission);
                        edition.getAdditionalInfo().setQuality(quality);
                        edition.setPublishingDate(publishingDate.toString());
                        ((Newspaper) edition).setType(type);
                        ((Newspaper) edition).setColorful(isColorful);
                    }
                    break;
                }
                case "Magazine": {
                    Magazine.AgeCategory ageCategory = ageComboBox.getValue();
                    if ((name.equals("")) || (publishingCompany.equals("")) || (quality == null) || (publishingDate == null) || (ageCategory == null)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please, enter all information!!!");
                        alert.showAndWait();
                    }
                    else {
                        edition.setName(name);
                        edition.getAdditionalInfo().setPublishingCompany(publishingCompany);
                        edition.getAdditionalInfo().setTakeawayPermission(takeawayPermission);
                        edition.getAdditionalInfo().setQuality(quality);
                        edition.setPublishingDate(publishingDate.toString());
                        ((Magazine) edition).setAgeCategory(ageCategory);
                    }
                    break;
                }
                case "Fiction Book": {
                    String authorName = authorTextField.getText();
                    FictionBook.Genre genre = genreComboBox.getValue();
                    if ((name.equals("")) || (publishingCompany.equals("")) || (quality == null) || (publishingDate == null) || (authorName.equals("")) || (pageNumTextField.getText().equals("")) || (rageTextField.getText().equals("")) || (genre == null)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please, enter all information!!!");
                        alert.showAndWait();
                    }
                    else {
                        int pageNumber = Integer.parseInt(pageNumTextField.getText());
                        int rage = Integer.parseInt(rageTextField.getText());
                        edition.setName(name);
                        edition.getAdditionalInfo().setPublishingCompany(publishingCompany);
                        edition.getAdditionalInfo().setTakeawayPermission(takeawayPermission);
                        edition.getAdditionalInfo().setQuality(quality);
                        edition.setPublishingDate(publishingDate.toString());
                        ((FictionBook) edition).setAuthorName(authorName);
                        ((FictionBook) edition).setGenre(genre);
                        ((FictionBook) edition).setPageNumber(pageNumber);
                        ((FictionBook) edition).setRage(rage);
                    }
                    break;
                }
                case "Non-fiction Book": {
                    String authorName = authorTextField.getText();
                    NonFictionBook.Sphere sphere = sphereComboBox.getValue();
                    if ((name.equals("")) || (publishingCompany.equals("")) || (quality == null) || (publishingDate == null) || (authorName.equals("")) || (pageNumTextField.getText().equals("")) || (rageTextField.getText().equals("")) || (sphere == null)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Please, enter all information!!!");
                        alert.showAndWait();
                    }
                    else {
                        int pageNumber = Integer.parseInt(pageNumTextField.getText());
                        int rage = Integer.parseInt(rageTextField.getText());
                        edition.setName(name);
                        edition.getAdditionalInfo().setPublishingCompany(publishingCompany);
                        edition.getAdditionalInfo().setTakeawayPermission(takeawayPermission);
                        edition.getAdditionalInfo().setQuality(quality);
                        edition.setPublishingDate(publishingDate.toString());
                        ((NonFictionBook) edition).setAuthorName(authorName);
                        ((NonFictionBook) edition).setSphere(sphere);
                        ((NonFictionBook) edition).setPageNumber(pageNumber);
                        ((NonFictionBook) edition).setRage(rage);
                    }
                    break;
                }
            }
        }
        tableEditions.refresh();
    }

    @FXML
    public void createNewRec()
    {
        String name = nameTextField.getText();
        String publishingCompany = companyTextField.getText();
        boolean takeawayPermission = takeawayCheckBox.isSelected();
        AdditionalInfo.Quality quality = qualityComboBox.getValue();
        LocalDate publishingDate = publDatePicker.getValue();
        switch (classComboBox.getValue()) {
            case "Newspaper": {
                Newspaper.NewspaperType type = typeComboBox.getValue();
                boolean isColorful = colorCheckBox.isSelected();
                if ((name.equals("")) || (publishingCompany.equals("")) || (quality == null) || (publishingDate == null) || (type == null)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter all information!!!");
                    alert.showAndWait();
                }
                else {
                    Newspaper newspaper = new Newspaper(name, publishingDate.toString(), publishingCompany, takeawayPermission, quality, type, isColorful);
                    editionData.add(newspaper);
                }
                break;
            }
            case "Magazine":{
                Magazine.AgeCategory ageCategory = ageComboBox.getValue();
                if ((name.equals("")) || (publishingCompany.equals("")) || (quality == null) || (publishingDate == null) || (ageCategory == null)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter all information!!!");
                    alert.showAndWait();
                }
                else {
                    Magazine magazine = new Magazine(name, publishingDate.toString(), publishingCompany, takeawayPermission, quality, ageCategory);
                    editionData.add(magazine);
                }
                break;
            }
            case "Fiction book":{
                String authorName = authorTextField.getText();
                FictionBook.Genre genre = genreComboBox.getValue();
                if ((name.equals("")) || (publishingCompany.equals("")) || (quality == null) || (publishingDate == null) || (authorName.equals("")) || (pageNumTextField.getText().equals("")) || (rageTextField.getText().equals("")) || (genre == null)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter all information!!!");
                    alert.showAndWait();
                }
                else {
                    int pageNumber = Integer.parseInt(pageNumTextField.getText());
                    int rage = Integer.parseInt(rageTextField.getText());
                    FictionBook fictionBook = new FictionBook(name, publishingDate.toString(), publishingCompany, takeawayPermission, quality, authorName, pageNumber, rage, genre);
                    editionData.add(fictionBook);
                }
                break;
            }
            case "Non-fiction book":{
                String authorName = authorTextField.getText();
                NonFictionBook.Sphere sphere = sphereComboBox.getValue();
                if ((name.equals("")) || (publishingCompany.equals("")) || (quality == null) || (publishingDate == null) || (authorName.equals("")) || (pageNumTextField.getText().equals("")) || (rageTextField.getText().equals("")) || (sphere == null)){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Please, enter all information!!!");
                    alert.showAndWait();
                }
                else {
                    int pageNumber = Integer.parseInt(pageNumTextField.getText());
                    int rage = Integer.parseInt(rageTextField.getText());
                    NonFictionBook fictionBook = new NonFictionBook(name, publishingDate.toString(), publishingCompany, takeawayPermission, quality, authorName, pageNumber, rage, sphere);
                    editionData.add(fictionBook);
                }
                break;
            }
        }
        tableEditions.refresh();
    }

    @FXML
    public void onClickPlugin(Event event) throws Exception{
        plugins = getClassesFromPackage("sample/plugins/");
        ObservableList<String> pluginList = FXCollections.observableArrayList("None");
        for (Class<?> plugin : plugins) {
            pluginList.add(plugin.toString());
        }
        plugComboBox.setItems(pluginList);
    }

    public static Class<?>[] getClassesFromPackage(String packageName) throws Exception {
        List<Class<?>> list = new ArrayList<>();
        ArrayList<File> fileList = new ArrayList<>();
        Enumeration<URL> urlEnum = Thread.currentThread().getContextClassLoader().getResources(packageName);
        while (urlEnum.hasMoreElements()){
            URL url = urlEnum.nextElement();
            File dir = new File(url.getFile());
            Collections.addAll(fileList, Objects.requireNonNull(dir.listFiles()));
        }
        File[] fileArr = fileList.toArray(new File[]{});
        for (File file: fileArr){
            String fileName = file.getName();
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
            Class<?> newClass = Class.forName("sample.plugins." + fileName);
            list.add(newClass);
        }
        return list.toArray(new Class<?>[]{});
    }
}
