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
import java.util.Objects;
import java.util.Optional;
import software.amazon.awssdk.eventstreamrpc.model.EventStreamJsonMessage;

public class GetSecretValueRequest implements EventStreamJsonMessage {
  public static final String APPLICATION_MODEL_TYPE = "aws.greengrass#GetSecretValueRequest";

  public static final GetSecretValueRequest VOID;

  static {
    VOID = new GetSecretValueRequest() {
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
  private Optional<String> secretId;

  @Expose(
      serialize = true,
      deserialize = true
  )
  private Optional<String> versionId;

  @Expose(
      serialize = true,
      deserialize = true
  )
  private Optional<String> versionStage;

  public GetSecretValueRequest() {
    this.secretId = Optional.empty();
    this.versionId = Optional.empty();
    this.versionStage = Optional.empty();
  }

  public String getSecretId() {
    if (secretId.isPresent()) {
      return secretId.get();
    }
    return null;
  }

  public void setSecretId(final String secretId) {
    this.secretId = Optional.ofNullable(secretId);
  }

  public GetSecretValueRequest withSecretId(final String secretId) {
    setSecretId(secretId);
    return this;
  }

  public String getVersionId() {
    if (versionId.isPresent()) {
      return versionId.get();
    }
    return null;
  }

  public void setVersionId(final String versionId) {
    this.versionId = Optional.ofNullable(versionId);
  }

  public GetSecretValueRequest withVersionId(final String versionId) {
    setVersionId(versionId);
    return this;
  }

  public String getVersionStage() {
    if (versionStage.isPresent()) {
      return versionStage.get();
    }
    return null;
  }

  public void setVersionStage(final String versionStage) {
    this.versionStage = Optional.ofNullable(versionStage);
  }

  public GetSecretValueRequest withVersionStage(final String versionStage) {
    setVersionStage(versionStage);
    return this;
  }

  @Override
  public String getApplicationModelType() {
    return APPLICATION_MODEL_TYPE;
  }

  @Override
  public boolean equals(Object rhs) {
    if (rhs == null) return false;
    if (!(rhs instanceof GetSecretValueRequest)) return false;
    if (this == rhs) return true;
    final GetSecretValueRequest other = (GetSecretValueRequest)rhs;
    boolean isEquals = true;
    isEquals = isEquals && this.secretId.equals(other.secretId);
    isEquals = isEquals && this.versionId.equals(other.versionId);
    isEquals = isEquals && this.versionStage.equals(other.versionStage);
    return isEquals;
  }

  @Override
  public int hashCode() {
    return Objects.hash(secretId, versionId, versionStage);
  }
}
