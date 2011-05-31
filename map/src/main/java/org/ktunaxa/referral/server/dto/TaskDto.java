/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Task DTO object.
 * 
 * @author Jan De Moerloose
 * @author Joachim Van der Auwera
 */
public class TaskDto implements Serializable {

	private static final long serialVersionUID = 100L;

	/** DB id of the task. */
	private String id;

	/** Name or title of the task. */
	private String name;

	/** Free text description of the task. */
	private String description;

	/**
	 * Indication of how important/urgent this task is with a number between 0 and 100 where higher values mean a higher
	 * priority and lower values mean lower priority: [0..19] lowest, [20..39] low, [40..59] normal, [60..79] high
	 * [80..100] highest.
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

	private List<String> candidates = new ArrayList<String>();
	private Map<String, String> variables = new HashMap<String, String>();

	/**
	 * Get the task id.
	 *
	 * @return task id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Set the task id.
	 *
	 * @param id task id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Set the task name, also named "title".
	 *
	 * @return task name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the task name, also named "title".
	 *
	 * @param name task name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get the task description.
	 *
	 * @return task description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the task description.
	 *
	 * @param description task description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get the priority for the task.
	 * <p/>
	 * Priority is a number between 0 and 100 where higher values mean a higher priority and lower values mean lower
	 * priority: [0..19] lowest, [20..39] low, [40..59] normal, [60..79] high [80..100] highest.
	 *
	 * @return priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Set the priority for the task.
	 * <p/>
	 * Priority is a number between 0 and 100 where higher values mean a higher priority and lower values mean lower
	 * priority: [0..19] lowest, [20..39] low, [40..59] normal, [60..79] high [80..100] highest.
	 *
	 * @param priority priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Get the task owner.
	 *
	 * @return task owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * Set the task owner.
	 *
	 * @param owner task owner
	 */
	public void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Get task assignee.
	 *
	 * @return task assignee
	 */
	public String getAssignee() {
		return assignee;
	}

	/**
	 * Set task assignee.
	 *
	 * @param assignee task assignee
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	/**
	 * Get process instance id.
	 *
	 * @return process instance id
	 */
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * Set process instance id.
	 *
	 * @param processInstanceId process instance id
	 */
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	/**
	 * Get execution id.
	 *
	 * @return execution id
	 */
	public String getExecutionId() {
		return executionId;
	}

	/**
	 * Set execution id.
	 *
	 * @param executionId execution id
	 */
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	/**
	 * Get process definition id.
	 *
	 * @return process definition id
	 */
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	/**
	 * Set process definition id.
	 *
	 * @param processDefinitionId process definition id
	 */
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	/**
	 * Get the creation time for the task.
	 *
	 * @return creation time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Set the creation time for the task.
	 *
	 * @param createTime create time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Get task definition key.
	 *
	 * @return task definition key
	 */
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	/**
	 * Set task definition key.
	 *
	 * @param taskDefinitionKey task definition key
	 */
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	/**
	 * Get task due date.
	 *
	 * @return due date
	 */
	public Date getDueDate() {
		return dueDate;
	}

	/**
	 * Set task due date.
	 *
	 * @param dueDate due date
	 */
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	/**
	 * Get delegation state.
	 *
	 * @return delegation state
	 */
	public DelegationState getDelegationState() {
		return this.delegationState;
	}

	/**
	 * Set delegation state.
	 *
	 * @param delegationState delegation state
	 */
	public void setDelegationState(DelegationState delegationState) {
		this.delegationState = delegationState;
	}

	/**
	 * Add a candidate assignee.
	 *
	 * @param candidate candidate assignee
	 */
	public void addCandidate(String candidate) {
		candidates.add(candidate);
	}

	/**
	 * Get list of candidate assignees.
	 *
	 * @return candidate assignees
	 */
	public List<String> getCandidates() {
		return candidates;
	}

	/**
	 * Add a variable to the list of variables.
	 *
	 * @param name variable name
	 * @param value variable value
	 */
	public void addVariable(String name, String value) {
		variables.put(name, value);
	}

	/**
	 * Get the variables for the task.
	 *
	 * @return variables for the task
	 */
	public Map<String, String> getVariables() {
		return variables;
	}

	/**
	 * Copy of task enum for delegation state.
	 *
	 * @author Jan De Moerloose
	 */
	public enum DelegationState {

		/**
		 * The owner delegated the task and wants to review the result after the assignee has resolved the task. When
		 * the assignee completes the task, the task is marked as {@link #RESOLVED} and sent back to the owner. When
		 * that happens, the owner is set as the assignee so that the owner gets this task back in the "to do".
		 */
		PENDING,

		/**
		 * The assignee has resolved the task, the assignee was set to the owner again and the owner now finds this task
		 * back in the "to do" list for review. The owner now is able to complete the task.
		 */
		RESOLVED
	}

}
