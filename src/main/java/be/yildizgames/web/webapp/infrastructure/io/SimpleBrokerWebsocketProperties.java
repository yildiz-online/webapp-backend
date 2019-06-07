/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildizgames.web.webapp.infrastructure.io;


import be.yildizgames.common.configuration.PropertiesHelper;

import java.util.Properties;

/**
 * Contains all the properties for websocket broker connexion.
 * Immutable class.
 * @author Grégory Van den Borre
 */
public class SimpleBrokerWebsocketProperties {

    private final String relay;

    private final String host;

    private final String login;

    private final String password;

    private final String endPoint;

    /**
     * Build a broker websocket properties from a property object.
     * Required properties are:
     * <ul>
     *     <li>broker.relay</li>
     *     <li>broker.host</li>
     *     <li>broker.login</li>
     *     <li>broker.password</li>
     *     <li>websocket.endpoint</li>
     * </ul>
     * @param properties Property object to extract values from.
     */
    public SimpleBrokerWebsocketProperties(final Properties properties) {
        super();
        this.relay = PropertiesHelper.getValue(properties, "broker.relay");
        this.host = PropertiesHelper.getValue(properties, "broker.host");
        this.login = PropertiesHelper.getValue(properties, "broker.login");
        this.password = PropertiesHelper.getValue(properties, "broker.password");
        this.endPoint = PropertiesHelper.getValue(properties, "websocket.endpoint");
    }

    public final String getRelay() {
        return this.relay;
    }

    public final String getBrokerHost() {
        return this.host;
    }

    public final String getBrokerLogin() {
        return this.login;
    }

    public final String getBrokerPassword() {
        return this.password;
    }

    public final String getWebsocketEndpoint() {
        return this.endPoint;
    }
}
