-- ----------------------------------------------------------------------------
-- DROP ALL TABLES BEFORE CREATING THEM AGAIN
-- ----------------------------------------------------------------------------

DROP TABLE reference_base_attribute;
DROP TABLE reference_base;

DROP TABLE reference_value_attribute;
DROP TABLE reference_value;

DROP TABLE reference_layer;
DROP TABLE reference_layer_type;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE LAYER CATEGORY
-- ----------------------------------------------------------------------------
CREATE TABLE reference_layer_type(
	id serial NOT NULL,
	description character varying NOT NULL,
	base_layer boolean NOT NULL default false,
	CONSTRAINT reference_layer_type_pkey PRIMARY KEY (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference_layer_type OWNER TO ktunaxa;
GRANT ALL ON TABLE reference_layer_type TO ktunaxa;
GRANT ALL ON TABLE reference_layer_type TO postgres;

INSERT INTO reference_layer_type (id, description, base_layer) values (1, 'Administrative', true);
INSERT INTO reference_layer_type (id, description, base_layer) values (2, 'Mining', true);
INSERT INTO reference_layer_type (id, description, base_layer) values (3, 'Neatlines', true);
INSERT INTO reference_layer_type (id, description, base_layer) values (4, 'NTS', true);
INSERT INTO reference_layer_type (id, description, base_layer) values (5, 'TRIM', true);
INSERT INTO reference_layer_type (id, description, base_layer) values (6, 'Aquatic', false);
INSERT INTO reference_layer_type (id, description, base_layer) values (7, 'Archaeological', false);
INSERT INTO reference_layer_type (id, description, base_layer) values (8, 'Cultural', false);
INSERT INTO reference_layer_type (id, description, base_layer) values (9, 'Ecology', false);
INSERT INTO reference_layer_type (id, description, base_layer) values (10, 'Treaty', false);



-- ----------------------------------------------------------------------------
-- Table: REFERENCE LAYER
-- ----------------------------------------------------------------------------
CREATE TABLE reference_layer(
	id serial NOT NULL,
	name character varying NOT NULL,
	type_id bigint NOT NULL,
	scale_min character varying NOT NULL default 0,
	scale_max character varying NOT NULL default 0,
	visible_by_default boolean NOT NULL default false,
	CONSTRAINT reference_layer_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_layer_type FOREIGN KEY (type_id) REFERENCES reference_layer_type (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference_layer OWNER TO ktunaxa;
GRANT ALL ON TABLE reference_layer TO ktunaxa;
GRANT ALL ON TABLE reference_layer TO postgres;

-- Base: Adminitrative
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (1, 'Municipalities', 1, '0', '1:10000000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (2, 'Native Communities', 1, '0', '1:10000000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (3, 'Landscape Units', 1, '1:100000', '1:500000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (4, 'Registered Traplines', 1, '1:100000', '1:500000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (5, 'Wildlife Habitat', 1, '0', '1:250000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (6, 'Private Parcels', 1, '0', '1:250000', false);

-- Base: Mining
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (7, 'Coal Fields', 2, '0', '1:1000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (8, 'Coal Tenures', 2, '0', '1:1000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (9, 'Dominion Coal Blocks', 2, '0', '1:1000000', false);

-- Base: Neatlines
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (10, 'NTS 250K', 3, '1:750000', '1:6000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (11, 'TRIM 20K', 3, '1:125000', '1:750000', false);

-- Base: NTS
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (12, 'Cities', 4, '1:100000', '1:500000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (13, 'Contours', 4, '1:100000', '1:300000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (14, 'Glaciers', 4, '1:100000', '1:500000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (15, 'Lakes', 4, '1:100000', '1:500000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (16, 'Rivers', 4, '1:100000', '1:500000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (17, 'Streams', 4, '1:100000', '1:500000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (18, 'Transportation', 4, '1:100000', '1:500000', false);

-- Base: TRIM
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (19, 'Pipeline', 5, '0', '1:100000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (20, 'Transmissionline', 5, '0', '1:100000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (21, 'Contours', 5, '0', '1:100000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (22, 'Icemass', 5, '0', '1:100000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (23, 'Rivers', 5, '0', '1:100000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (24, 'Transportation', 5, '0', '1:100000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (25, 'Wetlands', 5, '0', '1:100000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (26, 'Waterbody', 5, '0', '1:100000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (27, 'Waterlines', 5, '0', '1:100000', true);

-- Value: Aquatic
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (28, 'Waterlines', 6, '0', '1:100000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (29, 'Fresh Water Obstructions', 6, '0', '1:100000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (30, 'Designated Flood Plains', 6, '0', '1:150000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (31, 'Fish Distribution Historic', 6, '0', '1:100000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (32, 'Groundwater wells', 6, '0', '1:500000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (33, 'Water Diversion Points', 6, '0', '1:500000', false);

-- Value: Archaelogical
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (34, 'Kootenay West', 7, '0', '1:150000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (35, 'Preferred Model', 7, '0', '1:150000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (36, 'Archaelogical Polygons', 7, '0', '1:50000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (37, 'Archaelogical Sites', 7, '0', '1:150000', false);

-- Value: Cultural
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (38, 'Akisqnuk - Campsites', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (39, 'Akisqnuk - Religious', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (40, 'Akisqnuk - Cross Cultural', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (41, 'Akisqnuk - Cultural Lands', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (42, 'Akisqnuk - Tradition', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (43, 'Akisqnuk - Food', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (44, 'Akisqnuk - Habitat', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (45, 'Akisqnuk - Material', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (46, 'Akisqnuk - Medicinal', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (47, 'Akisqnuk - Recreation', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (48, 'Akisqnuk - Supernatural', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (49, 'Akisqnuk - Transport', 8, '1:15000', '1:3000000', false);

INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (50, 'Flathead - Fish', 8, '1:15000', '1:750000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (51, 'Flathead - Sites', 8, '1:15000', '1:750000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (52, 'Flathead - Transport', 8, '1:15000', '1:750000', false);

INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (53, 'JS - Campsites', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (54, 'JS - Fishing', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (55, 'JS - Gather', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (56, 'JS - Hunting', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (57, 'JS - Trapping', 8, '1:15000', '1:3000000', false);

INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (58, 'KNC - Cross Cultural', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (59, 'KNC - Habitat', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (60, 'KNC - Historic', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (61, 'KNC - Hunting', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (62, 'KNC - Material', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (63, 'KNC - Medicinal', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (64, 'KNC - Transportation', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (65, 'KNC - Trapping', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (66, 'KNC - Vegetation', 8, '1:15000', '1:3000000', false);

INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (67, 'Other - Ktunaxa placenames', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (68, 'Other - Spirit Trail', 8, '1:15000', '1:3000000', false);

INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (69, 'Wigwam - Ethnosites', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (70, 'Wigwam - Fishing', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (71, 'Wigwam - Habitation', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (72, 'Wigwam - Heritage Route', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (73, 'Wigwam - Hunting', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (74, 'Wigwam - Planning Cell', 8, '1:15000', '1:3000000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (75, 'Wigwam - Vegetation', 8, '1:15000', '1:3000000', false);

-- Value: Ecology
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (76, 'Biogeoclimatic', 9, '1:100000', '1:500000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (77, 'Conservation Covenant Land', 9, '0', '1:250000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (78, 'National Parks', 9, '0', '1:10000000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (79, 'Provincial Parks', 9, '0', '1:10000000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (80, 'Ungulate Winter Range', 9, '0', '1:150000', false);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (81, 'Rare Endangered Species', 9, '0', '1:150000', false);

INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (82, 'Ktunaxa Statement of Intent', 10, '0', '1:10000000', true);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default) values (83, 'Treaty Identified Lands', 10, '0', '1:1000000', true);



-- ----------------------------------------------------------------------------
-- Table: REFERENCE_VALUE layer
-- ----------------------------------------------------------------------------
CREATE TABLE reference_value(
	id serial NOT NULL,
	layer_id bigint NOT NULL,
	style_code character varying,
	label character varying,
	geom geometry,
	CONSTRAINT enforce_dims_geom CHECK ((ndims(geom) = 2)),
	CONSTRAINT reference_value_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_value_layer FOREIGN KEY (layer_id) REFERENCES reference_layer (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference_value OWNER TO ktunaxa;
GRANT ALL ON TABLE reference_value TO ktunaxa;
GRANT ALL ON TABLE reference_value TO postgres;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE VALUE KEY VALUE
-- ----------------------------------------------------------------------------
CREATE TABLE reference_value_attribute(
	id serial NOT NULL,
	reference_value_id bigint NOT NULL,
	the_key character varying NOT NULL,
	the_value character varying,
	CONSTRAINT reference_value_attribute_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_value_attribute_reference FOREIGN KEY (reference_value_id) REFERENCES reference_value (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference_value_attribute OWNER TO ktunaxa;
GRANT ALL ON TABLE reference_value_attribute TO ktunaxa;
GRANT ALL ON TABLE reference_value_attribute TO postgres;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE_BASE layer
-- ----------------------------------------------------------------------------
CREATE TABLE reference_base(
	id serial NOT NULL,
	layer_id bigint NOT NULL,
	style_code character varying,
	label character varying,
	geom geometry,
	CONSTRAINT enforce_dims_geom CHECK ((ndims(geom) = 2)),
	CONSTRAINT reference_base_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_base_layer FOREIGN KEY (layer_id) REFERENCES reference_layer (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference_base OWNER TO ktunaxa;
GRANT ALL ON TABLE reference_base TO ktunaxa;
GRANT ALL ON TABLE reference_base TO postgres;



-- ----------------------------------------------------------------------------
-- Table: REFERENCE VALUE KEY VALUE
-- ----------------------------------------------------------------------------
CREATE TABLE reference_base_attribute(
	id serial NOT NULL,
	reference_base_id bigint NOT NULL,
	the_key character varying NOT NULL,
	the_value character varying,
	CONSTRAINT reference_base_attribute_pkey PRIMARY KEY (id),
	CONSTRAINT fk_reference_base_attribute_reference FOREIGN KEY (reference_base_id) REFERENCES reference_base (id)
) WITH (OIDS=FALSE);

ALTER TABLE reference_base_attribute OWNER TO ktunaxa;
GRANT ALL ON TABLE reference_base_attribute TO ktunaxa;
GRANT ALL ON TABLE reference_base_attribute TO postgres;

