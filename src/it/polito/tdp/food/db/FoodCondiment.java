package it.polito.tdp.food.db;

public class FoodCondiment {
	
	private Condiment condiment1;
	private Condiment condiment2;
	private int weight;
	
	public FoodCondiment(Condiment condiment1, Condiment condiment2, int weight) {
		super();
		this.condiment1 = condiment1;
		this.condiment2 = condiment2;
		this.weight = weight;
	}
	
	public Condiment getCondiment1() {
		return condiment1;
	}
	public void setCondiment1(Condiment condiment1) {
		this.condiment1 = condiment1;
	}
	public Condiment getCondiment2() {
		return condiment2;
	}
	public void setCondiment2(Condiment condiment2) {
		this.condiment2 = condiment2;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((condiment1 == null) ? 0 : condiment1.hashCode());
		result = prime * result + ((condiment2 == null) ? 0 : condiment2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodCondiment other = (FoodCondiment) obj;
		if (condiment1 == null) {
			if (other.condiment1 != null)
				return false;
		} else if (!condiment1.equals(other.condiment1))
			return false;
		if (condiment2 == null) {
			if (other.condiment2 != null)
				return false;
		} else if (!condiment2.equals(other.condiment2))
			return false;
		return true;
	}

}
