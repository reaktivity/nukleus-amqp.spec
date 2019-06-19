/**
 * Copyright 2016-2019 The Reaktivity Project
 *
 * The Reaktivity Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.reaktivity.specification.amqp.internal;

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.kaazing.k3po.lang.el.Function;
import org.kaazing.k3po.lang.el.spi.FunctionMapperSpi;
import org.reaktivity.specification.amqp.internal.types.*;
import org.reaktivity.specification.amqp.internal.types.control.AmqpRouteExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpAbortExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpBeginExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpDataExFW;

import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public final class AmqpFunctions
{
    private static final int MAX_BUFFER_SIZE = 1024 * 8;

    public static class AmqpRouteExHelper
    {
        private final AmqpRouteExFW.Builder amqpRouteExRW;

        public AmqpRouteExHelper()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[MAX_BUFFER_SIZE]);
            this.amqpRouteExRW = new AmqpRouteExFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public AmqpRouteExHelper targetAddress(
            String targetAddress)
        {
            amqpRouteExRW.targetAddress(targetAddress);
            return this;
        }

        public AmqpRouteExHelper role(
            String role)
        {
            amqpRouteExRW.role(
                roleRW -> roleRW.set(AmqpRole.valueOf(role))
            );
            return this;
        }

        public byte[] build()
        {
            final AmqpRouteExFW amqpRouteEx = amqpRouteExRW.build();
            final byte[] result = new byte[amqpRouteEx.sizeof()];
            amqpRouteEx.buffer().getBytes(0, result);
            return result;
        }
    }

    public static class AmqpBeginExHelper
    {
        private final AmqpBeginExFW.Builder amqpBeginExRW;

        public AmqpBeginExHelper()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[MAX_BUFFER_SIZE]);
            this.amqpBeginExRW = new AmqpBeginExFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public AmqpBeginExHelper containerId(
            String containerId)
        {
            amqpBeginExRW.containerId(containerId);
            return this;
        }

        public AmqpBeginExHelper channel(
            int channel)
        {
            amqpBeginExRW.channel(channel);
            return this;
        }

        public AmqpBeginExHelper address(
            String address)
        {
            amqpBeginExRW.address(address);
            return this;
        }

        public AmqpBeginExHelper role(
            String role)
        {
            amqpBeginExRW.role(
                roleRW -> roleRW.set(AmqpRole.valueOf(role))
            );
            return this;
        }

        public AmqpBeginExHelper senderSettleMode(
            String senderSettleMode)
        {
            amqpBeginExRW.senderSettleMode(
                senderSettleModeRW -> senderSettleModeRW.set(AmqpSenderSettleMode.valueOf(senderSettleMode))
            );
            return this;
        }

        public AmqpBeginExHelper receiverSettleMode(
            String receiverSettleMode)
        {
            amqpBeginExRW.receiverSettleMode(
                receiverSettleModeRW -> receiverSettleModeRW.set(AmqpReceiverSettleMode.valueOf(receiverSettleMode))
            );
            return this;
        }

        public byte[] build()
        {
            final AmqpBeginExFW amqpBeginEx = amqpBeginExRW.build();
            final byte[] result = new byte[amqpBeginEx.sizeof()];
            amqpBeginEx.buffer().getBytes(0, result);
            return result;
        }
    }

    public static class AmqpDataExHelper
    {
        private final AmqpDataExFW.Builder amqpDataExRW;

        public AmqpDataExHelper()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[MAX_BUFFER_SIZE]);
            this.amqpDataExRW = new AmqpDataExFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public AmqpDataExHelper deliveryId(
            long deliveryId)
        {
            amqpDataExRW.deliveryId(deliveryId);
            return this;
        }

        public AmqpDataExHelper deliveryTag(
            String deliveryTag)
        {
            amqpDataExRW.deliveryTag(
                deliveryTagRW -> deliveryTagRW.bytes(
                    bytesRW -> bytesRW.set(deliveryTag.getBytes(StandardCharsets.UTF_8))
                )
            );
            return this;
        }

        public AmqpDataExHelper messageFormat(
            long messageFormat)
        {
            amqpDataExRW.messageFormat(messageFormat);
            return this;
        }

        public AmqpDataExHelper flags(
            int flags)
        {
            amqpDataExRW.flags(flags);
            return this;
        }

        public AmqpDataExHelper annotations(
            long key, String value)
        {
            Consumer<ListFW.Builder<AmqpAnnotationFW.Builder, AmqpAnnotationFW>> annotation =
                annotationsRW -> annotationsRW.item(itemRW ->
                {
                    itemRW.key(keyRW -> keyRW.id(key));
                    itemRW.value(valueRW -> valueRW.bytes(
                        bytesRW -> bytesRW.set(value.getBytes(StandardCharsets.UTF_8))
                    ));
                });
            amqpDataExRW.annotations(annotation);
            return this;
        }

        public AmqpDataExHelper annotations(
            String key, String value)
        {
            Consumer<ListFW.Builder<AmqpAnnotationFW.Builder, AmqpAnnotationFW>> annotation =
                annotationsRW -> annotationsRW.item(itemRW ->
                {
                    itemRW.key(keyRW -> keyRW.name(key));
                    itemRW.value(valueRW -> valueRW.bytes(
                        bytesRW -> bytesRW.set(value.getBytes(StandardCharsets.UTF_8))
                    ));
                });
            amqpDataExRW.annotations(annotation);
            return this;
        }

        // properties below
        public AmqpDataExHelper messageId(
            String messageId)
        {
            // TODO - need to fix
            amqpDataExRW.properties(propertiesRW -> propertiesRW.messageId(
                messageIdRW -> messageIdRW.stringtype(messageId)
            ));
            return this;
        }

        public AmqpDataExHelper userId(
            String userId)
        {
            amqpDataExRW.properties(
                propertiesRW -> propertiesRW.userId(
                    userIdRW -> userIdRW.bytes(
                        bytesRW -> bytesRW.set(userId.getBytes(StandardCharsets.UTF_8))
                    )
                )
            );
            return this;
        }

        public AmqpDataExHelper to(
            String to)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.to(to));
            return this;
        }

        public AmqpDataExHelper subject(
            String subject)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.subject(subject));
            return this;
        }

        public AmqpDataExHelper replyTo(
            String replyTo)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.replyTo(replyTo));
            return this;
        }

        public AmqpDataExHelper correlationId(
            )
        {
            // TODO
            return this;
        }

        public AmqpDataExHelper contentType(
            String contentType)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.contentType(contentType));
            return this;
        }

        public AmqpDataExHelper contentEncoding(
            String contentEncoding)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.contentEncoding(contentEncoding));
            return this;
        }

        public AmqpDataExHelper absoluteExpiryTime(
            long absoluteExpiryTime)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.absoluteExpiryTime(absoluteExpiryTime));
            return this;
        }

        public AmqpDataExHelper creationTime(
            long creationTime)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.creationTime(creationTime));
            return this;
        }

        public AmqpDataExHelper groupId(
            String groupId)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.groupId(groupId));
            return this;
        }

        public AmqpDataExHelper groupSequence(
            int groupSequence)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.groupSequence(groupSequence));
            return this;
        }

        public AmqpDataExHelper replyToGroupId(
            String replyToGroupId)
        {
            amqpDataExRW.properties(propertiesRW -> propertiesRW.replyToGroupId(replyToGroupId));
            return this;
        }

        public byte[] build()
        {
            final AmqpDataExFW amqpDataEx = amqpDataExRW.build();
            final byte[] result = new byte[amqpDataEx.sizeof()];
            amqpDataEx.buffer().getBytes(0, result);
            return result;
        }
    }

    public static class AmqpAbortExHelper
    {
        private final AmqpAbortExFW.Builder amqpAbortExRW;

        public AmqpAbortExHelper()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[MAX_BUFFER_SIZE]);
            this.amqpAbortExRW = new AmqpAbortExFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public AmqpAbortExHelper condition(
            String condition)
        {
            amqpAbortExRW.condition(condition);
            return this;
        }

        public byte[] build()
        {
            final AmqpAbortExFW amqpAbortEx = amqpAbortExRW.build();
            final byte[] result = new byte[amqpAbortEx.sizeof()];
            amqpAbortEx.buffer().getBytes(0, result);
            return result;
        }
    }

    @Function
    public static AmqpRouteExHelper routeEx()
    {
        return new AmqpRouteExHelper();
    }

    @Function
    public static AmqpBeginExHelper beginEx()
    {
        return new AmqpBeginExHelper();
    }

    @Function
    public static AmqpDataExHelper dataEx()
    {
        return new AmqpDataExHelper();
    }

    @Function
    public static AmqpAbortExHelper abortEx()
    {
        return new AmqpAbortExHelper();
    }

    public static class Mapper extends FunctionMapperSpi.Reflective
    {
        public Mapper()
        {
            super(AmqpFunctions.class);
        }

        @Override
        public String getPrefixName()
        {
            return "amqp";
        }
    }

    private AmqpFunctions()
    {
        // utility
    }
}
