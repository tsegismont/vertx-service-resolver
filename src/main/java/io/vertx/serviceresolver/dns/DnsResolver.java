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
package io.vertx.serviceresolver.dns;

import io.vertx.serviceresolver.ServiceResolver;
import io.vertx.serviceresolver.dns.impl.DnsResolverImpl;
import io.vertx.serviceresolver.impl.ServiceResolverImpl;

public interface DnsResolver {

  static ServiceResolver create(DnsResolverOptions options) {
    return new ServiceResolverImpl((vertx, lookup) -> new DnsResolverImpl(vertx, options, lookup.loadBalancer));
  }
}
