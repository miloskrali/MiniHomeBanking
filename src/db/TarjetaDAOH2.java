package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Tarjeta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.UsuarioNoExistenteException;

public class TarjetaDAOH2 implements TarjetaDAO {

    private Connection connection;

    public TarjetaDAOH2(Connection connection) {
        this.connection = connection;
    }

    public TarjetaDAOH2() {
    }

    public void agregarTarjeta(Tarjeta tarjeta) throws DAOException, DuplicadoException, UsuarioNoExistenteException {
    	int numero = tarjeta.getNumero();
        double disponible = tarjeta.getDisponible();
        double saldoPagar = tarjeta.getSaldoPagar();
        int dni = tarjeta.getDni();

        
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            String verificarUsuarioSql = "SELECT COUNT(*) FROM usuarios WHERE dni = " + dni;
            ResultSet resultado = s.executeQuery(verificarUsuarioSql);
            resultado.next();
            int cuentaUsuarios = resultado.getInt(1);
            
            if (cuentaUsuarios == 0) {
                throw new UsuarioNoExistenteException(); 
            }else {
            String sql = "INSERT INTO tarjetas (numeroTarjeta, disponible, saldoPagar, dniTitular) VALUES ('" + numero + "', '" + disponible + "', '"+saldoPagar+"','" + dni + "' )";
            s.executeUpdate(sql);
            c.commit();
            }
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

    public void eliminarTarjeta(int numeroTarjeta) throws DAOException {
        String query = "DELETE FROM tarjetas WHERE numeroTarjeta = "+numeroTarjeta;
        Connection c = DBManager.connect();        
        try {
            Statement s = c.createStatement();
            s.executeUpdate(query);
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

    public List<Tarjeta> listarTarjetasUsuario(int dni) throws DAOException{
        List<Tarjeta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM tarjetas WHERE dniTitular = " + dni;
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                int dniTarjeta = rs.getInt("dniTitular");
                int numero = rs.getInt("numeroTarjeta");
                double disponible = rs.getDouble("disponible");
                double saldoPagar = rs.getDouble("saldoPagar");
                Tarjeta tarjeta = new Tarjeta(dniTarjeta, numero, disponible, saldoPagar);
                resultado.add(tarjeta);

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
    
    public List<Tarjeta> listarTodasLasTarjetas() throws DAOException{
        List<Tarjeta> tarjetas = new ArrayList<>();
        String query = "SELECT * FROM tarjetas";
        Connection c = DBManager.connect();
        
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                int dni = rs.getInt("dniTitular");
                int numero = rs.getInt("numeroTarjeta");
                double disponible = rs.getDouble("disponible");
                double saldoPagar = rs.getDouble("saldoPagar");

                Tarjeta tarjeta = new Tarjeta(dni, numero, disponible, saldoPagar);
                tarjetas.add(tarjeta);
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
        return tarjetas;
}

    public Tarjeta obtenerTarjetaPorNumero(int numero) throws DAOException {
        String query = "SELECT * FROM tarjetas WHERE numeroTarjeta = ?";
        Tarjeta tarjeta = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, numero);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int dni = resultSet.getInt("dniTitular");
                double disponible = resultSet.getDouble("disponible");
                double saldoPagar = resultSet.getDouble("saldoPagar");

                tarjeta = new Tarjeta(dni, numero, disponible, saldoPagar);
            }
        } catch (SQLException e) {
            throw new DAOException();
        }

        return tarjeta;
    }

    public void actualizarTarjeta(Tarjeta tarjeta) throws DAOException{
    	int numero = tarjeta.getNumero();
    	double disponible = tarjeta.getDisponible();
    	double saldoPagar = tarjeta.getSaldoPagar();
    	
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            s.executeUpdate("UPDATE tarjetas SET disponible = " + disponible + " saldoPagar = " + saldoPagar + " WHERE numeroCuenta =" + numero);
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
}