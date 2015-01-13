select addtofulltext('floodplain_areas','rms_label','feat_code','feat_name','drawing_no','file_no','dsgntn_dt','fp_name');

-- select addtofulltext('valuable_fish_habtiat');

select addtofulltext('historical_fish_distribution','rms_label','nwwtrshdcd','gzttdnm','spcscd','spcsnm','stock_name','stock_char','stckmngmnt','activity','comments','rfsnddts','theme_name');

select addtofulltext('fishpassage_obstacles','gzttdnm','bstclcd','bstclnm');

select addtofulltext('groundwater_wells','drllrcmpnc','gnrlrmrks','lgldstrctl','legal_plan','lglrng','lgltwnshp','lthlgdscrp','lthlgmsrmn','lot_number','lglblck',
'lgllnddst1','lglsctn','ldmpsht','prmtnmbr','pltttchdb','pmpdscrptn','rig_number','scrnflg','scrnnfrmtn','scrnlngth','scrnmnfctr','scrnwr','site_area','site_flag',
'stslnd','ststrt','surname','tpfrg','tpfwrk','tmccrccd','cnstrctnm1','crdnt','wtrspplsst','wtrspplwll','wlllctn','wlltgn','wllscd','wllsnm','whncrtd','whnpdtd','whrpltttch',
'whcrtd','whpdtd','ldntcd','ldntdscrpt','drllrcmpnn','qfrlthlgcd','cnstrctnnd','cnstrctnmt','cnstrctnst','cnsltntcmp','cntrctrnf1','cntrctrwll','crdntx','crdntz','crwdrllrnm','dtntrd','dvlpmntnts','diameter');

select addtofulltext('lakeshore_guidancezones','rms_label','wbody_name','shoreline_','shore_type','land_use','impact','ecol_value','colour_zon');

select addtofulltext('waterdiversion_points','licence_no','flag_desc','file_no','dstprcnm','licensee','lic_status','pcl_no','pstlcd','prcstts','purpose','country','tpod_tag','ddrssln1','ddrssln3','ddrssln4','ddrssln2','strm_name','mpshtpd');

select addtofulltext('cultural_landscapes','rms_label','location','landuse_ty');

select addtofulltext('conservation_lands','rms_label','pid','pin','site_name','site_alias','location','ownertype','crwn_tnr_t','agreemnt_t','landowner','lease_hold','mgmt_agenc','source');

select addtofulltext('ccvf','rms_label', 'hcvf_id','name','ccvf_value','document_p','location');

select addtofulltext('qatmuk_area','type','rms_label');

select addtofulltext('uwr','rms_label', 'uwr_number','uwr_un_no','species_1','species_2','apprvl_dat','notice_dat','feat_notes','feat_code');

select addtofulltext('grassland','rms_label','npforestde','forest_spe','esahigh','esalow','esawildlif','beclabel','grasslands','grs_asso_v',
'grs_asso_s','grs_asso_c','grs_asso_d','grs_asso_w','ecodom_nam','ecodiv_nam','ecoprv_nam','ecoreg_nam','ecosec_nam',
'reserve','own_type','reg_distri','municipali','acquired_l','owner_grou');

select addtofulltext('riparian','rms_label','fc_tag','fcode','"group"');

select addtofulltext('ktunaxa_treaty_areas','rms_label','site_name','type','who_added','notes','legend','status','rating');

select addtofulltext('bc_treaty_landoffer','rms_label','csu_x_nm');

select addtofulltext('ktunaxa_soi','rms_label','rms_label');

select addtofulltext('kootenaywest_oao','rms_label','disfield');

-- select addtofulltext('choquette_oao');

select addtofulltext('arc_polygons','rms_label','modifiedby','modifiedus','bordennumb','siteformus','registrati','approvalfl','paccprojof','createdby',
'createdusi','importpk','importfk','ty_typolog','sn_sitenam','sn_tempora','mr_mapshee','ad_locatio','ad_siteacc','mr_spatial','ad_address',
'ad_legalty','ad_legalnu','ad_legalde','ty_feature','ty_length','ty_width','ty_heighta','ty_thickne','ty_depth','ty_diamete','ty_depthbe',
'ty_depth_1','ty_shape','ty_slope','ty_orienta','ty_species','ty_side','ty_fldesc','ac_descrip','dt_rcdunad','dt_rcdun_1','dt_rcdadju',
'dt_rcdad_1','dt_rcdrema','dt_todate','dt_todateq','dt_todatec','dt_fromdat','dt_fromd_1','dt_fromd_2','dt_method','dt_source',
'dt_remarks','dm_length','dm_width','co_descrip','co_percent','co_conditi','co_status','co_factor','co_history','co_distdat',
'ev_giselev','ev_gisel_1','ev_userele','ev_usere_1','ev_comment','sv_sitevis','sv_date_af','sv_svdesc','sv_personn','sv_personr',
'sv_cmtype','sv_cmstat_','sv_cmdate','sv_cmlocat','sr_auth_ti','tn_type','tn_subtype','tn_agencyn','tn_remarks','au_governm',
'au_legalin','au_protect','au_referen','au_startda','au_descrip','re_date_re');

select addtofulltext('arc_polygon_centroids','rms_label','modifiedby','modifiedus','bordennumb','siteformus',
'registrati','approvalfl','paccprojof','createdby','createdusi','importpk','importfk','ty_typolog','sn_sitenam',
'sn_tempora','mr_mapshee','ad_locatio','ad_siteacc','mr_spatial','ad_address','ad_legalty','ad_legalnu','ad_legalde',
'ty_feature','ty_length','ty_width','ty_heighta','ty_thickne','ty_depth','ty_diamete','ty_depthbe','ty_depth_1','ty_shape',
'ty_slope','ty_orienta','ty_species','ty_side','ty_fldesc','ac_descrip','dt_rcdunad','dt_rcdun_1','dt_rcdadju','dt_rcdad_1','dt_rcdrema',
'dt_todate','dt_todateq','dt_todatec','dt_fromdat','dt_fromd_1','dt_fromd_2','dt_method','dt_source','dt_remarks','dm_length','dm_width',
'co_descrip','co_percent','co_conditi','co_status','co_factor','co_history','co_distdat','ev_giselev','ev_gisel_1','ev_userele','ev_usere_1',
'ev_comment','sv_sitevis','sv_date_af','sv_svdesc','sv_personn','sv_personr','sv_cmtype','sv_cmstat_','sv_cmdate','sv_cmlocat','sr_auth_ti',
'tn_type','tn_subtype','tn_agencyn','tn_remarks','au_governm','au_legalin','au_protect','au_referen','au_startda','au_descrip','re_date_re');
