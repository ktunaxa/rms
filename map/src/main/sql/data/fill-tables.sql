-- ----------------------------------------------------------------------------
-- Table: REFERENCE LAYER CATEGORY
-- ----------------------------------------------------------------------------

INSERT INTO reference_layer_type (id, description, base_layer) values (1, 'Administrative', true); 
INSERT INTO reference_layer_type (id, description, base_layer) values (2, 'Mining', true); 
INSERT INTO reference_layer_type (id, description, base_layer) values (3, 'Neatlines', true); 
INSERT INTO reference_layer_type (id, description, base_layer) values (4, 'NTS', true); 
INSERT INTO reference_layer_type (id, description, base_layer) values (5, 'TRIM', true); 
INSERT INTO reference_layer_type (id, description, base_layer) values (6, 'Aquatic', false); 
INSERT INTO reference_layer_type (id, description, base_layer) values (7, 'Archaeological', false); 
INSERT INTO reference_layer_type (id, description, base_layer) values (8, 'Cultural', false); 
INSERT INTO reference_layer_type (id, description, base_layer) values (9, 'Terrestrial', false);
INSERT INTO reference_layer_type (id, description, base_layer) values (10, 'Treaty', false); 


-- ----------------------------------------------------------------------------
-- Table: REFERENCE LAYER
-- ----------------------------------------------------------------------------

INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (1, 'Municipalities', 1, '1:10000000', '1:1', true, 1); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (2, 'Native Communities', 1, '1:10000000', '1:1', true, 2); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (3, 'Landscape Units', 1, '1:500000', '1:100000', false, 3); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (4, 'Registered Traplines', 1, '1:500000', '1:100000', false, 4); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (5, 'Wildlife Habitat', 1, '1:250000', '1:1', false, 5); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (6, 'Private Parcels', 1, '1:250000', '1:1', false, 6); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (7, 'Coal Fields', 2, '1:1000000', '1:1', false, 7); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (8, 'Coal Tenures', 2, '1:1000000', '1:1', false, 8); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (9, 'Dominion Coal Blocks', 2, '1:1000000', '1:1', false, 9); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (10, 'NTS 250K', 3, '1:6000000', '1:750000', false, 10); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (11, 'TRIM 20K', 3, '1:750000', '1:125000', false, 11); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (12, 'Cities', 4, '1:500000', '1:100000', true, 12); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (13, 'Contours', 4, '1:300000', '1:100000', false, 13); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (14, 'Glaciers', 4, '1:500000', '1:100000', false, 14); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (15, 'Lakes', 4, '1:500000', '1:100000', true, 15); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (16, 'Rivers', 4, '1:500000', '1:100000', true, 16); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (17, 'Streams', 4, '1:500000', '1:100000', true, 17); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (18, 'Transportation', 4, '1:500000', '1:100000', false, 18); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (19, 'Pipeline', 5, '1:100000', '1:1', false, 19); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (20, 'Transmissionline', 5, '1:100000', '1:1', false, 20); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (21, 'Contours', 5, '1:100000', '1:1', false, 21); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (22, 'Icemass', 5, '1:100000', '1:1', true, 22); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (23, 'Rivers', 5, '1:100000', '1:1', true, 23); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (24, 'Transportation', 5, '1:100000', '1:1', false, 24); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (25, 'Wetlands', 5, '1:100000', '1:1', true, 25); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (26, 'Waterbody', 5, '1:100000', '1:1', true, 26); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (27, 'Waterlines', 5, '1:100000', '1:1', true, 27); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (28, 'Waterlines', 6, '1:100000', '1:1', true, 28); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (29, 'Fresh Water Obstructions', 6, '1:100000', '1:1', false, 29); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (30, 'Designated Flood Plains', 6, '1:150000', '1:1', false, 30); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (31, 'Fish Distribution Historic', 6, '1:100000', '1:1', false, 31); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (32, 'Groundwater wells', 6, '1:500000', '1:1', false, 32); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (33, 'Water Diversion Points', 6, '1:500000', '1:1', false, 33); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (34, 'Kootenay West', 7, '1:150000', '1:1', true, 34); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (35, 'Preferred Model', 7, '1:150000', '1:1', false, 35); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (36, 'Archaelogical Polygons', 7, '1:50000', '1:1', false, 36); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (37, 'Archaelogical Sites', 7, '1:150000', '1:1', false, 37); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (38, 'Akisqnuk - Campsites', 8, '1:3000000', '1:15000', false, 38); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (39, 'Akisqnuk - Religious', 8, '1:3000000', '1:15000', false, 39); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (40, 'Akisqnuk - Cross Cultural', 8, '1:3000000', '1:15000', false, 40); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (41, 'Akisqnuk - Cultural Lands', 8, '1:3000000', '1:15000', false, 41); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (42, 'Akisqnuk - Tradition', 8, '1:3000000', '1:15000', false, 42); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (43, 'Akisqnuk - Food', 8, '1:3000000', '1:15000', false, 43); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (44, 'Akisqnuk - Habitat', 8, '1:3000000', '1:15000', false, 44); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (45, 'Akisqnuk - Material', 8, '1:3000000', '1:15000', false, 45); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (46, 'Akisqnuk - Medicinal', 8, '1:3000000', '1:15000', false, 46); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (47, 'Akisqnuk - Recreation', 8, '1:3000000', '1:15000', false, 47); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (48, 'Akisqnuk - Supernatural', 8, '1:3000000', '1:15000', false, 48); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (49, 'Akisqnuk - Transport', 8, '1:3000000', '1:15000', false, 49); 
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (50, 'Flathead - Fish', 8, '1:750000', '1:15000', false, 50);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (51, 'Flathead - Sites', 8, '1:750000', '1:15000', false, 51);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (52, 'Flathead - Transport', 8, '1:750000', '1:15000', false, 52);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (53, 'JS - Campsites', 8, '1:3000000', '1:15000', false, 53); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (54, 'JS - Fishing', 8, '1:3000000', '1:15000', false, 54); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (55, 'JS - Gather', 8, '1:3000000', '1:15000', false, 55); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (56, 'JS - Hunting', 8, '1:3000000', '1:15000', false, 56); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (57, 'JS - Trapping', 8, '1:3000000', '1:15000', false, 57); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (58, 'KNC - Cross Cultural', 8, '1:3000000', '1:15000', false, 58); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (59, 'KNC - Habitat', 8, '1:3000000', '1:15000', false, 59); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (60, 'KNC - Historic', 8, '1:3000000', '1:15000', false, 60); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (61, 'KNC - Hunting', 8, '1:3000000', '1:15000', false, 61); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (62, 'KNC - Material', 8, '1:3000000', '1:15000', false, 62); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (63, 'KNC - Medicinal', 8, '1:3000000', '1:15000', false, 63); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (64, 'KNC - Transportation', 8, '1:3000000', '1:15000', false, 64); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (65, 'KNC - Trapping', 8, '1:3000000', '1:15000', false, 65); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (66, 'KNC - Vegetation', 8, '1:3000000', '1:15000', false, 66); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (67, 'Other - Ktunaxa placenames', 8, '1:3000000', '1:15000', false, 67); 
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (68, 'Other - Spirit Trail', 8, '1:3000000', '1:15000', false, 68);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (69, 'Wigwam - Ethnosites', 8, '1:3000000', '1:15000', false, 69);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (70, 'Wigwam - Fishing', 8, '1:3000000', '1:15000', false, 70);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (71, 'Wigwam - Habitation', 8, '1:3000000', '1:15000', false, 71);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (72, 'Wigwam - Heritage Route', 8, '1:3000000', '1:15000', false, 72);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (73, 'Wigwam - Hunting', 8, '1:3000000', '1:15000', false, 73);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (74, 'Wigwam - Planning Cell', 8, '1:3000000', '1:15000', false, 74);
-- INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (75, 'Wigwam - Vegetation', 8, '1:3000000', '1:15000', false, 75);
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (76, 'Biogeoclimatic', 9, '1:500000', '1:100000', false, 76); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (77, 'Conservation Covenant Land', 9, '1:250000', '1:1', false, 77); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (78, 'National Parks', 9, '1:10000000', '1:1', true, 78); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (79, 'Provincial Parks', 9, '1:10000000', '1:1', true, 79); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (80, 'Ungulate Winter Range', 9, '1:150000', '1:1', false, 80); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (81, 'Rare Endangered Species', 9, '1:150000', '1:1', false, 81); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (82, 'Ktunaxa Statement of Intent', 10, '1:10000000', '1:1', true, 82); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (83, 'Treaty Identified Lands', 10, '1:1000000', '1:1', true, 83); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (84, 'Grassland Eco System', 9, '1:150000', '1:1', false, 84); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (85, 'Riparian', 9, '1:150000', '1:1', false, 85); 
INSERT INTO reference_layer (id, name, type_id, scale_min, scale_max, visible_by_default, code) VALUES (86, 'Guidance Zones', 6, '1:150000', '1:1', false, 86); 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL STATUS
-- ----------------------------------------------------------------------------

INSERT INTO referral_status (id, title, description) values (1, 'New', 'Referral is not yet complete.');
INSERT INTO referral_status (id, title, description) values (2, 'In progress', 'Referral is being processed.');
INSERT INTO referral_status (id, title, description) values (3, 'Finished', 'Referral has been fully processed.');
INSERT INTO referral_status (id, title, description) values (4, 'Stopped', 'Referral processing was stopped.');


-- ----------------------------------------------------------------------------
-- Table: REFERRAL DECISION
-- ----------------------------------------------------------------------------

INSERT INTO referral_decision (id, title, description) values (1, 'Unknown', 'The decision is not yet known.');
INSERT INTO referral_decision (id, title, description) values (2, 'Approved', 'The referral was approved.');
INSERT INTO referral_decision (id, title, description) values (3, 'Denied', 'The referral was denied.');


-- ----------------------------------------------------------------------------
-- Table: REFERRAL TYPE
-- ----------------------------------------------------------------------------


INSERT INTO referral_type (id, title, description) values (1,E'Alternative Energy: Investigation',E'The exploration phase of a potential future alternative energy facility. Alternative energy could include water power, wind power, ocean power, geothermal power etc. Investigation may include a license of occupation, geothermal test hole etc.');
INSERT INTO referral_type (id, title, description) values (2,E'Alternative Energy: Production',E'Includes permits required for the production phase of an alternative energy facility. (Water power, wind power, ocean power, geothermal). Could include a lease, a water license etc.');
INSERT INTO referral_type (id, title, description) values (3,E'Environment: Contaminated Sites and Hazardous Waste',E'Site designation or delisting, clean up (remediation), hazardous waste facility approval…');
INSERT INTO referral_type (id, title, description) values (4,E'Environment: Guide Outfitting',E'Anything related to a guide outfitter operation. Could include new cabins, transfers, renewals etc.');
INSERT INTO referral_type (id, title, description) values (5,E'Environment: Park Planning or Operations',E'Park management planning, forest health or firee management within parks, or facility development, vegetation management etc within a park, and generally conducted by Parks.');
INSERT INTO referral_type (id, title, description) values (6,E'Environment: Park Use Permit',E'Applications by external individuals forr activities within a park. E.g. food vending, research, film production etc. ');
INSERT INTO referral_type (id, title, description) values (7,E'Environment: Pest Management',E'E.g. Vegetation control, mosquito control, forest pest management');
INSERT INTO referral_type (id, title, description) values (8,E'Environment: Trapline',E'Anything related to traplines including cabins, transfers, renewals etc.');
INSERT INTO referral_type (id, title, description) values (9,E'Environment: Waste Discharge',E'Includes sewage discarge, solid waste (lanfills), and air pollution permitting.');
INSERT INTO referral_type (id, title, description) values (10,E'Environment: Wildlife/Hunting/Angling',E'Anything related to hunting/fishing regulations, species management plans, guidelines/BMPs re wildlife, habitat areas.');
INSERT INTO referral_type (id, title, description) values (11,E'Forestry: FSP, WLP',E'New Forest Stewardhsip Plans / Woodlot License Plans, or changes/extenstions to existing plans');
INSERT INTO referral_type (id, title, description) values (12,E'Forestry: General',E'Anything forestry related that doesn\'t fall into the other forestry categories (e.g. licence to cut - LtC)');
INSERT INTO referral_type (id, title, description) values (13,E'Forestry: Provincial Planning/Administration',E'Annual Allowable Cut (AAC), private land deletion, Tree Farm Licence transfer, Forest Management Planning etc.');
INSERT INTO referral_type (id, title, description) values (14,E'Forestry: Roads, bridges and cutblocks',E'Typically we receive these directly from industry - notifications of planned road development and cutblocks');
INSERT INTO referral_type (id, title, description) values (15,E'Lands: Adventure Tourism',E'Tenures for things like adventure races, heli-skiing, commercial (guided) hiking, mountain biking…');
INSERT INTO referral_type (id, title, description) values (16,E'Lands: Commercial/Industrial',E'Tenures for e.g. ofice buildings, malls, golf courses, campgrounds, processing facility, machine shops, mills etc');
INSERT INTO referral_type (id, title, description) values (17,E'Lands: Communication Sites',E'Tenures for sites required for transmission/reception of signals - e.g. radio, television, microwave, satellite, cell phone');
INSERT INTO referral_type (id, title, description) values (18,E'Lands: Community & Institutional ',E'Tenures for e.g. regional park, community center, hospital, town hall etc.');
INSERT INTO referral_type (id, title, description) values (19,E'Lands: Crown Grant / Sales',E'Transfer of crown (public) land to private land');
INSERT INTO referral_type (id, title, description) values (20,E'Lands: Private Moorage/ Marrinas',E'Tenures for lands required for personal docks (i.e. not public or commercial docks), or marinas and yacht clubs. Can include associated restaurants, gas docks etc.');
INSERT INTO referral_type (id, title, description) values (21,E'Lands: General and Renewals',E'E.g. Other tenures (film, agriculture, aquaculture), assignments, tenure renewals, etc');
INSERT INTO referral_type (id, title, description) values (22,E'Lands: Resorts',E'Any approvals for resorts - e.g. operating agreement, master development agreement.');
INSERT INTO referral_type (id, title, description) values (23,E'Lands: Roadways & Bridges',E'Tenures for roads and bridges (e.g. s.80 roadway) - not major highways (see Transportation/Infrastructure) or forest roads/bridges (see Forestry)');
INSERT INTO referral_type (id, title, description) values (24,E'Lands: Utilities',E'Tenures for utilities e.g. distribution lines, pipelines, flow lines, sewer and water systems, electrical transmission and distribution lines etc');
INSERT INTO referral_type (id, title, description) values (25,E'Local Govt: OCP, Zoning ',E'New or changes to an Official Community Plan (OCP) or zoning bylaw, includes changes to accommodate a subdivision');
INSERT INTO referral_type (id, title, description) values (26,E'Mining: Aggregate and Quarry',E'Permits, NoW, or leases for gravel, sand, landscaping rock etc.');
INSERT INTO referral_type (id, title, description) values (27,E'Mining: Mineral and Coal Exploration',E'Licences, notices of work, bulk sample for exploration purposes for mineral or coal mining');
INSERT INTO referral_type (id, title, description) values (28,E'Mining: Mineral and Coal Production',E'Leases or other approvals for mineral or coal mining - production');
INSERT INTO referral_type (id, title, description) values (29,E'Mining: Placer',E'Permits, NoW, leases for placer (usually gold) operations');
INSERT INTO referral_type (id, title, description) values (30,E'Misc: Multiple',E'');
INSERT INTO referral_type (id, title, description) values (31,E'Misc: Other',E'');
INSERT INTO referral_type (id, title, description) values (32,E'Oil and Gas: All',E'Any permits related to exploration, infrastructure or production of oil and gas including coal bed methane gas');
INSERT INTO referral_type (id, title, description) values (33,E'Range: All',E'Range (grazing) permits, leases, transfers, amendments etc.');
INSERT INTO referral_type (id, title, description) values (34,E'Recreation Sites and Trails: All',E'Establish or cancel recreation sites or trails + any associated authorizations');
INSERT INTO referral_type (id, title, description) values (35,E'Transportation/Infrastructure: Highways',E'Includes any highways projects including planning, land acquisition, bridge or road construction, maintenance, designations, approvals. ');
INSERT INTO referral_type (id, title, description) values (36,E'Transportation/Infrastructure: Subdivision Approvals',E'Provincial approvals for subdivisions');
INSERT INTO referral_type (id, title, description) values (37,E'Water: Changes in/about stream',E'Applications for modifications to a stream or lake. Usually related to construction or maintenance of a structure like a bridge, retaining wall, rip rap etc.');
INSERT INTO referral_type (id, title, description) values (38,E'Water: General',E'Any other water related decisions - e.g. transfer of appurtenancy, short term use, notifications, etc');
INSERT INTO referral_type (id, title, description) values (39,E'Water: Licenses',E'Enables use of water for a variety of purposes (domestic, agricultural, industrial…). Also includes amendments and transfers of licences.');
INSERT INTO referral_type (id, title, description) values (40,E'Water: Planning',E'Water use plans, water management plans');

-- ----------------------------------------------------------------------------
-- Table: REFERRAL APPLICATION TYPE
-- ----------------------------------------------------------------------------

INSERT INTO referral_application_type (id, title, description) values (1, 'New', 'The referral refers to a completely new project.'); 
INSERT INTO referral_application_type (id, title, description) values (2, 'Renewal', 'The referral is actually a request for renewing an existing referral.'); 
INSERT INTO referral_application_type (id, title, description) values (3, 'Amendment', 'The referral is an addition to an existing referral.'); 
INSERT INTO referral_application_type (id, title, description) values (4, 'Replacement', 'The referral should replace an existing referral.'); 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL DISPOSITION TYPE
-- ----------------------------------------------------------------------------

INSERT INTO referral_disposition_type (id, code, description) values (1, 'D', 'Destruction'); 
INSERT INTO referral_disposition_type (id, code, description) values (2, 'P', 'Permanent Retention - Archives'); 
INSERT INTO referral_disposition_type (id, code, description) values (3, 'SR', 'Selective Retention - Archives'); 


-- ----------------------------------------------------------------------------
-- Table: DOCUMENT TYPE
-- ----------------------------------------------------------------------------

INSERT INTO document_type (id, title, description) values (1, 'KLRA Response', 'The official response to the referral by the KLRA.'); 
INSERT INTO document_type (id, title, description) values (2, 'KLRA General Communication', 'A general communication document. Can be anything.'); 
INSERT INTO document_type (id, title, description) values (3, 'KLRA Community Input', 'This document describes input as provided by the KLRA community.'); 
INSERT INTO document_type (id, title, description) values (4, 'KLRA Request for Information', 'A general request for more information regarding the referral.'); 
INSERT INTO document_type (id, title, description) values (5, 'External - Initial Referral Notification', 'External document describing the referral notification.'); 
INSERT INTO document_type (id, title, description) values (6, 'External - General Communication', 'External document containing general communication.'); 
INSERT INTO document_type (id, title, description) values (7, 'External - Request for Information', 'External document requesting more information on the referral.'); 


-- ----------------------------------------------------------------------------
-- Table: REFERRAL EXTERNAL AGENCY TYPE
-- ----------------------------------------------------------------------------

INSERT INTO referral_external_agency_type (id, title, description) values (1, 'BC Government', 'BC Government');
INSERT INTO referral_external_agency_type (id, title, description) values (2, 'Government of Canada', 'Government of Canada');
INSERT INTO referral_external_agency_type (id, title, description) values (3, 'Local Government', 'Local Government');
INSERT INTO referral_external_agency_type (id, title, description) values (4, 'Industry', 'Industry');
INSERT INTO referral_external_agency_type (id, title, description) values (5, 'Other', 'Other');


-- ----------------------------------------------------------------------------
-- Table: REFERRAL PRIORITY
-- ----------------------------------------------------------------------------

INSERT INTO referral_priority (id, title, description) values (1, 'Low', 'Low priority.');
INSERT INTO referral_priority (id, title, description) values (2, 'Medium', 'Medium priority.');
INSERT INTO referral_priority (id, title, description) values (3, 'High', 'High priority.');


-- ----------------------------------------------------------------------------
-- Table: TEMPLATE
-- ----------------------------------------------------------------------------

INSERT INTO template (id, mime_type, mail_sender, title, subject, description, string_content) values
(1, 'text', 'bla@ktunaxa.org', 'notify.level0', '${referralId} ${referralName} Received, but no action.', 
'Referral ${referralId} level 0 notification',
E'Referral ${referralId} ${referralName}\n\nWe have received this referral but do not think we need to take action to process it. For us it has engagement level 0.\n\nKind regards,\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, subject, description, string_content) values
(2, 'text', 'bla@ktunaxa.org', 'notify.change.engagementLevel', '${referralId} ${referralName} Received and started with different engagement level.',
'Referral ${referralId} engagement level notification',
E'Referral ${referralId} ${referralName}\n\nThanks for submitting this referral. After investigation, we believe the engagement level needs to be changed from ${provinceEngagementLevel} to ${engagementLevel}. Our seasoning for this:\n\n${engagementComment}\n\nPlease confirm receipt of this message or let us know of any problems with this decision.\n\nWe will process your referral by ${completionDeadline}.\n\nKind regards,\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, subject, description, string_content) values
(3, 'text', 'bla@ktunaxa.org', 'notify.start', '${referralId} ${referralName} Received and started.',
'Start processing referral ${referralId}',
E'Referral ${referralId} ${referralName}\n\nThanks for submitting this referral.\n\nWe will process your referral by ${completionDeadline}.\n\nKind regards,\nKtunaxa Nation Council');
INSERT INTO template (id, mime_type, mail_sender, title, subject, description, string_content) values
(4, 'text', 'bla@ktunaxa.org', 'notify.result', 'Evaluation of referral: ${referralId} ${referralName}.',
'Referral ${referralId} result',
E'Referral ${referralId} ${referralName}\n\nOur evaluation of this referral is attached.\n\nKind regards,\nKtunaxa Nation Council');
