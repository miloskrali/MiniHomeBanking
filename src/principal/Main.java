package principal;

import db.DBTableManager;
import db.UsuarioDAO;
import db.UsuarioDAOH2;
import entidades.Administrador;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.SaldoInsuficienteException;
import excepciones.SaldonegativoException;
import ui.Login;

public class Main {

    public static void main(String[] args) throws SaldonegativoException, SaldoInsuficienteException, DAOException {

        DBTableManager table = new DBTableManager();
        table.createUserTable();
        table.createCuentaTable();
        table.createTarjetaTable();
        table.createMovimientoTable();

        UsuarioDAO dao = new UsuarioDAOH2();

        int dni = 44482701;
        String nombre = "admin";
        String clave = "admin";
        Administrador admin = new Administrador();
        admin.setDni(dni);
        admin.setNombre(nombre);
        admin.setClave(clave);
        
        try {
			dao.crearUsuario(admin);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (DuplicadoException e) {
			e.printStackTrace();
		}

        Login loginFrame = new Login();
        loginFrame.setVisible(true);
    }
}