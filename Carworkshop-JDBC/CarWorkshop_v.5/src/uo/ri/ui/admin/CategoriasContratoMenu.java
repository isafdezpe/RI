package uo.ri.ui.admin;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.action.contractCategories.AddCategoryAction;
import uo.ri.ui.admin.action.contractCategories.DeleteCategoryAction;
import uo.ri.ui.admin.action.contractCategories.FindCategoryByIdAction;
import uo.ri.ui.admin.action.contractCategories.ListCategoriesAction;
import uo.ri.ui.admin.action.contractCategories.UpdateCategoryAction;

public class CategoriasContratoMenu extends BaseMenu {

	public CategoriasContratoMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de categorías de contrato", null},
			
			{ "Añadir categoría", 				AddCategoryAction.class }, 
			{ "Actualizar categoría", 			UpdateCategoryAction.class }, 
			{ "Eliminar categoría", 			DeleteCategoryAction.class }, 
			{ "Listar categorías", 				ListCategoriesAction.class },
			{ "Buscar categoría por su id", 	FindCategoryByIdAction.class },
		};
	}
}
