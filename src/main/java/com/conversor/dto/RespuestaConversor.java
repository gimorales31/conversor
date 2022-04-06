package com.conversor.dto;

public class RespuestaConversor {
	
	private double monto;
	private double montoTipoCambio;
	private String monedaOrigen;
	private String monedaDestino;
	private String tipoCambio;
	
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public double getMontoTipoCambio() {
		return montoTipoCambio;
	}
	public void setMontoTipoCambio(double montoTipoCambio) {
		this.montoTipoCambio = montoTipoCambio;
	}
	public String getMonedaOrigen() {
		return monedaOrigen;
	}
	public void setMonedaOrigen(String monedaOrigen) {
		this.monedaOrigen = monedaOrigen;
	}
	public String getMonedaDestino() {
		return monedaDestino;
	}
	public void setMonedaDestino(String monedaDestino) {
		this.monedaDestino = monedaDestino;
	}
	public String getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	
	 
    
}
