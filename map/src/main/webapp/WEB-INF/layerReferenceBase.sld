<?xml version="1.0" encoding="ISO-8859-1"?>
<StyledLayerDescriptor version="1.0.0" 
    xsi:schemaLocation="http://www.opengis.net/sld StyledLayerDescriptor.xsd" 
    xmlns="http://www.opengis.net/sld" 
    xmlns:ogc="http://www.opengis.net/ogc" 
    xmlns:xlink="http://www.w3.org/1999/xlink" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<NamedLayer>
		<Name>layerReferenceBase</Name>
		<UserStyle>
			<Title>Default</Title>
			<FeatureTypeStyle>
			<!-- Administration_Boundaries.pdf start -->
				<Rule>
					<Name>LandscapeUnits</Name>
					<Title>LandscapeUnits</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>LU</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Stroke>
							<CssParameter name="stroke">#734C00</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
							<CssParameter name="stroke-dasharray">5 2</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>RegisteredTraplines</Name>
					<Title>RegisteredTraplines</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>RT</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Stroke>
							<CssParameter name="stroke">#686868</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>WildlifeHabitatAreas</Name>
					<Title>WildlifeHabitatAreas</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>WHA</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#A8A800</CssParameter>
						</Fill>
					</PolygonSymbolizer>
					
					<PolygonSymbolizer>
						<Fill>
							<GraphicFill>
								<Graphic>
									<Mark>
										<WellKnownName>shape://backslash</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#72726A</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>7</Size>
								</Graphic>
							</GraphicFill>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#737300</CssParameter>
			              <CssParameter name="stroke-width">1</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>Municipalities</Name>
					<Title>Municipalities</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>MU</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<GraphicFill>
								<Graphic>
									<Mark>
										<WellKnownName>shape://slash</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#C6C6C6</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>16</Size>
								</Graphic>
							</GraphicFill>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#000000</CssParameter>
			              <CssParameter name="stroke-width">1</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>NativeCommunities</Name>
					<Title>NativeCommunities</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>NC</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#FFFFBE</CssParameter>
						</Fill>
						<Stroke>
							<CssParameter name="stroke">#E6E600</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>PrivateParcels</Name>
					<Title>PrivateParcels</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>PP</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#CCCCCC</CssParameter>
						</Fill>
						<Stroke>
							<CssParameter name="stroke">#686868</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
			<!-- Administration_Boundaries.pdf stop -->
			<!-- Mining_Features.pdf start -->
				<Rule>
					<Name>CoalFields</Name>
					<Title>CoalFields</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>MIN_CF</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#FFFFBE</CssParameter>
						</Fill>
					</PolygonSymbolizer>
					<PolygonSymbolizer>
						<Fill>
							<GraphicFill>
								<Graphic>
									<Mark>
										<WellKnownName>shape://slash</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#84847A</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>16</Size>
								</Graphic>
							</GraphicFill>
						</Fill>
			            <Stroke>
			              <CssParameter name="stroke">#E6E600</CssParameter>
			              <CssParameter name="stroke-width">1</CssParameter>
			            </Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>CoalTenures</Name>
					<Title>CoalTenures</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>MIN_CT</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Stroke>
							<CssParameter name="stroke">#A83800</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>DominionCoalBlocks</Name>
					<Title>DominionCoalBlocks</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>MIN_DCB</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Stroke>
							<CssParameter name="stroke">#4C0073</CssParameter>
							<CssParameter name="stroke-width">2</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
			<!-- Mining_Features.pdf stop -->
			<!-- NTS_Features.pdf start -->
		        <Rule>
					<Name>NTS_Streams</Name>
					<Title>NTS_Streams</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>NTS_STR</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#00A9E6</CssParameter>
					    <CssParameter name="stroke-width">2</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
		       	</Rule>
				<Rule>
					<Name>NTS_Glaciers</Name>
					<Title>NTS_Glaciers</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>NTS_G</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#DEFCFF</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>NTS_Lakes</Name>
					<Title>NTS_Lakes</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>NTS_L</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#97DBF2</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>NTS_Rivers</Name>
					<Title>NTS_Rivers</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>NTS_R</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#97DBF2</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>Road (Paved)</Name>
					<Title>Road (Paved)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRANS_RP</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#E69800</CssParameter>
					    <CssParameter name="stroke-width">2</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
				</Rule>
		        <Rule>
					<Name>Road (Loose, Gravel)</Name>
					<Title>Road (Loose, Gravel)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRANS_RLG</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#732600</CssParameter>
					    <CssParameter name="stroke-width">2</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
		       	</Rule>
		        <Rule>
					<Name>Bridge</Name>
					<Title>Bridge</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRANS_BR</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#ECB989</CssParameter>
					    <CssParameter name="stroke-width">3</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
		       	</Rule>
		        <Rule>
					<Name>Trail</Name>
					<Title>Trail</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRANS_TR</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#8400A8</CssParameter>
					    <CssParameter name="stroke-width">2</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
		       	</Rule>
		        <Rule>
					<Name>Railway</Name>
					<Title>Railway</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRANS_RW</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
						<Stroke>
							<CssParameter name="stroke">#828282</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</LineSymbolizer>
					<LineSymbolizer>
						<Stroke>
							<GraphicStroke>
								<Graphic>
									<Mark>
										<WellKnownName>shape://vertline</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#828282</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>12</Size>
								</Graphic>
							</GraphicStroke>
						</Stroke>
					</LineSymbolizer>
		       	</Rule>
		        <Rule>
					<Name>Tunnel</Name>
					<Title>Tunnel</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRANS_TUN</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#686868</CssParameter>
					    <CssParameter name="stroke-width">3</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
				</Rule>
 			<!-- NTS_Features.pdf stop -->
 			
 			<!-- TRIM_Features.pdf start -->
				<Rule>
					<Name>TRIM2_WaterBodies</Name>
					<Title>TRIM2_WaterBodies</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_WB</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#97DBF2</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>TRIM2_WaterCourses_Rivers</Name>
					<Title>TRIM2_WaterCourses_Rivers</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_WCR</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#97DBF2</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>TRIM2_WetLands</Name>
					<Title>TRIM2_WetLands</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_WL</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Stroke>
			                <CssParameter name="stroke">#OOFF00</CssParameter>
							<CssParameter name="stroke-width">2</CssParameter>
							<CssParameter name="stroke-dasharray">7 3</CssParameter>
						</Stroke>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>TRIM2_IceMass</Name>
					<Title>TRIM2_IceMass</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_ICE</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<PolygonSymbolizer>
						<Fill>
							<CssParameter name="fill">#DEFCFF</CssParameter>
						</Fill>
					</PolygonSymbolizer>
				</Rule>
				<Rule>
					<Name>TRIM2 Road (Paved)</Name>
					<Title>Road (Paved)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_TRANS_RP</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#E69800</CssParameter>
					    <CssParameter name="stroke-width">2</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
				</Rule>
		        <Rule>
					<Name>TRIM2 Road (Loose, Gravel)</Name>
					<Title>Road (Loose, Gravel)</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_TRANS_RLG</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#732600</CssParameter>
					    <CssParameter name="stroke-width">2</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
		       	</Rule>
		        <Rule>
					<Name>TRIM2 Bridge</Name>
					<Title>Bridge</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_TRANS_BR</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#ECB989</CssParameter>
					    <CssParameter name="stroke-width">3</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
		       	</Rule>
		        <Rule>
					<Name>TRIM2 Trails</Name>
					<Title>Trails</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_TRANS_TR</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#8400A8</CssParameter>
					    <CssParameter name="stroke-width">2</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
		       	</Rule>
		        <Rule>
					<Name>TRIM2 Railway</Name>
					<Title>Railway</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_TRANS_RW</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
						<Stroke>
							<CssParameter name="stroke">#828282</CssParameter>
							<CssParameter name="stroke-width">1</CssParameter>
						</Stroke>
					</LineSymbolizer>
					<LineSymbolizer>
						<Stroke>
							<GraphicStroke>
								<Graphic>
									<Mark>
										<WellKnownName>shape://vertline</WellKnownName>
										<Stroke>
											<CssParameter name="stroke">#828282</CssParameter>
											<CssParameter name="stroke-width">1</CssParameter>
										</Stroke>
									</Mark>
									<Size>12</Size>
								</Graphic>
							</GraphicStroke>
						</Stroke>
					</LineSymbolizer>
		       	</Rule>
		        <Rule>
					<Name>TRIM2 Tunnel</Name>
					<Title>Tunnel/Snow Shed/Bridge Trestle-Foot</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_TRANS_TUN</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#686868</CssParameter>
					    <CssParameter name="stroke-width">3</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
				</Rule>
		        <Rule>
					<Name>TRIM2 River/Stream Definite</Name>
					<Title>TRIM2 River/Stream Definite</Title>
					<ogc:Filter>
						<ogc:PropertyIsEqualTo>
							<ogc:PropertyName>styleCode</ogc:PropertyName>
							<ogc:Literal>TRIM2_WC_RSD</ogc:Literal>
						</ogc:PropertyIsEqualTo>
					</ogc:Filter>
					<LineSymbolizer>
					  <Stroke>
					    <CssParameter name="stroke">#00A9E6</CssParameter>
					    <CssParameter name="stroke-width">2</CssParameter>    
					  </Stroke>
					</LineSymbolizer>
		       	</Rule>
 			
 			<!-- TRIM_Features.pdf stop -->
			</FeatureTypeStyle>
		</UserStyle>
	</NamedLayer>
</StyledLayerDescriptor>