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

package be.yildizgames.web.webapp.infrastructure.persistence;

import be.yildizgames.common.logging.LogFactory;
import be.yildizgames.module.database.DataBaseConnectionProvider;
import be.yildizgames.module.database.QueryBuilder;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
abstract class AbstractPersistence <T> {

    private static final Logger LOGGER = LogFactory.getInstance().getLogger(AbstractPersistence.class);

    private final DataBaseConnectionProvider provider;

    AbstractPersistence(DataBaseConnectionProvider provider) {
        super();
        this.provider = provider;
    }

    final Optional<T> fromSQL(String sql, String param) {
        try(Connection c = this.provider.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = this.getResultSet(stmt, param)) {
            if(rs.first()) {
                T a = fromRS(rs);
                return Optional.of(a);
            }
            return Optional.empty();
        } catch (SQLException e) {
            LOGGER.error("Persistence error", e);
            throw new PersistenceException(e);
        }
    }

    private ResultSet getResultSet(PreparedStatement stmt, String param) throws SQLException {
        stmt.setString(1, param);
        return stmt.executeQuery();
    }

    final List<T> listFromSQL(String sql) {
        try (Connection c = this.provider.getConnection();
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
                List<T> result = new ArrayList<>();
                while(rs.next()) {
                    result.add(this.fromRS(rs));
                }
                return result;
        } catch (SQLException e) {
            LOGGER.error("Persistence error" ,e);
            throw new PersistenceException(e);
        }
    }

    protected QueryBuilder getBuilder() {
        return this.provider.getBuilder();
    }

    protected abstract T fromRS(ResultSet rs) throws SQLException;
}
