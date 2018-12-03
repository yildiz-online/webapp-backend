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

import be.yildizgames.common.authentication.TemporaryAccountValidationException;
import be.yildizgames.web.webapp.infrastructure.controller.account.creation.AccountCreationController;
import be.yildizgames.web.webapp.infrastructure.controller.account.creation.AccountForm;
import be.yildizgames.web.webapp.infrastructure.services.AccountService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Grégory Van den Borre
 */
class AccountCreationControllerTest {

    private static final String LOGIN_OK = "available";

    private static final String LOGIN_INVALID = "ab";

    private static final String NOT_AVAILABLE = "not-available";

    private static final String NOT_AVAILABLE_IN_TEMP = "not-available-in-temp";

    private static final String PASSWORD_OK = "azerty";

    private static final String PASSWORD_INVALID = "ab";

    private static final String EMAIL_OK = "available@test.com";

    private static final String EMAIL_NOT_AVAILABLE = "notavailable@test.com";

    private static final String EMAIL_NOT_AVAILABLE_IN_TEMP = "notavailabletemp@test.com";

    @Nested
    class IsLoginAvailable {

        @Test
        void happyFlow() {
            /*givenAnAccountController().isLoginAvailable(LOGIN_OK);*/
        }

        @Test
        void available() {
          /*  AccountCreationController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isLoginAvailable(LOGIN_OK);

            assertEquals(200, r.getStatusCode().value());*/
        }

        @Test
        void notAvailableInTempAccount() {
           /* AccountCreationController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isLoginAvailable(NOT_AVAILABLE_IN_TEMP);

            assertEquals(400, r.getStatusCode().value());*/
        }

        @Test
        void notAvailableInAccount() {
         /*   AccountCreationController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isLoginAvailable(NOT_AVAILABLE);

            assertEquals(400, r.getStatusCode().value());*/
        }

    }

    @Nested
    class IsEmailAvailable {

        @Test
        void happyFlow() {
            /*givenAnAccountController().isEmailAvailable(EMAIL_OK);*/
        }

        @Test
        void available() {
           /* AccountCreationController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isEmailAvailable(EMAIL_OK);

            assertEquals(200, r.getStatusCode().value());*/
        }

        @Test
        void notAvailableInTempAccount() {
         /*   AccountCreationController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isEmailAvailable(EMAIL_NOT_AVAILABLE_IN_TEMP);

            assertEquals(400, r.getStatusCode().value());*/
        }

        @Test
        void notAvailableInAccount() {
           /* AccountCreationController a = givenAnAccountController();

            ResponseEntity<Void> r = a.isEmailAvailable(EMAIL_NOT_AVAILABLE);

            assertEquals(400, r.getStatusCode().value());*/
        }
    }

    @Nested
    class Create {

        @Test
        void happyFlow() {
            AccountCreationController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(LOGIN_OK);
            f.setPassword(PASSWORD_OK);
            f.setEmail(EMAIL_OK);

            a.create(f);
        }

        @Test
        void WithNullLogin() {
            AccountCreationController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(null);
            f.setPassword(PASSWORD_OK);
            f.setEmail(EMAIL_OK);

            assertThrows(TemporaryAccountValidationException.class, () -> a.create(f));
        }

        @Test
        void withNullPassword() {
            AccountCreationController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(LOGIN_OK);
            f.setPassword(null);
            f.setEmail(EMAIL_OK);

            assertThrows(TemporaryAccountValidationException.class, () -> a.create(f));
        }

        @Test
        void withNullEmail() {
            AccountCreationController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(LOGIN_OK);
            f.setPassword(PASSWORD_OK);
            f.setEmail(null);

            assertThrows(TemporaryAccountValidationException.class, () -> a.create(f));
        }

        @Test
        void withInvalidLogin() {
            AccountCreationController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(LOGIN_INVALID);
            f.setPassword(PASSWORD_OK);
            f.setEmail(EMAIL_OK);

            assertThrows(TemporaryAccountValidationException.class, () -> a.create(f));
        }

        @Test
        void withInvalidPassword() {
            AccountCreationController a = givenAnAccountController();
            AccountForm f = new AccountForm();
            f.setLogin(LOGIN_OK);
            f.setPassword(PASSWORD_INVALID);
            f.setEmail(EMAIL_OK);

            assertThrows(TemporaryAccountValidationException.class, () -> a.create(f));
        }
    }

    private static AccountCreationController givenAnAccountController() {
        return new AccountCreationController((a, b) -> {});
    }

    private static AccountService givenAnAccountService() {
       /* AccountService as = Mockito.mock(AccountService.class);
        Mockito.when(as.findByLogin(LOGIN_OK)).thenReturn(Optional.empty());
        Mockito.when(as.findByLogin(NOT_AVAILABLE)).thenReturn(Optional.of(AccountTest.givenAnAccount()));
        Mockito.when(as.findByLogin(NOT_AVAILABLE_IN_TEMP)).thenReturn(Optional.empty());

        Mockito.when(as.findByEmail(EMAIL_OK)).thenReturn(Optional.empty());
        Mockito.when(as.findByEmail(EMAIL_NOT_AVAILABLE)).thenReturn(Optional.of(AccountTest.givenAnAccount()));
        Mockito.when(as.findByEmail(EMAIL_NOT_AVAILABLE_IN_TEMP)).thenReturn(Optional.empty());*/
        return null;
    }
}
