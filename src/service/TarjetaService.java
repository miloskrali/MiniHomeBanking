package service;

import java.util.List;

import db.TarjetaDAO;
import db.TarjetaDAOH2;
import entidades.Tarjeta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.ServicioException;
import excepciones.UsuarioNoExistenteException;

public class TarjetaService {

    private TarjetaDAO tarjetaDAO;

    public TarjetaService(TarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;
    }
    
    public void agregarTarjeta(Tarjeta tarjeta) throws DuplicadoException, UsuarioNoExistenteException, ServicioException {
        try {
			tarjetaDAO.agregarTarjeta(tarjeta);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
    }
    
    public void eliminarTarjeta(int numeroTarjeta) throws ServicioException {
        try {
			tarjetaDAO.eliminarTarjeta(numeroTarjeta);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
    }

    public List<Tarjeta> listarTodasLasTarjetas() throws ServicioException {
        try {
			return tarjetaDAO.listarTodasLasTarjetas();
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
    }
    
    public List<Tarjeta> listarTarjetasUsuario(int dni) throws ServicioException {
        try {
			return tarjetaDAO.listarTarjetasUsuario(dni);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
    }

    public Tarjeta obtenerTarjetaPorNumero(int numero) throws ServicioException {
        try {
			return tarjetaDAO.obtenerTarjetaPorNumero(numero);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
    }

    public void actualizarTarjeta(Tarjeta tarjeta) throws DAOException {
        tarjetaDAO.actualizarTarjeta(tarjeta);
    }

    public List<Tarjeta> listarTarjetas() throws ServicioException {
        TarjetaDAO d = new TarjetaDAOH2();
        List<Tarjeta> tarjetas;
        try {
			tarjetas = d.listarTodasLasTarjetas();
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
        return tarjetas;
    }

//    public void agregarTarjetaNueva(Tarjeta tarjeta) throws ServicioException, DuplicadoException {
//        TarjetaDAO d = new TarjetaDAOH2();
//        try {
//            try {
//				d.agregarTarjeta(tarjeta);
//			} catch (DuplicadoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (UsuarioNoExistenteException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        } catch (DAOException e) {
//            throw new ServicioException(e);
//        }
//    }
}