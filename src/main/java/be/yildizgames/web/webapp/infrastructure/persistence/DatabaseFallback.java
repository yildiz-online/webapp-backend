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

package be.yildizgames.web.webapp.infrastructure.persistence;

import be.yildizgames.module.database.derby.DerbySystem;

import java.util.Objects;
import java.util.Properties;

/**
 * @author Grégory Van den Borre
 */
@Deprecated
public class DatabaseFallback {

    private final System.Logger logger = System.getLogger(DatabaseFallback.class.getName());

    private final Properties properties;

    private DatabaseFallback(Properties properties) {
        super();
        Objects.requireNonNull(properties);
        this.properties = properties;
    }

    public static DatabaseFallback prepare(Properties properties) {
        return new DatabaseFallback(properties);
    }

    public static DatabaseFallback prepare() {
        return new DatabaseFallback(new Properties());
    }

    public final void activate(String databaseName) {
        this.logger.log(System.Logger.Level.ERROR,"Cannot load the database configuration file, fallback to temporary internal DB");
        this.properties.setProperty("database.user", "sa");
        this.properties.setProperty("database.password", "");
        this.properties.setProperty("database.root.user", "sa");
        this.properties.setProperty("database.root.password", "");
        this.properties.setProperty("database.host", "localhost");
        this.properties.setProperty("database.port", "1527");
        this.properties.setProperty("database.system", "derby-file");
        this.properties.setProperty("database.name", databaseName);
        DerbySystem.support();
    }
}
