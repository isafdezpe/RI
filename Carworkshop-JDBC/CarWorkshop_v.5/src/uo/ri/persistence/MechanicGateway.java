package uo.ri.persistence;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import uo.ri.business.dto.MechanicDto;

public interface MechanicGateway extends SQLGateway {

	/**
	 * Añade un mecánico
	 * @param mechanic mecánico a añadir
	 * @throws SQLException
	 */
	void add(MechanicDto mechanic) throws SQLException;
	
	/**
	 * Elimina un mecánico
	 * @param idMecanico id del mecánico a eliminar
	 * @throws SQLException
	 */
	void delete(Long idMecanico) throws SQLException;
	
	/**
	 * Actualiza nombre y apellidos de un mecánico
	 * @param mechanic mecánico a actualizar
	 * @throws SQLException
	 */
	void update(MechanicDto mechanic) throws SQLException;
	
	/**
	 * @return lista de todos los mecánicos del sistema,
	 * lista vacía si no hay ninguno
	 * @throws SQLException
	 */
	List<MechanicDto> read() throws SQLException;
	
	/**
	 * @param mechanicId id del mecánico a buscar
	 * @return mecánico con id dado, null si no existe
	 * @throws SQLException
	 */
	MechanicDto findById(Long mechanicId) throws SQLException;
	
	/**
	 * @return lista de todos los mecánicos activos del sistema,
	 * lista vacía si no hay ninguno
	 * @throws SQLException
	 */
	List<MechanicDto> listActive() throws SQLException;
	
	/**
	 * @param mechanicId id del mecánico a buscar
	 * @param startDate fecha de inicio del periodo
	 * @param endDate fecha de fin del periodo
	 * @return true si el mecánico ha realizado actividades en el periodo indicado,
	 * false si no
	 * @throws SQLException
	 */
	boolean hasActivities(Long mechanicId, Date startDate, Date endDate) throws SQLException;
	
	/**
	 * @param id id del mecánico a borrar
	 * @return true si puede ser borrado, false si no
	 * @throws SQLException
	 */
	boolean canBeDeleted(Long id) throws SQLException;
	
}
