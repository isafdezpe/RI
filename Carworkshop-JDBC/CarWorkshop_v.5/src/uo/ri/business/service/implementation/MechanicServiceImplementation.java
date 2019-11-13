package uo.ri.business.service.implementation;

import java.util.List;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.service.MechanicCrudService;
import uo.ri.business.transaction.mechanicManagement.AddMechanicBusiness;
import uo.ri.business.transaction.mechanicManagement.DeleteMechanicBusiness;
import uo.ri.business.transaction.mechanicManagement.FindMechanicBusiness;
import uo.ri.business.transaction.mechanicManagement.ListActiveMechanicBusiness;
import uo.ri.business.transaction.mechanicManagement.ListMechanicBusiness;
import uo.ri.business.transaction.mechanicManagement.UpdateMechanicBusiness;

public class MechanicServiceImplementation implements MechanicCrudService {

	/* (non-Javadoc)
	 * @see uo.ri.business.service.MechanicCrudService#addMechanic(uo.ri.business.dto.MechanicDto)
	 */
	@Override
	public void addMechanic(MechanicDto mecanico) throws BusinessException {
		new AddMechanicBusiness(mecanico).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.MechanicCrudService#deleteMechanic(java.lang.Long)
	 */
	@Override
	public void deleteMechanic(Long idMecanico) throws BusinessException {
		new DeleteMechanicBusiness(idMecanico).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.MechanicCrudService#updateMechanic(uo.ri.business.dto.MechanicDto)
	 */
	@Override
	public void updateMechanic(MechanicDto mecanico) throws BusinessException {
		new UpdateMechanicBusiness(mecanico).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.MechanicCrudService#findMechanicById(java.lang.Long)
	 */
	@Override
	public MechanicDto findMechanicById(Long id) throws BusinessException {
		return new FindMechanicBusiness(id).execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.MechanicCrudService#findAllMechanics()
	 */
	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return new ListMechanicBusiness().execute();
	}

	/* (non-Javadoc)
	 * @see uo.ri.business.service.MechanicCrudService#findActiveMechanics()
	 */
	@Override
	public List<MechanicDto> findActiveMechanics() throws BusinessException {
		return new ListActiveMechanicBusiness().execute();
	}

}
