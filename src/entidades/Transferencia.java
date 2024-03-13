package entidades;

import db.CuentaDAO;
import excepciones.DAOException;
import excepciones.SaldoInsuficienteException;
import excepciones.SaldonegativoException;

public class Transferencia {
    private Cuenta cuentaOrigen;
    private Cuenta cuentaDestino;
    private CuentaDAO cuentaDAO;

    public Transferencia() {
    }

    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }

    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaOrigen(Cuenta cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public void setCuentaDestino(Cuenta cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }


}