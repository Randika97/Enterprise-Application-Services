package com.misaka.stores.common;

public class DataAccessException extends Exception {

	

	private static final long serialVersionUID = 6982024227128575579L;

	public static final int CONCURRENT_MODIFICATION = 1;

	private String id = null;

	private int rootState = 0;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String id, String message) {
		super(message);
		this.id = id;
	}

	public DataAccessException(String id, String message, Throwable rootCause) {
		super(message + " Root Cause: " + rootCause.getMessage());
		this.id = id;
	}

	public DataAccessException(String id, String message, Throwable rootCause,
			int rootState) {
		this(id, message, rootCause);
		this.rootState = rootState;
		this.id = id;
	}

	/**
	 * @return
	 * @uml.property name="rootState"
	 */
	public int getRootState() {
		return rootState;
	}

	/**
	 * @return
	 * @uml.property name="id"
	 */
	public String getId() {
		return id;
	}
}
