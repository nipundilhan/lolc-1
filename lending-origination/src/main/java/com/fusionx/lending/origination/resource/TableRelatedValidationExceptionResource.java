package com.fusionx.lending.origination.resource;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TableRelatedValidationExceptionResource {
	
	private String tableName;
	private String column;
	private Long rowId;
	private String field;
	private String value;
	private String message;
	
	
	public TableRelatedValidationExceptionResource(String tableName, String column, Long rowId, String field,String value,
			String message) {
		//super();
		this.tableName = tableName;
		this.column = column;
		this.rowId = rowId;
		this.field = field;
		this.value =  value;
		this.message = message;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getColumn() {
		return column;
	}


	public void setColumn(String column) {
		this.column = column;
	}


	public Long getRowId() {
		return rowId;
	}


	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
