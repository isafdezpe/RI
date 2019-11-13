package uo.ri.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import uo.ri.business.dto.ContractDto;

public interface ContractGateway extends SQLGateway {

	/**
	 * Añade un contrato
	 * @param contract contrato a añadir
	 * @throws SQLException
	 */
	void add(ContractDto contract) throws SQLException;

	/**
	 * @param mechanicId mecánico a buscar
	 * @return lista de contratos del mecánico dado, 
	 * lista vacía si no hay ninguno
	 * @throws SQLException
	 */
	List<ContractDto> findContractsByMechanicId(Long mechanicId) throws SQLException;

	/**
	 * Finaliza el contrato con id dado, indicando la fecha de fin y la compensación
	 * @param id contrato a finalizar
	 * @param endDate fecha de fin de contrato
	 * @param compensation compensación por finalización del contrato
	 * @throws SQLException
	 */
	void finishContract(Long id, Date endDate, double compensation) throws SQLException;

	/**
	 * Actualiza la fecha de fin y el salario base anual del contrato
	 * @param contract contrato a actualizar
	 * @throws SQLException
	 */
	void update(ContractDto contract) throws SQLException;

	/**
	 * Elimina un contrato
	 * @param idContrato contrato a eliminar
	 * @throws SQLException
	 */
	void delete(Long idContrato) throws SQLException;

	/**
	 * @param idContrato contrato a buscar
	 * @return contrato cuyo id se pasa
	 * @throws SQLException
	 */
	ContractDto findById(Long idContrato) throws SQLException;

	/**
	 * @param idCategoria categoría de contrato
	 * @return lista de contratos cuya categoría sea la dada,
	 * lista vacía si no hay ninguno
	 * @throws SQLException
	 */
	List<ContractDto> findByCategoryId(Long idCategoria) throws SQLException;
	
	/**
	 * @param idTipo tipo de contrato
	 * @returnlista de contratos cuyo tipo sea el dado,
	 * lista vacía si no hay ninguno
	 * @throws SQLException
	 */
	List<ContractDto> findByTypeId(Long idTipo) throws SQLException;

	/**
	 * @param id contrato
	 * @return días de compensación para el contrato con id dado
	 * @throws SQLException
	 */
	int getCompensationDaysContract(Long id) throws SQLException;

}
