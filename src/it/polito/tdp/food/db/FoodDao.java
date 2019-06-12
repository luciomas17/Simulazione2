package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FoodDao {

	public List<Food> listAllFood(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getInt("portion_default"), 
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"),
							res.getDouble("calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiment(){
		String sql = "SELECT * FROM condiment ORDER" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_id"),
							res.getInt("food_code"),
							res.getString("display_name"), 
							res.getString("condiment_portion_size"), 
							res.getDouble("condiment_calories")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}

	}

	public Map<Integer, Condiment> mapCondimentByCalories(double calories){
		
		String sql = "SELECT * FROM condiment WHERE condiment_calories <= ? ORDER BY condiment_calories DESC";
		Map<Integer, Condiment> map = new HashMap<>();
				
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, calories);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				try {
					map.put(res.getInt("condiment_id"), new Condiment(res.getInt("condiment_id"), res.getInt("food_code"),
							res.getString("display_name"), res.getString("condiment_portion_size"), res.getDouble("condiment_calories")));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return map;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<FoodCondiment> findEdges(Map<Integer, Condiment> condimentIdMap, double calories) {
		
		String sql = "SELECT c1.condiment_id AS condiment1, c2.condiment_id AS condiment2, COUNT(fc1.food_code) AS weight " + 
				"FROM food_condiment fc1, food_condiment fc2, condiment c1, condiment c2 " + 
				"WHERE fc1.food_code = fc2.food_code AND fc1.condiment_food_code != fc2.condiment_food_code " + 
				"AND fc1.condiment_food_code = c1.food_code AND fc2.condiment_food_code = c2.food_code " + 
				"AND c1.condiment_calories <= ? AND c2.condiment_calories <= ? " + 
				"GROUP BY condiment1, condiment2";
		List<FoodCondiment> result = new ArrayList<>();
				
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, calories);
			st.setDouble(2, calories);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				try {
					Condiment c1 = condimentIdMap.get(res.getInt("condiment1"));
					Condiment c2 = condimentIdMap.get(res.getInt("condiment2"));
					int weight = res.getInt("weight");
					
					result.add(new FoodCondiment(c1, c2, weight));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
