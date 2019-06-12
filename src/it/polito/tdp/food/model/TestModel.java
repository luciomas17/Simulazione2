package it.polito.tdp.food.model;

import java.util.List;
import it.polito.tdp.food.db.Condiment;

public class TestModel {
	public static void main(String[] args) {
		
		Model model = new Model();
		
		double calorie = 50;
		
		model.createGraph(calorie);
		
		/* List<Condiment> condiments = model.getOrderedCondimentList();
		
		for(Condiment c : condiments)
			System.out.format("Ingrediente: %s, Calorie: %f, Numero di cibi: %d\n", c.getDisplay_name(), c.getCondiment_calories(),
					c.getNumOfFoods()); */
		
		List<Condiment> vertexList = model.getVertexList();
		
		List<Condiment> bestDiet = model.findBestDiet(vertexList.get(18), calorie);
		
		
		System.out.println("\n\n" + bestDiet);
		System.out.println(model.getBestCaloriesCount());
	}
}
