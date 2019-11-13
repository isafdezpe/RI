package uo.ri.business.transaction.contractCategoryManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractCategoryGateway;

public class FindAllContractCategoriesBusiness {
	
	private Connection c;
	private ContractCategoryGateway contractCategoryGateway;
	
	/**
	 * @return lista de todas las categorías del contrato que hay en el sistema,
	 * 		o una lista vacía si no hay ninguna
	 * @throws BusinessException
	 */
	public List<ContractCategoryDto> execute() throws BusinessException {
		List<ContractCategoryDto> categories;
		
		try {
			c = Jdbc.getConnection();

			contractCategoryGateway = 
					PersistenceFactory.getContractCategoryGateway();
			contractCategoryGateway.setConnection(c);

			categories = contractCategoryGateway.read();
			c.commit();
		} catch (SQLException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException(e.getMessage());
		} finally {
			Jdbc.close(c);
		}
		
		return categories;
	}

}
