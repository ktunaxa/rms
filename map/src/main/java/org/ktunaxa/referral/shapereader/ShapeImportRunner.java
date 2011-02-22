/*
 * This is part of the Ktunaxa referral system.
 *
 * Copyright 2011 Geosparc nv, http://www.geosparc.com/, Belgium.
 * Copyright 2011 Ktunaxa Nation Counsil, http://www.ktunaxa.org/, Canada.
 *
 * The program is available in open source according to the GNU Affero
 * General Public License. All contributions in this program are covered
 * by the Geomajas Contributors License Agreement. For full licensing
 * details, see LICENSE.txt in the project root.
 */

package org.ktunaxa.referral.shapereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.geotools.data.DataStore;
import org.ktunaxa.referral.server.domain.ReferenceLayer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Stand-alone reader for shape files.
 * 
 * @author Pieter De Graef
 */
public final class ShapeImportRunner {

	private ShapeReaderService service;

	/** Initialize the object, by preparing a Spring context. */
	private ShapeImportRunner() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"classpath:org/ktunaxa/referral/context/spring-hibernate.xml",
				"classpath:org/ktunaxa/referral/shapereader/applicationContext.xml");
		service = ctx.getBean(ShapeReaderService.class);
	}

	/**
	 * Upload shape files from the command prompt.
	 * 
	 * @param args
	 *            no arguments used.
	 */
	public static void main(String[] args) {
		ShapeImportRunner reader = new ShapeImportRunner();
		try {
			File[] files = reader.service.getAllFiles();
			if (files != null && files.length > 0) {
				File choice = selectFile(files);
				System.out.println("Your choice: " + choice.getName());
				DataStore dataStore = reader.service.read(choice);
				reader.service.validateFormat(dataStore);
				System.out.println("");
				List<ReferenceLayer> layers = reader.service.getAllLayers();
				ReferenceLayer layer = selectLayer(layers);
				System.out.println("");
				System.out.println("You have selected shape file '" + choice.getName()
						+ "' to replace the current contents of data type '" + layer.getName() + "' in the database.");
				System.out.println("Are you sure you want to continue? (y / n)");
				boolean letsGo = readYesNo();
				if (letsGo) {
					System.out.println("Busy loading the new contents into the database...");
					reader.service.persist(layer, dataStore);
				} else {
					System.out.println("Aborting upload proces. Hope to see you later.");
				}
			} else {
				throw new IOException("No shape files could be found.");
			}
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
			System.err.println("Aborting...");
			System.exit(1);
		}
		System.out.println("Done!");
		System.exit(0);
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	/** Given a list of reference layers, let the user choose one. */
	private static ReferenceLayer selectLayer(List<ReferenceLayer> layers) {
		System.out.println("Choose one of the following data types:");
		for (int i = 0; i < layers.size(); i++) {
			System.out.println((i + 1) + ": " + layers.get(i).getType().getDescription() + " - "
					+ layers.get(i).getName());
		}
		System.out.println("Type a number:");
		int choice = readIntegerLine();
		if (choice <= 0 || choice > layers.size()) {
			System.out.println("");
			System.out.println("Selected data type does not exist. Please try again.");
			return selectLayer(layers);
		}
		return layers.get(choice - 1);
	}

	/** Read 'y' or 'n' from the command prompt (system.in) and return it as a boolean value. */
	private static boolean readYesNo() {
		String answer = readLine();
		if ("y".equalsIgnoreCase(answer)) {
			return true;
		} else if ("n".equalsIgnoreCase(answer)) {
			return false;
		}
		System.out.println("Please type only 'y' or 'n':");
		return readYesNo();
	}

	/** Given a list of files, let the user select one. */
	private static File selectFile(File[] files) {
		System.out.println("Choose one of the following shape files:");
		for (int i = 0; i < files.length; i++) {
			System.out.println((i + 1) + ": " + files[i].getName());
		}
		System.out.println("Type a number:");
		int choice = readIntegerLine();
		if (choice <= 0 || choice > files.length) {
			System.out.println("");
			System.out.println("Selected shape files does not exist. Please try again.");
			return selectFile(files);
		}
		return files[choice - 1];
	}

	/** Read a line of text from the command prompt (system.in). */
	private static String readLine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			line = br.readLine();
		} catch (IOException ioe) {
			System.out.println("IO error trying to read your answer.");
			System.exit(1);
		}
		return line;
	}

	/** Read a line of text from the command prompt (system.in) and return it as an integer. */
	private static int readIntegerLine() {
		String line = readLine();
		try {
			int result = Integer.parseInt(line);
			return result;
		} catch (Exception e) {
			System.out.println("Error while parsing the input as an integer. Please try again:");
			return readIntegerLine();
		}
	}
}