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
package org.ktunaxa.referral.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * File utility for unzipping, copying files.
 * 
 * @author Jan De Moerloose
 */
public final class FileUtil {

	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

	private static final int BUFFER = 2048;

	private FileUtil() {
	}

	public static String[] unzip(File zipFile, File targetDir) throws IOException {
		ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
		try {
			ZipEntry entry;
			ArrayList<String> names = new ArrayList<String>();
			while ((entry = zin.getNextEntry()) != null) {
				LOG.info("Extracting: " + entry);
				String name = entry.getName();
				names.add(name);
				int count;
				byte[] data = new byte[BUFFER];
				FileOutputStream fos = new FileOutputStream(new File(targetDir, name));
				BufferedOutputStream destination = new BufferedOutputStream(fos, BUFFER);
				try {
					while ((count = zin.read(data, 0, BUFFER)) != -1) {
						destination.write(data, 0, count);
					}
					destination.flush();
				} finally {
					destination.close();
				}
			}
			return names.toArray(new String[names.size()]);
		} finally {
			zin.close();
		}
	}

	public static void copyURLToFile(URL url, File file) throws IOException {
		FileUtils.copyURLToFile(url, file);
	}
}
