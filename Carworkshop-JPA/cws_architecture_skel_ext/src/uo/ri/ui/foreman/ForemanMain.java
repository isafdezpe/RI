package uo.ri.ui.foreman;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import alb.util.menu.NotYetImplementedAction;
import uo.ri.persistence.jpa.util.Jpa;
import uo.ri.ui.foreman.cliente.ClientesMenu;
import uo.ri.ui.foreman.recepcion.RecepcionMenu;
import uo.ri.ui.foreman.vehicle.VehiculosMenu;

public class ForemanMain {

	private static class MainMenu extends BaseMenu {
		MainMenu() {
			menuOptions = new Object[][] { 
				{ "Jefe de Taller", null },
				{ "Recepción en taller", 		RecepcionMenu.class }, 
				{ "Gestión de clientes", 		ClientesMenu.class },
				{ "Gestión de vehículos", 		VehiculosMenu.class },
				{ "Revisar historial de un cliente", NotYetImplementedAction.class }, 
			};
		}
	}

	public static void main(String[] args) {
		new ForemanMain()
			.config()
			.run()
			.close();
	}

	private ForemanMain config() {
		return this;
	}

	public ForemanMain run() {
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
