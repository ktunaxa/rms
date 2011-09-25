/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
 */

package org.ktunaxa.referral.server.email;

/**
 * Contains the notifiers/identifiers for retrieving {@link Template} objects from the DB.
 * @author Emiel Ackermann
 *
 */
public interface EmailNotifiers {

	String LEVEL_0 = "notify.level0";
	String START = "notify.start";
	String CHANGE = "notify.change.engagementLevel";
	String RESULT = "notify.result";
}
