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

	String REFERRAL_PROCESS_ID = "referral-handling";

	String VAR_REFERRAL_ID = "referralId";
	String VAR_REFERRAL_NAME = "referralName";
	String VAR_EMAIL = "requesterEmail";
	String VAR_PROVINCE_ENGAGEMENT_LEVEL = "provinceEngagementLevel";
	String VAR_ENGAGEMENT_LEVEL = "engagementLevel";
	String VAR_ENGAGEMENT_COMMENT = "engagementComment";
	String VAR_COMPLETION_DEADLINE = "completionDeadline";
	String VAR_NEED_CHANGE_NOTIFICATION = "needChangeNotification";
	String VAR_INCOMPLETE = "incomplete";
	String VAR_FINAL_DECISION_CONSISTENT = "finalDecisionConsistent";
	String VAR_REPORT_VALUES = "reportValues";
	String VAR_DECISION_CONSISTENT = "decisionConsistent";
	String VAR_EVALUATE_AQUATIC = "evalAquatic";
	String VAR_EVALUATE_ARCHAEOLOGICAL = "evalArchaeological";
	String VAR_EVALUATE_CULTURAL = "evalCultural";
	String VAR_EVALUATE_TERRESTRIAL = "evalTerrestrial";
	String VAR_EVALUATE_TREATY = "evalTreaty";
	String VAR_COMMUNITY_A_INPUT = "communityAInput";
	String VAR_COMMUNITY_B_INPUT = "communityBInput";
	String VAR_COMMUNITY_C_INPUT = "communityCInput";
	String VAR_COMMUNITY_D_INPUT = "communityDInput";

	String DATE_FORMAT = "yyyy-MM-dd";
	String SET_BOOLEAN = "_Boolean";

	String ROLE_REFERRAL_MANAGER = "referralManager";
	String ROLE_ARCHAEOLOGY = "archaeologyEvaluator";
	String ROLE_CULTURAL = "culturalEvaluator";
	String ROLE_TERRESTRIAL = "terrestrialEvaluator";
	String ROLE_TREATY = "treatyEvaluator";
	String ROLE_AQUATIC = "aquaticEvaluator";
	String ROLE_COMMUNITY_AKISQNUK = "communityAManager";
	String ROLE_COMMUNITY_LOWER_KOOTENAY = "communityBManager";
	String ROLE_COMMUNITY_ST_MARYS = "communityCManager";
	String ROLE_COMMUNITY_TOBACCO_PLAINS = "communityDManager";
}
