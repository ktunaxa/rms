<?xml version="1.0" encoding="ISO-8859-1"?>
<!--
  ~ Ktunaxa Referral Management System.
  ~
  ~ Copyright (C) see version control system
  ~
  ~ This program is free software: you can redistribute it and/or modify
  ~ it under the terms of the GNU Affero General Public License as published by
  ~ the Free Software Foundation, either version 3 of the License, or
  ~ (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU Affero General Public License for more details.
  ~
  ~ You should have received a copy of the GNU Affero General Public License
  ~ along with this program.  If not, see <http://www.gnu.org/licenses/>.
  -->

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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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
							<ogc:PropertyName>style_code</ogc:PropertyName>
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


			<!-- New Layer Styling.pdf start -->
				<Rule>
					<Name>Grassland Ecosystem</Name>
					<Title>Grassland Ecosystem</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>GRASS</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
                      		<CssParameter name="fill">#E9FFBE</CssParameter>
						</Fill>
					</PolygonSymbolizer>
					<PolygonSymbolizer>
						<Fill>
							<GraphicFill>
								<Graphic>
									<Mark>
										<WellKnownName>shape://slash</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#787A74</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>14</Size>
								</Graphic>
							</GraphicFill>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#98E600</CssParameter>
			              <CssParameter name="stroke-width">0</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>

				<Rule>
					<Name>Shoreline Management Units</Name>
					<Title>Shoreline Management Units</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>BEC_SMU</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
			            <Stroke>
			              <CssParameter name="stroke">#005CE6</CssParameter>
			              <CssParameter name="stroke-width">0</CssParameter>
				          <CssParameter name="stroke-dasharray">10 5</CssParameter>
			            </Stroke>
					</LineSymbolizer>
				</Rule>

				<Rule>
					<Name>Deciduous Forest</Name>
					<Title>Deciduous Forest</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>RIP_DEC</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#EEF1A0</CssParameter>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#A8A800</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
			
				<Rule>
					<Name>Common Reparian Subhygric</Name>
					<Title>Common Reparian Subhygric</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>RIP_CRS</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#73FFDF</CssParameter>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#00FFC5</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>

				<Rule>
					<Name>Uncommon Reparian Subhygric</Name>
					<Title>Uncommon Reparian Subhygric</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>RIP_URS</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#0070FF</CssParameter>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#005CE6</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>

				<Rule>
					<Name>Common Reparian Hygric</Name>
					<Title>Common Reparian Hygric</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>RIP_CRH</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#00A9E6</CssParameter>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#004C73</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>

				<Rule>
					<Name>Uncommon Reparian Hygric</Name>
					<Title>Uncommon Reparian Hygric</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>RIP_URH</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#BED2FF</CssParameter>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#73B2FF</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
				
				<Rule>
					<Name>Red Shoreline</Name>
					<Title>Red Shoreline</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>LSG_RED</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
			            <Stroke>
			              <CssParameter name="stroke">#FF0000</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</LineSymbolizer>
				</Rule>

				<Rule>
					<Name>Red Shoreline</Name>
					<Title>Red Shoreline</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>LSG_RED</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
			            <Stroke>
			              <CssParameter name="stroke">#FF0000</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</LineSymbolizer>
				</Rule>

				<Rule>
					<Name>Orange Shoreline</Name>
					<Title>Orange Shoreline</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>LSG_ORA</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
			            <Stroke>
			              <CssParameter name="stroke">#FF9900</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</LineSymbolizer>
				</Rule>

				<Rule>
					<Name>Yellow Shoreline</Name>
					<Title>Yellow Shoreline</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>LSG_YEL</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
			            <Stroke>
			              <CssParameter name="stroke">#FFFF00</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</LineSymbolizer>
				</Rule>
				
				<Rule>
					<Name>Grey Shoreline</Name>
					<Title>Grey Shoreline</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>style_code</ogc:PropertyName>
							<ogc:Literal>LSG_GRE</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
			            <Stroke>
			              <CssParameter name="stroke">#888888</CssParameter>
			              <CssParameter name="stroke-width">2</CssParameter>
			            </Stroke>
					</LineSymbolizer>
				</Rule>
			<!-- New Layer Styling.pdf stop -->

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
									<CssParameter name="fill">#660066</CssParameter>
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
							<CssParameter name="stroke">#660066</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</LineSymbolizer>
				</Rule>
			</FeatureTypeStyle>
		</UserStyle>
	</NamedLayer>
</StyledLayerDescriptor>
