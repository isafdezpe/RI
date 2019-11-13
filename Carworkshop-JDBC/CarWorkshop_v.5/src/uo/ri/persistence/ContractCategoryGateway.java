package uo.ri.persistence;

import java.sql.SQLException;
import java.util.List;

import uo.ri.business.dto.ContractCategoryDto;

public interface ContractCategoryGateway extends SQLGateway {

	/**
	 * Añade una categoría de contrato
	 * @param contractCategory categoría a añadir
	 * @throws SQLException
	 */
	void add(ContractCategoryDto contractCategory) throws SQLException;

	/**
	 * Actualiza el plus de productividad y los trienios de una categoría
	 * de contrato
	 * @param contractCategory categoría a actualizar
	 * @throws SQLException
	 */
	void update(ContractCategoryDto contractCategory) throws SQLException;

	/**
	 * Elimina una categoría 
	 * @param idCategoria categoría a eliminar
	 * @throws SQLException
	 */
	void delete(Long idCategoria) throws SQLException;

	/**
	 * @return lista de todas las categorías de contrato del sistema,
	 * lista vacía si no hay ninguna
	 * @throws SQLException
	 */
	List<ContractCategoryDto> read() throws SQLException;

	/**
	 * @param idCategoria categoría a buscar
	 * @return categoría correspondiente con el id dado,
	 * null si no existe
	 * @throws SQLException
	 */
	ContractCategoryDto findById(Long idCategoria) throws SQLException;

}
