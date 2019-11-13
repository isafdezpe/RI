package uo.ri.business.transaction.contractCategoryManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractCategoryGateway;

public class AddContractCategoryBusiness {
	
	private ContractCategoryDto contractCategory;
	private Connection c;
	private ContractCategoryGateway contractCategoryGateway;

	public AddContractCategoryBusiness(ContractCategoryDto dto) {
		this.contractCategory = dto;
	}

	/**
	 * Añade una nueva categoria de contrato con los datos dados
	 * @throws BusinessException si:
	 * 		ya existe una categoría con el mismo nombre
	 * 		el plus de productividad o el trienio son negativos
	 */
	public void execute() throws BusinessException {
		if (contractCategory.trieniumSalary < 0 
				|| contractCategory.productivityPlus < 0 
				|| contractCategory.productivityPlus > 100)
			throw new BusinessException("No se ha podido insertar la categoría");
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractCategoryGateway = 
					PersistenceFactory.getContractCategoryGateway();
			contractCategoryGateway.setConnection(c);

			contractCategoryGateway.add(contractCategory);
			c.commit();
		} catch (SQLException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido insertar la categoría");
		} finally {
			Jdbc.close(c);
		}
	}

}
