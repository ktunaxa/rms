-- ----------------------------------------------------------------------------
-- DROP ALL TABLES BEFORE CREATING THEM AGAIN
-- ----------------------------------------------------------------------------

DROP TABLE template;
DROP TABLE document_comment;
DROP TABLE referral_comment;
DROP TABLE document;
DROP TABLE document_type;
DROP TABLE reference_key_value;
DROP TABLE reference_aspect;
DROP TABLE reference;
DROP TABLE referral;
DROP TABLE referral_application_type;
DROP TABLE referral_type;
DROP TABLE referral_status;
DROP TABLE aspect;



-- ----------------------------------------------------------------------------
-- Table: ASPECT
-- ----------------------------------------------------------------------------
CREATE TABLE aspect(
	id serial NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT aspect_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);

ALTER TABLE aspect OWNER TO ktunaxa;
GRANT ALL ON TABLE aspect TO ktunaxa;
GRANT ALL ON TABLE aspect TO postgres;

INSERT INTO aspect (id, description) values (1, 'Aquatic');
INSERT INTO aspect (id, description) values (2, 'Archaeological');
INSERT INTO aspect (id, description) values (3, 'Cultural');
INSERT INTO aspect (id, description) values (4, 'Ecology');
INSERT INTO aspect (id, description) values (5, 'Treaty');



-- ----------------------------------------------------------------------------
-- Table: REFERRAL STATUS
-- ----------------------------------------------------------------------------
CREATE TABLE referral_status(
	id serial NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT referral_status_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);

ALTER TABLE referral_status OWNER TO ktunaxa;
GRANT ALL ON TABLE referral_status TO ktunaxa;
GRANT ALL ON TABLE referral_status TO postgres;

INSERT INTO referral_status (id, description) values (1, 'New');
INSERT INTO referral_status (id, description) values (2, 'In progress');
INSERT INTO referral_status (id, description) values (3, 'Approved');
INSERT INTO referral_status (id, description) values (4, 'Denied');



-- ----------------------------------------------------------------------------
-- Table: REFERRAL TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE referral_type(
	id serial NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT referral_type_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);

ALTER TABLE referral_type OWNER TO ktunaxa;
GRANT ALL ON TABLE referral_type TO ktunaxa;
GRANT ALL ON TABLE referral_type TO postgres;

INSERT INTO referral_type (id, description) values (1, 'Adventure Tourism');
INSERT INTO referral_type (id, description) values (2, 'Agriculture');
INSERT INTO referral_type (id, description) values (3, 'Aquaculture');
INSERT INTO referral_type (id, description) values (4, 'Clean Energy');
INSERT INTO referral_type (id, description) values (5, 'Commercial');
INSERT INTO referral_type (id, description) values (6, 'Communication Sites');
INSERT INTO referral_type (id, description) values (7, 'Community & Institutional');
INSERT INTO referral_type (id, description) values (8, 'Contaminated Sites and Restoration');
INSERT INTO referral_type (id, description) values (9, 'Land Sales');
INSERT INTO referral_type (id, description) values (10, 'Flood Protection');
INSERT INTO referral_type (id, description) values (11, 'Forestry');
INSERT INTO referral_type (id, description) values (12, 'Grazing');
INSERT INTO referral_type (id, description) values (13, 'Guide Outfitting');
INSERT INTO referral_type (id, description) values (14, 'Industrial');
INSERT INTO referral_type (id, description) values (15, 'Mining: Placer');
INSERT INTO referral_type (id, description) values (16, 'Mining: Aggregate and Quarry');
INSERT INTO referral_type (id, description) values (17, 'Mining: Mine Development');
INSERT INTO referral_type (id, description) values (18, 'Oil and Gas');
INSERT INTO referral_type (id, description) values (19, 'Private Moorage');
INSERT INTO referral_type (id, description) values (20, 'Property Development');
INSERT INTO referral_type (id, description) values (21, 'Public Recreation - Parks');
INSERT INTO referral_type (id, description) values (22, 'Public Recreation - General');
INSERT INTO referral_type (id, description) values (23, 'Residential');
INSERT INTO referral_type (id, description) values (24, 'Resort Development');
INSERT INTO referral_type (id, description) values (25, 'Roads');
INSERT INTO referral_type (id, description) values (26, 'Utilities');



-- ----------------------------------------------------------------------------
-- Table: REFERRAL APPLICATION TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE referral_application_type(
	id serial NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT referral_application_type_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);

ALTER TABLE referral_application_type OWNER TO ktunaxa;
GRANT ALL ON TABLE referral_application_type TO ktunaxa;
GRANT ALL ON TABLE referral_application_type TO postgres;

INSERT INTO referral_application_type (id, description) values (1, 'New');
INSERT INTO referral_application_type (id, description) values (2, 'Renewal');
INSERT INTO referral_application_type (id, description) values (3, 'Amendment');
INSERT INTO referral_application_type (id, description) values (4, 'Replacement');



-- ----------------------------------------------------------------------------
-- Table: REFERRAL
-- ----------------------------------------------------------------------------
CREATE TABLE referral(
	id serial NOT NULL,
	land_referral_id character varying,
	applicant_name character varying NOT NULL,
	project_name character varying NOT NULL,
	project_description character varying NOT NULL,
	project_location character varying NOT NULL,
	project_background character varying,
	external_project_id character varying,
	external_file_id character varying,
	external_agency_name character varying NOT NULL,
	contact_name character varying NOT NULL,
	contact_email character varying NOT NULL,
	contact_phone character varying NOT NULL,
	contact_address character varying,
	type_id bigint NOT NULL,
	application_type_id bigint NOT NULL,
	confidential boolean NOT NULL DEFAULT FALSE,
	receive_date timestamp NOT NULL,
	response_date timestamp NOT NULL,
	active_retention_period int NOT NULL,
	semi_active_retention_period int NOT NULL,
	final_disposition int NOT NULL,
	assessment_level int,
	record_classification character varying NOT NULL,
	status_id bigint NOT NULL,
	geom geometry,
	CONSTRAINT enforce_dims_geom CHECK ((ndims(geom) = 2)),
--	CONSTRAINT enforce_geotype_geom CHECK (((geometrytype(geom) = 'POINT'::text) OR (geom IS NULL))),
--	CONSTRAINT enforce_srid_geom CHECK ((srid(geom) = 900913)),
	CONSTRAINT referral_pkey PRIMARY KEY (id),
	CONSTRAINT fk_referral_type FOREIGN KEY (type_id) REFERENCES referral_type (id),
	CONSTRAINT fk_referral_application_type FOREIGN KEY (application_type_id) REFERENCES referral_application_type (id),
	CONSTRAINT fk_referral_status FOREIGN KEY (status_id) REFERENCES referral_status (id)
) WITH (OIDS=FALSE);

ALTER TABLE referral OWNER TO ktunaxa;
GRANT ALL ON TABLE referral TO ktunaxa;
GRANT ALL ON TABLE referral TO postgres;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE
-- ----------------------------------------------------------------------------
CREATE TABLE reference(
	id serial NOT NULL,
	type_name character varying NOT NULL,
	geom geometry,
	CONSTRAINT enforce_dims_geom CHECK ((ndims(geom) = 2)),
--	CONSTRAINT enforce_geotype_geom CHECK (((geometrytype(geom) = 'POINT'::text) OR (geom IS NULL))),
--	CONSTRAINT enforce_srid_geom CHECK ((srid(geom) = 900913)),
	CONSTRAINT reference_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference OWNER TO ktunaxa;
GRANT ALL ON TABLE reference TO ktunaxa;
GRANT ALL ON TABLE reference TO postgres;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE ASPECT
-- ----------------------------------------------------------------------------
CREATE TABLE reference_aspect(
	id serial NOT NULL,
	aspect_id bigint NOT NULL,
	reference_id bigint NOT NULL,
	CONSTRAINT reference_aspect_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_aspect_aspect FOREIGN KEY (aspect_id) REFERENCES aspect (id),
	CONSTRAINT fk_reference_aspect_reference FOREIGN KEY (reference_id) REFERENCES reference (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference_aspect OWNER TO ktunaxa;
GRANT ALL ON TABLE reference_aspect TO ktunaxa;
GRANT ALL ON TABLE reference_aspect TO postgres;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE KEY VALUE
-- ----------------------------------------------------------------------------
CREATE TABLE reference_key_value(
	id serial NOT NULL,
	reference_id bigint NOT NULL,
	the_key character varying NOT NULL,
	the_value character varying,
	CONSTRAINT reference_key_value_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_key_value_reference FOREIGN KEY (reference_id) REFERENCES reference (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference_key_value OWNER TO ktunaxa;
GRANT ALL ON TABLE reference_key_value TO ktunaxa;
GRANT ALL ON TABLE reference_key_value TO postgres;



-- ----------------------------------------------------------------------------
-- Table: DOCUMENT TYPE
-- ----------------------------------------------------------------------------
CREATE TABLE document_type(
	id serial NOT NULL,
	description character varying NOT NULL,
	CONSTRAINT document_type_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);

ALTER TABLE document_type OWNER TO ktunaxa;
GRANT ALL ON TABLE document_type TO ktunaxa;
GRANT ALL ON TABLE document_type TO postgres;

INSERT INTO document_type (id, description) values (1, 'KLRA Response');
INSERT INTO document_type (id, description) values (2, 'KLRA General Communication');
INSERT INTO document_type (id, description) values (3, 'KLRA Community Input');
INSERT INTO document_type (id, description) values (4, 'KLRA Request for Information');
INSERT INTO document_type (id, description) values (5, 'External - Initial Referral Notification');
INSERT INTO document_type (id, description) values (6, 'External - General Communication');
INSERT INTO document_type (id, description) values (7, 'External - Request for Information');



-- ----------------------------------------------------------------------------
-- Table: DOCUMENT
-- ----------------------------------------------------------------------------
CREATE TABLE document(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	keywords character varying,
	type_id bigint NOT NULL,
	document_id character varying NOT NULL,
	addition_date timestamp NOT NULL,
	added_by character varying,
	confidential boolean NOT NULL DEFAULT false,
	include_in_report boolean NOT NULL DEFAULT false,
	referral_id bigint NOT NULL,
	CONSTRAINT document_pkey PRIMARY KEY (id),
	CONSTRAINT fk_document_type FOREIGN KEY (type_id) REFERENCES document_type (id),
	CONSTRAINT fk_document_referral FOREIGN KEY (referral_id) REFERENCES referral (id)
) WITH (OIDS=FALSE);

ALTER TABLE document OWNER TO ktunaxa;
GRANT ALL ON TABLE document TO ktunaxa;
GRANT ALL ON TABLE document TO postgres;



-- ----------------------------------------------------------------------------
-- Table: REFERRAL COMMENT
-- ----------------------------------------------------------------------------
CREATE TABLE referral_comment(
	id serial NOT NULL,
	title character varying NOT NULL,
	content character varying NOT NULL,
	creation_date timestamp NOT NULL,
	created_by character varying,
	include_in_report boolean NOT NULL DEFAULT false,
	checked_content character varying,
	referral_id bigint NOT NULL,
	CONSTRAINT referral_comment_pkey PRIMARY KEY (id),
	CONSTRAINT fk_referral_comment_referral FOREIGN KEY (referral_id) REFERENCES referral (id)
) WITH (OIDS=FALSE);

ALTER TABLE referral_comment OWNER TO ktunaxa;
GRANT ALL ON TABLE referral_comment TO ktunaxa;
GRANT ALL ON TABLE referral_comment TO postgres;



-- ----------------------------------------------------------------------------
-- Table: DOCUMENT COMMENT
-- ----------------------------------------------------------------------------
CREATE TABLE document_comment(
	id serial NOT NULL,
	title character varying NOT NULL,
	content character varying NOT NULL,
	creation_date timestamp NOT NULL,
	created_by character varying,
	include_in_report boolean NOT NULL DEFAULT false,
	checked_content character varying,
	document_id bigint NOT NULL,
	CONSTRAINT document_comment_pkey PRIMARY KEY (id),
	CONSTRAINT fk_document_comment_document FOREIGN KEY (document_id) REFERENCES document (id)
) WITH (OIDS=FALSE);

ALTER TABLE document_comment OWNER TO ktunaxa;
GRANT ALL ON TABLE document_comment TO ktunaxa;
GRANT ALL ON TABLE document_comment TO postgres;



-- ----------------------------------------------------------------------------
-- Table: TEMPLATE
-- ----------------------------------------------------------------------------
CREATE TABLE template(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	content oid NOT NULL,
	CONSTRAINT template_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);

ALTER TABLE template OWNER TO ktunaxa;
GRANT ALL ON TABLE template TO ktunaxa;
GRANT ALL ON TABLE template TO postgres;

