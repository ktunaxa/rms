/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
        Assert.assertFalse(m.matches());
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
