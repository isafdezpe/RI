package uo.ri.business.transaction.contractTypeManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractTypeGateway;

public class AddContractTypeBusiness {
	
	private ContractTypeDto contractType;
	private Connection c;
	private ContractTypeGateway contractTypeGateway;

	public AddContractTypeBusiness(ContractTypeDto dto) {
		this.contractType = dto;
	}

	/**
	 * Añade un nuevo tipo de contrato con los datos dados
	 * @throws BusinessException si:
	 * 		ya existe un tipo con el mismo nombre
	 * 		los días de compensación son negativos
	 */
	public void execute() throws BusinessException {
		if (contractType.compensationDays < 0)
			throw new BusinessException("No se ha podido insertar el tipo");
		
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractTypeGateway = PersistenceFactory.getContractTypeGateway();
			contractTypeGateway.setConnection(c);

			contractTypeGateway.add(contractType);
			c.commit();
		} catch (SQLException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido insertar el tipo");
		} finally {
			Jdbc.close(c);
		}
	}

}
