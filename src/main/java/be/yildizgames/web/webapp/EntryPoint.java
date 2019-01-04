/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2018 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
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

package be.yildizgames.web.webapp;

import be.yildizgames.module.database.DataBaseConnectionProvider;
import be.yildizgames.module.database.DatabaseConnectionProviderFactory;
import be.yildizgames.module.database.DatabaseUpdater;
import be.yildizgames.module.database.LiquibaseDatabaseUpdater;
import be.yildizgames.module.database.SimpleDbProperties;
import be.yildizgames.module.database.derby.DerbySystem;
import be.yildizgames.module.database.postgresql.PostgresqlSystem;
import be.yildizgames.module.messaging.Broker;
import be.yildizgames.module.messaging.BrokerProvider;
import be.yildizgames.module.messaging.StandardBrokerProperties;
import be.yildizgames.module.messaging.activemq.ActivemqBrokerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Grégory Van den Borre
 */
@SpringBootApplication
@ComponentScan("be.yildizgames.web.webapp.infrastructure.*")
public class EntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(EntryPoint.class);

    @Value("${dbconfig:/yildiz/db.properties}")
    private String databaseConfigFile;

    @Value("${brokerconfig:/yildiz/broker.properties}")
    private String brokerConfigFile;

    @Bean
    public DataBaseConnectionProvider connectionProvider() throws IOException, SQLException {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(this.databaseConfigFile)){
            p.load(fis);
            PostgresqlSystem.support();
        } catch (FileNotFoundException e) {
            LOGGER.error("Cannot load the database configuration file {}, fallback to temporary internal DB",  this.databaseConfigFile);
            p.setProperty("database.user", "sa");
            p.setProperty("database.password", "");
            p.setProperty("database.root.user", "sa");
            p.setProperty("database.root.password", "");
            p.setProperty("database.host", "localhost");
            p.setProperty("database.port", "1527");
            p.setProperty("database.system", "derby-file");
            p.setProperty("database.name", "webapp");
            DerbySystem.support();
        }
        DataBaseConnectionProvider provider =  DatabaseConnectionProviderFactory.getInstance()
                .create(new SimpleDbProperties(p));
        DatabaseUpdater databaseUpdater = LiquibaseDatabaseUpdater.fromConfigurationPath("web-database-update.xml");
        databaseUpdater.update(provider);
        return provider;
    }

    @Bean
    public Broker broker() throws IOException {
        BrokerProvider provider = new ActivemqBrokerProvider();
        Properties p = new Properties();
        try(FileInputStream fis = new FileInputStream(this.brokerConfigFile)) {
            p.load(fis);
        } catch (FileNotFoundException e) {
            LOGGER.error("Cannot load the broker configuration file {}, fallback to temporary internal broker", this.brokerConfigFile);
            p = manualProperties();
        }
        return provider.initialize(StandardBrokerProperties.fromProperties(p));
    }

    /**
     * @deprecated have a StandardBrokerProperties.manual(args) to do this
     * @return The properties
     */
    @Deprecated(since = "2.0.0", forRemoval = true)
    private Properties manualProperties() {
        Properties p = new Properties();
        p.setProperty("broker.host", "localhost");
        p.setProperty("broker.port", "7896");
        p.setProperty("broker.data", "temp");
        p.setProperty("broker.internal", "true");
        return p;
    }

    public static void main(String[] args) {
        SpringApplication.run(EntryPoint.class, args);
    }
}
