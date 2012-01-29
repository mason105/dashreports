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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity(name = "T_PARAMETER")
@NamedQueries( {
	@NamedQuery(name = "getParmatersByJob", query = "from T_PARAMETER p where p.pk.runnerJob_pk.jobName=? and p.pk.runnerJob_pk.group.groupName=?")
})
public class RunnerJobParameter extends DatabaseObject<RunnerJobParameter_pk> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5963262941328410080L;
	public RunnerJobParameter_pk getId() {
		return pk;
	}

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

	@Id
	private RunnerJobParameter_pk pk;
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
	
	
	
	public RunnerJobParameter_pk getPk() {
		return pk;
	}

	public void setPk(RunnerJobParameter_pk pk) {
		this.pk = pk;
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

}
