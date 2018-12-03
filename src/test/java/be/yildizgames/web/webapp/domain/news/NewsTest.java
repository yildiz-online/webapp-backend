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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
class NewsTest {

    private static final Integer NEWSID_OK = 1;

    private static final String TITLE_OK = "aTitle";

    private static final String CONTENT_OK = "aContent";

    private static final Integer TAGID_OK = 1;

    private static final String IMAGE_OK = "1.jpg";

    private static final Author AUTHOR_OK = new Author("name");

    @Nested
    class Constructor {

        @Test
        void happyFlow() throws InvalidNewsException {
            News news = new News(NEWSID_OK, TITLE_OK, CONTENT_OK, TAGID_OK, IMAGE_OK, AUTHOR_OK);
            assertEquals(NEWSID_OK, news.getNewsId());
            assertEquals(TITLE_OK, news.getTitle());
            assertEquals(CONTENT_OK, news.getContent());
            assertEquals(TAGID_OK, news.getTagId());
            assertEquals(IMAGE_OK, news.getImage());
            assertEquals(AUTHOR_OK, news.getAuthor());
        }

        @Test
        void withTitleNull() {
            assertThrows(InvalidNewsException.class, () -> new News(NEWSID_OK, null, CONTENT_OK, TAGID_OK, IMAGE_OK, AUTHOR_OK));
        }

        @Test
        void withContentNull() {
            assertThrows(InvalidNewsException.class, () -> new News(NEWSID_OK, TITLE_OK, null, TAGID_OK, IMAGE_OK, AUTHOR_OK));
        }

        @Test
        void withContentTooShort() {
            assertThrows(InvalidNewsException.class, () -> new News(NEWSID_OK, TITLE_OK, "", TAGID_OK, IMAGE_OK, AUTHOR_OK));
        }

        @Test
        void withContentTooLong() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= News.CONTENT_MAX; i++) {
                sb.append('a');
            }

            assertThrows(InvalidNewsException.class, () -> new News(NEWSID_OK, TITLE_OK, sb.toString(), TAGID_OK, IMAGE_OK, AUTHOR_OK));
        }

        @Test
        void withNullImage() {
            assertThrows(InvalidNewsException.class, () -> new News(NEWSID_OK, TITLE_OK, CONTENT_OK, TAGID_OK, null, AUTHOR_OK));
        }

        @Test
        void withNullAuthor() {
            assertThrows(InvalidNewsException.class, () -> new News(NEWSID_OK, TITLE_OK, CONTENT_OK, TAGID_OK, IMAGE_OK, null));
        }
    }

    @Nested
    class UpdateTitle {

        @Test
        void happyFlow() throws InvalidNewsException {
            News news = new News(NEWSID_OK, TITLE_OK, CONTENT_OK, TAGID_OK, IMAGE_OK, AUTHOR_OK);
            news = news.updateTitle(TITLE_OK + "a");
            assertEquals(TITLE_OK + "a", news.getTitle());
        }
    }
}
