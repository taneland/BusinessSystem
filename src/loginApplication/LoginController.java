package loginApplication;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import admin.AdminController;
import employees.EmployeeController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	LoginModel loginModel = new LoginModel();
	@FXML
	private Label dbStatus;
	
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private ComboBox<Option> combobox;
	@FXML 
	private Button login;
	@FXML
	private Label loginstatus;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(this.loginModel.isDatabaseConnected()){
			this.dbStatus.setText("Connected");
			this.dbStatus.setTextFill(Color.GREEN);
		}else{
			this.dbStatus.setText("Not Connected");
			this.dbStatus.setTextFill(Color.RED);
		}
		this.combobox.setItems(FXCollections.observableArrayList(Option.values()));
	}
	@FXML
	public void Login(ActionEvent event){
		
		try{
			this.combobox.setValue(Option.Admin);
			if(this.combobox.getValue() == null){
				this.loginstatus.setText("Enter Admin or Employee");
				this.loginstatus.setTextFill(Color.RED);
				return;
			}
			if(this.loginModel.isLogin(this.username.getText(), this.password.getText(), ((Option)this.combobox.getValue()).toString())){
				
				
				Stage stage = (Stage)this.login.getScene().getWindow();
				stage.close();
				
				switch (((Option)this.combobox.getValue()).toString()) {
				case "Admin":
					
					adminLogin();
					break;
				case "Employee":
					employeeLogin();
					break;
				}
			}else{
				this.loginstatus.setText("Wrong Creditials");
				this.loginstatus.setTextFill(Color.RED);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void employeeLogin(){
		Stage employeeStage = new Stage();
	
		try {		
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/employees/employee.fxml"));
			
			Scene scene = new Scene(root);
			employeeStage.setScene(scene);
			employeeStage.setTitle("Product Manager");
			employeeStage.setResizable(false);
			employeeStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void adminLogin(){
		Stage adminStage = new Stage();
		
		try {
			
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("/admin/admin.fxml"));
			
			Scene scene = new Scene(root);
			adminStage.setScene(scene);
			adminStage.setTitle("Product Manager");
			adminStage.setResizable(false);
			adminStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
