package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    public ComboBox<String> matrixSizeBox;
    public ComboBox<String> figureBox;
    public GridPane root;
    public TableView<Rule> table;
    public TableColumn<Rule, Integer> fromColumn;
    public TableColumn<Rule, Integer> toColumn;
    public TextField resultText;
    int matrixSize = 5;
    String figure = "Кінь";
    Integer location;
    Integer firstLocation;

    public static List<Integer> locationForHorse = new ArrayList();
    public static List<Integer> locationForElephant = new ArrayList();

    public static List<Integer> locationFromForHorse = new ArrayList();
    public static List<Integer> locationToForHorse = new ArrayList();
    public static List<Integer> locationFromForElephant = new ArrayList();
    public static List<Integer> locationToForElephant = new ArrayList();

    public static Map<Integer, List<Integer>> locationHorseMap = new HashMap<>();
    public static Map<Integer, List<Integer>> locationElephantMap = new HashMap<>();
    public static Set<Integer> visitedLocations = new LinkedHashSet<>();

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
            table.setItems(rules);
            table.setPrefWidth(50);
            table.setPrefHeight(50);
            fromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
            toColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
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
        locationHorseMap = new HashMap<>();
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

        int key = 1;
        for (int i = 0; i < matrix.size(); i++){
            for (int j = 0; j < matrix.get(i).size(); j++){
                locationHorseMap.put(key, new ArrayList<>());
                for (int x = 0; x < matrix.size(); x++){
                    for (int y = 0; y < matrix.get(x).size(); y++){
                        if (((Math.abs(x - i) == 2 && Math.abs(y-j) == 1) || (Math.abs(x - i) == 1 && Math.abs(y-j) == 2))
                                && !matrix.get(i).get(j).equals(matrix.get(x).get(y))){
                            if (matrix.get(i).get(j).equals(location)){
                                locationForHorse.add(matrix.get(x).get(y));
                            }
                            locationFromForHorse.add(matrix.get(i).get(j));
                            locationToForHorse.add(matrix.get(x).get(y));
                            locationHorseMap.get(key).add(matrix.get(x).get(y));
                        }
                    }
                }
                key++;
            }
        }

//        locationHorseMap.entrySet().forEach(x -> System.out.println(x));

        for (int i = 0; i < locationFromForHorse.size(); i++){
            rules.add(new Rule(locationFromForHorse.get(i), locationToForHorse.get(i)));
        }
    }

    public static void getLocationForElephant(Integer location, Integer matrixSize){
        locationElephantMap = new HashMap<>();
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

        int key = 1;
        for (int i = 0; i < matrix.size(); i++){
            for (int j = 0; j < matrix.get(i).size(); j++){
                locationElephantMap.put(key, new ArrayList<>());
                for (int x = 0; x < matrix.size(); x++){
                    for (int y = 0; y < matrix.get(x).size(); y++){
                        if ((Math.abs(x - i) == Math.abs(y-j)) && !matrix.get(i).get(j).equals(matrix.get(x).get(y))) {
                            if (matrix.get(i).get(j).equals(location)){
                                locationForElephant.add(matrix.get(x).get(y));
                            }
                            locationFromForElephant.add(matrix.get(i).get(j));
                            locationToForElephant.add(matrix.get(x).get(y));
                            locationElephantMap.get(key).add(matrix.get(x).get(y));
                        }
                    }
                }
                key++;
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
                square.getChildren().add(new Text(String.valueOf(count)));
                square.setOnMouseClicked(e -> {
                    Text txt = null;
                    try {
                        txt = (Text) square.getChildren().get(0);
                    } catch (Exception ex) {
                        txt = (Text) square.getChildren().get(1);
                    }
                    location = Integer.parseInt(txt.getText());
                    firstLocation = location;
//                    getLocationsForFigures();
                    visitedLocations.add(location);
                    if (figure.equals("Кінь")){
                        calculateTripForHorse();
                    } if (figure.equals("Слон")){
                        calculateTripForElephant();
                    }
                    drawBoardFolLR5();
                    visitedLocations.clear();
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

    public void drawBoardFolLR5(){
        List<StackPane> children = new ArrayList<>();
        for(Node square : root.getChildren()){
            children.add((StackPane) square);
        }
        int i = 1;
        for (Integer loc : visitedLocations){
            for (StackPane square : children) {
                Text txt = (Text) square.getChildren().get(0);
                if(Integer.parseInt(txt.getText()) == loc){
                    ((Text) square.getChildren().get(0)).setText(String.valueOf(i));
                    i++;
                    break;
                }
            }
        }
    }

    private void calculateTripForHorse() {
        try {
            while (visitedLocations.size() != matrixSize * matrixSize) {
                List<Integer> nextLocations = locationHorseMap.get(location);
                Map<Integer, Integer> countOfNextLocationsMap = new HashMap<>();
                for (Integer loc : nextLocations) {
                    Integer count = locationHorseMap.get(loc).size();
                    for (Integer l : locationHorseMap.get(loc)) {
                        if (visitedLocations.contains(l)) count--;
                    }
                    if (!visitedLocations.contains(loc)) countOfNextLocationsMap.put(loc, count);
                }
                int minCount = countOfNextLocationsMap.entrySet().stream().mapToInt(e -> e.getValue()).min().getAsInt();
                countOfNextLocationsMap.entrySet().removeIf(e -> e.getValue() != minCount);
                nextLocations = countOfNextLocationsMap.keySet().stream().filter(l -> !visitedLocations.contains(l)).collect(Collectors.toList());
                if (nextLocations.size() > 1) {
                    int min = 0;
                    int max = nextLocations.size();
                    int diff = max - min;
                    Random random = new Random();
                    int i = random.nextInt(diff);
                    location = nextLocations.get(i);
                } else {
                    location = nextLocations.get(0);
                }
                visitedLocations.add(location);
                System.out.println(visitedLocations);
            }
            StringBuilder sb = new StringBuilder();
            visitedLocations.forEach(l -> sb.append(l).append(" -> "));
            resultText.setText(sb.substring(0, sb.toString().length()-3));
        }  catch (Exception ex) {
            try{
                location = firstLocation;
                visitedLocations.clear();
                visitedLocations.add(location);
                System.out.println("____________________________________________________________________________________");
                calculateTripForHorse();
            } catch (StackOverflowError err){
                System.out.println("Кінь не може пройти всю дошку з положення " + firstLocation);
                resultText.setText("Кінь не може пройти всю дошку з положення ");
                return;
            }

        }
    }

    private void calculateTripForElephant() {
        try {
            while (visitedLocations.size() != matrixSize * matrixSize) {
                List<Integer> nextLocations = locationElephantMap.get(location);
                Map<Integer, Integer> countOfNextLocationsMap = new HashMap<>();
                for (Integer loc : nextLocations) {
                    Integer count = locationElephantMap.get(loc).size();
                    for (Integer l : locationElephantMap.get(loc)) {
                        if (visitedLocations.contains(l)) count--;
                    }
                    if (!visitedLocations.contains(loc)) countOfNextLocationsMap.put(loc, count);
                }
                int minCount = countOfNextLocationsMap.entrySet().stream().mapToInt(e -> e.getValue()).min().getAsInt();
                countOfNextLocationsMap.entrySet().removeIf(e -> e.getValue() != minCount);
                nextLocations = countOfNextLocationsMap.keySet().stream().filter(l -> !visitedLocations.contains(l)).collect(Collectors.toList());
                if (nextLocations.size() > 1) {
                    int min = 0;
                    int max = nextLocations.size();
                    int diff = max - min;
                    Random random = new Random();
                    int i = random.nextInt(diff);
                    location = nextLocations.get(i);
                } else {
                    location = nextLocations.get(0);
                }
                visitedLocations.add(location);
                System.out.println(visitedLocations);
            }
            StringBuilder sb = new StringBuilder();
            visitedLocations.forEach(l -> sb.append(l).append(" -> "));
            resultText.setText(sb.substring(0, sb.toString().length()-3));
        }  catch (Exception ex) {
            try{
                location = firstLocation;
                visitedLocations.clear();
                visitedLocations.add(location);
                System.out.println("____________________________________________________________________________________");
                calculateTripForElephant();
            } catch (StackOverflowError err){
                System.out.println("Слон не може пройти всю дошку з положення " + firstLocation);
                resultText.setText("Слон не може пройти всю дошку з положення ");
                return;
            }

        }
    }

    public void refreshBoard(ActionEvent actionEvent) {
        drawBoard();
    }
}
