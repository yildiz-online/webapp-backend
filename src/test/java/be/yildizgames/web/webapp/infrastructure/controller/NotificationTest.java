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
package be.yildizgames.web.webapp.infrastructure.controller;

import be.yildizgames.common.exception.implementation.ImplementationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class NotificationTest {

    private static final String TITLE_OK = "titleOk";

    private static final String CONTENT_OK = "contentOk";

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            Notification notification = new Notification(TITLE_OK, CONTENT_OK, "smtg");
            Assertions.assertEquals(TITLE_OK, notification.getTitle());
            Assertions.assertEquals(CONTENT_OK, notification.getContent());
            Assertions.assertEquals("smtg", notification.getType());
        }

        @Test
        void nullTitle() {
            Assertions.assertThrows(ImplementationException.class, () -> new Notification(null, CONTENT_OK, "smtg"));
        }

        @Test
        void nullContent() {
            Assertions.assertThrows(ImplementationException.class, () -> new Notification(TITLE_OK, null, "smtg"));
        }

        @Test
        void nullType() {
            Assertions.assertThrows(ImplementationException.class, () -> new Notification(TITLE_OK, CONTENT_OK, null));
        }

    }

    @Nested
    class Success {

        @Test
        void happyFlow() {
            Notification notification = Notification.success(TITLE_OK, CONTENT_OK);
            Assertions.assertEquals(TITLE_OK, notification.getTitle());
            Assertions.assertEquals(CONTENT_OK, notification.getContent());
            Assertions.assertEquals("success", notification.getType());
        }

        @Test
        void nullTitle() {
            Assertions.assertThrows(ImplementationException.class, () -> Notification.success(null, CONTENT_OK));
        }

        @Test
        void nullContent() {
            Assertions.assertThrows(ImplementationException.class, () -> Notification.success(TITLE_OK, null));
        }

    }

    @Nested
    class Warning {

        @Test
        void happyFlow() {
            Notification notification = Notification.warning(TITLE_OK, CONTENT_OK);
            Assertions.assertEquals(TITLE_OK, notification.getTitle());
            Assertions.assertEquals(CONTENT_OK, notification.getContent());
            Assertions.assertEquals("warning", notification.getType());
        }

        @Test
        void nullTitle() {
            Assertions.assertThrows(ImplementationException.class, () -> Notification.warning(null, CONTENT_OK));
        }

        @Test
        void nullContent() {
            Assertions.assertThrows(ImplementationException.class, () -> Notification.warning(TITLE_OK, null));
        }

    }

    @Nested
    class Error {

        @Test
        void happyFlow() {
            Notification notification = Notification.error(TITLE_OK, CONTENT_OK);
            Assertions.assertEquals(TITLE_OK, notification.getTitle());
            Assertions.assertEquals(CONTENT_OK, notification.getContent());
            Assertions.assertEquals("error", notification.getType());
        }

        @Test
        void nullTitle() {
            Assertions.assertThrows(ImplementationException.class, () -> Notification.error(null, CONTENT_OK));
        }

        @Test
        void nullContent() {
            Assertions.assertThrows(ImplementationException.class, () -> Notification.error(TITLE_OK, null));
        }

    }

}
