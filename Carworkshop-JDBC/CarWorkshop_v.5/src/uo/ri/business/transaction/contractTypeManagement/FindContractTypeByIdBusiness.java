package uo.ri.business.transaction.contractTypeManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractTypeGateway;

public class FindContractTypeByIdBusiness {
	
	private Long idTipo;
	private Connection c;
	private ContractTypeGateway contractTypeGateway;

	public FindContractTypeByIdBusiness(Long id) {
		this.idTipo = id;
	}

	/**
	 * @return tipo de contrato con id dado, null si no existe
	 * @throws BusinessException
	 */
	public ContractTypeDto execute() throws BusinessException {
		ContractTypeDto ct;
		
		try {
			c = Jdbc.getConnection();

			contractTypeGateway = PersistenceFactory.getContractTypeGateway();
			contractTypeGateway.setConnection(c);

			ct = contractTypeGateway.findById(idTipo);
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
		
		return ct;
	}

}
