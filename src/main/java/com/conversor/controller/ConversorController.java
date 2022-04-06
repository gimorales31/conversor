package com.conversor.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.conversor.dto.ConversorMoneda;
import com.conversor.dto.ResponseDetalleValidacion;
import com.conversor.dto.ResponseValidacion;
import com.conversor.dto.RespuestaConversor;
import com.conversor.model.TipoCambio;
import com.conversor.repository.TipoCambioRepository;
import com.conversor.security.JwtTokenUtil;
import com.conversor.security.JwtUser;
import com.conversor.util.Constante;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/conversor")
public class ConversorController {

    @Value("${jwt.header}")
    private String tokenHeader;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TipoCambioRepository tipoCambioRepo;

    @PostMapping(value = "/tipoCambio")
    @PreAuthorize("hasRole('ADMIN')")
    public Object addTipoCambio(@RequestBody TipoCambio tipoCambio, HttpServletRequest request) {
        if (obtenerRolAdmin(request)) {
            return tipoCambioRepo.save(tipoCambio);
        } else {
            return ResponseEntity.status(401).body("Este Recurso está Protegido");
        }
    }

    @GetMapping(value = "/tipoCambio")
    @PreAuthorize("hasRole('ADMIN')")
    public Object getAllTipoCambio(HttpServletRequest request) {
        if (obtenerRolAdmin(request)) {
            return tipoCambioRepo.findAll();
        } else {
            return ResponseEntity.status(401).body("Este Recurso está Protegido");
        }
    }

    @PutMapping(value = "/tipoCambio")
    @PreAuthorize("hasRole('ADMIN')")
    public Object update(@RequestBody TipoCambio tipoCambio, HttpServletRequest request) {
        if (obtenerRolAdmin(request)) {
            return tipoCambioRepo.save(tipoCambio);
        } else {
            return ResponseEntity.status(401).body("Este Recurso está Protegido");
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public Object calcularTipoCambio(@RequestBody ConversorMoneda conversor, HttpServletRequest request) {
        if (obtenerRolUser(request)) {
            Double valorTC = 0.00;
            List<TipoCambio> tc = tipoCambioRepo.findAll();
            for (TipoCambio tipoCambio2 : tc) {
                if (tipoCambio2.getMonedaOrigen().equals(conversor.getMonedaOrigen())
                        && tipoCambio2.getMonedaDestino().equals(conversor.getMonedaDestino())) {
                    valorTC = tipoCambio2.getTipoCambio();
                }
            }
            return armarRespuesta(valorTC, conversor);
        } else {
            return ResponseEntity.status(401).body("Este Recurso está Protegido");
        }
    }

    private Object armarRespuesta(Double valorTC, ConversorMoneda conversor) {
        RespuestaConversor rptaConversor = new RespuestaConversor();
        ResponseDetalleValidacion detalleError = new ResponseDetalleValidacion();
        List<ResponseDetalleValidacion> listError = new ArrayList<ResponseDetalleValidacion>();
        if (valorTC != 0.00) {
            rptaConversor.setMonto(conversor.getMonto());
            rptaConversor.setMontoTipoCambio(valorTC * conversor.getMonto());
            rptaConversor.setMonedaOrigen(conversor.getMonedaOrigen());
            rptaConversor.setMonedaDestino(conversor.getMonedaDestino());
            rptaConversor.setTipoCambio(valorTC.toString());
            return rptaConversor;
        } else {

            detalleError.setCod(Constante.codError100);
            detalleError.setMsg(Constante.msgError100);
            listError.add(detalleError);
            return ResponseEntity.status(Integer.parseInt(Constante.statusCode422)).body(response(listError));
        }
    }

    private ResponseValidacion response(List<ResponseDetalleValidacion> listResponseDetalle) {
        ResponseValidacion response = new ResponseValidacion();
        response.setCodigo(Constante.statusCode422);
        response.setExcepcion(Constante.genericMessage422);
        response.setError(listResponseDetalle);
        return response;
    }

    public boolean obtenerRolAdmin(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        for (Object object : user.getAuthorities()) {
            if (object.toString().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    public boolean obtenerRolUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        for (Object object : user.getAuthorities()) {
            if (object.toString().equals("ROLE_USER")) {
                return true;
            }
        }
        return false;
    }

}
