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
				"classpath:org/ktunaxa/referral/shapereader/spring-hibernate.xml",
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
		try {
			new ShapeImportRunner().importShape(args.length > 0 ? args[0] : null);
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage()); // NOSONAR
			System.err.println("Aborting..."); // NOSONAR
			System.exit(1); // NOSONAR
		}
		System.out.println("Done!");
	}

	public void importShape(String subDirectory) throws IOException {
		ShapeImportRunner reader = new ShapeImportRunner();
		File[] files = reader.service.getAllFiles(subDirectory);
		if (files != null && files.length > 0) {
			File choice = selectFile(files);
			System.out.println("Your choice: " + choice.getName()); // NOSONAR
			DataStore dataStore = reader.service.read(choice);
			reader.service.validateFormat(dataStore);
			System.out.println(""); // NOSONAR
			List<ReferenceLayer> layers = reader.service.getAllLayers();
			ReferenceLayer layer = selectLayer(layers);
			System.out.println(""); // NOSONAR
			System.out.println("You have selected shape file '" + choice.getName() // NOSONAR
					+ "' to replace the current contents of data type '" + layer.getName() + "' in the database.");
			System.out.println("Are you sure you want to continue? (y / n)"); // NOSONAR
			boolean letsGo = readYesNo();
			if (letsGo) {
				System.out.println("Busy loading the new contents into the database..."); // NOSONAR
				reader.service.persist(layer, dataStore);
			} else {
				System.out.println("Aborting upload process. Hope to see you later."); // NOSONAR
			}
		} else {
			throw new IOException("No shape files could be found.");
		}
	}

	// ------------------------------------------------------------------------
	// Private methods:
	// ------------------------------------------------------------------------

	/**
	 * Given a list of reference layers, let the user choose one.
	 *
	 * @param layers layers to choose from
	 * @return chosen layer
	 */
	private ReferenceLayer selectLayer(List<ReferenceLayer> layers) {
		System.out.println("Choose one of the following data types:"); // NOSONAR
		for (int i = 0; i < layers.size(); i++) {
			System.out.println((i + 1) + ": " + layers.get(i).getType().getDescription() + " - " // NOSONAR
					+ layers.get(i).getName());
		}
		System.out.println("Type a number:"); // NOSONAR
		int choice = readIntegerLine();
		if (choice <= 0 || choice > layers.size()) {
			System.out.println(""); // NOSONAR
			System.out.println("Selected data type does not exist. Please try again."); // NOSONAR
			return selectLayer(layers);
		}
		return layers.get(choice - 1);
	}

	/**
	 * Read 'y' or 'n' from the command prompt (system.in) and return it as a boolean value.
	 *
	 * @return input value
	 */
	private boolean readYesNo() {
		String answer = readLine();
		if ("y".equalsIgnoreCase(answer)) {
			return true;
		} else if ("n".equalsIgnoreCase(answer)) {
			return false;
		}
		System.out.println("Please type only 'y' or 'n':"); // NOSONAR
		return readYesNo();
	}

	/**
	 * Given a list of files, let the user select one.
	 *
	 * @param files files to choose from
	 * @return chosen file
	 */
	private File selectFile(File[] files) {
		System.out.println("Choose one of the following shape files:"); // NOSONAR
		for (int i = 0; i < files.length; i++) {
			System.out.println((i + 1) + ": " + files[i].getName()); // NOSONAR
		}
		System.out.println("Type a number:"); // NOSONAR
		int choice = readIntegerLine();
		if (choice <= 0 || choice > files.length) {
			System.out.println("\nSelected shape files does not exist. Please try again."); // NOSONAR
			return selectFile(files);
		}
		return files[choice - 1];
	}

	/**
	 * Read a line of text from the command prompt (system.in).
	 *
	 * @return input text
	 */
	private String readLine() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = null;
		try {
			line = br.readLine();
		} catch (IOException ioe) {
			System.out.println("IO error trying to read your answer."); // NOSONAR
			System.exit(1); // NOSONAR
		}
		return line;
	}

	/**
	 * Read a line of text from the command prompt (system.in) and return it as an integer.
	 *
	 * @return input value
	 */
	private int readIntegerLine() {
		String line = readLine();
		try {
			return Integer.parseInt(line);
		} catch (NumberFormatException e) {
			System.out.println("Error while parsing the input as an integer. Please try again:"); // NOSONAR
			return readIntegerLine();
		}
	}
}