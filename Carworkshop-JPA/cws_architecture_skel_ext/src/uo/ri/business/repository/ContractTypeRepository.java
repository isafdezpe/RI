package uo.ri.business.repository;

import uo.ri.model.ContractType;

public interface ContractTypeRepository extends Repository<ContractType> {

	/**
	 * 
	 * @param name
	 * @return contract type identified by name
	 */
	ContractType findByName(String name);

}
