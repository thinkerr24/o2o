package com.rr.o2o.exceptions;

public class ProductOperationException extends RuntimeException{
	private static final long serialVersionUID = -105578621629284750L;
	public ProductOperationException(String msg) {
		super(msg);
	}
}
