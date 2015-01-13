-- Function: addtofulltext(text, text, text[])

-- DROP FUNCTION addtofulltext(text, text, text[]);

CREATE OR REPLACE FUNCTION addtofulltext(IN table_name text, IN description_column text, VARIADIC text_columns text[])
  RETURNS integer AS
$BODY$
DECLARE
rows_added integer;
insertSql text;
BEGIN
--
--		Delete previous rows in fts_layer from this layer
--
	execute 'delete from ' || 'fts_layers where layer_name = ''' || table_name || '''';
--
--		Create the insert statement
--
	insertSql := 'insert into fts_layers(layer_name, fid, description, geom, tsv) select ''';
	insertSql := insertSql || table_name ||''', ''' || table_name ||'.'' || gid, ' || description_column ||', st_force_2d(geom), ';
--
--		Add the description column to the tsvector
--
	insertSql := insertSql || 'to_tsvector(''pg_catalog.english'', coalesce(' || description_column ||',''''))';
--
--		Loop all extra columns and add to the tsvector
--
	FOR i IN 1 .. array_upper(text_columns, 1)
	LOOP
	insertSql := insertSql || '|| to_tsvector(''pg_catalog.english'', coalesce(' || text_columns[i] ||',''''))';
	END LOOP;
	insertSql :=  insertSql || ' from layers.' || table_name;
--
--		Print the insert statement in messages (pgadmin)
--
	RAISE NOTICE 'insertSql =%', insertSql;
	execute insertSql;
	execute 'select count(*) from fts_layers where layer_name = ''' || table_name || '''' into rows_added;
	return rows_added;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION addtofulltext(text, text, text[])
  OWNER TO postgres;
