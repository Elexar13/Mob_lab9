package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.*;

public class Controller {

    public ComboBox<String> matrixSizeBox;
    public ComboBox<String> figureBox;
    public GridPane root;
    public TableView<Rule> table;
    public TableColumn<Rule, Integer> fromColumn;
    public TableColumn<Rule, Integer> toColumn;
    int matrixSize = 5;
    String figure = "Кінь";
    Integer location;

    public static List<Integer> locationForHorse = new ArrayList();
    public static List<Integer> locationForElephant = new ArrayList();

    public static List<Integer> locationFromForHorse = new ArrayList();
    public static List<Integer> locationToForHorse = new ArrayList();
    public static List<Integer> locationFromForElephant = new ArrayList();
    public static List<Integer> locationToForElephant = new ArrayList();

    static ObservableList<Rule> rules = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        ObservableList<String> sizes = FXCollections.observableArrayList("4x4", "5x5", "6x6", "7x7", "8x8");
        matrixSizeBox.setItems(sizes);
        matrixSizeBox.setValue("5x5");
        ObservableList<String> figures = FXCollections.observableArrayList("Кінь", "Слон");
        figureBox.setItems(figures);
        figureBox.setValue("Кінь");

        matrixSize =  Integer.parseInt(matrixSizeBox.getValue().split("x")[0]);
        figure = figureBox.getValue();
        getLocationsForFigures();
//        drawBoard();
    }
    public void getLocations(ActionEvent actionEvent) {
        matrixSize =  Integer.parseInt(matrixSizeBox.getValue().split("x")[0]);
        figure = figureBox.getValue();
        getLocationsForFigures();
        drawBoard();
    }

    private void getLocationsForFigures() {
        if (figure.equals("Кінь")){
            rules.clear();
            getLocationForHorse(location, matrixSize);

            // определяем таблицу и устанавливаем данные
            table.setItems(rules);
            table.setPrefWidth(50);
            table.setPrefHeight(50);

            // столбец для вывода имени
            // определяем фабрику для столбца с привязкой к свойству name
            fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
            // добавляем столбец
//            table.getColumns().add(fromColumn);

            // столбец для вывода возраста
            toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
//            table.getColumns().add(toColumn);
        }
        if (figure.equals("Слон")){
            rules.clear();
            getLocationForElephant(location, matrixSize);
        }
        drawBoard();
    }

    public static void getLocationForHorse(Integer location, Integer matrixSize){
        locationForHorse = new ArrayList<>();
        locationFromForHorse = new ArrayList<>();
        locationToForHorse = new ArrayList<>();
        List<List<Integer>> matrix = new ArrayList<>();
        int n = matrixSize * matrixSize;
        List<Integer> row = new ArrayList<>();
        int count = matrixSize;
        for (int i = 1; i <= n; i ++){
            row.add(i);
            if (i == count){
                matrix.add(row);
                count += matrixSize;
                row = new ArrayList<>();
            }
        }

        count = 1;
        for (int i = 0; i < matrix.size(); i++){
            for (int j = 0; j < matrix.get(i).size(); j++){
                for (int x = 0; x < matrix.size(); x++){
                    for (int y = 0; y < matrix.get(x).size(); y++){
                        if (((Math.abs(x - i) == 2 && Math.abs(y-j) == 1) || (Math.abs(x - i) == 1 && Math.abs(y-j) == 2))
                                && !matrix.get(i).get(j).equals(matrix.get(x).get(y))){
                            if (matrix.get(i).get(j).equals(location)){
                                locationForHorse.add(matrix.get(x).get(y));
                            }
                            locationFromForHorse.add(matrix.get(i).get(j));
                            locationToForHorse.add(matrix.get(x).get(y));
                        }
                    }
                }
                count++;
            }
        }

        for (int i = 0; i < locationFromForHorse.size(); i++){
            rules.add(new Rule(locationFromForHorse.get(i), locationToForHorse.get(i)));
        }
    }

    public static void getLocationForElephant(Integer location, Integer matrixSize){
        locationForElephant = new ArrayList<>();
        locationFromForElephant = new ArrayList<>();
        locationToForElephant = new ArrayList<>();
        List<List<Integer>> matrix = new ArrayList<>();
        int n = matrixSize * matrixSize;
        List<Integer> row = new ArrayList<>();
        int count = matrixSize;
        for (int i = 1; i <= n; i ++){
            row.add(i);
            if (i == count){
                matrix.add(row);
                count += matrixSize;
                row = new ArrayList<>();
            }
        }

        for (int i = 0; i < matrix.size(); i++){
            for (int j = 0; j < matrix.get(i).size(); j++){
                for (int x = 0; x < matrix.size(); x++){
                    for (int y = 0; y < matrix.get(x).size(); y++){
                        if ((Math.abs(x - i) == Math.abs(y-j)) && !matrix.get(i).get(j).equals(matrix.get(x).get(y))) {
                            if (matrix.get(i).get(j).equals(location)){
                                locationForElephant.add(matrix.get(x).get(y));
                            }
                            locationFromForElephant.add(matrix.get(i).get(j));
                            locationToForElephant.add(matrix.get(x).get(y));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < locationFromForElephant.size(); i++){
            rules.add(new Rule(locationFromForElephant.get(i), locationToForElephant.get(i)));
        }
    }

    public void drawBoard(){
        root.getColumnConstraints().clear();
        root.getRowConstraints().clear();
        root.getColumnConstraints().removeAll(root.getColumnConstraints());
        root.getRowConstraints().removeAll(root.getRowConstraints());
        root.getChildren().clear();
        root.getChildren().removeAll(root.getChildren());
        final int size = matrixSize ;
        int count = 1;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col ++) {
                StackPane square = new StackPane();
                String color ;
                if ((row + col) % 2 == 0) {
                    color = "#ffce9e";
                } else {
                    color = "#d18b47";
                }
                square.setStyle("-fx-background-color: "+color+";");
                if ((location != null && figure.equals("Кінь") && locationForHorse.contains(count))){
                    square.setStyle("-fx-border-color: #000000; -fx-background-color: "+color +"; -fx-border-width: 5;");
                    Image img = new Image("horse.png");
                    ImageView imgView = new ImageView(img);
                    imgView.setFitWidth(35);
                    imgView.setFitHeight(35);
                    square.getChildren().add(imgView);
                }
                if (Objects.equals(location, count) && figure.equals("Кінь")){
                    square.setStyle("-fx-border-color: #ffffff; -fx-background-color: "+color +"; -fx-border-width: 5;");
                    Image img = new Image("horse.png");
                    ImageView imgView = new ImageView(img);
                    imgView.setFitWidth(35);
                    imgView.setFitHeight(35);
                    square.getChildren().add(imgView);
                }
                if (Objects.equals(location, count) && figure.equals("Слон")){
                    square.setStyle("-fx-border-color: #ffffff; -fx-background-color: "+color +"; -fx-border-width: 5;");
                    Image img = new Image("elephant.png");
                    ImageView imgView = new ImageView(img);
                    imgView.setFitWidth(35);
                    imgView.setFitHeight(35);
                    square.getChildren().add(imgView);
                }
                if ((location != null && figure.equals("Слон") && locationForElephant.contains(count))){
                    square.setStyle("-fx-border-color: #000000; -fx-background-color: "+color +"; -fx-border-width: 5;");
                    Image img = new Image("elephant.png");
                    ImageView imgView = new ImageView(img);
                    imgView.setFitWidth(35);
                    imgView.setFitHeight(35);
                    square.getChildren().add(imgView);
                }
                square.getChildren().add(new Text(String.valueOf(count)));
                square.setOnMouseClicked(e -> {
                    Text txt = (Text) square.getChildren().get(0);
                    location = Integer.parseInt(txt.getText());
                    getLocationsForFigures();
                });
                root.add(square, col, row);
                count++;
            }
        }
        for (int i = 0; i < size; i++) {
            root.getColumnConstraints().add(new ColumnConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, HPos.CENTER, true));
            root.getRowConstraints().add(new RowConstraints(5, Control.USE_COMPUTED_SIZE, Double.POSITIVE_INFINITY, Priority.ALWAYS, VPos.CENTER, true));
        }
    }
}
