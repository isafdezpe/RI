package uo.ri.persistence.jpa;

import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.model.ContractType;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class ContractTypeJpaRepository extends BaseRepository<ContractType>
		implements ContractTypeRepository {

	@Override
	public ContractType findByName(String name) {
		return Jpa.getManager()
				.createNamedQuery("ContractType.findByName", 
						ContractType.class)
				.setParameter(1, name)
				.getResultList().stream()
				.findFirst()
				.orElse(null);
	}

}
