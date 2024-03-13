package ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entidades.Movimiento;
import excepciones.ServicioException;
import service.MovimientoService;

public class PanelResumen extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;
	private MovimientoService movimientoService;

	public PanelResumen(MovimientoService movimientoService) {
		this.movimientoService = movimientoService;

		setLayout(new BorderLayout());

		tableModel = new DefaultTableModel();
		tableModel.addColumn("Descripción");
		tableModel.addColumn("Monto");

		table = new JTable(tableModel);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);
	}

	public void mostrarResumen(int dni) {
		List<Movimiento> movimientos;
		try {

			movimientos = movimientoService.obtenerMovimientosPorTarjeta(dni);
			tableModel.setRowCount(0);
			for (Movimiento movimiento : movimientos) {
				Object[] rowData = { movimiento.getDescripcion(), movimiento.getMonto() };
				tableModel.addRow(rowData);
			}
		} catch (ServicioException e) {
			JOptionPane.showMessageDialog(this, "No se pudo listar los movimientos.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
