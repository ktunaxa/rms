/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Taks DTO object.
 * 
 * @author Jan De Moerloose
 * 
 */
public class TaskDto implements Serializable {

	/**
	 * Copy of task enum for delegation state.
	 * 
	 * @author Jan De Moerloose
	 * 
	 */
	public enum DelegationState {

		/**
		 * The owner delegated the task and wants to review the result after the assignee has resolved the task. When
		 * the assignee completes the task, the task is marked as {@link #RESOLVED} and sent back to the owner. When
		 * that happens, the owner is set as the assignee so that the owner gets this task back in the ToDo.
		 */
		PENDING,

		/**
		 * The assignee has resolved the task, the assignee was set to the owner again and the owner now finds this task
		 * back in the ToDo list for review. The owner now is able to complete the task.
		 */
		RESOLVED
	}

	private static final long serialVersionUID = 100L;

	/** DB id of the task. */
	private String id;

	/** Name or title of the task. */
	private String name;

	/** Free text description of the task. */
	private String description;

	/**
	 * indication of how important/urgent this task is with a number between 0 and 100 where higher values mean a higher
	 * priority and lower values mean lower priority: [0..19] lowest, [20..39] low, [40..59] normal, [60..79] high
	 * [80..100] highest
	 */
	private int priority;

	/** The userId of the person that is responsible for this task. */
	private String owner;

	/** The userId of the person to which this task is delegated. */
	private String assignee;

	/** Reference to the process instance or null if it is not related to a process instance. */
	private String processInstanceId;

	/** Reference to the path of execution or null if it is not related to a process instance. */
	private String executionId;

	/** Reference to the process definition or null if it is not related to a process. */
	private String processDefinitionId;

	/** The date/time when this task was created */
	private Date createTime;

	/** The id of the activity in the process defining this task or null if this is not related to a process */
	private String taskDefinitionKey;

	/** Due date of the task. */
	private Date dueDate;

	/** The current delegation state of this task */
	private DelegationState delegationState;

	public TaskDto() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getExecutionId() {
		return executionId;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public DelegationState getDelegationState() {
		return this.delegationState;
	}

	public void setDelegationState(DelegationState delegationState) {
		this.delegationState = delegationState;
	}

}
