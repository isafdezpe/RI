package uo.ri.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "TTipoContratos")
public class ContractType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;
	private int compensationDaysPerYear;

	@OneToMany(mappedBy = "tipoContrato")
	private Set<Contract> contratos = new HashSet<>();

	ContractType() {
	}

	public ContractType(String name, int compensationDays) {
		super();
		this.name = name;
		this.compensationDaysPerYear = compensationDays;
	}

	/**
	 * @return id del tipo de contrato
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return nombre del tipo de contrato
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece los dias de compensacion
	 * 
	 * @param compensationDays
	 */
	public void setCompensationDays(int compensationDays) {
		this.compensationDaysPerYear = compensationDays;
	}

	/**
	 * @return dias de compensacion
	 */
	public int getCompensationDays() {
		return compensationDaysPerYear;
	}

	/**
	 * @return contratos pertenecientes al tipo de contrato
	 */
	Set<Contract> _getContracts() {
		return contratos;
	}

	/**
	 * @return copia de los contratos
	 */
	public Set<Contract> getContracts() {
		return new HashSet<>(contratos);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(compensationDaysPerYear);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContractType other = (ContractType) obj;
		if (Double.doubleToLongBits(compensationDaysPerYear) != Double
				.doubleToLongBits(other.compensationDaysPerYear))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ContractType [id=" + id + ", name=" + name
				+ ", compensationDaysPerYear=" + compensationDaysPerYear
				+ ", contratos=" + contratos + "]";
	}

}
