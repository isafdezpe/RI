package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;

public class MainMenu extends BaseMenu {

	public MainMenu() {
		menuOptions = new Object[][] { 
			{ "Administrador", null },
			{ "Gestión de mecánicos", 			MecanicosMenu.class }, 
			{ "Gestión de repuestos", 			RepuestosMenu.class },
			{ "Gestión de tipos de vehículo", 	TiposVehiculoMenu.class },
			{ "Gestión de contratos", 			ContratosMenu.class },
			{ "Gestión de nóminas", 			NominasMenu.class },
			{ "Gestión de categorías de contrato", 	CategoriasContratoMenu.class },
			{ "Gestión de tipos de contrato", 	TiposContratoMenu.class },
		};
	}

	public static void main(String[] args) {
		new MainMenu().execute();
	}

}
