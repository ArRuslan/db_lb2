package me.ruslan.dblb1;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import me.ruslan.dblb1.db.ModelsSelectController;
import me.ruslan.dblb1.editForms.CategoryCUForm;
import me.ruslan.dblb1.editForms.ProductCUForm;
import me.ruslan.dblb1.editForms.UserCUForm;
import me.ruslan.dblb1.models.Category;
import me.ruslan.dblb1.models.Model;
import me.ruslan.dblb1.models.Product;
import me.ruslan.dblb1.models.User;

import java.sql.SQLException;

public class MainFormController {
    private String currentTable = "users";

    @FXML
    public Pane mainPane;
    public TableView table = null;
    @FXML
    public ChoiceBox<String> tablesChoiceBox;

    public void initialize() throws SQLException {
        tablesChoiceBox.getItems().addAll("Users", "Categories", "Products");
        tablesChoiceBox.setValue("Users");
        tablesChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentTable = newValue.toLowerCase();
            try {
                setTable();
                refreshData();
            } catch(SQLException e) {}
        });

        setTable();
    }

    private void setFactory() {
        table.setRowFactory(tableView -> {
            final TableRow<Model> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem removeItem = new MenuItem("Delete");
            removeItem.setOnAction(event -> {
                try {
                    row.getItem().delete();
                    table.getItems().remove(row.getItem());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            rowMenu.getItems().addAll(removeItem);

            row.setOnMouseClicked(event -> {
                if(event.getClickCount() != 2 || row.isEmpty())
                    return;
                switch (currentTable) {
                    case "categories": {
                        new CategoryCUForm((Category) row.getItem()).show(mainPane.getScene().getWindow());
                        break;
                    }
                    case "products": {
                        new ProductCUForm((Product) row.getItem()).show(mainPane.getScene().getWindow());
                        break;
                    }
                    case "users":
                    default: {
                        new UserCUForm((User)row.getItem()).show(mainPane.getScene().getWindow());
                        break;
                    }
                }
                try {
                    refreshData();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu));
            return row;
        });
    }

    private void destroyTable() {
        if(table != null)
            mainPane.getChildren().remove(table);
    }

    private void createTable() {
        table.setPrefHeight(224);
        table.setPrefWidth(688);
        mainPane.getChildren().add(table);
        setFactory();
    }

    private void setTable() throws SQLException {
        switch (currentTable) {
            case "categories": {
                createCategoriesTable();
                break;
            }
            case "products": {
                createProductsTable();
                break;
            }
            case "users":
            default: {
                createUsersTable();
                break;
            }
        }
    }

    public void refreshData() throws SQLException {
        switch (currentTable) {
            case "categories": {
                loadCategoriesData();
                break;
            }
            case "products": {
                loadProductsData();
                break;
            }
            case "users":
            default: {
                loadUsersData();
                break;
            }
        }
    }

    public void createObj() {
        switch (currentTable) {
            case "categories": {
                new CategoryCUForm(new Category(-1, "New category"), true)
                        .show(mainPane.getScene().getWindow());
                break;
            }
            case "products": {
                new ProductCUForm(new Product(-1, 0, "New product", "New product", null, 0, 0), true)
                        .show(mainPane.getScene().getWindow());
                break;
            }
            case "users":
            default: {
                new UserCUForm(new User(-1, "First name", "Last name", "password", "email@example.com", 380000000000L), true)
                        .show(mainPane.getScene().getWindow());
                break;
            }
        }
        try {
            refreshData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUsersData() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        ((TableView<User>)table).setItems(users);
        users.addAll(ModelsSelectController.getUsers());
    }

    private void loadCategoriesData() throws SQLException {
        ObservableList<Category> categories = FXCollections.observableArrayList();
        ((TableView<Category>)table).setItems(categories);
        categories.addAll(ModelsSelectController.getCategories());
    }

    private void loadProductsData() throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        ((TableView<Product>)table).setItems(products);
        products.addAll(ModelsSelectController.getProducts());
    }

    private void createUsersTable() throws SQLException {
        destroyTable();
        table = new TableView<User>();
        createTable();

        TableColumn<User, Integer> id_cell = new TableColumn<>("Id");
        TableColumn<User, String> fn_cell = new TableColumn<>("First name");
        TableColumn<User, String> ln_cell = new TableColumn<>("Last name");
        TableColumn<User, String> em_cell = new TableColumn<>("Email");
        TableColumn<User, String> pw_cell = new TableColumn<>("Password");
        TableColumn<User, Long> pn_cell = new TableColumn<>("Phone number");

        id_cell.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getId()).asObject());
        fn_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getFirstName()));
        ln_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getLastName()));
        em_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getEmail()));
        pw_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getPassword()));
        pn_cell.setCellValueFactory(p -> new SimpleLongProperty(p.getValue().getPhoneNumber()).asObject());

        table.getColumns().addAll(id_cell, fn_cell, ln_cell, em_cell, pn_cell, pw_cell);

        loadUsersData();
    }

    private void createCategoriesTable() throws SQLException {
        destroyTable();
        table = new TableView<Category>();
        createTable();

        TableColumn<Category, Integer> id_cell = new TableColumn<>("Id");
        TableColumn<Category, String> nm_cell = new TableColumn<>("Name");

        id_cell.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getId()).asObject());
        nm_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));

        table.getColumns().addAll(id_cell, nm_cell);

        loadCategoriesData();
    }

    private void createProductsTable() throws SQLException {
        destroyTable();
        table = new TableView<Product>();
        createTable();

        TableColumn<Product, Integer> id_cell = new TableColumn<>("Id");
        TableColumn<Product, String> ci_cell = new TableColumn<>("Category Id");
        TableColumn<Product, String> cn_cell = new TableColumn<>("Category Name");
        TableColumn<Product, String> nm_cell = new TableColumn<>("Name");
        TableColumn<Product, String> ds_cell = new TableColumn<>("Description");
        TableColumn<Product, String> iu_cell = new TableColumn<>("Image url");
        TableColumn<Product, Integer> pr_cell = new TableColumn<>("Price");
        TableColumn<Product, Integer> qt_cell = new TableColumn<>("Quantity");

        id_cell.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getId()).asObject());
        ci_cell.setCellValueFactory(p -> {
            int cid = p.getValue().getCategoryId();
            return new SimpleStringProperty(cid == 0 ? "<NULL>" : cid+"");
        });
        cn_cell.setCellValueFactory(p -> {
            Category c = p.getValue().getCategory();
            return new SimpleStringProperty(c == null ? "<NULL>" : c.getName());
        });
        nm_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));
        ds_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDescription()));
        iu_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getImageUrl()));
        pr_cell.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getPrice()).asObject());
        qt_cell.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getQuantity()).asObject());

        table.getColumns().addAll(id_cell, ci_cell, cn_cell, nm_cell, ds_cell, iu_cell, pr_cell, qt_cell);

        loadProductsData();
    }
}