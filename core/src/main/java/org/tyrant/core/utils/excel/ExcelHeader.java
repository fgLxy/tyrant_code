package org.tyrant.core.utils.excel;

import java.lang.reflect.Field;

public class ExcelHeader {
	private String title;
	private Field field;
	
	public ExcelHeader(String title, Field field) {
		this.title = title;
		this.field = field;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	
}