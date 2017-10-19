package admin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import dbUtils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AdminController implements Initializable{

	//Employee Fields
	@FXML
	private TextField id;
	@FXML
	private TextField firstname;
	@FXML
	private TextField lastname;
	@FXML
	private TextField email;
	@FXML
	private DatePicker dob;	
	@FXML
	private TableView<EmployeeData> employeetable;
	@FXML
	private TableColumn<EmployeeData, String> idcolumn;
	@FXML
	private TableColumn<EmployeeData, String> firstnamecolumn;
	@FXML
	private TableColumn<EmployeeData, String> lastnamecolumn;
	@FXML
	private TableColumn<EmployeeData, String> emailcolumn;
	@FXML
	private TableColumn<EmployeeData, String> dobcolumn;

	//Product Fields

	@FXML
	private TextField productname;
	@FXML
	private TextField productlenght;
	@FXML
	private TableView<ProductData> producttable;

	@FXML
	private TableColumn<ProductData, String> productnamecolumn;
	@FXML
	private TableColumn<ProductData, String> productlenghtcolumn;


	private ObservableList<EmployeeData> employeeData;
	private ObservableList<ProductData> productData;
	private String employeeSql = "SELECT * FROM Employees";
	private String productSql = "SELECT * FROM Products";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		onMouseClicked();
	}
	@FXML
	public void loadEmployeeData(ActionEvent event){

		try {
			Connection connection = DatabaseConnection.getConnection();
			this.employeeData = FXCollections.observableArrayList();

			ResultSet result = connection.createStatement().executeQuery(employeeSql);
			while(result.next()){
				this.employeeData.add(new EmployeeData(result.getString(1), result.getString(2), result.getString(3)
						, result.getString(4), result.getString(5)));
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		this.idcolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("ID"));
		this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("firstName"));
		this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("lastName"));
		this.emailcolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("email"));
		this.dobcolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData,String>("DOB"));

		this.employeetable.setItems(null);
		this.employeetable.setItems(this.employeeData);
	}
	@FXML 
	public void addEmployee(ActionEvent event){

		String sqlInsertEmployee = "INSERT INTO Employees(id,firstname,lastname,email,DoB) VALUES (?,?,?,?,?)";

		try {
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlInsertEmployee);
			
			
			statement.setString(1, this.id.getText());
			statement.setString(2, this.firstname.getText());
			statement.setString(3, this.lastname.getText());
			statement.setString(4, this.email.getText());
			statement.setString(5, this.dob.getEditor().getText());	

			statement.execute();
			statement.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	@FXML
	private void addProduct(ActionEvent event){
		String sqlInsertProduct = "INSERT INTO Products(productname,lenght) VALUES (?,?)";
		
		createNewTable();
		try{
			Connection connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sqlInsertProduct);

			statement.setString(1, this.productname.getText());
			statement.setString(2, this.productlenght.getText());

			statement.execute();
			statement.close();
		} catch (Exception e) {

			e.printStackTrace();

		}		
	}
	public void createNewTable() {

		// SQL statement for creating a new table
		String sql = "CREATE TABLE IF NOT EXISTS "+productname.getText() + "(\n"
			    
				+ "	lenght text\n"
				
				+ ");";

		Connection connection;
		try {
			connection = DatabaseConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql);


			statement.execute();
			statement.close();
		} catch (Exception e) {
			
			e.printStackTrace();
		}


	}


	@FXML
	private void clearEmployeeForm(ActionEvent event){
		this.id.setText("");
		this.firstname.setText("");
		this.lastname.setText("");
		this.email.setText("");
		this.dob.getEditor().setText("");
	}

	@FXML
	private void clearProductForm(ActionEvent event){

		this.productname.setText("");
		this.productlenght.setText("");
	}
	@FXML
	private void loadProductData(ActionEvent event){
		try{
			Connection connection = DatabaseConnection.getConnection();
			this.productData = FXCollections.observableArrayList();

			ResultSet result =  connection.createStatement().executeQuery(productSql);

			while(result.next()){
				this.productData.add(new ProductData(result.getString(1),result.getString(2)));
			}


		}catch(Exception e){
			e.printStackTrace();
		}
		

		this.productnamecolumn.setCellValueFactory(new PropertyValueFactory<ProductData,String>("name"));
		this.productlenghtcolumn.setCellValueFactory(new PropertyValueFactory<ProductData,String>("lenght"));

		this.producttable.setItems(null);
		this.producttable.setItems(this.productData);
	}

	@FXML
	private void onMouseClicked(){

		
		producttable.setOnMouseClicked(e -> {
			if(e.getClickCount() == 2 && (producttable.hasProperties())){
				productClicked();
			}
		});
	}
	private void productClicked(){
		Stage productStage = new Stage();

		try {

			ObservableList<ProductData> rowItems = producttable.getSelectionModel().getSelectedItems();
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/product/product.fxml"));

			Scene scene = new Scene(root);
			productStage.setScene(scene);
			productStage.setTitle(rowItems.get(0).getName());

			productStage.setResizable(false);
			productStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
