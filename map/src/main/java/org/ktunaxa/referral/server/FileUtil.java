/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Ktunaxa Nation Council, http://www.ktunaxa.org/, Canada.
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
 * 
 */
public final class FileUtil {

	private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

	private static final int BUFFER = 2048;

	private FileUtil() {

	}

	public static String[] unzip(File zipFile, File targetDir) throws IOException {
		ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile));
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
			while ((count = zin.read(data, 0, BUFFER)) != -1) {
				destination.write(data, 0, count);
			}
			destination.flush();
			destination.close();
		}
		return names.toArray(new String[names.size()]);
	}

	public static void copyURLToFile(URL url, File file) throws IOException {
		FileUtils.copyURLToFile(url, file);
	}
}
