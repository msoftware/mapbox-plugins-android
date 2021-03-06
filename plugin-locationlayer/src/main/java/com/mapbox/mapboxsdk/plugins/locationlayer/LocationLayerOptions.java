package com.mapbox.mapboxsdk.plugins.locationlayer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.mapbox.mapboxsdk.constants.MapboxConstants;

import java.util.Arrays;

/**
 * This class exposes options for the Location Layer Plugin. The options can be set by defining a
 * style in your apps style.xml file and passing in directly into the {@link LocationLayerPlugin}
 * class. Alternatively, if properties need to be changed at runtime depending on a specific state,
 * you can build an instance of this class, setting the values you desire, and then passing it into
 * either the {@link LocationLayerPlugin} constructor (if it isn't initialized yet) or
 * {@link LocationLayerPlugin#applyStyle(LocationLayerOptions)}.
 * <p>
 * When the {@link #createFromAttributes(Context, int)} methods called, any attributes not found
 * inside the style will revert back to using their default set values. Likewise, when building a
 * new {@link LocationLayerOptions} class using the builder, any options neglecting to be set will
 * reset to their default values.
 * <p>
 * If you would like to keep your custom style changes while modifying a single attribute, you can
 * get the currently used options object using {@link LocationLayerPlugin#getLocationLayerOptions()}
 * and it's {@code toBuilder} method to modify a single entry while also maintaining the other
 * settings. Once your modifications have been made, you'll need to pass it back into the location
 * layer plugin using {@link LocationLayerPlugin#applyStyle(LocationLayerOptions)}.
 *
 * @since 0.4.0
 */
public class LocationLayerOptions implements Parcelable {

  /**
   * Default accuracy alpha
   */
  private static final float ACCURACY_ALPHA_DEFAULT = 0.15f;

  /**
   * Default max map zoom
   */
  private static final float MAX_ZOOM_DEFAULT = 18;

  /**
   * Default min map zoom
   */
  private static final float MIN_ZOOM_DEFAULT = 2;

  /**
   * Default icon scale factor when the map is zoomed out
   */
  private static final float MIN_ZOOM_ICON_SCALE_DEFAULT = 0.6f;

  /**
   * Default icon scale factor when the map is zoomed in
   */
  private static final float MAX_ZOOM_ICON_SCALE_DEFAULT = 1f;

  /**
   * Default map padding
   */
  private static final int[] PADDING_DEFAULT = {0, 0, 0, 0};

  /**
   * The default value which is used when the stale state is enabled
   */
  private static final long STALE_STATE_DELAY_MS = 30000;

  private float accuracyAlpha;
  private int accuracyColor;
  private int backgroundDrawableStale;
  private String backgroundStaleName;
  private int foregroundDrawableStale;
  private String foregroundStaleName;
  private int gpsDrawable;
  private String gpsName;
  private int foregroundDrawable;
  private String foregroundName;
  private int backgroundDrawable;
  private String backgroundName;
  private int bearingDrawable;
  private String bearingName;
  private Integer bearingTintColor;
  private Integer foregroundTintColor;
  private Integer backgroundTintColor;
  private Integer foregroundStaleTintColor;
  private Integer backgroundStaleTintColor;
  private float elevation;
  private boolean enableStaleState;
  private long staleStateTimeout;
  private int[] padding;
  private double maxZoom;
  private double minZoom;
  private float maxZoomIconScale;
  private float minZoomIconScale;
  private float trackingInitialMoveThreshold;
  private float trackingMultiFingerMoveThreshold;
  private String layerBelow;

  public LocationLayerOptions(
    float accuracyAlpha,
    int accuracyColor,
    int backgroundDrawableStale,
    @Nullable String backgroundStaleName,
    int foregroundDrawableStale,
    @Nullable String foregroundStaleName,
    int gpsDrawable,
    @Nullable String gpsName,
    int foregroundDrawable,
    @Nullable String foregroundName,
    int backgroundDrawable,
    @Nullable String backgroundName,
    int bearingDrawable,
    @Nullable String bearingName,
    @Nullable Integer bearingTintColor,
    @Nullable Integer foregroundTintColor,
    @Nullable Integer backgroundTintColor,
    @Nullable Integer foregroundStaleTintColor,
    @Nullable Integer backgroundStaleTintColor,
    float elevation,
    boolean enableStaleState,
    long staleStateTimeout,
    int[] padding,
    double maxZoom,
    double minZoom,
    float maxZoomIconScale,
    float minZoomIconScale,
    float trackingInitialMoveThreshold,
    float trackingMultiFingerMoveThreshold,
    String layerBelow) {
    this.accuracyAlpha = accuracyAlpha;
    this.accuracyColor = accuracyColor;
    this.backgroundDrawableStale = backgroundDrawableStale;
    this.backgroundStaleName = backgroundStaleName;
    this.foregroundDrawableStale = foregroundDrawableStale;
    this.foregroundStaleName = foregroundStaleName;
    this.gpsDrawable = gpsDrawable;
    this.gpsName = gpsName;
    this.foregroundDrawable = foregroundDrawable;
    this.foregroundName = foregroundName;
    this.backgroundDrawable = backgroundDrawable;
    this.backgroundName = backgroundName;
    this.bearingDrawable = bearingDrawable;
    this.bearingName = bearingName;
    this.bearingTintColor = bearingTintColor;
    this.foregroundTintColor = foregroundTintColor;
    this.backgroundTintColor = backgroundTintColor;
    this.foregroundStaleTintColor = foregroundStaleTintColor;
    this.backgroundStaleTintColor = backgroundStaleTintColor;
    this.elevation = elevation;
    this.enableStaleState = enableStaleState;
    this.staleStateTimeout = staleStateTimeout;
    if (padding == null) {
      throw new NullPointerException("Null padding");
    }
    this.padding = padding;
    this.maxZoom = maxZoom;
    this.minZoom = minZoom;
    this.maxZoomIconScale = maxZoomIconScale;
    this.minZoomIconScale = minZoomIconScale;
    this.trackingInitialMoveThreshold = trackingInitialMoveThreshold;
    this.trackingMultiFingerMoveThreshold = trackingMultiFingerMoveThreshold;
    this.layerBelow = layerBelow;
  }

  /**
   * Construct a new Location Layer Options class using the attributes found within a style
   * resource. It's important to note that you only need to define the attributes you plan to
   * change and can safely ignore the other attributes which will be set to their default value.
   *
   * @param context  your activity's context used for acquiring resources
   * @param styleRes the style id where your custom attributes are defined
   * @return a new {@link LocationLayerOptions} object with the settings you defined in your style
   * resource
   * @since 0.4.0
   */
  public static LocationLayerOptions createFromAttributes(@NonNull Context context,
                                                          @StyleRes int styleRes) {

    TypedArray typedArray = context.obtainStyledAttributes(
      styleRes, R.styleable.mapbox_LocationLayer);

    LocationLayerOptions.Builder builder = new LocationLayerOptions.Builder()
      .enableStaleState(true)
      .staleStateTimeout(STALE_STATE_DELAY_MS)
      .maxZoom(MAX_ZOOM_DEFAULT)
      .minZoom(MIN_ZOOM_DEFAULT)
      .maxZoomIconScale(MAX_ZOOM_ICON_SCALE_DEFAULT)
      .minZoomIconScale(MIN_ZOOM_ICON_SCALE_DEFAULT)
      .padding(PADDING_DEFAULT);

    builder.foregroundDrawable(typedArray.getResourceId(
      R.styleable.mapbox_LocationLayer_mapbox_foregroundDrawable, -1));
    if (typedArray.hasValue(R.styleable.mapbox_LocationLayer_mapbox_foregroundTintColor)) {
      builder.foregroundTintColor(typedArray.getColor(
        R.styleable.mapbox_LocationLayer_mapbox_foregroundTintColor, -1));
    }
    builder.backgroundDrawable(typedArray.getResourceId(
      R.styleable.mapbox_LocationLayer_mapbox_backgroundDrawable, -1));
    if (typedArray.hasValue(R.styleable.mapbox_LocationLayer_mapbox_backgroundTintColor)) {
      builder.backgroundTintColor(typedArray.getColor(
        R.styleable.mapbox_LocationLayer_mapbox_backgroundTintColor, -1));
    }
    builder.foregroundDrawableStale(typedArray.getResourceId(
      R.styleable.mapbox_LocationLayer_mapbox_foregroundDrawableStale, -1));
    if (typedArray.hasValue(R.styleable.mapbox_LocationLayer_mapbox_foregroundStaleTintColor)) {
      builder.foregroundStaleTintColor(typedArray.getColor(
        R.styleable.mapbox_LocationLayer_mapbox_foregroundStaleTintColor, -1));
    }
    builder.backgroundDrawableStale(typedArray.getResourceId(
      R.styleable.mapbox_LocationLayer_mapbox_backgroundDrawableStale, -1));
    if (typedArray.hasValue(R.styleable.mapbox_LocationLayer_mapbox_backgroundStaleTintColor)) {
      builder.backgroundStaleTintColor(typedArray.getColor(
        R.styleable.mapbox_LocationLayer_mapbox_backgroundStaleTintColor, -1));
    }
    builder.bearingDrawable(typedArray.getResourceId(
      R.styleable.mapbox_LocationLayer_mapbox_bearingDrawable, -1));
    if (typedArray.hasValue(R.styleable.mapbox_LocationLayer_mapbox_bearingTintColor)) {
      builder.bearingTintColor(typedArray.getColor(
        R.styleable.mapbox_LocationLayer_mapbox_bearingTintColor, -1));
    }
    if (typedArray.hasValue(R.styleable.mapbox_LocationLayer_mapbox_enableStaleState)) {
      builder.enableStaleState(typedArray.getBoolean(
        R.styleable.mapbox_LocationLayer_mapbox_enableStaleState, true));
    }
    if (typedArray.hasValue(R.styleable.mapbox_LocationLayer_mapbox_staleStateTimeout)) {
      builder.staleStateTimeout(typedArray.getInteger(
        R.styleable.mapbox_LocationLayer_mapbox_staleStateTimeout, (int) STALE_STATE_DELAY_MS));
    }
    builder.gpsDrawable(typedArray.getResourceId(
      R.styleable.mapbox_LocationLayer_mapbox_gpsDrawable, -1));
    float elevation = typedArray.getDimension(
      R.styleable.mapbox_LocationLayer_mapbox_elevation, 0);
    builder.accuracyColor(typedArray.getColor(
      R.styleable.mapbox_LocationLayer_mapbox_accuracyColor, -1));
    builder.accuracyAlpha(typedArray.getFloat(
      R.styleable.mapbox_LocationLayer_mapbox_accuracyAlpha, ACCURACY_ALPHA_DEFAULT));
    builder.elevation(elevation);

    builder.trackingInitialMoveThreshold(typedArray.getDimension(
      R.styleable.mapbox_LocationLayer_mapbox_trackingInitialMoveThreshold,
      context.getResources().getDimension(R.dimen.mapbox_locationLayerTrackingInitialMoveThreshold)));

    builder.trackingMultiFingerMoveThreshold(typedArray.getDimension(
      R.styleable.mapbox_LocationLayer_mapbox_trackingMultiFingerMoveThreshold,
      context.getResources().getDimension(R.dimen.mapbox_locationLayerTrackingMultiFingerMoveThreshold)));

    builder.padding(new int[] {
      typedArray.getInt(R.styleable.mapbox_LocationLayer_mapbox_iconPaddingLeft, 0),
      typedArray.getInt(R.styleable.mapbox_LocationLayer_mapbox_iconPaddingTop, 0),
      typedArray.getInt(R.styleable.mapbox_LocationLayer_mapbox_iconPaddingRight, 0),
      typedArray.getInt(R.styleable.mapbox_LocationLayer_mapbox_iconPaddingBottom, 0),
    });

    float maxZoom
      = typedArray.getFloat(R.styleable.mapbox_LocationLayer_mapbox_maxZoom, MAX_ZOOM_DEFAULT);
    if (maxZoom < MapboxConstants.MINIMUM_ZOOM || maxZoom > MapboxConstants.MAXIMUM_ZOOM) {
      throw new IllegalArgumentException("Max zoom value must be within "
        + MapboxConstants.MINIMUM_ZOOM + " and " + MapboxConstants.MAXIMUM_ZOOM);
    }

    float minZoom
      = typedArray.getFloat(R.styleable.mapbox_LocationLayer_mapbox_minZoom, MIN_ZOOM_DEFAULT);
    if (minZoom < MapboxConstants.MINIMUM_ZOOM || minZoom > MapboxConstants.MAXIMUM_ZOOM) {
      throw new IllegalArgumentException("Min zoom value must be within "
        + MapboxConstants.MINIMUM_ZOOM + " and " + MapboxConstants.MAXIMUM_ZOOM);
    }

    builder.maxZoom(maxZoom);
    builder.minZoom(minZoom);

    builder.layerBelow(
      typedArray.getString(R.styleable.mapbox_LocationLayer_mapbox_layer_below));

    float minScale = typedArray.getFloat(
      R.styleable.mapbox_LocationLayer_mapbox_minZoomIconScale, MIN_ZOOM_ICON_SCALE_DEFAULT);
    float maxScale = typedArray.getFloat(
      R.styleable.mapbox_LocationLayer_mapbox_maxZoomIconScale, MAX_ZOOM_ICON_SCALE_DEFAULT);
    builder.minZoomIconScale(minScale);
    builder.maxZoomIconScale(maxScale);

    typedArray.recycle();

    return builder.build();
  }

  /**
   * Takes the currently constructed {@link LocationLayerOptions} object and provides it's builder
   * with all the values set matching the values in this instance. This allows you to modify a
   * single attribute and then rebuild the object.
   *
   * @return the Location Layer builder which contains the values defined in this current instance
   * as defaults.
   * @since 0.4.0
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  /**
   * Build a new instance of the {@link LocationLayerOptions} class with all the attributes set
   * automatically to their defined defaults in this library. This allows you to adjust a few
   * attributes while leaving the rest alone and maintaining their default behavior.
   *
   * @param context your activities context used to acquire the style resource
   * @return the Location Layer builder which contains the default values defined by the style
   * resource
   * @since 0.4.0
   */
  public static Builder builder(Context context) {
    return LocationLayerOptions.createFromAttributes(context,
      R.style.mapbox_LocationLayer).toBuilder();
  }

  /**
   * Set the opacity of the accuracy view to a value from 0 to 1, where 0 means the accuracy view is
   * completely transparent and 1 means the view is completely opaque.
   *
   * @return the opacity of the accuracy view
   * @attr ref R.styleable#LocationLayer_accuracyAlpha
   * @since 0.4.0
   */
  public float accuracyAlpha() {
    return accuracyAlpha;
  }

  /**
   * Solid color to use as the accuracy view color property.
   *
   * @return the color of the accuracy view
   * @attr ref R.styleable#LocationLayer_accuracyColor
   * @since 0.4.0
   */
  @ColorInt
  public int accuracyColor() {
    return accuracyColor;
  }

  /**
   * Defines the drawable used for the stale background icon.
   *
   * @return the drawable resource ID
   * @attr ref R.styleable#LocationLayer_backgroundDrawableStale
   * @since 0.4.0
   */
  @DrawableRes
  public int backgroundDrawableStale() {
    return backgroundDrawableStale;
  }

  /**
   * String image name, identical to one used in
   * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
   * plugin, will used this image in place of the provided or default mapbox_foregroundDrawableStale.
   * <p>
   * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
   * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
   * </p>
   *
   * @return String icon or maki-icon name
   * @since 0.6.0
   */
  @Nullable
  public String backgroundStaleName() {
    return backgroundStaleName;
  }

  /**
   * Defines the drawable used for the stale foreground icon.
   *
   * @return the drawable resource ID
   * @attr ref R.styleable#LocationLayer_foregroundDrawableStale
   * @since 0.4.0
   */
  @DrawableRes
  public int foregroundDrawableStale() {
    return foregroundDrawableStale;
  }

  /**
   * String image name, identical to one used in
   * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
   * plugin, will used this image in place of the provided or default mapbox_foregroundDrawableStale.
   * <p>
   * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
   * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
   * </p>
   *
   * @return String icon or maki-icon name
   * @since 0.6.0
   */
  @Nullable
  public String foregroundStaleName() {
    return foregroundStaleName;
  }

  /**
   * Defines the drawable used for the navigation state icon.
   *
   * @return the drawable resource ID
   * @attr ref R.styleable#LocationLayer_gpsDrawable
   * @since 0.4.0
   */
  @DrawableRes
  public int gpsDrawable() {
    return gpsDrawable;
  }

  /**
   * String image name, identical to one used in
   * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
   * plugin, will used this image in place of the provided or default mapbox_gpsDrawable.
   * <p>
   * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
   * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
   * </p>
   *
   * @return String icon or maki-icon name
   * @since 0.6.0
   */
  @Nullable
  public String gpsName() {
    return gpsName;
  }

  /**
   * Supply a Drawable that is to be rendered on top of all of the content in the Location Layer
   * Plugin layer stack.
   *
   * @return the drawable resource used for the foreground layer
   * @attr ref R.styleable#LocationLayer_foregroundDrawable
   * @since 0.4.0
   */
  @DrawableRes
  public int foregroundDrawable() {
    return foregroundDrawable;
  }

  /**
   * String image name, identical to one used in
   * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
   * plugin, will used this image in place of the provided or default mapbox_foregroundDrawable.
   * <p>
   * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
   * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
   * </p>
   *
   * @return String icon or maki-icon name
   * @since 0.6.0
   */
  @Nullable
  public String foregroundName() {
    return foregroundName;
  }

  /**
   * Defines the drawable used for the background state icon.
   *
   * @return the drawable resource ID
   * @attr ref R.styleable#LocationLayer_backgroundDrawable
   * @since 0.4.0
   */
  @DrawableRes
  public int backgroundDrawable() {
    return backgroundDrawable;
  }

  /**
   * String image name, identical to one used in
   * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
   * plugin, will used this image in place of the provided or default mapbox_backgroundDrawable.
   * <p>
   * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
   * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
   * </p>
   *
   * @return String icon or maki-icon name
   * @since 0.6.0
   */
  @Nullable
  public String backgroundName() {
    return backgroundName;
  }

  /**
   * Defines the drawable used for the bearing icon.
   *
   * @return the drawable resource ID
   * @attr ref R.styleable#LocationLayer_bearingDrawable
   * @since 0.4.0
   */
  @DrawableRes
  public int bearingDrawable() {
    return bearingDrawable;
  }

  /**
   * String image name, identical to one used in
   * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
   * plugin, will used this image in place of the provided or default mapbox_bearingDrawable.
   * <p>
   * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
   * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
   * </p>
   *
   * @return String icon or maki-icon name
   * @since 0.6.0
   */
  @Nullable
  public String bearingName() {
    return bearingName;
  }

  /**
   * Defines the bearing icon color as an integer.
   *
   * @return the color integer resource
   * @attr ref R.styleable#LocationLayer_bearingTintColor
   * @since 0.4.0
   */
  @ColorInt
  @Nullable
  public Integer bearingTintColor() {
    return bearingTintColor;
  }

  /**
   * Defines the foreground color as an integer.
   *
   * @return the color integer resource
   * @attr ref R.styleable#LocationLayer_foregroundTintColor
   * @since 0.4.0
   */
  @ColorInt
  @Nullable
  public Integer foregroundTintColor() {
    return foregroundTintColor;
  }

  /**
   * Defines the background color as an integer.
   *
   * @return the color integer resource
   * @attr ref R.styleable#LocationLayer_backgroundTintColor
   * @since 0.4.0
   */
  @ColorInt
  @Nullable
  public Integer backgroundTintColor() {
    return backgroundTintColor;
  }

  /**
   * Defines the foreground stale color as an integer.
   *
   * @return the color integer resource
   * @attr ref R.styleable#LocationLayer_foregroundStaleTintColor
   * @since 0.4.0
   */
  @ColorInt
  @Nullable
  public Integer foregroundStaleTintColor() {
    return foregroundStaleTintColor;
  }

  /**
   * Defines the background stale color as an integer.
   *
   * @return the color integer resource
   * @attr ref R.styleable#LocationLayer_backgroundStaleTintColor
   * @since 0.4.0
   */
  @ColorInt
  @Nullable
  public Integer backgroundStaleTintColor() {
    return backgroundStaleTintColor;
  }

  /**
   * Sets the base elevation of this view, in pixels.
   *
   * @return the elevation currently set for the location layer icon
   * @attr ref R.styleable#LocationLayer_elevation
   * @since 0.4.0
   */
  @Dimension
  public float elevation() {
    return elevation;
  }

  /**
   * Enable or disable to stale state mode. This mode indicates to the user that the location being
   * displayed on the map hasn't been updated in a specific amount of time.
   *
   * @return whether the stale state mode is enabled or not
   * @attr ref R.styleable#LocationLayer_enableStaleState
   * @since 0.4.0
   */
  public boolean enableStaleState() {
    return enableStaleState;
  }

  /**
   * Set the delay before the location icon becomes stale. The timer begins approximately when a new
   * location update comes in and using this defined time, if an update hasn't occured by the end,
   * the location is considered stale.
   *
   * @return the duration in milliseconds which it should take before the location layer is
   * considered stale
   * @attr ref R.styleable#LocationLayer_staleStateDelay
   * @since 0.4.0
   */
  public long staleStateTimeout() {
    return staleStateTimeout;
  }

  /**
   * Sets the distance from the edges of the map view’s frame to the edges of the map
   * view’s logical viewport.
   * </p>
   * <p>
   * When the value of this property is equal to {0,0,0,0}, viewport
   * properties such as `centerCoordinate` assume a viewport that matches the map
   * view’s frame. Otherwise, those properties are inset, excluding part of the
   * frame from the viewport. For instance, if the only the top edge is inset, the
   * map center is effectively shifted downward.
   * </p>
   *
   * @return integer array of padding values
   * @since 0.5.0
   */
  @SuppressWarnings("mutable")
  public int[] padding() {
    return padding;
  }

  /**
   * The maximum zoom level the map can be displayed at.
   *
   * @return the maximum zoom level
   * @since 0.5.0
   */
  public double maxZoom() {
    return maxZoom;
  }

  /**
   * The minimum zoom level the map can be displayed at.
   *
   * @return the minimum zoom level
   * @since 0.5.0
   */
  public double minZoom() {
    return minZoom;
  }

  /**
   * The scale factor of the location icon when the map is zoomed in. Based on {@link #maxZoom()}.
   * Scaling is linear.
   *
   * @return icon scale factor
   * @since 0.6.0
   */
  public float maxZoomIconScale() {
    return maxZoomIconScale;
  }

  /**
   * The scale factor of the location icon when the map is zoomed out. Based on {@link #minZoom()}.
   * Scaling is linear.
   *
   * @return icon scale factor
   * @since 0.6.0
   */
  public float minZoomIconScale() {
    return minZoomIconScale;
  }

  /**
   * Minimum single pointer movement in pixels required to break camera tracking.
   *
   * @return the minimum movement
   * @since 0.5.0
   */
  public float trackingInitialMoveThreshold() {
    return trackingInitialMoveThreshold;
  }

  /**
   * Minimum multi pointer movement in pixels required to break camera tracking (for example during scale gesture).
   *
   * @return the minimum movement
   * @since 0.5.0
   */
  public float trackingMultiFingerMoveThreshold() {
    return trackingMultiFingerMoveThreshold;
  }

  /**
   * Gets the id of the layer to add the location layer above to.
   *
   * @return layerBelow the id of the layer to add the location layer above to
   * @since 0.8.0
   */
  public String layerBelow() {
    return layerBelow;
  }

  @Override
  public String toString() {
    return "LocationLayerOptions{"
      + "accuracyAlpha=" + accuracyAlpha + ", "
      + "accuracyColor=" + accuracyColor + ", "
      + "backgroundDrawableStale=" + backgroundDrawableStale + ", "
      + "backgroundStaleName=" + backgroundStaleName + ", "
      + "foregroundDrawableStale=" + foregroundDrawableStale + ", "
      + "foregroundStaleName=" + foregroundStaleName + ", "
      + "gpsDrawable=" + gpsDrawable + ", "
      + "gpsName=" + gpsName + ", "
      + "foregroundDrawable=" + foregroundDrawable + ", "
      + "foregroundName=" + foregroundName + ", "
      + "backgroundDrawable=" + backgroundDrawable + ", "
      + "backgroundName=" + backgroundName + ", "
      + "bearingDrawable=" + bearingDrawable + ", "
      + "bearingName=" + bearingName + ", "
      + "bearingTintColor=" + bearingTintColor + ", "
      + "foregroundTintColor=" + foregroundTintColor + ", "
      + "backgroundTintColor=" + backgroundTintColor + ", "
      + "foregroundStaleTintColor=" + foregroundStaleTintColor + ", "
      + "backgroundStaleTintColor=" + backgroundStaleTintColor + ", "
      + "elevation=" + elevation + ", "
      + "enableStaleState=" + enableStaleState + ", "
      + "staleStateTimeout=" + staleStateTimeout + ", "
      + "padding=" + Arrays.toString(padding) + ", "
      + "maxZoom=" + maxZoom + ", "
      + "minZoom=" + minZoom + ", "
      + "maxZoomIconScale=" + maxZoomIconScale + ", "
      + "minZoomIconScale=" + minZoomIconScale + ", "
      + "trackingInitialMoveThreshold=" + trackingInitialMoveThreshold + ", "
      + "trackingMultiFingerMoveThreshold=" + trackingMultiFingerMoveThreshold + ", "
      + "layerBelow=" + layerBelow
      + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof LocationLayerOptions) {
      LocationLayerOptions that = (LocationLayerOptions) o;
      return (Float.floatToIntBits(this.accuracyAlpha) == Float.floatToIntBits(that.accuracyAlpha()))
        && (this.accuracyColor == that.accuracyColor())
        && (this.backgroundDrawableStale == that.backgroundDrawableStale())
        && ((this.backgroundStaleName == null) ? (that.backgroundStaleName() == null)
        : this.backgroundStaleName.equals(that.backgroundStaleName()))
        && (this.foregroundDrawableStale == that.foregroundDrawableStale())
        && ((this.foregroundStaleName == null) ? (that.foregroundStaleName() == null)
        : this.foregroundStaleName.equals(that.foregroundStaleName()))
        && (this.gpsDrawable == that.gpsDrawable())
        && ((this.gpsName == null) ? (that.gpsName() == null) : this.gpsName.equals(that.gpsName()))
        && (this.foregroundDrawable == that.foregroundDrawable())
        && ((this.foregroundName == null) ? (that.foregroundName() == null)
        : this.foregroundName.equals(that.foregroundName()))
        && (this.backgroundDrawable == that.backgroundDrawable())
        && ((this.backgroundName == null) ? (that.backgroundName() == null)
        : this.backgroundName.equals(that.backgroundName()))
        && (this.bearingDrawable == that.bearingDrawable())
        && ((this.bearingName == null) ? (that.bearingName() == null)
        : this.bearingName.equals(that.bearingName()))
        && ((this.bearingTintColor == null) ? (that.bearingTintColor() == null)
        : this.bearingTintColor.equals(that.bearingTintColor()))
        && ((this.foregroundTintColor == null) ? (that.foregroundTintColor() == null)
        : this.foregroundTintColor.equals(that.foregroundTintColor()))
        && ((this.backgroundTintColor == null) ? (that.backgroundTintColor() == null)
        : this.backgroundTintColor.equals(that.backgroundTintColor()))
        && ((this.foregroundStaleTintColor == null) ? (that.foregroundStaleTintColor() == null)
        : this.foregroundStaleTintColor.equals(that.foregroundStaleTintColor()))
        && ((this.backgroundStaleTintColor == null) ? (that.backgroundStaleTintColor() == null)
        : this.backgroundStaleTintColor.equals(that.backgroundStaleTintColor()))
        && (Float.floatToIntBits(this.elevation) == Float.floatToIntBits(that.elevation()))
        && (this.enableStaleState == that.enableStaleState())
        && (this.staleStateTimeout == that.staleStateTimeout())
        && (Arrays.equals(this.padding, that.padding())
        && (Double.doubleToLongBits(this.maxZoom) == Double.doubleToLongBits(that.maxZoom()))
        && (Double.doubleToLongBits(this.minZoom) == Double.doubleToLongBits(that.minZoom()))
        && (Float.floatToIntBits(this.maxZoomIconScale) == Float.floatToIntBits(that.maxZoomIconScale()))
        && (Float.floatToIntBits(this.minZoomIconScale) == Float.floatToIntBits(that.minZoomIconScale()))
        && (Float.floatToIntBits(this.trackingInitialMoveThreshold)
        == Float.floatToIntBits(that.trackingInitialMoveThreshold()))
        && (Float.floatToIntBits(this.trackingMultiFingerMoveThreshold)
        == Float.floatToIntBits(that.trackingMultiFingerMoveThreshold()))
        && layerBelow.equals(that.layerBelow));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(accuracyAlpha);
    h$ *= 1000003;
    h$ ^= accuracyColor;
    h$ *= 1000003;
    h$ ^= backgroundDrawableStale;
    h$ *= 1000003;
    h$ ^= (backgroundStaleName == null) ? 0 : backgroundStaleName.hashCode();
    h$ *= 1000003;
    h$ ^= foregroundDrawableStale;
    h$ *= 1000003;
    h$ ^= (foregroundStaleName == null) ? 0 : foregroundStaleName.hashCode();
    h$ *= 1000003;
    h$ ^= gpsDrawable;
    h$ *= 1000003;
    h$ ^= (gpsName == null) ? 0 : gpsName.hashCode();
    h$ *= 1000003;
    h$ ^= foregroundDrawable;
    h$ *= 1000003;
    h$ ^= (foregroundName == null) ? 0 : foregroundName.hashCode();
    h$ *= 1000003;
    h$ ^= backgroundDrawable;
    h$ *= 1000003;
    h$ ^= (backgroundName == null) ? 0 : backgroundName.hashCode();
    h$ *= 1000003;
    h$ ^= bearingDrawable;
    h$ *= 1000003;
    h$ ^= (bearingName == null) ? 0 : bearingName.hashCode();
    h$ *= 1000003;
    h$ ^= (bearingTintColor == null) ? 0 : bearingTintColor.hashCode();
    h$ *= 1000003;
    h$ ^= (foregroundTintColor == null) ? 0 : foregroundTintColor.hashCode();
    h$ *= 1000003;
    h$ ^= (backgroundTintColor == null) ? 0 : backgroundTintColor.hashCode();
    h$ *= 1000003;
    h$ ^= (foregroundStaleTintColor == null) ? 0 : foregroundStaleTintColor.hashCode();
    h$ *= 1000003;
    h$ ^= (backgroundStaleTintColor == null) ? 0 : backgroundStaleTintColor.hashCode();
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(elevation);
    h$ *= 1000003;
    h$ ^= enableStaleState ? 1231 : 1237;
    h$ *= 1000003;
    h$ ^= (int) ((staleStateTimeout >>> 32) ^ staleStateTimeout);
    h$ *= 1000003;
    h$ ^= Arrays.hashCode(padding);
    h$ *= 1000003;
    h$ ^= (int) ((Double.doubleToLongBits(maxZoom) >>> 32) ^ Double.doubleToLongBits(maxZoom));
    h$ *= 1000003;
    h$ ^= (int) ((Double.doubleToLongBits(minZoom) >>> 32) ^ Double.doubleToLongBits(minZoom));
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(maxZoomIconScale);
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(minZoomIconScale);
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(trackingInitialMoveThreshold);
    h$ *= 1000003;
    h$ ^= Float.floatToIntBits(trackingMultiFingerMoveThreshold);
    return h$;
  }

  public static final Parcelable.Creator<LocationLayerOptions> CREATOR =
    new Parcelable.Creator<LocationLayerOptions>() {
    @Override
    public LocationLayerOptions createFromParcel(Parcel in) {
      return new LocationLayerOptions(
        in.readFloat(),
        in.readInt(),
        in.readInt(),
        in.readInt() == 0 ? in.readString() : null,
        in.readInt(),
        in.readInt() == 0 ? in.readString() : null,
        in.readInt(),
        in.readInt() == 0 ? in.readString() : null,
        in.readInt(),
        in.readInt() == 0 ? in.readString() : null,
        in.readInt(),
        in.readInt() == 0 ? in.readString() : null,
        in.readInt(),
        in.readInt() == 0 ? in.readString() : null,
        in.readInt() == 0 ? in.readInt() : null,
        in.readInt() == 0 ? in.readInt() : null,
        in.readInt() == 0 ? in.readInt() : null,
        in.readInt() == 0 ? in.readInt() : null,
        in.readInt() == 0 ? in.readInt() : null,
        in.readFloat(),
        in.readInt() == 1,
        in.readLong(),
        in.createIntArray(),
        in.readDouble(),
        in.readDouble(),
        in.readFloat(),
        in.readFloat(),
        in.readFloat(),
        in.readFloat(),
        in.readString()
      );
    }

    @Override
    public LocationLayerOptions[] newArray(int size) {
      return new LocationLayerOptions[size];
    }
  };

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeFloat(accuracyAlpha());
    dest.writeInt(accuracyColor());
    dest.writeInt(backgroundDrawableStale());
    if (backgroundStaleName() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeString(backgroundStaleName());
    }
    dest.writeInt(foregroundDrawableStale());
    if (foregroundStaleName() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeString(foregroundStaleName());
    }
    dest.writeInt(gpsDrawable());
    if (gpsName() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeString(gpsName());
    }
    dest.writeInt(foregroundDrawable());
    if (foregroundName() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeString(foregroundName());
    }
    dest.writeInt(backgroundDrawable());
    if (backgroundName() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeString(backgroundName());
    }
    dest.writeInt(bearingDrawable());
    if (bearingName() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeString(bearingName());
    }
    if (bearingTintColor() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeInt(bearingTintColor());
    }
    if (foregroundTintColor() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeInt(foregroundTintColor());
    }
    if (backgroundTintColor() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeInt(backgroundTintColor());
    }
    if (foregroundStaleTintColor() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeInt(foregroundStaleTintColor());
    }
    if (backgroundStaleTintColor() == null) {
      dest.writeInt(1);
    } else {
      dest.writeInt(0);
      dest.writeInt(backgroundStaleTintColor());
    }
    dest.writeFloat(elevation());
    dest.writeInt(enableStaleState() ? 1 : 0);
    dest.writeLong(staleStateTimeout());
    dest.writeIntArray(padding());
    dest.writeDouble(maxZoom());
    dest.writeDouble(minZoom());
    dest.writeFloat(maxZoomIconScale());
    dest.writeFloat(minZoomIconScale());
    dest.writeFloat(trackingInitialMoveThreshold());
    dest.writeFloat(trackingMultiFingerMoveThreshold());
    dest.writeString(layerBelow());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  /**
   * Builder class for constructing a new instance of {@link LocationLayerOptions}.
   *
   * @since 0.4.0
   */
  public static class Builder {

    /**
     * Build a new instance of this {@link LocationLayerOptions} class.
     *
     * @return a new instance of {@link LocationLayerOptions}
     * @since 0.4.0
     */
    public LocationLayerOptions build() {
      LocationLayerOptions locationLayerOptions = autoBuild();
      if (locationLayerOptions.accuracyAlpha() < 0 || locationLayerOptions.accuracyAlpha() > 1) {
        throw new IllegalArgumentException(
          "Location layer accuracy alpha value must be between 0.0 and 1.0.");
      }

      if (locationLayerOptions.elevation() < 0f) {
        throw new IllegalArgumentException("Invalid shadow size "
          + locationLayerOptions.elevation() + ". Must be >= 0");
      }

      return locationLayerOptions;
    }

    private Float accuracyAlpha;
    private Integer accuracyColor;
    private Integer backgroundDrawableStale;
    private String backgroundStaleName;
    private Integer foregroundDrawableStale;
    private String foregroundStaleName;
    private Integer gpsDrawable;
    private String gpsName;
    private Integer foregroundDrawable;
    private String foregroundName;
    private Integer backgroundDrawable;
    private String backgroundName;
    private Integer bearingDrawable;
    private String bearingName;
    private Integer bearingTintColor;
    private Integer foregroundTintColor;
    private Integer backgroundTintColor;
    private Integer foregroundStaleTintColor;
    private Integer backgroundStaleTintColor;
    private Float elevation;
    private Boolean enableStaleState;
    private Long staleStateTimeout;
    private int[] padding;
    private Double maxZoom;
    private Double minZoom;
    private Float maxZoomIconScale;
    private Float minZoomIconScale;
    private Float trackingInitialMoveThreshold;
    private Float trackingMultiFingerMoveThreshold;
    private String layerBelow;

    Builder() {
    }

    private Builder(LocationLayerOptions source) {
      this.accuracyAlpha = source.accuracyAlpha();
      this.accuracyColor = source.accuracyColor();
      this.backgroundDrawableStale = source.backgroundDrawableStale();
      this.backgroundStaleName = source.backgroundStaleName();
      this.foregroundDrawableStale = source.foregroundDrawableStale();
      this.foregroundStaleName = source.foregroundStaleName();
      this.gpsDrawable = source.gpsDrawable();
      this.gpsName = source.gpsName();
      this.foregroundDrawable = source.foregroundDrawable();
      this.foregroundName = source.foregroundName();
      this.backgroundDrawable = source.backgroundDrawable();
      this.backgroundName = source.backgroundName();
      this.bearingDrawable = source.bearingDrawable();
      this.bearingName = source.bearingName();
      this.bearingTintColor = source.bearingTintColor();
      this.foregroundTintColor = source.foregroundTintColor();
      this.backgroundTintColor = source.backgroundTintColor();
      this.foregroundStaleTintColor = source.foregroundStaleTintColor();
      this.backgroundStaleTintColor = source.backgroundStaleTintColor();
      this.elevation = source.elevation();
      this.enableStaleState = source.enableStaleState();
      this.staleStateTimeout = source.staleStateTimeout();
      this.padding = source.padding();
      this.maxZoom = source.maxZoom();
      this.minZoom = source.minZoom();
      this.maxZoomIconScale = source.maxZoomIconScale();
      this.minZoomIconScale = source.minZoomIconScale();
      this.trackingInitialMoveThreshold = source.trackingInitialMoveThreshold();
      this.trackingMultiFingerMoveThreshold = source.trackingMultiFingerMoveThreshold();
      this.layerBelow = source.layerBelow();
    }

    /**
     * Set the opacity of the accuracy view to a value from 0 to 1, where 0 means the accuracy view
     * is completely transparent and 1 means the view is completely opaque.
     *
     * @param accuracyAlpha the opacity of the accuracy view
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_accuracyAlpha
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder accuracyAlpha(float accuracyAlpha) {
      this.accuracyAlpha = accuracyAlpha;
      return this;
    }

    /**
     * Solid color to use as the accuracy view color property.
     *
     * @param accuracyColor the color of the accuracy view
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_accuracyColor
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder accuracyColor(int accuracyColor) {
      this.accuracyColor = accuracyColor;
      return this;
    }

    /**
     * Defines the drawable used for the stale background icon.
     *
     * @param backgroundDrawableStale the drawable resource ID
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_backgroundDrawableStale
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder backgroundDrawableStale(int backgroundDrawableStale) {
      this.backgroundDrawableStale = backgroundDrawableStale;
      return this;
    }

    /**
     * Given a String image name, identical to one used in
     * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
     * plugin, will used this image in place of the provided or default mapbox_backgroundDrawableStale.
     * <p>
     * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
     * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
     * </p>
     *
     * @param backgroundStaleName String icon or maki-icon name
     * @return this builder for chaining options together
     * @since 0.6.0
     */
    public LocationLayerOptions.Builder backgroundStaleName(@Nullable String backgroundStaleName) {
      this.backgroundStaleName = backgroundStaleName;
      return this;
    }

    /**
     * Defines the drawable used for the stale foreground icon.
     *
     * @param foregroundDrawableStale the drawable resource ID
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_foregroundDrawableStale
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder foregroundDrawableStale(int foregroundDrawableStale) {
      this.foregroundDrawableStale = foregroundDrawableStale;
      return this;
    }

    /**
     * Given a String image name, identical to one used in
     * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
     * plugin, will used this image in place of the provided or default mapbox_foregroundDrawableStale.
     * <p>
     * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
     * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
     * </p>
     *
     * @param foregroundStaleName String icon or maki-icon name
     * @return this builder for chaining options together
     * @since 0.6.0
     */
    public LocationLayerOptions.Builder foregroundStaleName(@Nullable String foregroundStaleName) {
      this.foregroundStaleName = foregroundStaleName;
      return this;
    }

    /**
     * Defines the drawable used for the navigation state icon.
     *
     * @param gpsDrawable the drawable resource ID
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_gpsDrawable
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder gpsDrawable(int gpsDrawable) {
      this.gpsDrawable = gpsDrawable;
      return this;
    }

    /**
     * Given a String image name, identical to one used in
     * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
     * plugin, will used this image in place of the provided or default mapbox_gpsDrawable.
     * <p>
     * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
     * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
     * </p>
     *
     * @param gpsName String icon or maki-icon name
     * @return this builder for chaining options together
     * @since 0.6.0
     */
    public LocationLayerOptions.Builder gpsName(@Nullable String gpsName) {
      this.gpsName = gpsName;
      return this;
    }

    /**
     * Supply a Drawable that is to be rendered on top of all of the content in the Location Layer
     * Plugin layer stack.
     *
     * @param foregroundDrawable the drawable resource used for the foreground layer
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_foregroundDrawable
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder foregroundDrawable(int foregroundDrawable) {
      this.foregroundDrawable = foregroundDrawable;
      return this;
    }

    /**
     * Given a String image name, identical to one used in
     * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
     * plugin, will used this image in place of the provided or default mapbox_foregroundDrawable.
     * <p>
     * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
     * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
     * </p>
     *
     * @param foregroundName String icon or maki-icon name
     * @return this builder for chaining options together
     * @since 0.6.0
     */
    public LocationLayerOptions.Builder foregroundName(@Nullable String foregroundName) {
      this.foregroundName = foregroundName;
      return this;
    }

    /**
     * Defines the drawable used for the background state icon.
     *
     * @param backgroundDrawable the drawable resource ID
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_backgroundDrawable
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder backgroundDrawable(int backgroundDrawable) {
      this.backgroundDrawable = backgroundDrawable;
      return this;
    }

    /**
     * Given a String image name, identical to one used in
     * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
     * plugin, will used this image in place of the provided or default mapbox_backgroundDrawable.
     * <p>
     * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
     * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
     * </p>
     *
     * @param backgroundName String icon or maki-icon name
     * @return this builder for chaining options together
     * @since 0.6.0
     */
    public LocationLayerOptions.Builder backgroundName(@Nullable String backgroundName) {
      this.backgroundName = backgroundName;
      return this;
    }

    /**
     * Defines the drawable used for the bearing icon.
     *
     * @param bearingDrawable the drawable resource ID
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_bearingDrawable
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder bearingDrawable(int bearingDrawable) {
      this.bearingDrawable = bearingDrawable;
      return this;
    }

    /**
     * Given a String image name, identical to one used in
     * the first parameter of {@link com.mapbox.mapboxsdk.maps.MapboxMap#addImage(String, Bitmap)}, the
     * plugin, will used this image in place of the provided or default mapbox_bearingDrawable.
     * <p>
     * A maki-icon name (example: "circle-15") may also be provided.  These are images that can be loaded
     * with certain styles.  Note, this will fail if the provided icon name is not provided by the loaded map style.
     * </p>
     *
     * @param bearingName String icon or maki-icon name
     * @return this builder for chaining options together
     * @since 0.6.0
     */
    public LocationLayerOptions.Builder bearingName(@Nullable String bearingName) {
      this.bearingName = bearingName;
      return this;
    }

    /**
     * Defines the bearing icon color as an integer.
     *
     * @param bearingTintColor the color integer resource
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_bearingTintColor
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder bearingTintColor(@Nullable Integer bearingTintColor) {
      this.bearingTintColor = bearingTintColor;
      return this;
    }

    /**
     * Defines the foreground color as an integer.
     *
     * @param foregroundTintColor the color integer resource
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_foregroundTintColor
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder foregroundTintColor(@Nullable Integer foregroundTintColor) {
      this.foregroundTintColor = foregroundTintColor;
      return this;
    }

    /**
     * Defines the background color as an integer.
     *
     * @param backgroundTintColor the color integer resource
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_backgroundTintColor
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder backgroundTintColor(@Nullable Integer backgroundTintColor) {
      this.backgroundTintColor = backgroundTintColor;
      return this;
    }

    /**
     * Defines the foreground stale color as an integer.
     *
     * @param foregroundStaleTintColor the color integer resource
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_foregroundStaleTintColor
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder foregroundStaleTintColor(@Nullable Integer foregroundStaleTintColor) {
      this.foregroundStaleTintColor = foregroundStaleTintColor;
      return this;
    }

    /**
     * Defines the background stale color as an integer.
     *
     * @param backgroundStaleTintColor the color integer resource
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_backgroundStaleTintColor
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder backgroundStaleTintColor(@Nullable Integer backgroundStaleTintColor) {
      this.backgroundStaleTintColor = backgroundStaleTintColor;
      return this;
    }

    /**
     * Sets the base elevation of this view, in pixels.
     *
     * @param elevation the elevation currently set for the location layer icon
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_elevation
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder elevation(float elevation) {
      this.elevation = elevation;
      return this;
    }

    /**
     * Enable or disable to stale state mode. This mode indicates to the user that the location
     * being displayed on the map hasn't been updated in a specific amount of time.
     *
     * @param enabled whether the stale state mode is enabled or not
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_enableStaleState
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder enableStaleState(boolean enabled) {
      this.enableStaleState = enabled;
      return this;
    }

    /**
     * Set the timeout before the location icon becomes stale. The timer begins approximately when a
     * new location update comes in and using this defined time, if an update hasn't occurred by the
     * end, the location is considered stale.
     *
     * @param timeout the duration in milliseconds which it should take before the location layer is
     *                considered stale
     * @return this builder for chaining options together
     * @attr ref R.styleable#LocationLayer_staleStateTimeout
     * @since 0.4.0
     */
    public LocationLayerOptions.Builder staleStateTimeout(long timeout) {
      this.staleStateTimeout = timeout;
      return this;
    }

    /**
     * Sets the distance from the edges of the map view’s frame to the edges of the map
     * view’s logical viewport.
     * </p>
     * <p>
     * When the value of this property is equal to {0,0,0,0}, viewport
     * properties such as `centerCoordinate` assume a viewport that matches the map
     * view’s frame. Otherwise, those properties are inset, excluding part of the
     * frame from the viewport. For instance, if the only the top edge is inset, the
     * map center is effectively shifted downward.
     * </p>
     *
     * @param padding The margins for the map in pixels (left, top, right, bottom).
     * @since 0.5.0
     */
    public LocationLayerOptions.Builder padding(int[] padding) {
      if (padding == null) {
        throw new NullPointerException("Null padding");
      }
      this.padding = padding;
      return this;
    }

    /**
     * Sets the maximum zoom level the map can be displayed at.
     * <p>
     * The default maximum zoomn level is 22. The upper bound for this value is 25.5.
     *
     * @param maxZoom The new maximum zoom level.
     * @since 0.5.0
     */
    public LocationLayerOptions.Builder maxZoom(double maxZoom) {
      this.maxZoom = maxZoom;
      return this;
    }

    /**
     * Sets the minimum zoom level the map can be displayed at.
     *
     * @param minZoom The new minimum zoom level.
     * @since 0.5.0
     */
    public LocationLayerOptions.Builder minZoom(double minZoom) {
      this.minZoom = minZoom;
      return this;
    }

    /**
     * Sets the scale factor of the location icon when the map is zoomed in. Based on {@link #maxZoom()}.
     * Scaling is linear and the new pixel size of the image will be the original pixel size multiplied by the argument.
     * <p>
     * Set both this and {@link #minZoomIconScale(float)} to 1f to disable location icon scaling.
     * </p>
     *
     * @param maxZoomIconScale icon scale factor
     * @since 0.6.0
     */
    public LocationLayerOptions.Builder maxZoomIconScale(float maxZoomIconScale) {
      this.maxZoomIconScale = maxZoomIconScale;
      return this;
    }

    /**
     * Sets the scale factor of the location icon when the map is zoomed out. Based on {@link #maxZoom()}.
     * Scaling is linear and the new pixel size of the image will be the original pixel size multiplied by the argument.
     * <p>
     * Set both this and {@link #maxZoomIconScale(float)} to 1f to disable location icon scaling.
     * </p>
     *
     * @param minZoomIconScale icon scale factor
     * @since 0.6.0
     */
    public LocationLayerOptions.Builder minZoomIconScale(float minZoomIconScale) {
      this.minZoomIconScale = minZoomIconScale;
      return this;
    }

    /**
     * Sets minimum single pointer movement (map pan) in pixels required to break camera tracking.
     *
     * @param moveThreshold the minimum movement
     * @since 0.5.0
     */
    public LocationLayerOptions.Builder trackingInitialMoveThreshold(float moveThreshold) {
      this.trackingInitialMoveThreshold = moveThreshold;
      return this;
    }

    /**
     * Sets minimum multi pointer movement (map pan) in pixels required to break camera tracking
     * (for example during scale gesture).
     *
     * @param moveThreshold the minimum movement
     * @since 0.5.0
     */
    public LocationLayerOptions.Builder trackingMultiFingerMoveThreshold(float moveThreshold) {
      this.trackingMultiFingerMoveThreshold = moveThreshold;
      return this;
    }

    /**
     * Sets the layer id to set the location layer plugin below to.
     *
     * @param layerBelow the id to set the location layer plugin below to.
     * @since 0.8.0
     */
    public LocationLayerOptions.Builder layerBelow(String layerBelow) {
      this.layerBelow = layerBelow;
      return this;
    }

    LocationLayerOptions autoBuild() {
      String missing = "";
      if (this.accuracyAlpha == null) {
        missing += " accuracyAlpha";
      }
      if (this.accuracyColor == null) {
        missing += " accuracyColor";
      }
      if (this.backgroundDrawableStale == null) {
        missing += " backgroundDrawableStale";
      }
      if (this.foregroundDrawableStale == null) {
        missing += " foregroundDrawableStale";
      }
      if (this.gpsDrawable == null) {
        missing += " gpsDrawable";
      }
      if (this.foregroundDrawable == null) {
        missing += " foregroundDrawable";
      }
      if (this.backgroundDrawable == null) {
        missing += " backgroundDrawable";
      }
      if (this.bearingDrawable == null) {
        missing += " bearingDrawable";
      }
      if (this.elevation == null) {
        missing += " elevation";
      }
      if (this.enableStaleState == null) {
        missing += " enableStaleState";
      }
      if (this.staleStateTimeout == null) {
        missing += " staleStateTimeout";
      }
      if (this.padding == null) {
        missing += " padding";
      }
      if (this.maxZoom == null) {
        missing += " maxZoom";
      }
      if (this.minZoom == null) {
        missing += " minZoom";
      }
      if (this.maxZoomIconScale == null) {
        missing += " maxZoomIconScale";
      }
      if (this.minZoomIconScale == null) {
        missing += " minZoomIconScale";
      }
      if (this.trackingInitialMoveThreshold == null) {
        missing += " trackingInitialMoveThreshold";
      }
      if (this.trackingMultiFingerMoveThreshold == null) {
        missing += " trackingMultiFingerMoveThreshold";
      }
      if (!missing.isEmpty()) {
        throw new IllegalStateException("Missing required properties:" + missing);
      }
      return new LocationLayerOptions(
        this.accuracyAlpha,
        this.accuracyColor,
        this.backgroundDrawableStale,
        this.backgroundStaleName,
        this.foregroundDrawableStale,
        this.foregroundStaleName,
        this.gpsDrawable,
        this.gpsName,
        this.foregroundDrawable,
        this.foregroundName,
        this.backgroundDrawable,
        this.backgroundName,
        this.bearingDrawable,
        this.bearingName,
        this.bearingTintColor,
        this.foregroundTintColor,
        this.backgroundTintColor,
        this.foregroundStaleTintColor,
        this.backgroundStaleTintColor,
        this.elevation,
        this.enableStaleState,
        this.staleStateTimeout,
        this.padding,
        this.maxZoom,
        this.minZoom,
        this.maxZoomIconScale,
        this.minZoomIconScale,
        this.trackingInitialMoveThreshold,
        this.trackingMultiFingerMoveThreshold,
        this.layerBelow);
    }
  }
}
