package uo.ri.business.transaction.contractTypeManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractTypeGateway;

public class UpdateContractTypeBusiness {
	
	private ContractTypeDto contractType;
	private Connection c;
	private ContractTypeGateway contractTypeGateway;

	public UpdateContractTypeBusiness(ContractTypeDto dto) {
		this.contractType = dto;
	}

	/**
	 * Actualiza los días de compensación de un contrato
	 * @throws BusinessException si:
	 * 		el contrato no existe
	 * 		los días de compensación son negativos
	 */
	public void execute() throws BusinessException {
		if (contractType.compensationDays < 0)
			throw new BusinessException("No se ha podido actualizar el tipo");
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractTypeGateway = PersistenceFactory.getContractTypeGateway();
			contractTypeGateway.setConnection(c);

			contractTypeGateway.update(contractType);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido actualizar el tipo");
		} finally {
			Jdbc.close(c);
		}
	}

}
