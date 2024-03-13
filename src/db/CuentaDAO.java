package db;

import java.util.List;

import entidades.Cuenta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.SaldoInsuficienteException;
import excepciones.UsuarioNoExistenteException;

public interface CuentaDAO {

    void crearCuenta(Cuenta cuenta) throws DAOException, DuplicadoException, UsuarioNoExistenteException;

    void borrarCuenta(int dni) throws DAOException;

    void actualizarCuenta(Cuenta cuenta) throws DAOException;

    void debitarCuenta(int dni, double saldo) throws DAOException, SaldoInsuficienteException;

    void acreditarCuenta(int dni, double saldo) throws DAOException;

    Cuenta mostrarCuenta(int dni) throws DAOException;

    List<Cuenta> listarTodasLasCuentas(int dni) throws DAOException;

    List<Cuenta> listarCuentasUsuario(int dni) throws DAOException;
    
    void realizarTransferencia(Cuenta cuentaOrigen, int numeroCuentaDestino, double monto) throws DAOException, SaldoInsuficienteException;

    Cuenta buscarCuentaPorID(int idcuenta) throws DAOException;
    
    public void borrarCuentaPorDNI(int dni) throws DAOException;
}
