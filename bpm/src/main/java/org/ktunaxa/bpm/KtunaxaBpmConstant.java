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

	String REFERRAL_PROCESS_ID = "referral-handling";

	String VAR_REFERRAL_ID = "referralId";
	String VAR_REFERRAL_NAME = "referralName";
	String VAR_EMAIL = "requesterEmail";
	String VAR_PROVINCE_ENGAGEMENT_LEVEL = "provinceEngagementLevel";
	String VAR_ENGAGEMENT_LEVEL = "engagementLevel";
	String VAR_ENGAGEMENT_COMMENT = "engagementComment";
	String VAR_COMPLETION_DEADLINE = "completionDeadline";
	String VAR_FINAL_DECISION_CONSISTENT = "finalDecisionConsistent";
	String VAR_REPORT_VALUES = "reportValues";
	String VAR_DECISION_CONSISTENT = "decisionConsistent";
	String VAR_EVALUATE_AQUATIC = "evalAquatic";
	String VAR_EVALUATE_ARCHAEOLOGICAL = "evalArchaeological";
	String VAR_EVALUATE_CULTURAL = "evalCultural";
	String VAR_EVALUATE_ECOLOGICAL = "evalEcological";
	String VAR_EVALUATE_TREATY = "evalTreaty";
	String VAR_COMMUNITY_INPUT = "communityInput";

	String DATE_FORMAT = "yyyy-MM-dd";
	String SET_BOOLEAN = "_Boolean";
}
