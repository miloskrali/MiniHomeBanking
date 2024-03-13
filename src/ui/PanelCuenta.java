package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import entidades.Cuenta;
import excepciones.DuplicadoException;
import excepciones.ServicioException;
import excepciones.UsuarioNoExistenteException;
import service.CuentaService;

public class PanelCuenta extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaCuenta;
	private TableModelCuenta modelo;
	private CuentaService serviceDAO;
	private JScrollPane scroll;
	private JButton buttonTrans;
	private JButton buttonBorrar;
	private JButton buttonAgregar;
	private JList<String> listaCuentasPropias;
	private boolean esAdministrador;

	public PanelCuenta(CuentaService serviceDAO, boolean esAdministrador, int dni) {
		super();
		this.serviceDAO = serviceDAO;
		this.esAdministrador = esAdministrador;
		armarPanel();
		cargarTodasLasCuentas(dni);
	}

	public PanelCuenta(int dni, CuentaService serviceDAO) {
		this.serviceDAO = serviceDAO;
		JLabel lblCuentaPropia = new JLabel("Cuentas propias:");
		lblCuentaPropia.setBounds(40, 40, 100, 40);
		add(lblCuentaPropia);
		listaCuentasPropias = new JList<>();
		JScrollPane scrollPane = new JScrollPane(listaCuentasPropias);
		scrollPane.setBounds(130, 60, 200, 100);
		add(scrollPane);
		cargarCuentas(dni);
	}

	private void armarPanel() {
		this.setLayout(new FlowLayout());

		modelo = new TableModelCuenta();
		tablaCuenta = new JTable(modelo);
		scroll = new JScrollPane(tablaCuenta);
		this.add(scroll);

		buttonAgregar = new JButton("Agregar");
		buttonAgregar.addActionListener(this);
		this.add(buttonAgregar);

		buttonBorrar = new JButton("Borrar");
		buttonBorrar.addActionListener(this);
		this.add(buttonBorrar);

		if (!esAdministrador) {
			buttonTrans = new JButton("Realizar Transferencia");
			buttonTrans.addActionListener(this);
			this.add(buttonTrans);
		}
	}

	private void cargarCuentas(int dni) {
		List<Cuenta> cuentasPropias;
		try {
			cuentasPropias = serviceDAO.listarCuentasUsuario(dni);
			String[] cuentasPropiasArr = new String[cuentasPropias.size()];
			for (int i = 0; i < cuentasPropias.size(); i++) {
				Cuenta cuenta = cuentasPropias.get(i);
				String cuentaInfo = " Tipo de cuenta: " + cuenta.getTipo() + " - Numero de cuenta: "
						+ cuenta.getNumeroCuenta() + " - Saldo: " + cuenta.getSaldo();
				cuentasPropiasArr[i] = cuentaInfo;
			}
			listaCuentasPropias.setListData(cuentasPropiasArr);
		} catch (ServicioException e) {
			JOptionPane.showMessageDialog(this, "No se pudo listar cuentas.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void cargarTodasLasCuentas(int dni) {
		List<Cuenta> listaCuentas;
		try {
			listaCuentas = serviceDAO.listarTodasLasCuentas(dni);
			modelo.setContenido(listaCuentas);
			modelo.fireTableDataChanged();
		} catch (ServicioException e) {
			JOptionPane.showMessageDialog(this, "No se pudo cargar cuentas", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttonBorrar) {
			int filaSeleccionada = this.tablaCuenta.getSelectedRow();
			int clave = (int) modelo.getValueAt(filaSeleccionada, 2);
			this.modelo.getContenido().remove(filaSeleccionada);
			modelo.fireTableDataChanged();
			try {
				serviceDAO.borrarCuenta(clave);
			} catch (ServicioException e1) {
				JOptionPane.showMessageDialog(this, "No se pudo borrar la cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else if (e.getSource() == buttonAgregar) {
			Cuenta nuevaCuenta = crearCuenta();
			try {
				serviceDAO.crearCuenta(nuevaCuenta);
				modelo.getContenido().add(nuevaCuenta);
				modelo.fireTableDataChanged();
			} catch (ServicioException e1) {
				JOptionPane.showMessageDialog(this, "No se pudo agregar la cuenta.", "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (UsuarioNoExistenteException e1) {
				JOptionPane.showMessageDialog(this, "El usuario al que le quiere agregar una cuenta no existe.",
						"Error", JOptionPane.ERROR_MESSAGE);
			} catch (DuplicadoException e1) {
				JOptionPane.showMessageDialog(this, "El numero de cuenta ya existe.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	private Cuenta crearCuenta() {
		int dni = obtenerDniDesdeInput();
		String tipo = obtenerTipoDesdeInput();
		int numeroCuenta = obtenerNumeroCuentaDesdeInput();
		double saldo = obtenerSaldoDesdeInput();

		return new Cuenta(dni, tipo, numeroCuenta, saldo);
	}

	private int obtenerDniDesdeInput() {
		String dniStr = JOptionPane.showInputDialog("Ingrese el DNI: ");
		return Integer.parseInt(dniStr);
	}

	private String obtenerTipoDesdeInput() {
		String tipo = JOptionPane.showInputDialog("Ingrese el tipo de cuenta: ");
		return tipo;
	}

	private int obtenerNumeroCuentaDesdeInput() {
		String numeroCuentaStr = JOptionPane.showInputDialog("Ingrese el número de cuenta: ");
		return Integer.parseInt(numeroCuentaStr);
	}

	private double obtenerSaldoDesdeInput() {
		String saldoStr = JOptionPane.showInputDialog("Ingrese el saldo: ");
		return Double.parseDouble(saldoStr);
	}
}