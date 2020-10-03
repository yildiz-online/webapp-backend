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

package be.yildizgames.web.webapp.infrastructure.persistence;

import be.yildizgames.module.database.DataBaseConnectionProvider;
import be.yildizgames.module.database.QueryBuilder;
import be.yildizgames.web.webapp.application.news.NewsProvider;
import be.yildizgames.web.webapp.domain.news.Author;
import be.yildizgames.web.webapp.domain.news.InvalidNewsException;
import be.yildizgames.web.webapp.domain.news.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Persists the calls to the News class
 *
 * @author Oleksandr Gryniuk
 *
 */
@Repository
public class NewsPersistence extends AbstractPersistence<News> implements NewsProvider {

    private static final System.Logger LOGGER = System.getLogger(NewsPersistence.class.getName());

    private Map<String, String> tableByLanguage = new HashMap<>();

    @Autowired
    public NewsPersistence(DataBaseConnectionProvider provider) {
        super(provider);
        this.registerLanguage("fr", "NEWS_FR");
        this.registerLanguage("en", "NEWS_EN");
    }

    @Override
    public List<News> findLatest(String language, int newsNumber) {
        QueryBuilder builder = this.getBuilder(this.tableByLanguage.get(language));
        String sql = builder
                .selectAllFrom(this.tableByLanguage.get(language))
                .append("NATURAL JOIN NEWS N NATURAL JOIN AUTHOR ORDER BY N.DATE DESC")
                .limit(10)
                .build();
        return listFromSQL(sql);
    }

    @Override
    protected News fromRS(ResultSet rs) throws SQLException {
        try {
            return new News(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    0,
                    rs.getString("image"),
                    new Author(rs.getString("name")),
                    rs.getTimestamp("date").getTime());
        } catch (InvalidNewsException e) {
            LOGGER.log(System.Logger.Level.ERROR, "Invalid News", e);
            return null;
        }
    }

    private void registerLanguage(String language, String tableName) {
        this.tableByLanguage.put(language, tableName);
    }
}