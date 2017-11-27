/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.RealmResourceProvider;
import org.keycloak.services.resource.RealmResourceProviderFactory;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class userIDResourceProviderFactory implements RealmResourceProviderFactory {

    public static final String ID = "getLastUserId";
    public Logger log = Logger.getLogger(getClass().getName());

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public RealmResourceProvider create(KeycloakSession session) {
        return new userIDResourceProvider(session);
    }

    @Override
    public void init(Scope config) {
        log.log(Level.INFO, "init => {0}", config);
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        log.log(Level.INFO, "postInit => {0}", factory);
    }

    @Override
    public void close() {
        log.info("close()");
    }

}
