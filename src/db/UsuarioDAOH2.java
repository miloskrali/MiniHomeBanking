package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Usuario;
import excepciones.DAOException;
import excepciones.DuplicadoException;

public class UsuarioDAOH2 implements UsuarioDAO {

    public void crearUsuario(Usuario usuario) throws DuplicadoException, DAOException {
        int dni = usuario.getDni();
        String nombre = usuario.getNombre();
        String clave = usuario.getClave();

        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            String sql = "INSERT INTO usuarios (dni, nombre, clave) VALUES ('" + dni + "', '" + nombre + "', '" + clave + "')";
            s.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                if(e.getErrorCode() == 23505) {
                    throw new DuplicadoException();
                }
                e.printStackTrace();
                c.rollback();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        }
    }

    public void borraUsuario(int dni) throws DAOException {
        String sql = "DELETE FROM usuarios WHERE dni = '" + dni + "'";
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            s.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException ex) {
            	 throw new DAOException();
            }
            throw new DAOException();
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        }
    }

    public void actualizaUsuario(Usuario usuario) throws DAOException {
        int dni = usuario.getDni();
        String nombre = usuario.getNombre();
        String clave = usuario.getClave();

        String sql = "UPDATE usuarios set clave = '" + clave + "', nombre = '" + nombre + "' WHERE dni = '" + dni + "'";
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            s.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        }
    }
    
    public List<Usuario> listaTodosLosUsuarios() throws DAOException {
    	List<Usuario> resultado = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            
            while (rs.next()) {
                int dni = rs.getInt("dni");
                String nombre = rs.getString("nombre");
                String clave = rs.getString("clave");
                Usuario u = new Usuario(dni, nombre, clave);
                resultado.add(u);

            }
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        }
        return resultado;
    }
	
    public Usuario muestraUsuario(int dni) throws DAOException {
        String sql = "SELECT * FROM usuarios WHERE dni = '" + dni + "'";
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            
            if (rs.next()) {
                int dni1 = rs.getInt("dni");
                String nombre = rs.getString("nombre");
                String clave = rs.getString("clave");
                Usuario u = new Usuario(dni1, nombre, clave);
                return u;
            }

        } catch (SQLException e) {
            try {
                c.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
            	throw new DAOException();
            }
        }
        return null;
    }

	
}
