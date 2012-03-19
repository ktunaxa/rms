/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.ktunaxa.referral.server.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;


public class KtunaxaConstantRegexTest {

	@Test
	public void testOneMailRegex() {
        Pattern p = Pattern.compile(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@ktunaxa.org");
        Assert.assertTrue(m.matches());
	}
	
	@Test
	public void testMultiMailRegexForOne() {
		Pattern p = Pattern.compile(KtunaxaConstant.MULTIPLE_MAIL_VALIDATOR_REGEX);
		Matcher m = p.matcher("bla@ktunaxa.org");
		Assert.assertTrue(m.matches());
	}
	
	@Test
	public void testMultiMailRegex1() {
        Pattern p = Pattern.compile(KtunaxaConstant.MULTIPLE_MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@ktunaxa.org, emiel.ackermann@geosparc.com, bla@ktunaxa.org, emiel.ackermann@geosparc.com");
        Assert.assertTrue(m.matches());
	}
	
	@Test
	public void testMultiMailRegex2() {
        Pattern p = Pattern.compile(KtunaxaConstant.MULTIPLE_MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@ktunaxa.org; kl@io.ol; bla@ktunaxa.org; emiel.ackermann@geosparc.com");
        Assert.assertTrue(m.matches());
	}
	
	@Test
	public void testMultiMailRegex3() {
        Pattern p = Pattern.compile(KtunaxaConstant.MULTIPLE_MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@ktunaxa.org, kl@io.ol; bla@ktunaxa.org; emiel.ackermann@geosparc.com");
        Assert.assertTrue(m.matches());
	}
	
	// Email addresses that should fail.
	
	@Test
	public void testOneMailRegexFail() {
        Pattern p = Pattern.compile(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@ktunaxa");
        Assert.assertFalse(m.matches());
	}
	
	@Test
	public void testOneMailRegexFail1() {
        Pattern p = Pattern.compile(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@ktunaxa.canada");
        Assert.assertFalse(m.matches());
	}
	
	@Test
	public void testOneMailRegexFail2() {
        Pattern p = Pattern.compile(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@ktunaxa.canada.org");
        Assert.assertTrue(m.matches());
	}
	
	@Test
	public void testOneMailRegexFail3() {
        Pattern p = Pattern.compile(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@yada@ktunaxa.org");
        Assert.assertFalse(m.matches());
	}
	
	@Test
	public void testOneMailRegexFail4() {
        Pattern p = Pattern.compile(KtunaxaConstant.MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla@ktunaxa.org; bla@ktunaxa.org");
        Assert.assertFalse(m.matches());
	}
	
	@Test
	public void testMultiMailRegexFails1() {
        Pattern p = Pattern.compile(KtunaxaConstant.MULTIPLE_MAIL_VALIDATOR_REGEX);
        Matcher m = p.matcher("bla.ktunaxa.org; bla@ktunaxa.org; emiel.ackermann@geosparc.com");
        Assert.assertFalse(m.matches());
	}
	
}
