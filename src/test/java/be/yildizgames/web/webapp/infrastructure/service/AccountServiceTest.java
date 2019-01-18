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

package be.yildizgames.web.webapp.infrastructure.service;

import be.yildizgames.web.webapp.infrastructure.services.AccountService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Grégory Van den Borre
 */
class AccountServiceTest {

    @Nested
    class Constructor {

        @Test
        void happyFlow() {
            new AccountService();
        }
    }

   /* @Nested
    class GetById {

        @Test
        void happyFlow() {
            AccountPersistence persistence = Mockito.mock(AccountPersistence.class);
            AccountService service = new AccountService(persistence);
            Account a = new Account("1", "azs", "aze", "email", 10);
            Mockito.when(persistence.getById("1")).thenReturn(Optional.of(a));
            assertEquals(a, service.getById("1"));
        }

        @Test
        void withNullId() {

        }

        @Test
        void withNoResult() {
            AccountPersistence persistence = Mockito.mock(AccountPersistence.class);
            AccountService service = new AccountService(persistence);
            Account a = new Account("1", "azs", "aze", "email", 10);
            Mockito.when(persistence.getById("1")).thenReturn(Optional.of(a));
            assertThrows(AccountNoFoundException.class, () -> service.getById("2"));
        }
    }

    @Nested
    class FindByLogin {

        @Test
        void happyFlow() {
            AccountPersistence persistence = Mockito.mock(AccountPersistence.class);
            AccountService service = new AccountService(persistence);
            Account a = new Account("1", "azs", "aze", "email", 10);
            Mockito.when(persistence.findByLogin("azs")).thenReturn(Optional.of(a));
            assertEquals(a, service.findByLogin("azs").get());
        }

        @Test
        void notFound() {
            AccountPersistence persistence = Mockito.mock(AccountPersistence.class);
            AccountService service = new AccountService(persistence);
            Mockito.when(persistence.findByLogin("azs")).thenReturn(Optional.empty());
            assertFalse(service.findByLogin("azs").isPresent());
        }

        @Test
        void withNullLogin() {

        }
    }

    @Nested
    class FindByEmail {

        @Test
        void happyFlow() {
            AccountPersistence persistence = Mockito.mock(AccountPersistence.class);
            AccountService service = new AccountService(persistence);
            Account a = new Account("1", "azs", "aze", "email", 10);
            Mockito.when(persistence.findByEmail("email")).thenReturn(Optional.of(a));
            assertEquals(a, service.findByEmail("email").get());
        }

        @Test
        void notFound() {
            AccountPersistence persistence = Mockito.mock(AccountPersistence.class);
            AccountService service = new AccountService(persistence);
            Mockito.when(persistence.findByEmail("email")).thenReturn(Optional.empty());
            assertFalse(service.findByEmail("email").isPresent());
        }

        @Test
        void withNullEmail() {

        }
    }*/
}
