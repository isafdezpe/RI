package uo.ri.ui.admin;

import alb.util.console.Printer;
import alb.util.menu.BaseMenu;
import uo.ri.business.impl.BusinessFactory;
import uo.ri.conf.Factory;
import uo.ri.persistence.jpa.JpaRepositoryFactory;
import uo.ri.persistence.jpa.executor.JpaExecutorFactory;
import uo.ri.persistence.jpa.util.Jpa;
import uo.ri.ui.admin.contract.ContratosMenu;
import uo.ri.ui.admin.contractcategory.CategoriasContratoMenu;
import uo.ri.ui.admin.contracttype.TiposContratoMenu;
import uo.ri.ui.admin.mechanic.MecanicosMenu;
import uo.ri.ui.admin.payroll.NominasMenu;
import uo.ri.ui.admin.repuesto.RepuestosMenu;
import uo.ri.ui.admin.tipovehiculo.TiposVehiculoMenu;

public class AdminMain {

	private static class MainMenu extends BaseMenu {
		MainMenu() {
			menuOptions = new Object[][] { 
				{ "Administrador", null },
				
				{ "Gestión de mecánicos", 			MecanicosMenu.class },
				{ "Gestión de contratos", 			ContratosMenu.class },
				{ "Gestión de tipos de contrato", 	TiposContratoMenu.class },
				{ "Gestión de categorias de contrato", CategoriasContratoMenu.class },
				{ "Gestión de nóminas", 			NominasMenu.class },
				{ "", 								null },
				{ "Gestión de repuestos", 			RepuestosMenu.class },
				{ "Gestión de tipos de vehículo", 	TiposVehiculoMenu.class }, 
			};
		}
	}

	public static void main(String[] args) {
		new AdminMain()
			.configure()
			.run()
			.close();
	}

	private AdminMain configure() {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();

		return this;
	}

	private AdminMain run() {
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
