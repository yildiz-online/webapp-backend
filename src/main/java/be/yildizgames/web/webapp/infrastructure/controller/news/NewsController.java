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

package be.yildizgames.web.webapp.infrastructure.controller.news;

import be.yildizgames.web.webapp.application.news.NewsProvider;
import be.yildizgames.web.webapp.domain.news.InvalidNewsException;
import be.yildizgames.web.webapp.domain.news.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Grégory Van den Borre
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class NewsController {

    private final NewsProvider newsProvider;

    @Autowired
    public NewsController(NewsProvider newsProvider) {
        this.newsProvider = newsProvider;
    }

    @GetMapping("/api/v1/news")
    public List<News> find() throws InvalidNewsException{
        return this.newsProvider.findLatest("en", 10);
    }

    @GetMapping("/api/v1/news/{language}")
    public List<News> find(@PathVariable final String language) throws InvalidNewsException{
        return this.newsProvider.findLatest(language, 10);
    }
}
