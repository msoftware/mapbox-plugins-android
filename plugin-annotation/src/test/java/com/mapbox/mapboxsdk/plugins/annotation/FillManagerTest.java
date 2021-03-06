// This file is generated.

package com.mapbox.mapboxsdk.plugins.annotation;

import com.mapbox.geojson.*;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.layers.*;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class FillManagerTest {

  private MapboxMap mapboxMap = mock(MapboxMap.class);
  private GeoJsonSource geoJsonSource = mock(GeoJsonSource.class);
  private FillLayer fillLayer = mock(FillLayer.class);
  private FillManager fillManager;

  @Before
  public void beforeTest() {
    fillManager = new FillManager(mapboxMap, geoJsonSource, fillLayer, null);
  }

  @Test
  public void testAddFill() {
    List<LatLng>innerLatLngs = new ArrayList<>();
    innerLatLngs.add(new LatLng());
    innerLatLngs.add(new LatLng(1,1));
    innerLatLngs.add(new LatLng(-1,-1));
    List<List<LatLng>>latLngs = new ArrayList<>();
    latLngs.add(innerLatLngs);
    Fill fill = fillManager.createFill(latLngs);
    assertEquals(fillManager.getAnnotations().get(0), fill);
  }

  @Test
  public void addFills() {
    List<List<LatLng>> latLngListOne = new ArrayList<>();
    latLngListOne.add(new ArrayList<LatLng>() {{
      add(new LatLng(2, 2));
      add(new LatLng(2, 3));
    }});
    latLngListOne.add(new ArrayList<LatLng>() {{
      add(new LatLng(1, 1));
      add(new LatLng(2, 3));
    }});

    List<List<LatLng>> latLngListTwo = new ArrayList<>();
    latLngListTwo.add(new ArrayList<LatLng>() {{
      add(new LatLng(5, 7));
      add(new LatLng(2, 3));
    }});
    latLngListTwo.add(new ArrayList<LatLng>() {{
      add(new LatLng(1, 1));
      add(new LatLng(3, 9));
    }});

    List<List<List<LatLng>>> latLngList = new ArrayList<List<List<LatLng>>>(){{
      add(latLngListOne);
      add(latLngListTwo);
    }};
    List<Fill> fills = fillManager.createFills(latLngList);
    assertTrue("Returned value size should match", fills.size() == 2);
    assertTrue("Annotations size should match", fillManager.getAnnotations().size() == 2);
  }

  @Test
  public void testDeleteFill() {
    List<LatLng>innerLatLngs = new ArrayList<>();
    innerLatLngs.add(new LatLng());
    innerLatLngs.add(new LatLng(1,1));
    innerLatLngs.add(new LatLng(-1,-1));
    List<List<LatLng>>latLngs = new ArrayList<>();
    latLngs.add(innerLatLngs);
    Fill fill = fillManager.createFill(latLngs);
    fillManager.delete(fill);
    assertTrue(fillManager.getAnnotations().size() == 0);
  }

  @Test
  public void testGeometryFill() {
    List<LatLng>innerLatLngs = new ArrayList<>();
    innerLatLngs.add(new LatLng());
    innerLatLngs.add(new LatLng(1,1));
    innerLatLngs.add(new LatLng(-1,-1));
    List<List<LatLng>>latLngs = new ArrayList<>();
    latLngs.add(innerLatLngs);
    Fill fill = fillManager.createFill(latLngs);
    assertEquals(fill.getGeometry(), Polygon.fromLngLats(new ArrayList<List<Point>>() {{
      add(new ArrayList<Point>() {{
        add(Point.fromLngLat(0, 0));
        add(Point.fromLngLat(1, 1));
        add(Point.fromLngLat(-1, -1));
      }});
    }}));
  }

  @Test
  public void testFeatureIdFill() {
    List<LatLng>innerLatLngs = new ArrayList<>();
    innerLatLngs.add(new LatLng());
    innerLatLngs.add(new LatLng(1,1));
    innerLatLngs.add(new LatLng(-1,-1));
    List<List<LatLng>>latLngs = new ArrayList<>();
    latLngs.add(innerLatLngs);
    Fill fillZero = fillManager.createFill(latLngs);
    Fill fillOne = fillManager.createFill(latLngs);
    assertEquals(fillZero.getFeature().get(Fill.ID_KEY).getAsLong(), 0);
    assertEquals(fillOne.getFeature().get(Fill.ID_KEY).getAsLong(), 1);
  }
}