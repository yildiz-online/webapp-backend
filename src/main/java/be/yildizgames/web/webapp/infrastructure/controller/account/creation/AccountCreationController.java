/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
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

import be.yildizgames.common.authentication.TemporaryAccount;
import be.yildizgames.web.webapp.infrastructure.controller.AjaxResponse;
import be.yildizgames.web.webapp.infrastructure.controller.Notification;
import be.yildizgames.web.webapp.infrastructure.services.AccountCreationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * @author Grégory Van den Borre
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AccountCreationController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final long ACCOUNT_CREATION_TIMEOUT = 5000L;

    private static final Notification ACCOUNT_CREATION_TIMEOUT_NOTIF = Notification.warning(
            "account.creation.timeout.title",
            "account.creation.timeout.content");

    private final AccountCreationService accountCreationService;

    @Autowired
    public AccountCreationController(AccountCreationService accountCreationService) {
        super();
        this.accountCreationService = accountCreationService;
    }

    @RequestMapping(value = "api/v1/accounts/creations", method = RequestMethod.POST)
    public DeferredResult<AjaxResponse> create(@RequestBody AccountForm form) {
        logger.debug("Create (api/v1/accounts/creations) " + form);

        DeferredResult<AjaxResponse> response = new DeferredResult<>(ACCOUNT_CREATION_TIMEOUT,
                AjaxResponse.notification(ACCOUNT_CREATION_TIMEOUT_NOTIF));

        this.accountCreationService.send(
                this.getTemporaryAccountFromForm(form), TemporaryAccountCreationResultResponse.prepare(response));

        return response;
    }

    private TemporaryAccount getTemporaryAccountFromForm(AccountForm form) {
        return TemporaryAccount.create(
                form.getLogin(),
                form.getPassword(),
                form.getEmail(),
                form.getLanguage());
    }
}
