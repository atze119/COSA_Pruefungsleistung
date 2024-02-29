package de.leuphana.cosa.printingsystem.structure;

import java.time.LocalDate;

public class PrintReport {
	private String name;
//	private PrintOptions printOptions;
//	private String printableTitle;
	private LocalDate printDate;
	private double price;
	private String content;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrintDate(LocalDate printDate) {
		this.printDate = printDate;
	}
	
	public LocalDate getPrintDate() {
		return printDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
