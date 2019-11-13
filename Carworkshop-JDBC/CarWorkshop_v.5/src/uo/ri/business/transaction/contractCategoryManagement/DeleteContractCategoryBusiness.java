package uo.ri.business.transaction.contractCategoryManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractCategoryGateway;
import uo.ri.persistence.ContractGateway;

public class DeleteContractCategoryBusiness {
	
	private Long idCategoria;
	private Connection c;
	private ContractCategoryGateway contractCategoryGateway;
	private ContractGateway contractGateway;
	
	public DeleteContractCategoryBusiness(Long id) {
		this.idCategoria = id;
	}

	/**
	 * Elimina la categoria cuyo id sea el dado
	 * @throws BusinessException si:
	 * 		la categoría no existe
	 * 		la categoría tiene contratos asignados
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractCategoryGateway = 
					PersistenceFactory.getContractCategoryGateway();
			contractCategoryGateway.setConnection(c);
			
			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);
			
			if (contractGateway.findByCategoryId(idCategoria).size() > 0)
				throw new BusinessException(
						"No se ha podido eliminar la categoria");
			
			contractCategoryGateway.delete(idCategoria);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido eliminar la categoria");
		} finally {
			Jdbc.close(c);
		}
	}

}
