package com.fusionx.lending.origination.exception;

public class ListRecordPrimitiveValidateException  extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private final String path;
	private final String listName;
	private final int index;
	private final String field;
	
	public ListRecordPrimitiveValidateException(String exception,String path, String listName, int index, String field) {
		super(exception);
		this.path = path;
		this.listName = listName;
		this.index = index;
		this.field = field;
	}

	public String getPath() {
		return path;
	}

	public String getListName() {
		return listName;
	}

	public int getIndex() {
		return index;
	}

	public String getField() {
		return field;
	}
	
	
	

	

}

