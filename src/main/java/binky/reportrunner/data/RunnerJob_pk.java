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
 * Module: RunnerJob_pk.java
 ******************************************************************************/
package binky.reportrunner.data;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

@Embeddable
public class RunnerJob_pk implements Serializable {

	private static final long serialVersionUID = -1882140077608940619L;
	
	
	private String jobName;
	@ManyToOne
	private RunnerGroup group;
	
	@RequiredStringValidator
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public RunnerGroup getGroup() {
		return group;
	}
	public void setGroup(RunnerGroup group) {
		this.group = group;
	
	}
	public RunnerJob_pk(){}
	public RunnerJob_pk(String jobName, RunnerGroup group) {
		this.jobName = jobName;
		this.group = group;
	}
	
	
}
