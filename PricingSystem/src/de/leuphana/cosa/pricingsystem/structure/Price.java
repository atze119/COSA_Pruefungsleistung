package de.leuphana.cosa.pricingsystem.structure;

public class Price {
	private double amount;
	private PriceGroup priceGroup;
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public PriceGroup getPriceGroup() {
		return priceGroup;
	}
	public void setPriceGroup(PriceGroup priceGroup) {
		this.priceGroup = priceGroup;
	}
	
	

}
