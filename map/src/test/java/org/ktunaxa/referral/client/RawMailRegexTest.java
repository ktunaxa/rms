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

package org.ktunaxa.referral.client;

import org.junit.Assert;
import org.junit.Test;
import org.ktunaxa.referral.server.service.KtunaxaConstant;

import java.util.regex.Pattern;

/**
 * Verify that the e-mail regex we use is correct.
 *
 * @author Joachim Van der Auwera
 */
public class RawMailRegexTest {

	@Test
	public void testRawMailRegex() throws Exception {
		Pattern pattern = Pattern.compile(KtunaxaConstant.RAW_MAIL_REGEX);
		Assert.assertTrue(pattern.matcher("joachim@geosparc.com").matches());
		Assert.assertTrue(pattern.matcher("j.v.d.a@geosparc.com").matches());
		Assert.assertTrue(pattern.matcher("joachim@geo.sparc.com").matches());
		Assert.assertTrue(pattern.matcher("joa-chim@geo.sparc.com").matches());
		Assert.assertTrue(pattern.matcher("joa-chim@geo-sparc.com").matches());
		Assert.assertFalse(pattern.matcher("@geosparc.c").matches());
		Assert.assertFalse(pattern.matcher("joachim@").matches());
		Assert.assertFalse(pattern.matcher("joachim.geosparc.c").matches());
		Assert.assertFalse(pattern.matcher("joachim@geosparc.c").matches());
		Assert.assertFalse(pattern.matcher("joachim@geosparc.c-m").matches());
		Assert.assertFalse(pattern.matcher("joachim@geosparc.c").matches());
		Assert.assertFalse(pattern.matcher("joachim@geo.sparc").matches());
		Assert.assertFalse(pattern.matcher("jöachím@geo.sparc").matches());
		Assert.assertFalse(pattern.matcher("joachim@géosparc").matches());
	}
}
