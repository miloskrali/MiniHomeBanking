package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entidades.Usuario;
import excepciones.DuplicadoException;
import excepciones.ServicioException;
import service.CuentaService;
import service.UsuarioService;

public class PanelUsuarios extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaUsuarios;
	private TableModelUsuario modelo;
	private UsuarioService usuarioService;
	private CuentaService cuentaService;
	private JScrollPane scroll;
	private JButton buttonBorrar;
	private JButton buttonAgregar;

	public PanelUsuarios(UsuarioService usuarioService,CuentaService cuentaService) {
		super();
		this.usuarioService = usuarioService;
		this.cuentaService= cuentaService;
		armarPanel();
		cargarUsuarios();
	}

	private void armarPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		modelo = new TableModelUsuario();
		tablaUsuarios = new JTable(modelo);
		scroll = new JScrollPane(tablaUsuarios);
		this.add(scroll);

		buttonAgregar = new JButton("Agregar");
		buttonAgregar.addActionListener(this);
		this.add(buttonAgregar);

		buttonBorrar = new JButton("Borrar");
		buttonBorrar.addActionListener(this);
		this.add(buttonBorrar);
	}

	private void cargarUsuarios() {
		List<Usuario> listaUsuarios;
		try {
			listaUsuarios = usuarioService.listaTodosLosUsuarios();
			modelo.setContenido(listaUsuarios);
			modelo.fireTableDataChanged();
		} catch (ServicioException e) {
			JOptionPane.showMessageDialog(this, "No se pudo listar los usuarios.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonAgregar) {
			String dniString = JOptionPane.showInputDialog(this, "Ingrese el DNI del nuevo usuario:");
			if (dniString == null || dniString.isEmpty()) {
				return;
			}
			int dni;
			try {
				dni = Integer.parseInt(dniString);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo usuario:");
			if (nombre == null || nombre.isEmpty()) {
				return;
			}
			String clave = JOptionPane.showInputDialog(this, "Ingrese la clave del nuevo usuario:");
			if (clave == null || clave.isEmpty()) {
				return;
			}
			Usuario nuevoUsuario = new Usuario(dni, nombre, clave);
			try {
				usuarioService.crearUsuario(nuevoUsuario);
				
				modelo.getContenido().add(nuevoUsuario);
				modelo.fireTableDataChanged();
			} catch (ServicioException e1) {
				JOptionPane.showMessageDialog(this, "No se pudo agregar el usuario.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (DuplicadoException e1) {
				JOptionPane.showMessageDialog(this, "No se pudo agregar el usuario.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (e.getSource() == buttonBorrar) {
			int filaSeleccionada = this.tablaUsuarios.getSelectedRow();
			int clave = (int) modelo.getValueAt(filaSeleccionada, 0);
			this.modelo.getContenido().remove(filaSeleccionada);
			modelo.fireTableDataChanged();
			try {
				usuarioService.borraUsuario(clave);
				cuentaService.borrarCuentaPorDNI(clave);
			} catch (ServicioException e1) {
				JOptionPane.showMessageDialog(this, "No se pudo borrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			 
		}

	}
}
