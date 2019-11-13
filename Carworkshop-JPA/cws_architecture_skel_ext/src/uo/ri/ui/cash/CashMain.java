package uo.ri.ui.cash;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.persistence.jpa.util.Jpa;
import uo.ri.ui.cash.action.FacturarReparacionesAction;
import uo.ri.ui.cash.action.ReparacionesNoFacturadasUnClienteAction;

public class CashMain {

	private static class MainMenu extends BaseMenu {
		MainMenu() {
			menuOptions = new Object[][] { 
				{ "Caja de Taller", null },
				{ "Buscar reparaciones no facturadas de un cliente", ReparacionesNoFacturadasUnClienteAction.class }, 
				{ "Buscar reparación por matrícula", 	NotYetImplementedAction.class }, 
				{ "Facturar reparaciones", 				FacturarReparacionesAction.class },
				{ "Liquidar factura", 					NotYetImplementedAction.class },
			};
		}
	}

	public static void main(String[] args) {
		new CashMain()
			.config()
			.run()
			.close();
	}

	private CashMain config() {
		return this;
	}
	
	public CashMain run() {
		try {
			new MainMenu().execute();

		} catch (RuntimeException rte) {
			Printer.printRuntimeException(rte);
		}
		return this;
	}

	private void close() {
		Jpa.close();
	}

}
