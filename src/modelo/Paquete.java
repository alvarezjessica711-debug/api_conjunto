package modelo;

import java.sql.Timestamp;

public class Paquete {
    private int idPaquete;
    private String empresaTransportadora;
    private String descripcion;
    private String estado;
    private Timestamp fechaRecepcion;
    private Timestamp fechaEntrega;
    private int idUsuarioDestinatario;
    private int idApartamento;
    private int idUsuarioRegistra;

    public Paquete() {
    }

    public Paquete(int idPaquete, String empresaTransportadora, String descripcion, String estado,
                   Timestamp fechaRecepcion, Timestamp fechaEntrega, int idUsuarioDestinatario,
                   int idApartamento, int idUsuarioRegistra) {
        this.idPaquete = idPaquete;
        this.empresaTransportadora = empresaTransportadora;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaRecepcion = fechaRecepcion;
        this.fechaEntrega = fechaEntrega;
        this.idUsuarioDestinatario = idUsuarioDestinatario;
        this.idApartamento = idApartamento;
        this.idUsuarioRegistra = idUsuarioRegistra;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public String getEmpresaTransportadora() {
        return empresaTransportadora;
    }

    public void setEmpresaTransportadora(String empresaTransportadora) {
        this.empresaTransportadora = empresaTransportadora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Timestamp getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Timestamp fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Timestamp getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Timestamp fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public int getIdUsuarioDestinatario() {
        return idUsuarioDestinatario;
    }

    public void setIdUsuarioDestinatario(int idUsuarioDestinatario) {
        this.idUsuarioDestinatario = idUsuarioDestinatario;
    }

    public int getIdApartamento() {
        return idApartamento;
    }

    public void setIdApartamento(int idApartamento) {
        this.idApartamento = idApartamento;
    }

    public int getIdUsuarioRegistra() {
        return idUsuarioRegistra;
    }

    public void setIdUsuarioRegistra(int idUsuarioRegistra) {
        this.idUsuarioRegistra = idUsuarioRegistra;
    }
}
