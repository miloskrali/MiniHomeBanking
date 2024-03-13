package service;

import java.util.List;

import db.UsuarioDAO;
import entidades.Usuario;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.ServicioException;

public class UsuarioService {
	private UsuarioDAO usuarioDAO;

	public UsuarioService(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
	public void crearUsuario(Usuario usuario) throws ServicioException, DuplicadoException {
		try {
			usuarioDAO.crearUsuario(usuario);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
	}
	
	public void borraUsuario(int dni) throws ServicioException {
		try {
			usuarioDAO.borraUsuario(dni);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
	}
	
	public void actualizaUsuario(Usuario usuario) throws ServicioException {
		try {
			usuarioDAO.actualizaUsuario(usuario);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
	}
	
	public List<Usuario> listaTodosLosUsuarios() throws ServicioException{
		try {
			return usuarioDAO.listaTodosLosUsuarios();
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
	}
	
	public Usuario muestraUsuario(int dni) throws ServicioException {
		try {
			return usuarioDAO.muestraUsuario(dni);
		} catch (DAOException e) {
			throw new ServicioException(e);
		}
	}
	
   
}
