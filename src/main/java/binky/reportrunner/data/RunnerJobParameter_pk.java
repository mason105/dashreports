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
 * Module: RunnerJobParameter_pk.java
 ******************************************************************************/
package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RunnerJobParameter_pk implements Serializable {

	private static final long serialVersionUID = 5839942860917772506L;
	
	//@ManyToOne
	private RunnerJob_pk runnerJob_pk;
	private Integer parameterIdx;

	
	
	public RunnerJob_pk getRunnerJob_pk() {
		return runnerJob_pk;
	}
	public void setRunnerJob_pk(RunnerJob_pk runnerJob_pk) {
		this.runnerJob_pk = runnerJob_pk;
	}
	public Integer getParameterIdx() {
		return parameterIdx;
	}
	public void setParameterIdx(Integer parameterIdx) {
		this.parameterIdx = parameterIdx;
	}

	public String toString() {
		return this.runnerJob_pk.getGroup().getGroupName() + "." + this.runnerJob_pk.getJobName() +" - " + this.parameterIdx;
	}
	
}
