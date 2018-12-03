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

package be.yildizgames.web.webapp;

import be.yildizgames.module.messaging.Broker;
import be.yildizgames.module.messaging.BrokerMessageDestination;
import be.yildizgames.module.messaging.Message;
import be.yildizgames.module.messaging.MessageConsumer;
import be.yildizgames.module.messaging.activemq.ActivemqBroker;
import be.yildizgames.web.webapp.infrastructure.controller.AjaxResponse;
import be.yildizgames.web.webapp.infrastructure.controller.account.creation.AccountCreationController;
import be.yildizgames.web.webapp.infrastructure.controller.account.creation.AccountForm;
import be.yildizgames.web.webapp.infrastructure.io.account.JmsAccountConfirmation;
import be.yildizgames.web.webapp.infrastructure.io.account.JmsAccountCreation;
import be.yildizgames.web.webapp.infrastructure.services.AccountCreationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Integration tests to create an account.
 *
 * Different system will be the front end controller api, as entry point, and the broker as receiver,
 * and the authentication server as logic.
 * @author Grégory Van den Borre
 */
class AccountCreationIT {

    /**
     * Test with all components online.
     */
    @Nested
    class AllOnline {


    }

    @Nested
    class NoBroker {

    }

    @Nested
    class NoAuthenticationServer {

        @Test
        void test() throws IOException, InterruptedException {
            Broker broker = givenABroker();
            AccountCreationController controller = givenAnAccountController(givenAnAccountService(broker));
            DeferredResult<AjaxResponse> result = controller.create(givenACorrectAccountForm());
            BrokerMessageDestination destination = broker.registerQueue("create-account-request");
            MessageConsumer consumer = destination.createConsumer(m -> {});
            waitForConsumer(consumer);
            Message message = consumer.getMessageReceived().get(0);
            Assertions.assertEquals("myName@@myPassword@@me@me.com", message.getText());
        }
    }

    private static void waitForDeferredTimeout() throws InterruptedException {
        Thread.sleep(AccountCreationController.ACCOUNT_CREATION_TIMEOUT + 100);
    }

    private static Broker givenABroker() throws IOException {
        Path temp = Files.createTempDirectory("tempTest" + System.nanoTime());
        return ActivemqBroker.initializeInternal("test", temp, "localhost", 61613);
    }

    private static JmsAccountCreation givenAnAccountService(Broker broker) {
        return new JmsAccountCreation(broker);
    }

    private static JmsAccountConfirmation givenAnAccountConfirmationService(Broker broker) {
        return new JmsAccountConfirmation(broker);
    }

    private static AccountCreationController givenAnAccountController(AccountCreationService service) {
        return new AccountCreationController(service);
    }

    private static AccountForm givenACorrectAccountForm() {
        AccountForm form = new AccountForm();
        form.setLogin("myName");
        form.setPassword("myPassword");
        form.setEmail("me@me.com");
        return form;
    }

    /**
     * Wait for the consumer to receive the message.
     * @param consumer Consumer to wait for.
     * @throws InterruptedException For thread sleep.
     */
    private static void waitForConsumer(MessageConsumer consumer) throws InterruptedException{
        int wait = 0;
        while (!consumer.hasMessage()) {
            if(wait > 20) {
                Assertions.fail("Time out");
            }
            Thread.sleep(100);
            wait++;
        }
    }

}
