package uo.ri.persistence;

import java.sql.SQLException;
import java.util.List;

import uo.ri.business.dto.ContractTypeDto;

public interface ContractTypeGateway extends SQLGateway {

	/**
	 * Añade un tipo de contrato
	 * @param contractType tipo a añadir
	 * @throws SQLException
	 */
	void add(ContractTypeDto contractType) throws SQLException;

	/**
	 * Actualiza los días de compensación del tipo de contrato
	 * @param contractType tipo a actualizar
	 * @throws SQLException
	 */
	void update(ContractTypeDto contractType) throws SQLException;

	/**
	 * Elimina un tipo de contrato
	 * @param idTipo tipo a eliminar
	 * @throws SQLException
	 */
	void delete(Long idTipo) throws SQLException;

	/**
	 * @return lista de todos los tipos de contrato del sistema, 
	 * lisata vacía si no hay ninguno
	 * @throws SQLException
	 */
	List<ContractTypeDto> read() throws SQLException;

	/**
	 * @param typeId tipo a buscar
	 * @return tipo de contrato con id dado,
	 * null si no existe
	 * @throws SQLException
	 */
	ContractTypeDto findById(Long typeId) throws SQLException;

}
