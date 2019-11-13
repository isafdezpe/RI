package uo.ri.business.transaction.contractTypeManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractTypeGateway;

public class FindAllContractTypesBusiness {
	
	private Connection c;
	private ContractTypeGateway contractTypeGateway;
	
	/**
	 * @return lista con todos los tipos de contrato,
	 * lista vacía si no hay ninguno
	 * @throws BusinessException
	 */
	public List<ContractTypeDto> execute() throws BusinessException {
		List<ContractTypeDto> types;
		
		try {
			c = Jdbc.getConnection();

			contractTypeGateway = PersistenceFactory.getContractTypeGateway();
			contractTypeGateway.setConnection(c);

			types = contractTypeGateway.read();
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
		
		return types;
	}

}
