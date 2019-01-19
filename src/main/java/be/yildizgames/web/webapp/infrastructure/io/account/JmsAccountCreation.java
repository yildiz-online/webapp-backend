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

import be.yildizgames.common.authentication.TemporaryAccount;
import be.yildizgames.common.authentication.protocol.TemporaryAccountCreationResultDto;
import be.yildizgames.common.authentication.protocol.mapper.TemporaryAccountMapper;
import be.yildizgames.common.authentication.protocol.mapper.TemporaryAccountResultMapper;
import be.yildizgames.common.exception.business.BusinessException;
import be.yildizgames.module.messaging.Broker;
import be.yildizgames.module.messaging.BrokerMessageDestination;
import be.yildizgames.module.messaging.Header;
import be.yildizgames.module.messaging.JmsMessageProducer;
import be.yildizgames.web.webapp.infrastructure.services.AccountCreationService;
import be.yildizgames.web.webapp.infrastructure.services.CallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Grégory Van den Borre
 */
@Service
public class JmsAccountCreation implements AccountCreationService {

    private final Map <String, CallBack<TemporaryAccountCreationResultDto>> results = new HashMap<>();

    private final JmsMessageProducer producer;

    @Autowired
    public JmsAccountCreation(Broker broker) {
        super();
        //TODO have name in common authentication
        BrokerMessageDestination responseQueue = broker.registerQueue("authentication-creation-temporary");
        responseQueue.createConsumer(message -> {
            String correlationId = message.getCorrelationId();
            Optional<CallBack<TemporaryAccountCreationResultDto>> callback = Optional.ofNullable(results.get(correlationId));
            try {
                TemporaryAccountCreationResultDto dto = TemporaryAccountResultMapper.getInstance().from(message.getText());
                callback.ifPresent(r -> r.setResult(dto));
            } catch (BusinessException e) {
                callback.ifPresent(CallBack::error);
            }
        });
        //TODO have name in common authentication
        BrokerMessageDestination requestQueue = broker.registerQueue("create-account-request");
        this.producer = requestQueue.createProducer();
    }

    @Override
    public void send(TemporaryAccount ta, CallBack<TemporaryAccountCreationResultDto> response) {
        String cId = UUID.randomUUID().toString();
        this.results.put(cId, response);
        producer.sendMessage(TemporaryAccountMapper.getInstance().to(ta), Header.correlationId(cId));
    }
}
