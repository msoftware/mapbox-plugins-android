package com.mapbox.mapboxsdk.plugins.annotation;

import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.util.LongSparseArray;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

public abstract class AnnotationManager<
  T extends Annotation,
  U extends OnAnnotationClickListener<T>,
  V extends OnAnnotationLongClickListener<T>> {

  protected final MapboxMap mapboxMap;
  protected final LongSparseArray<T> annotations = new LongSparseArray<>();
  protected final List<Feature> features = new ArrayList<>();

  private final List<U> clickListeners = new ArrayList<>();
  private final List<V> longClickListeners = new ArrayList<>();
  protected long currentId;

  private final GeoJsonSource geoJsonSource;
  private final MapClickResolver mapClickResolver;

  @UiThread
  protected AnnotationManager(MapboxMap mapboxMap, GeoJsonSource geoJsonSource) {
    this.mapboxMap = mapboxMap;
    this.geoJsonSource = geoJsonSource;
    mapboxMap.addSource(geoJsonSource);
    mapboxMap.addOnMapClickListener(mapClickResolver = new MapClickResolver(mapboxMap));
    mapboxMap.addOnMapLongClickListener(mapClickResolver);
  }

  /**
   * Get a list of current annotations.
   *
   * @return long sparse array of annotations
   */
  @UiThread
  public LongSparseArray<T> getAnnotations() {
    return annotations;
  }

  @UiThread
  void add(@NonNull T t) {
    annotations.put(currentId, t);
    currentId++;
  }

  /**
   * Delete an annotation from the map.
   *
   * @param t annotation to be deleted
   */
  @UiThread
  public void delete(T t){
    annotations.remove(t.getId());
    updateSource();
  }

  /**
   * Trigger an update to the underlying source
   */
  public void updateSource() {
    // todo move feature creation to a background thread?
    features.clear();
    T t;
    for (int i = 0; i < annotations.size(); i++) {
      t = annotations.valueAt(i);
      features.add(Feature.fromGeometry(t.getGeometry(), t.getFeature()));
    }
    //Collections.sort(features, symbolComparator);
    geoJsonSource.setGeoJson(FeatureCollection.fromFeatures(features));
  }

  /**
   * Add a callback to be invoked when a symbol has been clicked.
   *
   * @param u the callback to be invoked when a symbol is clicked
   */
  @UiThread
  public void addClickListener(@NonNull U u) {
    clickListeners.add(u);
  }

  /**
   * Remove a previously added callback that was to be invoked when symbol has been clicked.
   *
   * @param u the callback to be removed
   */
  @UiThread
  public void removeClickListener(@NonNull U u) {
    if (clickListeners.contains(u)) {
      clickListeners.remove(u);
    }
  }

  /**
   * Add a callback to be invoked when a symbol has been long clicked.
   *
   * @param v the callback to be invoked when a symbol is clicked
   */
  @UiThread
  public void addLongClickListener(@NonNull V v) {
    longClickListeners.add(v);
  }

  /**
   * Remove a previously added callback that was to be invoked when symbol has been long clicked.
   *
   * @param v the callback to be removed
   */
  @UiThread
  public void removeLongClickListener(@NonNull V v) {
    if (longClickListeners.contains(v)) {
      longClickListeners.remove(v);
    }
  }

  /**
   * Cleanup annotation manager, used to clear listeners
   */
  @UiThread
  public void onDestroy() {
    mapboxMap.removeOnMapClickListener(mapClickResolver);
    mapboxMap.removeOnMapLongClickListener(mapClickResolver);
    clickListeners.clear();
    longClickListeners.clear();
  }

  abstract String getAnnotationLayerId();

  abstract String getAnnotationIdKey();

  /**
   * Inner class for transforming map click events into annotation clicks
   */
  private class MapClickResolver implements MapboxMap.OnMapClickListener, MapboxMap.OnMapLongClickListener {

    private MapboxMap mapboxMap;

    private MapClickResolver(MapboxMap mapboxMap) {
      this.mapboxMap = mapboxMap;
    }

    @Override
    public void onMapClick(@NonNull LatLng point) {
      if (clickListeners.isEmpty()) {
        return;
      }

      T annotation= queryMapForFeatures(point);
      if (annotation != null) {
        for (U clickListener : clickListeners) {
          clickListener.onAnnotationClick(annotation);
        }
      }
    }

    @Override
    public void onMapLongClick(@NonNull LatLng point) {
      if (longClickListeners.isEmpty()) {
        return;
      }

      T annotation= queryMapForFeatures(point);
      if (annotation != null) {
        for (V clickListener : longClickListeners) {
          clickListener.onAnnotationLongClick(annotation);
        }
      }
    }

    @Nullable
    private T queryMapForFeatures(@NonNull LatLng point) {
      PointF screenLocation = mapboxMap.getProjection().toScreenLocation(point);
      List<Feature> features = mapboxMap.queryRenderedFeatures(screenLocation, getAnnotationLayerId());
      if (!features.isEmpty()) {
        long id = features.get(0).getProperty(getAnnotationIdKey()).getAsLong();
        return annotations.get(id);
      }
      return null;
    }
  }
}
