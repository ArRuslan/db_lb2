package me.ruslan.dblb2.forms.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import me.ruslan.dblb2.db.LimitedResult;
import me.ruslan.dblb2.db.ModelsSelectController;
import me.ruslan.dblb2.models.Category;
import me.ruslan.dblb2.models.Model;
import me.ruslan.dblb2.models.Product;
import me.ruslan.dblb2.models.User;

import java.sql.SQLException;

public class AllTablesController {
    private int usersPage;
    private int categoriesPage;
    private int productsPage;

    @FXML
    public Pagination usersPagination;
    @FXML
    public Pagination categoriesPagination;
    @FXML
    public Pagination productsPagination;
    @FXML
    TableView<User> tableUsers;
    @FXML
    TableView<Category> tableCategories;
    @FXML
    TableView<Product> tableProducts;

    public void initialize() throws SQLException {
        setupPagination();

        initUsersTable();
        initCategoriesTable();
        initProductsTable();

        refreshData();
    }

    private void setupPagination() {
        Runnable refresh = () -> {
            try {
                refreshData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        };

        usersPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            usersPage = (int) newValue;
            refresh.run();
        });

        categoriesPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            categoriesPage = (int) newValue;
            refresh.run();
        });

        productsPagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
            productsPage = (int) newValue;
            refresh.run();
        });
    }

    private void setFactory(TableView table) {
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

            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu));
            return row;
        });
    }

    private void loadUsersData() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();
        tableUsers.setItems(users);

        LimitedResult<User> res = ModelsSelectController.getUsersLim(10, 10 * usersPage);
        usersPagination.setPageCount((int)Math.ceil(res.count / 10.0));
        users.addAll(res.objects);
    }

    private void loadCategoriesData() throws SQLException {
        ObservableList<Category> categories = FXCollections.observableArrayList();
        tableCategories.setItems(categories);

        LimitedResult<Category> res = ModelsSelectController.getCategoriesLim(10, 10 * categoriesPage);
        categoriesPagination.setPageCount((int)Math.ceil(res.count / 10.0));
        categories.addAll(res.objects);
    }

    private void loadProductsData() throws SQLException {
        ObservableList<Product> products = FXCollections.observableArrayList();
        tableProducts.setItems(products);

        LimitedResult<Product> res = ModelsSelectController.getProductsLim(10, 10 * productsPage);
        productsPagination.setPageCount((int)Math.ceil(res.count / 10.0));
        products.addAll(res.objects);
    }

    private void initUsersTable() {
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

        tableUsers.getColumns().addAll(id_cell, fn_cell, ln_cell, em_cell, pn_cell, pw_cell);
        setFactory(tableUsers);
    }

    private void initCategoriesTable() {
        TableColumn<Category, Integer> id_cell = new TableColumn<>("Id");
        TableColumn<Category, String> nm_cell = new TableColumn<>("Name");

        id_cell.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getId()).asObject());
        nm_cell.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));

        tableCategories.getColumns().addAll(id_cell, nm_cell);
        setFactory(tableCategories);
    }

    private void initProductsTable() {
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

        tableProducts.getColumns().addAll(id_cell, ci_cell, cn_cell, nm_cell, ds_cell, iu_cell, pr_cell, qt_cell);
        setFactory(tableProducts);
    }

    public void refreshData() throws SQLException {
        loadUsersData();
        loadCategoriesData();
        loadProductsData();
    }

    public void close() {
        ((Stage)tableUsers.getScene().getWindow()).close();
    }
}
