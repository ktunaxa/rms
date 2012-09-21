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

import java.io.IOException;
import java.io.InputStream;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.Session;

/**
 * Service for connecting to a CMIS server and executing basic operations on it.
 * 
 * @author Pieter De Graef
 */
public interface CmisService {

	/**
	 * Get the base URL for the CMIS service.
	 * 
	 * @return The base URL for the CMIS service.
	 */
	String getBaseUrl();

	/**
	 * Create a session on some external CMIS server and return it.
	 * 
	 * @return An active CMIS session.
	 */
	Session createSession();

	/**
	 * Get the working folder for all documents we're interested in.
	 * 
	 * @return Returns the folder instance.
	 * @throws IOException
	 *             In case there was a problem retrieving the data from the CMIS instance.
	 */
	Folder getWorkingFolder() throws IOException;

	/**
	 * Return the URL that points to a download link for the document.
	 * 
	 * @param document
	 *            The document object to get the download link for.
	 * @return The URL at which the document can be downloaded. Note that credentials will probably be required to
	 *         access the URL.
	 */
	String getDownloadUrl(Document document);

	/**
	 * Return the URL that points to a display link for the document.
	 * 
	 * @param document
	 *            The document object to get the display link for.
	 * @return The URL at which the document can be displayed. Note that credentials will probably be required to
	 *         access the URL.
	 */
	String getDisplayUrl(Document document);

	/**
	 * Create a new document or update an existing document within the working folder.
	 * 
	 * @param documentName
	 *            The document name.
	 * @param mimeType
	 *            The mime-type for the document (i.e. 'text/html').
	 * @param in
	 *            The actual document contents in the form of a stream.
	 * @param folderName names of the folder tree in which the document should be put
	 * @return Returns the CMIS document instance on a successful create/update.
	 * @throws IOException
	 *             In case there was a problem saving the document in the CMIS instance. No document will have been
	 *             created/updated.
	 */
	Document saveOrUpdate(String documentName, String mimeType, InputStream in, long contentLength, 
			String... folderName) throws IOException;

	/**
	 * Create a new document within the working folder.
	 * 
	 * @param documentName
	 *            The document name. If there is already a document with the same name, an exception is thrown.
	 * @param mimeType
	 *            The mime-type for the document (i.e. 'text/html').
	 * @param in
	 *            The actual document contents in the form of a stream.
	 * @param folderName names of the folder tree in which the document should be put
	 * @return Returns the CMIS document instance on a successful create.
	 * @throws IOException
	 *             In case there was a problem saving the document in the CMIS instance. No document will have been
	 *             created.
	 */
	Document create(String documentName, String mimeType, InputStream in, long contentLength,
			String... folderName) throws IOException;

	/**
	 * Get a certain document by it's document name.
	 * 
	 * @param documentName
	 *            The name of the document to return.
	 * @return The CMIS document instance.
	 * @throws IOException
	 *             In case there was a problem retrieving the document from the CMIS instance, or the document simply
	 *             doesn't exist.
	 */
	Document read(String documentName) throws IOException;

	/**
	 * Update the contents of a certain document.
	 * 
	 * @param documentName
	 *            The name of the document to update.
	 * @param mimeType
	 *            The mime-type for the document (i.e. 'text/html').
	 * @param in
	 *            The new contents as an InputStream.
	 * @throws IOException
	 *             In case there was a problem updating the document in the CMIS instance, or the document simply
	 *             doesn't exist. The document will not have been updated.
	 */
	void update(String documentName, String mimeType, InputStream in) throws IOException;

	/**
	 * Delete a given document using the document name.
	 * 
	 * @param documentName
	 *            The name of the document to delete.
	 * @throws IOException
	 *             In case there was a problem deleting the document in the CMIS instance, or the document simply
	 *             doesn't exist. The document will not have been deleted.
	 */
	void delete(String documentName) throws IOException;
}