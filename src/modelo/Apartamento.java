package modelo;

public class Apartamento {
    private int idApartamento;
    private String torre;
    private String numero;

    public Apartamento() {
    }

    public Apartamento(int idApartamento, String torre, String numero) {
        this.idApartamento = idApartamento;
        this.torre = torre;
        this.numero = numero;
    }

    public int getIdApartamento() {
        return idApartamento;
    }

    public void setIdApartamento(int idApartamento) {
        this.idApartamento = idApartamento;
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
