package db;

import java.util.List;

import entidades.Usuario;
import excepciones.DAOException;
import excepciones.DuplicadoException;

public interface UsuarioDAO {

	void crearUsuario(Usuario unUsuario) throws DAOException, DuplicadoException;

	void borraUsuario(int dni) throws DAOException;

	void actualizaUsuario(Usuario unUsuario) throws DAOException;

	Usuario muestraUsuario(int dni) throws DAOException;

	List<Usuario> listaTodosLosUsuarios() throws DAOException;
}
