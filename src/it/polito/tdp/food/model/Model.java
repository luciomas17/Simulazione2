package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Condiment;
import it.polito.tdp.food.db.FoodCondiment;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	public class ComparatorOfCondimentsByCalories implements Comparator<Condiment> {
		@Override
		public int compare(Condiment c1, Condiment c2) {
			return (int)(c2.getCondiment_calories() - c1.getCondiment_calories());
		}
	}
	
	private FoodDao dao;
	private Graph<Condiment, DefaultWeightedEdge> graph;
	private Map<Integer, Condiment> condimentIdMap;
	private List<FoodCondiment> edges;
	
	public Model() {
		this.dao = new FoodDao();
		this.condimentIdMap = new HashMap<>();
	}
	
	public void createGraph(double calories) {
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		condimentIdMap = dao.mapCondimentByCalories(calories);
		Graphs.addAllVertices(graph, condimentIdMap.values());
		
		edges = dao.findEdges(condimentIdMap, calories);
		for(FoodCondiment fc : edges)
			Graphs.addEdge(graph, fc.getCondiment1(), fc.getCondiment2(), fc.getWeight());
		
		System.out.println("Grafo creato.");
		System.out.format("%d vertici e %d archi.\n", graph.vertexSet().size(), graph.edgeSet().size());
	}
	
	public int countNumberOfFoods(Condiment c) {
		int count = 0;
		
		List<Condiment> neighbors = Graphs.neighborListOf(graph, c);
		for(Condiment n : neighbors) {
			for(FoodCondiment fc : edges) {
				if(n.equals(fc.getCondiment1()) && c.equals(fc.getCondiment2()))
					count += fc.getWeight();
			}
		}
		
		return count;
	}
	
	public List<Condiment> getOrderedCondimentList() {
		List<Condiment> list = new ArrayList<>();
		
		for(Condiment c : condimentIdMap.values()) {
			int numOfFoods = countNumberOfFoods(c);
			list.add(new Condiment(c.getDisplay_name(), c.getCondiment_calories(), numOfFoods));
		}
		
		Collections.sort(list, new ComparatorOfCondimentsByCalories());
		
		return list;
	}

}
