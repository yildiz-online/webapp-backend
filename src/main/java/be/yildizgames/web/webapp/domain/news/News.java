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
package be.yildizgames.web.webapp.domain.news;

import java.time.Instant;

/**
 * Implements a Domain model for News in the module 'webapp-backend'
 *
 * @author Grégory Van den Borre
 *
 */
public class News implements NewsDto {

    private final Integer newsId;

    private final String title;

    private final String content;

    private final Integer tagId;

    private final String image;

    public static final int CONTENT_MIN = 1;

    public static final int CONTENT_MAX = 255;

    public static final int TAG_ID_MIN = 1;

    public static final int TAG_ID_MAX = 20;

    private static final String MIN_VALID_CONTENT_MESSAGE = "Content must be at least " + CONTENT_MIN + " chars.";

    private static final String MAX_VALID_CONTENT_MESSAGE = "Content cannot have more than " + CONTENT_MAX + " chars.";

    private static final String TAG_ID_MIN_VALID = "Tag ID must be at least " + TAG_ID_MIN + " chars.";

    private static final String TAG_ID_MAX_VALID = "Tag ID cannot have more than " + TAG_ID_MAX + " chars.";

    private final long creationDate;

    private final Author author;

    public News(Integer newsId, String title, String content, Integer tagId, String image, Author author)
            throws InvalidNewsException {
        this(newsId, title, content, tagId, image, author, Instant.now().toEpochMilli());
    }

    public News(Integer newsId, String title, String content, Integer tagId, String image, Author author,
                 long creationDate) throws InvalidNewsException {
        super();

        if (newsId == null) {
            throw new InvalidNewsException("The News ID is null");
        }

        if (title == null) {
            throw new InvalidNewsException("Title is null");
        }

        if (content == null) {
            throw new InvalidNewsException("Content is null");
        }

        if (content.length() < CONTENT_MIN) {
            throw new InvalidNewsException(MIN_VALID_CONTENT_MESSAGE);
        } else if (content.length() > CONTENT_MAX) {
            throw new InvalidNewsException(MAX_VALID_CONTENT_MESSAGE);
        }
        if (tagId.toString().length() < TAG_ID_MIN) {
            throw new InvalidNewsException(TAG_ID_MIN_VALID);
        } else if (tagId.toString().length() > TAG_ID_MAX) {
            throw new InvalidNewsException(TAG_ID_MAX_VALID);
        }

        if (image == null) {
            throw new InvalidNewsException("Image is null");
        }

        if (author == null) {
            throw new InvalidNewsException("Author is null");
        }

        this.newsId = newsId;
        this.title = title;
        this.content = content;
        this.tagId = tagId;
        this.image = image;
        this.creationDate = creationDate;
        this.author = author;
    }

    public News updateTitle(String newTitle) throws InvalidNewsException {
        return new News(this.newsId, newTitle, this.content, this.tagId, this.image, this.author);
    }

    public News updateContent(String newContent) throws InvalidNewsException {
        return new News(this.newsId, this.title, newContent, this.tagId, this.image, this.author);
    }

    public News updatePicture(String newImage) throws InvalidNewsException {
        return new News(this.newsId, this.title, this.content, this.tagId, newImage, this.author);
    }

    @Override
    public Integer getNewsId() {
        return this.newsId;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getContent() { return content; }

    @Override
    public Integer getTagId() {
        return this.tagId;
    }

    @Override
    public String getImage() {
        return this.image;
    }

    @Override
    public Author getAuthor() {
        return this.author;
    }

    public long getCreationDate() {
        return this.creationDate;
    }
}
