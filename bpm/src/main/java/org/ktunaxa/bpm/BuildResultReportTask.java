/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.bpm;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Activiti task which builds the result report.
 *
 * @author Joachim Van der Auwera
 */
public class BuildResultReportTask implements JavaDelegate {

	private final Logger log  = LoggerFactory.getLogger(BuildResultReportTask.class);

	public void execute(DelegateExecution delegateExecution) throws Exception {
		log.debug("Start task execution " + delegateExecution.getVariable("referralId"));
		// @todo implement
	}
}
