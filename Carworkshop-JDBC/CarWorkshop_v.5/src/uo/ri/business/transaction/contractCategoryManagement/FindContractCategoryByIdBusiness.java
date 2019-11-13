package uo.ri.business.transaction.contractCategoryManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractCategoryGateway;

public class FindContractCategoryByIdBusiness {
	
	private Long idCategoria;
	private Connection c;
	private ContractCategoryGateway contractCategoryGateway;

	public FindContractCategoryByIdBusiness(Long id) {
		this.idCategoria = id;
	}

	/**
	 * @return la categoría cuyo id sea el dado, null si no existe
	 * @throws BusinessException
	 */
	public ContractCategoryDto execute() throws BusinessException {
		ContractCategoryDto cc;
		
		try {
			c = Jdbc.getConnection();

			contractCategoryGateway = 
					PersistenceFactory.getContractCategoryGateway();
			contractCategoryGateway.setConnection(c);

			cc = contractCategoryGateway.findById(idCategoria);
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
		
		return cc;
	}

}
