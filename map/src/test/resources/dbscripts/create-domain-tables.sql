-- ----------------------------------------------------------------------------
-- DROP ALL TABLES BEFORE CREATING THEM AGAIN
-- ----------------------------------------------------------------------------

DROP TABLE template;
DROP TABLE document_comment;
DROP TABLE referral_comment;
-- DROP TABLE comment;
DROP TABLE document;
DROP TABLE reference_key_value;
DROP TABLE reference_aspect;
DROP TABLE reference;
DROP TABLE deadline;
DROP TABLE referral;
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

INSERT INTO aspect (id, description) values (1, 'Culture');
INSERT INTO aspect (id, description) values (2, 'Ecology');
INSERT INTO aspect (id, description) values (3, 'Archaeology');



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
-- Table: REFERRAL
-- ----------------------------------------------------------------------------
CREATE TABLE referral(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	creation_date timestamp NOT NULL,
	created_by character varying,
	status_id bigint NOT NULL,
	contact_name character varying NOT NULL,
	contact_email character varying NOT NULL,
	geom geometry,
	CONSTRAINT enforce_dims_geom CHECK ((ndims(geom) = 2)),
--	CONSTRAINT enforce_geotype_geom CHECK (((geometrytype(geom) = 'POINT'::text) OR (geom IS NULL))),
--	CONSTRAINT enforce_srid_geom CHECK ((srid(geom) = 900913)),
	CONSTRAINT referral_pkey PRIMARY KEY (id),
	CONSTRAINT fk_referral_status FOREIGN KEY (status_id) REFERENCES referral_status (id)
) WITH (OIDS=FALSE);

ALTER TABLE referral OWNER TO ktunaxa;
GRANT ALL ON TABLE referral TO ktunaxa;
GRANT ALL ON TABLE referral TO postgres;



-- ----------------------------------------------------------------------------
-- Table: DEADLINE
-- ----------------------------------------------------------------------------
CREATE TABLE deadline(
	id serial NOT NULL,
	description character varying NOT NULL,
	deadline_date timestamp NOT NULL,
	referral_id bigint NOT NULL,
	CONSTRAINT deadline_pkey PRIMARY KEY (id),
	CONSTRAINT fk_deadline_referral FOREIGN KEY (referral_id) REFERENCES referral (id)
) WITH (OIDS=FALSE);

ALTER TABLE deadline OWNER TO ktunaxa;
GRANT ALL ON TABLE deadline TO ktunaxa;
GRANT ALL ON TABLE deadline TO postgres;



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
-- Table: DOCUMENT
-- ----------------------------------------------------------------------------
CREATE TABLE document(
	id serial NOT NULL,
	title character varying NOT NULL,
	description character varying NOT NULL,
	document_id character varying NOT NULL,
	addition_date timestamp NOT NULL,
	added_by character varying,
	confidential boolean NOT NULL DEFAULT false,
	include_in_report boolean NOT NULL DEFAULT false,
	referral_id bigint NOT NULL,
	CONSTRAINT document_pkey PRIMARY KEY (id),
	CONSTRAINT fk_document_referral FOREIGN KEY (referral_id) REFERENCES referral (id)
) WITH (OIDS=FALSE);

ALTER TABLE document OWNER TO ktunaxa;
GRANT ALL ON TABLE document TO ktunaxa;
GRANT ALL ON TABLE document TO postgres;



-- ----------------------------------------------------------------------------
-- Table: COMMENT
-- ----------------------------------------------------------------------------
-- CREATE TABLE comment(
-- 	id serial NOT NULL,
-- 	title character varying NOT NULL,
-- 	content character varying NOT NULL,
-- 	creation_date timestamp NOT NULL,
-- 	created_by character varying,
-- 	include_in_report boolean NOT NULL DEFAULT false,
-- 	checked_content character varying,
-- 	CONSTRAINT comment_pkey PRIMARY KEY (id)
-- ) WITH (OIDS=FALSE);

-- ALTER TABLE comment OWNER TO ktunaxa;
-- GRANT ALL ON TABLE comment TO ktunaxa;
-- GRANT ALL ON TABLE comment TO postgres;



-- ----------------------------------------------------------------------------
-- Table: REFERRAL COMMENT
-- ----------------------------------------------------------------------------
CREATE TABLE referral_comment(
	id serial NOT NULL,
--	comment_id bigint NOT NULL,
	title character varying NOT NULL,
	content character varying NOT NULL,
	creation_date timestamp NOT NULL,
	created_by character varying,
	include_in_report boolean NOT NULL DEFAULT false,
	checked_content character varying,
	referral_id bigint NOT NULL,
	CONSTRAINT referral_comment_pkey PRIMARY KEY (id),
--	CONSTRAINT fk_referral_comment_comment FOREIGN KEY (comment_id) REFERENCES comment (id),
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
--	comment_id bigint NOT NULL,
	title character varying NOT NULL,
	content character varying NOT NULL,
	creation_date timestamp NOT NULL,
	created_by character varying,
	include_in_report boolean NOT NULL DEFAULT false,
	checked_content character varying,
	document_id bigint NOT NULL,
	CONSTRAINT document_comment_pkey PRIMARY KEY (id),
--	CONSTRAINT fk_document_comment_comment FOREIGN KEY (comment_id) REFERENCES comment (id),
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

