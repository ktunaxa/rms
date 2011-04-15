package tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.dto.GetVectorTileRequest;
import org.geomajas.command.dto.GetVectorTileResponse;
import org.geomajas.configuration.NamedStyleInfo;
import org.geomajas.geometry.Coordinate;
import org.geomajas.layer.VectorLayerService;
import org.geomajas.layer.tile.TileCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/tools/client.xml" })
public class RunSingleTileTest {

	@Autowired
	private CommandDispatcher commandDispatcher;

	@Test
	public void testTile() throws ClientProtocolException, IOException {
		GetVectorTileRequest request = createRequest();
		for (int i = 0; i < 1; i++) {
			GetVectorTileResponse response = (GetVectorTileResponse) commandDispatcher.execute(
					GetVectorTileRequest.COMMAND, request, null, null);
			consumeTile(response);
		}
		
	}

	private void consumeTile(GetVectorTileResponse response) throws IOException, ClientProtocolException {
		String url = response.getTile().getFeatureContent();
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse r = httpclient.execute(httpget);
		HttpEntity entity = r.getEntity();
		entity.writeTo(new ByteArrayOutputStream());
	}

	private GetVectorTileRequest createRequest() {
		GetVectorTileRequest request = new GetVectorTileRequest();
		TileCode code = new TileCode(5, 16, 8);
		request.setCode(code);
		request.setLayerId("referenceBase");
		request.setCrs("EPSG:900913");
		request.setScale(0.006541332273339661);
		request.setPanOrigin(new Coordinate(-1.2803202237767024E7, 6306054.833527042));
		NamedStyleInfo style = new NamedStyleInfo();
		style.setName("referenceBaseStyleInfo");
		request.setStyleInfo(style);
		request.setScale(0.05233065818671729);
		request.setPaintGeometries(true);
		request.setPaintLabels(false);
		request.setFilter("layer.code = 1 or layer.code = 2 or layer.code = 5 or layer.code = 6 or layer.code = 7 or layer.code = 8 or layer.code = 9 or layer.code = 19 or layer.code = 20 or layer.code = 21 or layer.code = 22 or layer.code = 23 or layer.code = 24 or layer.code = 25 or layer.code = 26 or layer.code = 27 or layer.code = 28 or layer.code = 34 or layer.code = 78 or layer.code = 79 or layer.code = 82 or layer.code = 83");
		request.setFeatureIncludes(VectorLayerService.FEATURE_INCLUDE_LABEL & VectorLayerService.FEATURE_INCLUDE_STYLE);
		return request;
	}
}
