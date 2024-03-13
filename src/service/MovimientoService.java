package service;

import java.util.List;

import db.MovimientoDAO;
import entidades.Movimiento;
import excepciones.DAOException;
import excepciones.ServicioException;

public class MovimientoService {
    private MovimientoDAO movimientoDAO;

    public MovimientoService(MovimientoDAO movimientoDAO) {
        this.movimientoDAO = movimientoDAO;
    }
    
    public void agregarMovimiento(Movimiento movimiento) throws ServicioException  {
         try {
			movimientoDAO.agregarMovimiento(movimiento);
		} catch (DAOException e) {
			throw new ServicioException(e);
		};
    }

    public List<Movimiento> obtenerMovimientosPorTarjeta(int numeroTarjeta) throws ServicioException {
        try {
			return movimientoDAO.obtenerMovimientosPorTarjeta(numeroTarjeta);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
    }
}