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
import javax.ws.rs.CookieParam;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resource.RealmResourceProvider;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;

/**
 * @author <a href="mailto:sthorger@redhat.com">Stian Thorgersen</a>
 */
public class HelloResourceProvider implements RealmResourceProvider {

    private KeycloakSession session;
    public Logger log = Logger.getLogger(getClass().getName());

    public HelloResourceProvider(KeycloakSession session) {
        this.session = session;
    }

    @Override
    public Object getResource() {
        return this;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@CookieParam("KEYCLOAK_IDENTITY") Cookie cookie) {
        String name = session.getContext().getRealm().getDisplayName();
        if (name == null) {
            name = session.getContext().getRealm().getName();
        }

        String result = "";

        String[] parts = cookie.getValue().split("[.]");
        try {
            int index = 0;
            for (String part : parts) {
                if (index >= 2) {
                    break;
                }

                index++;
                byte[] partAsBytes = part.getBytes("UTF-8");
                String decodedPart = new String(java.util.Base64.getUrlDecoder().decode(partAsBytes), "UTF-8");

                result += decodedPart;
            }
        } catch (Exception e) {
            throw new RuntimeException("Couldnt decode jwt", e);
        }

        String[] resArr = result.split(",");
        for (int i = 0; i < resArr.length; i++) {
            log.log(Level.INFO, "resArr[{0}] => {1}", new Object[]{i, resArr[i]});
            if (resArr[i].contains("\"sub\":")) {
                String[] tempList = resArr[i].split(":");
                log.log(Level.INFO, "len => {0}", tempList.length);
                if (tempList.length > 2) {
                    int pos = resArr[i].indexOf(":");
                    log.log(Level.INFO, "pos => {0}", pos);
                    result = resArr[i].substring(pos+1).replaceAll("\"", "");
                }
            }
        }
        return result;
    }

    @Override
    public void close() {
    }

}
