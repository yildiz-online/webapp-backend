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

package be.yildizgames.web.webapp.infrastructure.io.account;

import be.yildizgames.common.authentication.protocol.AccountConfirmationDto;
import be.yildizgames.common.authentication.protocol.Queues;
import be.yildizgames.common.authentication.protocol.mapper.AccountConfirmationMapper;
import be.yildizgames.module.messaging.Broker;
import be.yildizgames.module.messaging.BrokerMessageDestination;
import be.yildizgames.module.messaging.BrokerMessageHeader;
import be.yildizgames.module.messaging.BrokerMessageProducer;
import be.yildizgames.web.webapp.infrastructure.services.AccountConfirmationService;
import be.yildizgames.web.webapp.infrastructure.services.CallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class JmsAccountConfirmation implements AccountConfirmationService {

    private final Map<String, CallBack<String>> results = new HashMap<>();

    private final BrokerMessageProducer producer;

    @Autowired
    public JmsAccountConfirmation(Broker broker) {
        super();
        BrokerMessageDestination responseQueue = broker.registerQueue(Queues.CREATE_ACCOUNT_CONFIRMATION_RESPONSE.getName());
        responseQueue.createConsumer(message -> {
            String correlationId = message.getCorrelationId();
            Optional.ofNullable(results.get(correlationId)).ifPresent(r -> r.setResult(message.getText()));
        });
        BrokerMessageDestination requestQueue = broker.registerQueue(Queues.CREATE_ACCOUNT_CONFIRMATION_REQUEST.getName());
        this.producer = requestQueue.createProducer();
    }

    @Override
    public void confirm(AccountConfirmationDto dto, CallBack<String> response) {
        String cId = UUID.randomUUID().toString();
        this.results.put(cId, response);
        producer.sendMessage(AccountConfirmationMapper.getInstance().to(dto), BrokerMessageHeader.correlationId(cId));
    }
}
