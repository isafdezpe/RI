package uo.ri.business.transaction.contractTypeManagement;

import java.sql.Connection;
import java.sql.SQLException;

import alb.util.jdbc.Jdbc;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.PersistenceFactory;
import uo.ri.persistence.ContractGateway;
import uo.ri.persistence.ContractTypeGateway;

public class DeleteContractTypeBusiness {
	
	private Long idTipo;
	private Connection c;
	private ContractTypeGateway contractTypeGateway;
	private ContractGateway contractGateway;
	
	public DeleteContractTypeBusiness(Long id) {
		this.idTipo = id;
	}

	/**
	 * Elimina el tipo de contrato con id dado
	 * @throws BusinessException si:
	 * 		el tipo no existe
	 * 		el tipo tiene contratos asignados
	 */
	public void execute() throws BusinessException {
		try {
			c = Jdbc.getConnection();
			c.setAutoCommit(false);

			contractTypeGateway = PersistenceFactory.getContractTypeGateway();
			contractTypeGateway.setConnection(c);
			
			contractGateway = PersistenceFactory.getContractGateway();
			contractGateway.setConnection(c);
			
			if (contractGateway.findByTypeId(idTipo).size() > 0)
				throw new BusinessException("No se ha podido eliminar el tipo");
			
			contractTypeGateway.delete(idTipo);
			c.commit();
		} catch (SQLException | RuntimeException e) {
			try {
				if (c != null) {
					c.rollback();
				} 
			}catch (SQLException e2) {
			};
			throw new BusinessException("No se ha podido eliminar el tipo");
		} finally {
			Jdbc.close(c);
		}
	}

}
