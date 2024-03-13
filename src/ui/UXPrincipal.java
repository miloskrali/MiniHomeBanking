package ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import db.CuentaDAO;
import db.CuentaDAOH2;
import db.MovimientoDAO;
import db.MovimientoDAOH2;
import db.TarjetaDAO;
import db.TarjetaDAOH2;
import service.CuentaService;
import service.MovimientoService;
import service.TarjetaService;

public class UXPrincipal extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
    private PanelCuenta panelCuenta;
    private PanelTransferencia panelTransferencia;
    private PanelResumen panelResumen;
    private PanelTarjetas panelTarjetas;

    public UXPrincipal(int dni) {
    	CuentaDAO cuentaDAO = new CuentaDAOH2();
    	CuentaService serviceCuenta = new CuentaService(cuentaDAO);
        TarjetaDAO tarjetaDAO = new TarjetaDAOH2();
        MovimientoDAO movimientoDAO = new MovimientoDAOH2();
        MovimientoService movimientoService = new MovimientoService(movimientoDAO);

        setTitle("Mi Aplicación Bancaria");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabbedPane = new JTabbedPane();

        panelCuenta = new PanelCuenta(dni, serviceCuenta);
        TarjetaService serviceTarjeta = new TarjetaService(tarjetaDAO);
        panelTransferencia = new PanelTransferencia(dni);
        panelResumen = new PanelResumen(movimientoService);
        panelTarjetas = new PanelTarjetas(dni, serviceTarjeta);

        tabbedPane.addTab("Cuenta", panelCuenta);
        tabbedPane.addTab("Transferencia", panelTransferencia);
        tabbedPane.addTab("Resumen", panelResumen);
        tabbedPane.addTab("Tarjetas", panelTarjetas);

        getContentPane().add(tabbedPane);
    }
}