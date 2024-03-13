package service;

import java.util.List;

import db.CuentaDAO;
import entidades.Cuenta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.SaldoInsuficienteException;
import excepciones.ServicioException;
import excepciones.UsuarioNoExistenteException;

public class CuentaService {

	    private CuentaDAO cuentaDAO;

	    public CuentaService(CuentaDAO cuentaDAO) {
	        this.cuentaDAO = cuentaDAO;
	    }

		public void crearCuenta(Cuenta cuenta) throws ServicioException, UsuarioNoExistenteException, DuplicadoException{
	        try {
				cuentaDAO.crearCuenta(cuenta);
			} catch (DAOException e) {
				throw new ServicioException(e);
			} 
	    }

	    public void borrarCuenta(int numeroCuenta) throws ServicioException{
	        try {
				cuentaDAO.borrarCuenta(numeroCuenta);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }
	    
	    public void borrarCuentaPorDNI(int dni) throws ServicioException{
	        try {
				cuentaDAO.borrarCuentaPorDNI(dni);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }

	    public void actualizarCuenta(Cuenta cuenta) throws ServicioException{
	        try {
				cuentaDAO.actualizarCuenta(cuenta);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }

	    public Cuenta mostrarCuenta(int dni) throws ServicioException{
	        try {
				return cuentaDAO.mostrarCuenta(dni);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }
	    
	    public List<Cuenta> listarCuentasUsuario(int dni) throws ServicioException{
	        try {
				return cuentaDAO.listarCuentasUsuario(dni);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }

	    public List<Cuenta> listarTodasLasCuentas(int dni) throws ServicioException {
	        try {
				return cuentaDAO.listarTodasLasCuentas(dni);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }

	    public void debitarCuenta(int dni, double saldo) throws ServicioException, SaldoInsuficienteException {
	        try {
				cuentaDAO.debitarCuenta(dni, saldo);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
		}
	    

	    public void acreditarCuenta(int dni, double saldo) throws ServicioException {
	        try {
				cuentaDAO.acreditarCuenta(dni, saldo);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }

	    public Cuenta buscarCuentaPorID(int idCuenta) throws ServicioException {
	        try {
				return cuentaDAO.buscarCuentaPorID(idCuenta);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }

	    public void realizarTransferencia(Cuenta cuentaOrigen, int numeroCuentaDestino, double monto) throws ServicioException, SaldoInsuficienteException{
	        try {
				cuentaDAO.realizarTransferencia(cuentaOrigen, numeroCuentaDestino, monto);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
	    }
}
