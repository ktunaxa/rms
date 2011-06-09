/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.bpm;

/**
 * Constants for the Ktunaxa business flows.
 *
 * @author Joachim Van der Auwera
 */
public interface KtunaxaBpmConstant {

	String QUERY_REFERRAL_ID = "r";
	String QUERY_TASK_ID = "bpm";

	String VAR_REFERRAL_ID = "referralId";
	String VAR_DESCRIPTION = "description";

	String REFERRAL_PROCESS_ID = "referral-handling";
	String REFERRAL_CONTEXT_REFERRAL_ID = "referralId";
	String REFERRAL_CONTEXT_DESCRIPTION = "description";
	String REFERRAL_CONTEXT_EMAIL = "requesterEmail";
	String REFERRAL_CONTEXT_ENGAGEMENT_LEVEL = "engagementLevel";
	String REFERRAL_CONTEXT_COMPLETION_DEADLINE = "completionDeadline";
	String REFERRAL_CONTEXT_FINAL_DECISION_CONSISTENT = "finalDecisionConsistent";
	String REFERRAL_CONTEXT_EVALUATE_VALUES = "evaluateValues";
	String REFERRAL_CONTEXT_DECISION_CONSISTENT = "decisionConsistent";
	String REFERRAL_CONTEXT_EVALUATE_AQUATIC = "evalAquatic";
}
