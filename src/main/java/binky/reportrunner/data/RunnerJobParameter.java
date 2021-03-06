/*******************************************************************************
 * Copyright (c) 2009 Daniel Grout.
 * 
 * GNU GENERAL PUBLIC LICENSE - Version 3
 * 
 * This file is part of Report Runner (http://code.google.com/p/reportrunner).
 * 
 * Report Runner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Report Runner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Report Runner. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Module: RunnerJobParameter.java
 ******************************************************************************/
package binky.reportrunner.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

@Entity(name = "T_PARAMETER")
@NamedQueries( {
	@NamedQuery(name = "getParmatersByJob", query = "from T_PARAMETER p where p.runnerJob.pk.jobName=? and p.runnerJob.pk.group.groupName=?")
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RunnerJobParameter extends DatabaseObject<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5963262941328410080L;
	public Integer getId() {
		return id;
	}

	//hack for struts 2 as the id part is from the generic typed abstract class and it is throwing an NPE when trying to deal with it.
	public Integer getUniqueId() {
		return id;
	}
	
	public void setUniqueId(Integer id) {
		this.id=id;
	}
	/**/
	
	public enum DataType {
		STRING("String"), DATE("Date"), BOOLEAN("Boolean"),INTEGER("Integer"),LONG("Long"),DOUBLE("Double");
		
		private String displayName;

		DataType(String displayName) {
			this.displayName = displayName;
		}

		public String getName() {
			return name();
		}

		public String getDisplayName() {
			return displayName;
		}
	};

	@Id 	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	//private RunnerJobParameter_pk pk;

	
	@ManyToOne
	@Index(name="paramJobIndex")
	private RunnerJob runnerJob;
	
	private Integer parameterIdx;

	
	private String parameterValue;
	private String parameterBurstColumn;
	private String description;
	public String getParameterBurstColumn() {
		return parameterBurstColumn;
	}

	public void setParameterBurstColumn(String parameterBurstColumn) {
		this.parameterBurstColumn = parameterBurstColumn;
	}


	private DataType parameterType;

	public DataType getParameterType() {
		return parameterType;
	}

	public void setParameterType(DataType parameterType) {
		this.parameterType = parameterType;
	}
	
	
	


	public Integer getParameterIdx() {
		return parameterIdx;
	}

	public void setParameterIdx(Integer parameterIdx) {
		this.parameterIdx = parameterIdx;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParameterValue() {
		return parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((parameterBurstColumn == null) ? 0 : parameterBurstColumn
						.hashCode());
		result = prime * result
				+ ((parameterType == null) ? 0 : parameterType.hashCode());
		result = prime * result
				+ ((parameterValue == null) ? 0 : parameterValue.hashCode());
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RunnerJobParameter other = (RunnerJobParameter) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (parameterBurstColumn == null) {
			if (other.parameterBurstColumn != null)
				return false;
		} else if (!parameterBurstColumn.equals(other.parameterBurstColumn))
			return false;
		if (parameterType != other.parameterType)
			return false;
		if (parameterValue == null) {
			if (other.parameterValue != null)
				return false;
		} else if (!parameterValue.equals(other.parameterValue))
			return false;

		return true;
	}

	public RunnerJob getRunnerJob() {
		return runnerJob;
	}

	public void setRunnerJob(RunnerJob runnerJob) {
		this.runnerJob = runnerJob;
	}

}
