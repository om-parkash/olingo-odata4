/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.olingo.client.core.communication.request.cud;

import java.net.URI;
import org.apache.olingo.client.api.CommonODataClient;
import org.apache.olingo.client.api.communication.request.cud.CommonCUDRequestFactory;
import org.apache.olingo.client.api.communication.request.cud.ODataDeleteRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityCreateRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityUpdateRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataLinkCreateRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataLinkUpdateRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataPropertyUpdateRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataValueUpdateRequest;
import org.apache.olingo.client.api.communication.request.cud.UpdateType;
import org.apache.olingo.commons.api.domain.CommonODataEntity;
import org.apache.olingo.commons.api.domain.ODataLink;
import org.apache.olingo.commons.api.domain.ODataPrimitiveValue;
import org.apache.olingo.commons.api.domain.CommonODataProperty;
import org.apache.olingo.client.api.http.HttpMethod;

public abstract class AbstractCUDRequestFactory implements CommonCUDRequestFactory {

  private static final long serialVersionUID = -2723641791198745990L;

  protected final CommonODataClient client;

  protected AbstractCUDRequestFactory(final CommonODataClient client) {
    this.client = client;
  }

  @Override
  public ODataEntityCreateRequest getEntityCreateRequest(final URI targetURI, final CommonODataEntity entity) {
    return new ODataEntityCreateRequestImpl(client, targetURI, entity);
  }

  @Override
  public ODataEntityUpdateRequest getEntityUpdateRequest(
          final URI targetURI, final UpdateType type, final CommonODataEntity changes) {

    final ODataEntityUpdateRequest req;

    if (client.getConfiguration().isUseXHTTPMethod()) {
      req = new ODataEntityUpdateRequestImpl(client, HttpMethod.POST, targetURI, changes);
      req.setXHTTPMethod(type.getMethod().name());
    } else {
      req = new ODataEntityUpdateRequestImpl(client, type.getMethod(), targetURI, changes);
    }

    return req;
  }

  @Override
  public ODataEntityUpdateRequest getEntityUpdateRequest(final UpdateType type, final CommonODataEntity entity) {
    if (entity.getEditLink() == null) {
      throw new IllegalArgumentException("No edit link found");
    }

    final ODataEntityUpdateRequest req;

    if (client.getConfiguration().isUseXHTTPMethod()) {
      req = new ODataEntityUpdateRequestImpl(client, HttpMethod.POST, entity.getEditLink(), entity);
      req.setXHTTPMethod(type.getMethod().name());
    } else {
      req = new ODataEntityUpdateRequestImpl(client, type.getMethod(), entity.getEditLink(), entity);
    }

    return req;
  }

  @Override
  public ODataValueUpdateRequest getValueUpdateRequest(
          final URI targetURI, final UpdateType type, final ODataPrimitiveValue value) {

    final ODataValueUpdateRequest req;

    if (client.getConfiguration().isUseXHTTPMethod()) {
      req = new ODataValueUpdateRequestImpl(client, HttpMethod.POST, targetURI, value);
      req.setXHTTPMethod(type.getMethod().name());
    } else {
      req = new ODataValueUpdateRequestImpl(client, type.getMethod(), targetURI, value);
    }

    return req;
  }

  @Override
  public ODataPropertyUpdateRequest getPropertyPrimitiveValueUpdateRequest(
          final URI targetURI, final CommonODataProperty property) {

    if (!property.hasPrimitiveValue()) {
      throw new IllegalArgumentException("A primitive value is required");
    }

    final ODataPropertyUpdateRequest req;

    if (client.getConfiguration().isUseXHTTPMethod()) {
      req = new ODataPropertyUpdateRequestImpl(client, HttpMethod.POST, targetURI, property);
      req.setXHTTPMethod(HttpMethod.PUT.name());
    } else {
      req = new ODataPropertyUpdateRequestImpl(client, HttpMethod.PUT, targetURI, property);
    }

    return req;
  }

  @Override
  public ODataPropertyUpdateRequest getPropertyComplexValueUpdateRequest(
          final URI targetURI, final UpdateType type, final CommonODataProperty property) {

    if (!property.hasComplexValue()) {
      throw new IllegalArgumentException("A complex value is required");
    }

    final ODataPropertyUpdateRequest req;

    if (client.getConfiguration().isUseXHTTPMethod()) {
      req = new ODataPropertyUpdateRequestImpl(client, HttpMethod.POST, targetURI, property);
      req.setXHTTPMethod(type.getMethod().name());
    } else {
      req = new ODataPropertyUpdateRequestImpl(client, type.getMethod(), targetURI, property);
    }

    return req;
  }

  @Override
  public ODataPropertyUpdateRequest getPropertyCollectionValueUpdateRequest(
          final URI targetURI, final CommonODataProperty property) {

    if (!property.hasCollectionValue()) {
      throw new IllegalArgumentException("A collection value is required");
    }

    final ODataPropertyUpdateRequest req;

    if (client.getConfiguration().isUseXHTTPMethod()) {
      req = new ODataPropertyUpdateRequestImpl(client, HttpMethod.POST, targetURI, property);
      req.setXHTTPMethod(HttpMethod.PUT.name());
    } else {
      req = new ODataPropertyUpdateRequestImpl(client, HttpMethod.PUT, targetURI, property);
    }

    return req;
  }

  @Override
  public ODataLinkCreateRequest getLinkCreateRequest(final URI targetURI, final ODataLink link) {
    return new ODataLinkCreateRequestImpl(client, targetURI, link);
  }

  @Override
  public ODataLinkUpdateRequest getLinkUpdateRequest(
          final URI targetURI, final UpdateType type, final ODataLink link) {

    final ODataLinkUpdateRequest req;

    if (client.getConfiguration().isUseXHTTPMethod()) {
      req = new ODataLinkUpdateRequestImpl(client, HttpMethod.POST, targetURI, link);
      req.setXHTTPMethod(type.getMethod().name());
    } else {
      req = new ODataLinkUpdateRequestImpl(client, type.getMethod(), targetURI, link);
    }

    return req;
  }

  @Override
  public ODataDeleteRequest getDeleteRequest(final URI targetURI) {
    final ODataDeleteRequest req;

    if (client.getConfiguration().isUseXHTTPMethod()) {
      req = new ODataDeleteRequestImpl(client, HttpMethod.POST, targetURI);
      req.setXHTTPMethod(HttpMethod.DELETE.name());
    } else {
      req = new ODataDeleteRequestImpl(client, HttpMethod.DELETE, targetURI);
    }

    return req;
  }
}