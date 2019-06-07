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

package be.yildizgames.web.webapp.infrastructure.controller;

import be.yildizgames.web.webapp.infrastructure.controller.account.creation.AccountCreationExceptionFactory;
import be.yildizgames.web.webapp.infrastructure.controller.account.creation.TemporaryAccountCreationValidationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Grégory Van den Borre
 */
class ExceptionHandlerTest {

    @Nested
    class HandleValidationException {

        @Test
        void invalidLogin() {
            validationException(AccountCreationExceptionFactory.invalidLogin(), "account.creation.validation.login.invalid");
        }

        @Test
        void existingLogin() {
            validationException(AccountCreationExceptionFactory.existingLogin(), "account.creation.validation.login.exist");
        }

        @Test
        void invalidEmail() {
            validationException(AccountCreationExceptionFactory.invalidEmail(), "account.creation.validation.email.invalid");
        }

        @Test
        void existingEmail() {
            validationException(AccountCreationExceptionFactory.existingEmail(), "account.creation.validation.email.exist");
        }

        @Test
        void invalidPassword() {
            validationException(AccountCreationExceptionFactory.invalidPassword(), "account.creation.validation.password.invalid");
        }

        private void validationException(TemporaryAccountCreationValidationException ex, String content) {
            ExceptionsHandler e = new ExceptionsHandler();
            ResponseEntity<AjaxResponse> response = e.handleValidationException(ex);
            assertEquals(422, response.getStatusCode().value());
            assertEquals("account.validation.error", response.getBody().getNotifications().get(0).getTitle());
            assertEquals(content, response.getBody().getNotifications().get(0).getContent());
            assertEquals("error", response.getBody().getNotifications().get(0).getType());
            assertEquals(1, response.getBody().getNotifications().size());
        }
    }

    @Nested
    class HandleException {

        @Test
        void happyFlow() {
            ExceptionsHandler e = new ExceptionsHandler();
            ResponseEntity<AjaxResponse> response = e.handleUnhandledException(new IllegalArgumentException("boum"));
            assertEquals(500, response.getStatusCode().value());
            assertEquals("technical.error", response.getBody().getNotifications().get(0).getTitle());
            assertEquals("technical.error.content", response.getBody().getNotifications().get(0).getContent());
            assertEquals("error", response.getBody().getNotifications().get(0).getType());
            assertEquals(1, response.getBody().getNotifications().size());
        }
    }

    @Nested
    class HandleTechnicalException {

        @Test
        void happyFlow() {
            ExceptionsHandler e = new ExceptionsHandler();
            ResponseEntity<AjaxResponse> response = e.handleTechnicalException(new TestException("boum"));
            assertEquals(500, response.getStatusCode().value());
            assertEquals("technical.error", response.getBody().getNotifications().get(0).getTitle());
            assertEquals("boum", response.getBody().getNotifications().get(0).getContent());
            assertEquals("error", response.getBody().getNotifications().get(0).getType());
            assertEquals(1, response.getBody().getNotifications().size());
        }
    }

    private static final class TestException extends IllegalStateException {

        TestException(String message, Exception cause) {
            super(message, cause);
        }

        TestException(Exception cause) {
            super(cause);
        }

        TestException(String s) {
            super(s);
        }
    }
}
