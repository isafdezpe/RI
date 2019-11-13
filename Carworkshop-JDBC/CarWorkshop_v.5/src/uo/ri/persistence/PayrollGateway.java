package uo.ri.persistence;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import uo.ri.business.dto.PayrollDto;

public interface PayrollGateway extends SQLGateway {

	/**
	 * @param id id de la nómina a buscar
	 * @return nómina con id dado, null si no existe
	 * @throws SQLException
	 */
	PayrollDto findById(Long id) throws SQLException;

	/**
	 * @param idMecanico id del mecánico a buscar
	 * @return lista de todas las nóminas del mecánico dado,
	 * lista vacía si no hay ninguna
	 * @throws SQLException
	 */
	List<PayrollDto> findByMechanic(Long idMecanico) throws SQLException;

	/**
	 * @return lista de todas las nóminas del sistema,
	 * lista vacía si no hay ninguna
	 * @throws SQLException
	 */
	List<PayrollDto> findAll() throws SQLException;

	/**
	 * Añade las nóminas creadas a la BDD
	 * @param payrolls nóminas a añadir
	 * @return número de nóminas generadas
	 * @throws SQLException
	 */
	int generatePayrolls(Map<Long, PayrollDto> payrolls) throws SQLException;

	/**
	 * Elimina las últimas nóminas generadas
	 * @return número de nóminas eliminadas
	 * @throws SQLException
	 */
	int deleteLastGeneratedPayrolls() throws SQLException;

	/**
	 * Elimina la última nómina de un mecánico
	 * @param idMecanico mecánico
	 * @throws SQLException
	 */
	void deleteLastPayrollForMechanic(Long idMecanico) throws SQLException;

	/**
	 * @param idContrato contrato a buscar
	 * @return lista de todas las nóminas generadas para un contrato,
	 * lista vacía si no hay ninguna
	 * @throws SQLException
	 */
	List<PayrollDto> findByContract(Long idContrato) throws SQLException;

}
