/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="boxIngrediente"
    private ComboBox<Condiment> boxIngrediente; // Value injected by FXMLLoader

    @FXML // fx:id="btnDietaEquilibrata"
    private Button btnDietaEquilibrata; // Value injected by FXMLLoader
    
    @FXML
    private TextArea txtResult;


    @FXML
    void doCalcolaDieta(ActionEvent event) {
    	txtResult.clear();
    	
    	if(model.getVertexListWithNumOfFoods().isEmpty()) {
    		txtResult.appendText("Creare prima grafo ingredienti.");
    		return;
    	}
    	
    	Condiment c;
    	if(boxIngrediente.getSelectionModel().isEmpty()) {
    		txtResult.appendText("Selezionare un ingrediente di partenza.");
    		return;
    	} else
    		c = boxIngrediente.getSelectionModel().getSelectedItem();
    	
    	try {
			double calories = Double.parseDouble(txtCalorie.getText());
			
			model.findBestDiet(c, calories);
			
			txtResult.appendText("Dieta migliore:\n\n");
			for(Condiment oth : model.getBestDiet())
				txtResult.appendText(oth + "\n");
			txtResult.appendText(String.format("\nTotale calorie: %.2f cal", model.getBestCaloriesCount()));
			
		} catch (NumberFormatException e) {
			txtResult.appendText("Inserire un numero reale di calorie valido.");
			e.printStackTrace();
		}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	
    	try {
			double calories = Double.parseDouble(txtCalorie.getText());
			
			model.createGraph(calories);
			
			List<Condiment> condiments = model.getVertexListWithNumOfFoods();
			
			for(Condiment c : condiments)
				txtResult.appendText(String.format("%s (%.2f cal) - %d cibi\n", c.getDisplay_name(), c.getCondiment_calories(),
						c.getNumOfFoods()));
			
			addItemsToBoxIngrediente();
			
		} catch (NumberFormatException e) {
			txtResult.appendText("Inserire un numero reale di calorie valido.");
			e.printStackTrace();
		}

    }

    private void addItemsToBoxIngrediente() {
		boxIngrediente.getItems().addAll(model.getVertexList());
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxIngrediente != null : "fx:id=\"boxIngrediente\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnDietaEquilibrata != null : "fx:id=\"btnDietaEquilibrata\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
