package com.conversor.dto;

import java.util.List;

public class ResponseValidacion {
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getExcepcion() {
		return excepcion;
	}
	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}
	public List<ResponseDetalleValidacion> getError() {
		return error;
	}
	public void setError(List<ResponseDetalleValidacion> error) {
		this.error = error;
	}
	private String codigo;
	private String excepcion;
	private List<ResponseDetalleValidacion> error;

}
