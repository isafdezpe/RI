package uo.ri.ui.admin.contractcategory;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.contractcategory.action.AddContractCategoryAction;
import uo.ri.ui.admin.contractcategory.action.DeleteContractCategoryAction;
import uo.ri.ui.admin.contractcategory.action.ListContractCategoriesAction;
import uo.ri.ui.admin.contractcategory.action.UpdateContractCategoryAction;

public class CategoriasContratoMenu extends BaseMenu {

	public CategoriasContratoMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de categorias de contrato", null},
			
			{ "Añadir", 	AddContractCategoryAction.class }, 
			{ "Modificar", 	UpdateContractCategoryAction.class }, 
			{ "Eliminar", 	DeleteContractCategoryAction.class }, 
			{ "Listar", 	ListContractCategoriesAction.class },
		};
	}

}
