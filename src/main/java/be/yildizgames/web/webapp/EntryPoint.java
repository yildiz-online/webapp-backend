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

package be.yildizgames.web.webapp;

import be.yildizgames.common.configuration.LoggerPropertiesConfiguration;
import be.yildizgames.common.logging.LogEngine;
import be.yildizgames.common.logging.LogEngineProvider;
import be.yildizgames.common.logging.LoggerConfiguration;
import be.yildizgames.module.database.DataBaseConnectionProvider;
import be.yildizgames.module.database.DatabaseConnectionProviderFactory;
import be.yildizgames.module.database.DatabaseUpdater;
import be.yildizgames.module.database.DbPropertiesStandard;
import be.yildizgames.module.database.LiquibaseDatabaseUpdater;
import be.yildizgames.module.database.postgresql.PostgresqlSystem;
import be.yildizgames.module.messaging.Broker;
import be.yildizgames.module.messaging.BrokerPropertiesStandard;
import be.yildizgames.module.messaging.BrokerProvider;
import be.yildizgames.module.messaging.activemq.ActivemqBrokerProvider;
import be.yildizgames.web.webapp.infrastructure.io.BrokerFallback;
import be.yildizgames.web.webapp.infrastructure.persistence.DatabaseFallback;
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

    @Value("${config:/config/config.properties}")
    private String configFile;

    @Bean
    public DataBaseConnectionProvider connectionProvider() throws IOException, SQLException {
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(this.configFile)){
            p.load(fis);
            PostgresqlSystem.support();
        } catch (FileNotFoundException e) {
            DatabaseFallback databaseFallback = DatabaseFallback.prepare(p);
            databaseFallback.activate("webapp");
        }
        DataBaseConnectionProvider provider =  DatabaseConnectionProviderFactory.getInstance()
                .create(DbPropertiesStandard.fromProperties(p));
        DatabaseUpdater databaseUpdater = LiquibaseDatabaseUpdater.fromConfigurationPath("web-database-update.xml");
        databaseUpdater.update(provider);
        return provider;
    }

    @Bean
    public Broker broker() throws IOException {
        BrokerProvider provider = new ActivemqBrokerProvider();
        final Properties p = new Properties();
        try(FileInputStream fis = new FileInputStream(this.configFile)) {
            p.load(fis);
        } catch (FileNotFoundException e) {
            BrokerFallback fallback = BrokerFallback.prepare(p);
            fallback.activate();
        }
        return provider.initialize(BrokerPropertiesStandard.fromProperties(p));
    }

    public static void main(String[] args) throws IOException {
        LogEngine engine = LogEngineProvider.getLoggerProvider().getLogEngine();
        Properties p = new Properties();
        try (FileInputStream fis = new FileInputStream(args[0])){
            p.load(fis);
            LoggerConfiguration configuration = LoggerPropertiesConfiguration.fromProperties(p);
            engine.configureFromProperties(configuration);
        }
        SpringApplication.run(EntryPoint.class, args);
    }
}
