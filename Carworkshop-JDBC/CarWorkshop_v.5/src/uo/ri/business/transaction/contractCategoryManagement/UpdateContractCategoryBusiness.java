package uo.ri.business.transaction.contractCategoryManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractCategoryGateway;

public class UpdateContractCategoryBusiness {
	
	private ContractCategoryDto contractCategory;
	private Connection c;
	private ContractCategoryGateway contractCategoryGateway;

	public UpdateContractCategoryBusiness(ContractCategoryDto dto) {
		this.contractCategory = dto;
	}

	/**
	 * Actualiza la productividad y el trienio de una categoría con id dado
	 * @throws BusinessException si:
	 * 		no existe la categoría
	 * 		el plus de productividad o el trienio son negativos
	 */
	public void execute() throws BusinessException {
		if (contractCategory.trieniumSalary < 0 
				|| contractCategory.productivityPlus < 0
				|| contractCategory.productivityPlus > 100)
			throw new BusinessException(
					"No se ha podido insertar la categoría");
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractCategoryGateway = 
					PersistenceFactory.getContractCategoryGateway();
			contractCategoryGateway.setConnection(c);

			contractCategoryGateway.update(contractCategory);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido actualizar la categoría");
		} finally {
			Jdbc.close(c);
		}
	}

}
