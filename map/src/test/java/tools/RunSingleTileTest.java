/*
 * Ktunaxa Referral Management System.
 *
 * Copyright (C) see version control system
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package tools;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.geomajas.command.CommandDispatcher;
import org.geomajas.command.dto.GetVectorTileRequest;
import org.geomajas.command.dto.GetVectorTileResponse;
import org.geomajas.configuration.NamedStyleInfo;
import org.geomajas.geometry.Coordinate;
import org.geomajas.layer.tile.TileCode;
import org.geotools.geometry.jts.JTS;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ktunaxa.referral.server.service.KtunaxaConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.io.WKTWriter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/tools/client.xml" })
public class RunSingleTileTest {

	@Autowired
	private CommandDispatcher commandDispatcher;
	
	public static void main(String[] args) {
		System.out.println(new WKTWriter().write(JTS.toGeometry(new Envelope(552400.0312770917,553200.168752717, 5519618.311531207,5520922.667338479))));
	}

	@Test
	public void testTile() throws Exception {
		GetVectorTileRequest request = createRequest();
		long time = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			long time2 = System.currentTimeMillis();
			GetVectorTileResponse response = (GetVectorTileResponse) commandDispatcher.execute(
					GetVectorTileRequest.COMMAND, request, null, null);
			System.out.println("---------- time " + (System.currentTimeMillis() - time2) + "ms");
			time2 = System.currentTimeMillis();
			consumeTile(response);
			System.out.println("---------- time " + (System.currentTimeMillis() - time2) + "ms");
		}
		System.out.println("+++++ time " + (System.currentTimeMillis() - time) + "ms");
	}

	private void consumeTile(GetVectorTileResponse response) throws Exception {
		String url = response.getTile().getFeatureContent();
		System.out.println("---------- " + url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse r = httpclient.execute(httpget);
		HttpEntity entity = r.getEntity();
		entity.writeTo(new ByteArrayOutputStream());
	}

	private GetVectorTileRequest createRequest() {
		GetVectorTileRequest request = new GetVectorTileRequest();
		Random rand = new Random();
		TileCode code = new TileCode(5, 16, 8);
		request.setCode(code);
		request.setLayerId(KtunaxaConstant.LAYER_REFERENCE_BASE_SERVER_ID);
		request.setCrs(KtunaxaConstant.MAP_CRS);
//		request.setScale(0.006541332273339661);
		request.setPanOrigin(new Coordinate(-1.2803202237767024E7, 6306054.833527042));
		NamedStyleInfo style = new NamedStyleInfo();
		style.setName("referenceBaseStyleInfo");
		request.setStyleInfo(style);
		request.setScale(0.05233065818671729 + rand.nextDouble()/1000);
		request.setPaintGeometries(true);
		request.setPaintLabels(false);
		request.setFilter("layer_id = 1 or layer_id = 2 or layer_id = 5 or layer_id = 6 or layer_id = 7 or " +
				"layer_id = 8 or layer_id = 9 or layer_id = 19 or layer_id = 20 or layer_id = 20 or " +
				"layer_id = 22 or layer_id = 22 or layer_id = 24 or layer_id = 25 or layer_id = 26 or " +
				"layer_id = 27 or layer_id = 28 or layer_id = 34 or layer_id = 78 or layer_id = 79 or " +
				"layer_id = 82 or layer_id = 83");
		return request;
	}
}
