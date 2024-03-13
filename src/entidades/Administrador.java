package entidades;

import db.CuentaDAO;
import db.CuentaDAOH2;
import db.DBTableManager;
import db.TarjetaDAO;
import db.TarjetaDAOH2;
import db.UsuarioDAO;
import db.UsuarioDAOH2;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.ServicioException;
import excepciones.UsuarioNoExistenteException;

public class Administrador extends Usuario {

	public Administrador(int dni, String nombre, String clave) {
		super(dni, nombre, clave);
	}

	public Administrador() {
	}

	public Usuario crearUsuario(int dni, String nombre, String clave) throws ServicioException, DuplicadoException {
		UsuarioDAO dao = new UsuarioDAOH2();
		Usuario usuario1 = new Usuario();
		usuario1.setDni(dni);
		usuario1.setNombre(nombre);
		usuario1.setClave(clave);
		try {
			dao.crearUsuario(usuario1);
		}catch (DAOException e) {
			throw new ServicioException(e);
		}
		return usuario1;
	}

	public void modificarUsuario(Usuario usuario) throws ServicioException {
		UsuarioDAO dao = new UsuarioDAOH2();
		String nombreEdit = "Fariña";
		String claveEdit = "Argentina";
		usuario.setNombre(nombreEdit);
		usuario.setClave(claveEdit);
		try {
			dao.actualizaUsuario(usuario);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
		System.out.println(usuario);
	}

	public void eliminarUsuario(int dni, DBTableManager table) throws ServicioException {
		
			UsuarioDAO dao = new UsuarioDAOH2();
			CuentaDAO cuentaDAO= new CuentaDAOH2();
			try {
				dao.borraUsuario(dni);
				cuentaDAO.borrarCuentaPorDNI(dni);
			} catch (DAOException e) {
				throw new ServicioException(e);
			}
		

		table.deleteUserTable(dni);
	}

	public Cuenta crearCuenta(Usuario usuario, String tipo, int numeroCuenta, double saldo) throws DuplicadoException, UsuarioNoExistenteException, ServicioException {
		CuentaDAO dao = new CuentaDAOH2();
		Cuenta cuenta = new Cuenta();
		cuenta.setDni(usuario.getDni());
		cuenta.setTipo(tipo);
		cuenta.setNumeroCuenta(numeroCuenta);
		cuenta.setSaldo(saldo);
		usuario.agregarCuenta(cuenta);
		try {
			dao.crearCuenta(cuenta);
		} catch (DAOException e) {
			throw new ServicioException(e);
		} 
		
		return cuenta;
	}
	
	public Tarjeta crearTarjeta(Usuario usuario, int numero, double disponible, double saldoPagar) throws DuplicadoException, UsuarioNoExistenteException, ServicioException {
		TarjetaDAO dao = new TarjetaDAOH2();
		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setDni(usuario.getDni());
		tarjeta.setNumero(numero);
		tarjeta.setDisponible(disponible);
		tarjeta.setSaldoPagar(saldoPagar);
		usuario.agregarTarjeta(tarjeta);
		try {
			dao.agregarTarjeta(tarjeta);
		} catch (DAOException e) {
			throw new ServicioException(e);
		} 
		return tarjeta;
	}

}
