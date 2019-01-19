/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.web.webapp.infrastructure.controller.account.creation;

import be.yildizgames.common.authentication.protocol.TemporaryAccountCreationResultDto;
import be.yildizgames.web.webapp.infrastructure.controller.AjaxResponse;
import be.yildizgames.web.webapp.infrastructure.controller.Notification;
import be.yildizgames.web.webapp.infrastructure.services.CallBack;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author Grégory Van den Borre
 */
class TemporaryAccountCreationResultResponse implements CallBack<TemporaryAccountCreationResultDto> {

    private final DeferredResult<AjaxResponse> response;

    private TemporaryAccountCreationResultResponse(DeferredResult<AjaxResponse> response) {
        super();
        this.response = response;
    }

    static TemporaryAccountCreationResultResponse prepare(DeferredResult<AjaxResponse> response) {
        return new TemporaryAccountCreationResultResponse(response);
    }

    @Override
    public void setResult(TemporaryAccountCreationResultDto o) {
        if(!o.hasError()) {
            this.response.setResult(AjaxResponse.notification(
                    Notification.success("account.creation.success.title", "account.creation.success.content")));
        } else if(o.isTechnicalIssue()) {
            throw new TemporaryAccountCreationTechnicalException();
        } else if(o.isInvalidPassword()) {
            throw new PasswordInvalidValidationException();
        } else if(o.isInvalidEmail()) {
            throw new EmailInvalidValidationException();
        } else if(o.isInvalidLogin()) {
            throw new LoginInvalidValidationException();
        } else if(o.isAccountExisting()) {
            throw new LoginExistsValidationException();
        } else if(o.isEmailExisting()) {
            throw new EmailExistsValidationException();
        }
    }

    @Override
    public void error() {
        this.response.setResult(AjaxResponse.notification(Notification.error(
                "account.creation.error.title", "account.creation.error.content")));
    }
}
