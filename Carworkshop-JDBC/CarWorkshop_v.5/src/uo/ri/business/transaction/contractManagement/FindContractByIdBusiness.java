package uo.ri.business.transaction.contractManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractGateway;

public class FindContractByIdBusiness {
	
	private Long idContrato;
	private Connection c;
	private ContractGateway contractGateway;

	public FindContractByIdBusiness(Long id) {
		this.idContrato = id;
	}

	/**
	 * @return el contrato con id dado, null si no existe;
	 * @throws BusinessException
	 */
	public ContractDto execute() throws BusinessException {
		ContractDto contract;
		try {
			c = Jdbc.getConnection();

			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);

			contract = contractGateway.findById(idContrato);
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
		
		return contract;
	}

}
