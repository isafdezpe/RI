package uo.ri.business.transaction.contractManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import alb.util.jdbc.Jdbc;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractGateway;

public class FindContractByMechanicIdBusiness {
	
	private Long idMecanico;
	private Connection c;
	private ContractGateway contractGateway;

	public FindContractByMechanicIdBusiness(Long id) {
		this.idMecanico = id;
	}

	/**
	 * @return contratos del mecánico con id dado,
	 * lista vacía si no tiene contratos
	 * @throws BusinessException
	 */
	public List<ContractDto> execute() throws BusinessException {
		List<ContractDto> contracts;
		
		try {
			c = Jdbc.getConnection();
			
			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);
			
			contracts = contractGateway.findContractsByMechanicId(idMecanico);
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
		
		return contracts;
	}

}
