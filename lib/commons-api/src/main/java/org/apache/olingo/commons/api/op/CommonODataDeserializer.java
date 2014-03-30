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
package org.apache.olingo.commons.api.op;

import java.io.InputStream;
import java.io.Serializable;
import org.apache.olingo.commons.api.data.Container;
import org.apache.olingo.commons.api.data.Entry;
import org.apache.olingo.commons.api.domain.ODataError;
import org.apache.olingo.commons.api.data.Feed;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.format.ODataFormat;
import org.apache.olingo.commons.api.format.ODataPubFormat;

/**
 * Interface for serialization.
 */
public interface CommonODataDeserializer extends Serializable {

  /**
   * Gets a feed object from the given InputStream.
   *
   * @param input stream to be de-serialized.
   * @param format Atom or JSON
   * @return Feed instance.
   */
  Container<Feed> toFeed(InputStream input, ODataPubFormat format);

  /**
   * Gets an entry object from the given InputStream.
   *
   * @param input stream to be de-serialized.
   * @param format Atom or JSON
   * @return Entry instance.
   */
  Container<Entry> toEntry(InputStream input, ODataPubFormat format);

  /**
   * Gets a property object from the given InputStream.
   *
   * @param input stream to be de-serialized.
   * @param format XML or JSON
   * @return Property instance.
   */
  Container<Property> toProperty(InputStream input, ODataFormat format);

  /**
   * Gets the ODataError object represented by the given InputStream.
   *
   * @param input stream to be parsed and de-serialized.
   * @param isXML 'TRUE' if the error is represented by XML; 'FALSE' otherwise.
   * @return
   */
  ODataError toError(InputStream input, boolean isXML);
}