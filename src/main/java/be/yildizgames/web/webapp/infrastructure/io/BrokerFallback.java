/*
 *
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
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.web.webapp.infrastructure.io;

import be.yildizgames.common.exception.implementation.ImplementationException;

import java.util.Properties;

@Deprecated
public class BrokerFallback {

    private final System.Logger logger = System.getLogger(BrokerFallback.class.getName());

    private final Properties properties;

    private BrokerFallback(Properties properties) {
        super();
        ImplementationException.throwForNull(properties);
        this.properties = properties;
    }

    public static BrokerFallback prepare(Properties properties) {
        return new BrokerFallback(properties);
    }

    public static BrokerFallback prepare() {
        return new BrokerFallback(new Properties());
    }

    public final void activate() {
        this.logger.log(System.Logger.Level.ERROR, "Cannot load the broker configuration file, fallback to temporary internal broker");
        this.properties.setProperty("broker.host", "localhost");
        this.properties.setProperty("broker.port", "7896");
        this.properties.setProperty("broker.data", "temp");
        this.properties.setProperty("broker.internal", "true");
    }
}
