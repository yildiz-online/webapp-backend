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

import be.yildizgames.common.authentication.TemporaryAccountValidationException;
import be.yildizgames.common.exception.technical.TechnicalException;
import be.yildizgames.web.webapp.infrastructure.controller.account.creation.TemporaryAccountCreationValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

/**
 * @author Grégory Van den Borre
 */
@ControllerAdvice
public class ExceptionsHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ACCOUNT_VALIDATION_ERROR = "account.validation.error";

    private static final String TECHNICAL_ERROR = "technical.error";

    /**
     * Handle validation exceptions coming by the authentication server,
     * this should not happen since a validation is done first on the web app, excepted for already existing
     * login or email validations.
     * @param e Exception
     * @return The response entity with 422 code.
     */
    @ExceptionHandler(TemporaryAccountCreationValidationException.class)
    @ResponseBody
    public ResponseEntity<AjaxResponse> handleValidationException(final TemporaryAccountCreationValidationException e) {
        return this.build(AjaxResponse.notification(Notification.error(ACCOUNT_VALIDATION_ERROR, e.getMessage())),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle validation exceptions done on the web app.
     * @param e Exception
     * @return The response entity with 422 code.
     */
    @ExceptionHandler(TemporaryAccountValidationException.class)
    @ResponseBody
    public ResponseEntity<AjaxResponse> handleValidationException(final TemporaryAccountValidationException e) {
        return this.build(AjaxResponse.notification(e.getErrors()
                        .stream()
                        .map(msg -> Notification.error(ACCOUNT_VALIDATION_ERROR, msg))
                        .collect(Collectors.toList())),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    public ResponseEntity<AjaxResponse> handleTechnicalException(final TechnicalException e) {
        return this.build(AjaxResponse.notification(
                Notification.error(TECHNICAL_ERROR, e.message)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<AjaxResponse> handleUnhandledException(final Exception e) {
        this.logger.error("Unhandled controller exception:", e);
        return this.build(AjaxResponse.notification(
                Notification.error(TECHNICAL_ERROR, "technical.error.content")),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<AjaxResponse> build(AjaxResponse response, HttpStatus status) {
        return new ResponseEntity<>(response, status);
    }
}
