<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0"
	xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd"
	xmlns="http://www.opengis.net/sld" xmlns:ogc="http://www.opengis.net/ogc"
	xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<NamedLayer>
		<Name>layerReferenceValue</Name>
		<UserStyle>
			<Title>Default</Title>
			<FeatureTypeStyle>
			<!-- Aquatic_Features.pdf start -->
				<Rule>
					<Name>GroundWaterWells</Name>
					<Title>GroundWaterWells</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>GWW</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/groundWaterWells.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>WaterDiversionPoints</Name>
					<Title>WaterDiversionPoints</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>WDP</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/waterDiversionPoints.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>FishDistribution_Historic</Name>
					<Title>FishDistribution_Historic</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>FDH</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/fishDistributionHistoric.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>CWB_FreshwaterObstructions (Artificial Waterfall)</Name>
					<Title>CWB_FreshwaterObstructions (Artificial Waterfall)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>FWO_AW</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/artificialWaterfall.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>CWB_FreshwaterObstructions (Beaver Dam)</Name>
					<Title>CWB_FreshwaterObstructions (Beaver Dam)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>FWO_BD</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/beaverDam.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>CWB_FreshwaterObstructions (Dam)</Name>
					<Title>CWB_FreshwaterObstructions (Dam)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>FWO_D</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/dam.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>CWB_FreshwaterObstructions (Falls)</Name>
					<Title>CWB_FreshwaterObstructions (Falls)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>FWO_F</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/falls.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>CWB_FreshwaterObstructions (Flattened Waterfall)</Name>
					<Title>CWB_FreshwaterObstructions (Flattened Waterfall)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>FWO_FW</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/flattenedWaterfall.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>CWB_FreshwaterObstructions (Rapids/Cascades)</Name>
					<Title>CWB_FreshwaterObstructions (Rapids/Cascades)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>FWO_RC</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/rapidsCascades.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>CWB_FreshwaterObstructions (SinkHole)</Name>
					<Title>CWB_FreshwaterObstructions (SinkHole)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>FWO_SH</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/sinkHole.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>DesignatedFloodPlainAreas (Floodplain Area)</Name>
					<Title>DesignatedFloodPlainAreas (Floodplain Area)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>DFPA_FA</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<GraphicFill>
								<Graphic>
									<Mark>
										<WellKnownName>shape://backslash</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#F2AF2E</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>7</Size>
								</Graphic>
							</GraphicFill>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#F2AF2E</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>DesignatedFloodPlainAreas (Alluvial Fan)</Name>
					<Title>DesignatedFloodPlainAreas (Alluvial Fan)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>DFPA_AF</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<GraphicFill>
								<Graphic>
									<Mark>
										<WellKnownName>shape://horline</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#C07242</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>7</Size>
								</Graphic>
							</GraphicFill>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#C07242</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
			<!-- Aquatic_Features.pdf stop -->
			
			<!-- Archaeological_Features.pdf start -->
				<Rule>
					<Name>Registered_Archaeological_Sites</Name>
					<Title>Registered_Archaeological_Sites</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>ARC_RAS</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<ExternalGraphic>
								<OnlineResource xlink:type="simple"
									xlink:href="images/registeredArchaeologicalSites.png" />
								<Format>image/png</Format>
							</ExternalGraphic>
							<Size>16</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>AOA_Polygons_WCandArcas</Name>
					<Title>AOA_Polygons_WCandArcas</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>ARC_APWCA</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#FFD37F</CssParameter>
						</Fill>
						<Stroke>
							<CssParameter name="stroke">#E69800</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>Registered_Archaeological_Polygons</Name>
					<Title>Registered_Archaeological_Polygons</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>ARC_RAP</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#F4DFFF</CssParameter>
						</Fill>
						<Stroke>
							<CssParameter name="stroke">#C280D4</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>AOA_Polygons_Kootenay_West</Name>
					<Title>AOA_Polygons_Kootenay_West</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>ARC_APKW</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#A8A800</CssParameter>
						</Fill>
						<Stroke>
							<CssParameter name="stroke">#737300</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
			<!-- Ecology_Features.pdf start -->
				<Rule>
					<Name>UngulateWinterRange</Name>
					<Title>UngulateWinterRange</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>UWR</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#CBA966</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>National Parks</Name>
					<Title>National Parks</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>NP</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#9DC99D</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>Provincial Parks</Name>
					<Title>Provincial Parks</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>PP</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#9DC99D</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>East Kootenay Conservation Covenats Lands</Name>
					<Title>East Kootenay Conservation Covenats Lands</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>EKCCL</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<GraphicFill>
								<Graphic>
									<Mark>
										<WellKnownName>shape://slash</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#000000</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>7</Size>
								</Graphic>
							</GraphicFill>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#000000</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>Rare_Endangered_Species</Name>
					<Title>Rare_Endangered_Species</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>RES</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<GraphicFill>
								<Graphic>
									<Mark>
										<WellKnownName>shape://backslash</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#F2AF2E</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>7</Size>
								</Graphic>
							</GraphicFill>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#F2AF2E</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>IMA: Interior Mountain-Heather Alpine</Name>
					<Title>IMA: Interior Mountain-Heather Alpine</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>BEC_IMA</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#ECE9E5</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>ESSF: Engelmann Spruce - Subalpine Fir</Name>
					<Title>ESSF: Engelmann Spruce - Subalpine Fir</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>BEC_ESSF</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#BB8DC1</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>ICH: Interior Cedar - Hemlock</Name>
					<Title>ICH: Interior Cedar - Hemlock</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>BEC_ICH</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#B6BD82</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>IDF: Interior Douglas-Fir</Name>
					<Title>IDF: Interior Douglas-Fir</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>BEC_IDF</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#F9E280</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>MS: Montane Spruce</Name>
					<Title>MS: Montane Spruce</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>BEC_MS</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#F497BE</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>PP: Ponderosa Pine</Name>
					<Title>PP: Ponderosa Pine</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>NP</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#E7A777</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
			<!-- Ecology_Features.pdf stop -->
			
			<!-- TreatyRelated_Features.pdf start -->
				<Rule>
					<Name>Treaty - Identified</Name>
					<Title>Treaty - Identified</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRT_ID</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
			            <Stroke>
			              <CssParameter name="stroke">#FF0000</CssParameter>
			              <CssParameter name="stroke-width">0</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
			
				<Rule>
					<Name>Ktunaxa Nation Consultative Area</Name>
					<Title>Ktunaxa Nation Consultative Area</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRT_KNCA</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Stroke>
			                <CssParameter name="stroke">#343434</CssParameter>
							<CssParameter name="stroke-width">2</CssParameter>
							<CssParameter name="stroke-dasharray">7 3</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
			<!-- TreatyRelated_Features.pdf stop -->
				<Rule>
					<Name>Default point - everything else</Name>
					<Title>Default point - everything else</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:Function name="geometryType">
								<ogc:PropertyName>geom</ogc:PropertyName>
							</ogc:Function>
							<ogc:Literal>Point</ogc:Literal>
							<ogc:Literal>MultiPoint</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PointSymbolizer>
						<Graphic>
							<Mark>
								<WellKnownName>circle</WellKnownName>
								<Fill>
									<CssParameter name="fill">#FFCCFF</CssParameter>
								</Fill>
							</Mark>
							<Size>6</Size>
						</Graphic>
					</PointSymbolizer>
				</Rule>
				<Rule>
					<Name>Default - everything else</Name>
					<Title>Default - everything else</Title>
					<LineSymbolizer>
						<Stroke>
							<CssParameter name="stroke">#FFCCFF</CssParameter>
							<CssParameter name="stroke-width">0</CssParameter>
						</Stroke>
					</LineSymbolizer>
				</Rule>
			</FeatureTypeStyle>
		</UserStyle>
	</NamedLayer>
</StyledLayerDescriptor>
