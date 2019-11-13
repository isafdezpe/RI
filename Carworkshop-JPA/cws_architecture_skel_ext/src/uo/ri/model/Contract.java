package uo.ri.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.persistence.*;

import alb.util.date.Dates;
import uo.ri.model.types.ContractStatus;

@Entity
@Table(name = "TContratos")
public class Contract {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@Column(nullable = true)
	private Date endDate;
	private double baseSalaryPerYear;
	private double compensation;
	@Enumerated(EnumType.STRING)
	private ContractStatus status = ContractStatus.ACTIVO;

	@ManyToOne
	private Mecanico mecanico;
	@ManyToOne
	private ContractType tipoContrato;
	@ManyToOne
	private ContractCategory categoriaContrato;
	@OneToMany(mappedBy = "contrato")
	private Set<Payroll> nominas = new HashSet<>();

	Contract() {
	}

	public Contract(Mecanico mechanic, Date startDate) {
		super();
		Association.Vincular.link(mechanic, this);
		this.startDate = Dates.firstDayOfMonth(startDate);
	}

	public Contract(Mecanico mechanic, Date startDate, double baseSalary) {
		this(mechanic, startDate);
		if (baseSalary < 0)
			throw new IllegalArgumentException();
		this.baseSalaryPerYear = baseSalary;
	}

	public Contract(Mecanico mechanic, Date startDate, Date endDate,
			double baseSalary) {
		this(mechanic, startDate, baseSalary);
		this.endDate = endDate;
	}

	/**
	 * @return id del contrato
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return copia de la fecha de inicio
	 */
	public Date getStartDate() {
		return (Date) startDate.clone();
	}

	/**
	 * Establece la fecha de finalización
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = Dates.lastDayOfMonth(endDate);
	}

	/**
	 * @return copia de la fecha de finalización
	 */
	public Date getEndDate() {
		return (Date) endDate.clone();
	}

	/**
	 * @return salario base anual
	 */
	public double getBaseSalaryPerYear() {
		return baseSalaryPerYear;
	}

	/**
	 * @return compensacion
	 */
	public double getCompensation() {
		return compensation;
	}

	/**
	 * @return estado del contrato
	 */
	public ContractStatus getStatus() {
		return status;
	}

	/**
	 * Establece el mecanico al que pertenece el contrato
	 * 
	 * @param mecanico
	 */
	void _setMechanic(Mecanico mecanico) {
		this.mecanico = mecanico;
	}

	/**
	 * @return mecanico al que pertenece el contrato
	 */
	public Mecanico getMechanic() {
		return mecanico;
	}

	/**
	 * Establece el tipo de contrato
	 * 
	 * @param tipo
	 */
	void _setContractType(ContractType tipo) {
		this.tipoContrato = tipo;
	}

	/**
	 * @return tipo de contrato
	 */
	public ContractType getContractType() {
		return tipoContrato;
	}

	/**
	 * Establece la categoria del contrato
	 * 
	 * @param categoria
	 */
	void _setContractCategory(ContractCategory categoria) {
		this.categoriaContrato = categoria;
	}

	/**
	 * @return categoria del contrato
	 */
	public ContractCategory getContractCategory() {
		return categoriaContrato;
	}

	/**
	 * @return nominas asociadas al contrato
	 */
	Set<Payroll> _getPayrolls() {
		return nominas;
	}

	/**
	 * @return copia de las nominas
	 */
	public Set<Payroll> getPayrolls() {
		return new HashSet<>(nominas);
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
		result = prime * result
				+ ((mecanico == null) ? 0 : mecanico.hashCode());
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		Contract other = (Contract) obj;
		if (mecanico == null) {
			if (other.mecanico != null)
				return false;
		} else if (!mecanico.equals(other.mecanico))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
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
		return "Contract [id=" + id + ", startDate=" + startDate + ", endDate="
				+ endDate + ", baseSalaryPerYear=" + baseSalaryPerYear
				+ ", compensation=" + compensation + ", status=" + status
				+ ", mecanico=" + mecanico + ", tipoContrato=" + tipoContrato
				+ ", categoriaContrato=" + categoriaContrato + ", nominas="
				+ nominas + "]";
	}

	/**
	 * Finaliza un contrato
	 * 
	 * @param endDate
	 */
	public void markAsFinished(Date endDate) {
		if (isFinished())
			throw new IllegalStateException();
		if (endDate.before(getStartDate()))
			throw new IllegalArgumentException();

		setEndDate(endDate);

		long diff = getEndDate().getTime() - getStartDate().getTime();
		if ((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)) / 30 < 12)
			this.compensation = 0.0;
		else
			this.compensation = baseSalaryPerYear / 365
					* getContractType().getCompensationDays();

		this.status = ContractStatus.EXTINTO;
	}

	/**
	 * @return true si el contrato ha finalizado
	 */
	public boolean isFinished() {
		return status.equals(ContractStatus.EXTINTO);
	}

	/**
	 * @return la ultima nomina generada para el contrato
	 */
	public Payroll getLastPayroll() {
		@SuppressWarnings("deprecation")
		Date d = new Date(1900, 1, 1);
		Payroll ultima = null;
		for (Payroll p : nominas) {
			if (p.getDate().after(d))
				d = p.getDate();
			ultima = p;
		}
		return ultima;
	}

	/**
	 * @return porcentaje de irpf
	 */
	public double getIrpfPercent() {
		if (baseSalaryPerYear < 12000)
			return 0;
		else if (baseSalaryPerYear < 30000)
			return 0.1;
		else if (baseSalaryPerYear < 40000)
			return 0.15;
		else if (baseSalaryPerYear < 50000)
			return 0.2;
		else if (baseSalaryPerYear < 60000)
			return 0.3;
		else
			return 0.4;
	}

}
