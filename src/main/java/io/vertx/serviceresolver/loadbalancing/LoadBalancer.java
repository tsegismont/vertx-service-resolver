/*
 * Copyright (c) 2011-2023 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package io.vertx.serviceresolver.loadbalancing;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public interface LoadBalancer {

  LoadBalancer LEAST_REQUESTS = () -> new EndpointSelector() {
    @Override
    public <E extends Endpoint> E selectEndpoint(List<E> endpoints) {
      E selected = null;
      int numberOfRequests = Integer.MAX_VALUE;
      for (E endpoint : endpoints) {
        int val = endpoint.numberOfInflightRequests();
        if (val < numberOfRequests) {
          numberOfRequests = val;
          selected = endpoint;
        }
      }
      return selected;
    }
  };

  LoadBalancer ROUND_ROBIN = () -> {
    AtomicInteger idx = new AtomicInteger();
    return new EndpointSelector() {
      @Override
      public <E extends Endpoint> E selectEndpoint(List<E> endpoints) {
        int next = idx.getAndIncrement();
        return endpoints.get(next % endpoints.size());
      }
    };
  };

  EndpointSelector selector();

}
