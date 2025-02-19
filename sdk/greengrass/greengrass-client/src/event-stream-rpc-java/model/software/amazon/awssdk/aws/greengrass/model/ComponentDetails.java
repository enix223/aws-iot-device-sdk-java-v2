/**
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0.
 *
 * This file is generated.
 */

package software.amazon.awssdk.aws.greengrass.model;

import com.google.gson.annotations.Expose;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import software.amazon.awssdk.eventstreamrpc.model.EventStreamJsonMessage;

public class ComponentDetails implements EventStreamJsonMessage {
  public static final String APPLICATION_MODEL_TYPE = "aws.greengrass#ComponentDetails";

  public static final ComponentDetails VOID;

  static {
    VOID = new ComponentDetails() {
      @Override
      public boolean isVoid() {
        return true;
      }
    };
  }

  @Expose(
      serialize = true,
      deserialize = true
  )
  private Optional<String> componentName;

  @Expose(
      serialize = true,
      deserialize = true
  )
  private Optional<String> version;

  @Expose(
      serialize = true,
      deserialize = true
  )
  private Optional<String> state;

  @Expose(
      serialize = true,
      deserialize = true
  )
  private Optional<Map<String, Object>> configuration;

  public ComponentDetails() {
    this.componentName = Optional.empty();
    this.version = Optional.empty();
    this.state = Optional.empty();
    this.configuration = Optional.empty();
  }

  public String getComponentName() {
    if (componentName.isPresent()) {
      return componentName.get();
    }
    return null;
  }

  public void setComponentName(final String componentName) {
    this.componentName = Optional.ofNullable(componentName);
  }

  public ComponentDetails withComponentName(final String componentName) {
    setComponentName(componentName);
    return this;
  }

  public String getVersion() {
    if (version.isPresent()) {
      return version.get();
    }
    return null;
  }

  public void setVersion(final String version) {
    this.version = Optional.ofNullable(version);
  }

  public ComponentDetails withVersion(final String version) {
    setVersion(version);
    return this;
  }

  public LifecycleState getState() {
    if (state.isPresent()) {
      return LifecycleState.get(state.get());
    }
    return null;
  }

  public String getStateAsString() {
    if (state.isPresent()) {
      return state.get();
    }
    return null;
  }

  public void setState(final String state) {
    this.state = Optional.ofNullable(state);
  }

  public ComponentDetails withState(final String state) {
    setState(state);
    return this;
  }

  public void setState(final LifecycleState state) {
    this.state = Optional.ofNullable(state.getValue());
  }

  public ComponentDetails withState(final LifecycleState state) {
    setState(state);
    return this;
  }

  public Map<String, Object> getConfiguration() {
    if (configuration.isPresent()) {
      return configuration.get();
    }
    return null;
  }

  public void setConfiguration(final Map<String, Object> configuration) {
    this.configuration = Optional.ofNullable(configuration);
  }

  public ComponentDetails withConfiguration(final Map<String, Object> configuration) {
    setConfiguration(configuration);
    return this;
  }

  @Override
  public String getApplicationModelType() {
    return APPLICATION_MODEL_TYPE;
  }

  @Override
  public boolean equals(Object rhs) {
    if (rhs == null) return false;
    if (!(rhs instanceof ComponentDetails)) return false;
    if (this == rhs) return true;
    final ComponentDetails other = (ComponentDetails)rhs;
    boolean isEquals = true;
    isEquals = isEquals && this.componentName.equals(other.componentName);
    isEquals = isEquals && this.version.equals(other.version);
    isEquals = isEquals && this.state.equals(other.state);
    isEquals = isEquals && this.configuration.equals(other.configuration);
    return isEquals;
  }

  @Override
  public int hashCode() {
    return Objects.hash(componentName, version, state, configuration);
  }
}
