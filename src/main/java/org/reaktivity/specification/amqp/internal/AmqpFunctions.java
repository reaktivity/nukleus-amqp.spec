/**
 * Copyright 2016-2020 The Reaktivity Project
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

import java.nio.charset.StandardCharsets;

import org.agrona.MutableDirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.kaazing.k3po.lang.el.Function;
import org.kaazing.k3po.lang.el.spi.FunctionMapperSpi;
import org.reaktivity.specification.amqp.internal.types.ReceiverSettleMode;
import org.reaktivity.specification.amqp.internal.types.Role;
import org.reaktivity.specification.amqp.internal.types.SenderSettleMode;
import org.reaktivity.specification.amqp.internal.types.control.AmqpRouteExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpAbortExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpBeginExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpDataExFW;

public final class AmqpFunctions
{
    private static final int MAX_BUFFER_SIZE = 1024 * 8;

    public static class AmqpRouteExBuilder
    {
        private final AmqpRouteExFW.Builder routeExRW;

        public AmqpRouteExBuilder()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[MAX_BUFFER_SIZE]);
            this.routeExRW = new AmqpRouteExFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public AmqpRouteExBuilder targetAddress(
            String targetAddress)
        {
            routeExRW.targetAddress(targetAddress);
            return this;
        }

        public AmqpRouteExBuilder role(
            String role)
        {
            routeExRW.role(r -> r.set(Role.valueOf(role)));
            return this;
        }

        public byte[] build()
        {
            final AmqpRouteExFW amqpRouteEx = routeExRW.build();
            final byte[] result = new byte[amqpRouteEx.sizeof()];
            amqpRouteEx.buffer().getBytes(0, result);
            return result;
        }
    }

    public static class AmqpBeginExBuilder
    {
        private final AmqpBeginExFW.Builder beginExRW;

        public AmqpBeginExBuilder()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[MAX_BUFFER_SIZE]);
            this.beginExRW = new AmqpBeginExFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public AmqpBeginExBuilder typeId(
            int typeId)
        {
            beginExRW.typeId(typeId);
            return this;
        }

        public AmqpBeginExBuilder containerId(
            String containerId)
        {
            beginExRW.containerId(containerId);
            return this;
        }

        public AmqpBeginExBuilder channel(
            int channel)
        {
            beginExRW.channel(channel);
            return this;
        }

        public AmqpBeginExBuilder address(
            String address)
        {
            beginExRW.address(address);
            return this;
        }

        public AmqpBeginExBuilder role(
            String role)
        {
            beginExRW.role(r -> r.set(Role.valueOf(role)));
            return this;
        }

        public AmqpBeginExBuilder senderSettleMode(
            String senderSettleMode)
        {
            beginExRW.senderSettleMode(s -> s.set(SenderSettleMode.valueOf(senderSettleMode)));
            return this;
        }

        public AmqpBeginExBuilder receiverSettleMode(
            String receiverSettleMode)
        {
            beginExRW.receiverSettleMode(r -> r.set(ReceiverSettleMode.valueOf(receiverSettleMode)));
            return this;
        }

        public byte[] build()
        {
            final AmqpBeginExFW amqpBeginEx = beginExRW.build();
            final byte[] result = new byte[amqpBeginEx.sizeof()];
            amqpBeginEx.buffer().getBytes(0, result);
            return result;
        }
    }

    public static class AmqpDataExBuilder
    {
        private final AmqpDataExFW.Builder dataExRW;

        public AmqpDataExBuilder()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[MAX_BUFFER_SIZE]);
            this.dataExRW = new AmqpDataExFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public AmqpDataExBuilder typeId(
            int typeId)
        {
            dataExRW.typeId(typeId);
            return this;
        }

        public AmqpDataExBuilder deliveryId(
            long deliveryId)
        {
            dataExRW.deliveryId(deliveryId);
            return this;
        }

        public AmqpDataExBuilder deliveryTag(
            String deliveryTag)
        {
            dataExRW.deliveryTag(d -> d.bytes(b -> b.set(deliveryTag.getBytes(StandardCharsets.UTF_8))));
            return this;
        }

        public AmqpDataExBuilder messageFormat(
            long messageFormat)
        {
            dataExRW.messageFormat(messageFormat);
            return this;
        }

        public AmqpDataExBuilder flags(
            int flags)
        {
            dataExRW.flags(flags);
            return this;
        }

        public AmqpDataExBuilder annotation(
            Object key, String value)
        {
            return key instanceof Long ? annotations((long) key, value) : annotations((String) key, value);
        }

        private AmqpDataExBuilder annotations(
            long key, String value)
        {
            dataExRW.annotationsItem(a -> a.key(k -> k.id(key))
                                           .value(v -> v.bytes(b -> b.set(value.getBytes(StandardCharsets.UTF_8)))));
            return this;
        }

        private AmqpDataExBuilder annotations(
            String key, String value)
        {
            dataExRW.annotationsItem(a -> a.key(k -> k.name(key))
                                           .value(v -> v.bytes(b -> b.set(value.getBytes(StandardCharsets.UTF_8)))));
            return this;
        }

        public AmqpDataExBuilder messageId(
            Object messageId)
        {
            if (messageId instanceof Long)
            {
                dataExRW.propertiesItem(p -> p.messageId(m -> m.ulong((long) messageId)));
                return this;
            }
            else if (messageId instanceof byte[])
            {
                dataExRW.propertiesItem(p -> p.messageId(m -> m.binary(b -> b.bytes(x -> x.set((byte[]) messageId)))));
                return this;
            }
            dataExRW.propertiesItem(p -> p.messageId(m -> m.stringtype((String) messageId)));
            return this;
        }

        public AmqpDataExBuilder userId(
            String userId)
        {
            dataExRW.propertiesItem(p ->
                p.userId(u -> u.bytes(b -> b.set(userId.getBytes(StandardCharsets.UTF_8)))));
            return this;
        }

        public AmqpDataExBuilder to(
            String to)
        {
            dataExRW.propertiesItem(p -> p.to(to));
            return this;
        }

        public AmqpDataExBuilder subject(
            String subject)
        {
            dataExRW.propertiesItem(p -> p.subject(subject));
            return this;
        }

        public AmqpDataExBuilder replyTo(
            String replyTo)
        {
            dataExRW.propertiesItem(p -> p.replyTo(replyTo));
            return this;
        }

        public AmqpDataExBuilder correlationId(
            Object correlationId)
        {
            if (correlationId instanceof Long)
            {
                dataExRW.propertiesItem(p -> p.correlationId(m -> m.ulong((long) correlationId)));
                return this;
            }
            else if (correlationId instanceof byte[])
            {
                dataExRW.propertiesItem(p -> p.correlationId(m -> m.binary(b -> b.bytes(x -> x.set((byte[]) correlationId)))));
                return this;
            }
            dataExRW.propertiesItem(p -> p.correlationId(m -> m.stringtype((String) correlationId)));
            return this;
        }

        public AmqpDataExBuilder contentType(
            String contentType)
        {
            dataExRW.propertiesItem(p -> p.contentType(contentType));
            return this;
        }

        public AmqpDataExBuilder contentEncoding(
            String contentEncoding)
        {
            dataExRW.propertiesItem(p -> p.contentEncoding(contentEncoding));
            return this;
        }

        public AmqpDataExBuilder absoluteExpiryTime(
            long absoluteExpiryTime)
        {
            dataExRW.propertiesItem(p -> p.absoluteExpiryTime(absoluteExpiryTime));
            return this;
        }

        public AmqpDataExBuilder creationTime(
            long creationTime)
        {
            dataExRW.propertiesItem(p -> p.creationTime(creationTime));
            return this;
        }

        public AmqpDataExBuilder groupId(
            String groupId)
        {
            dataExRW.propertiesItem(p -> p.groupId(groupId));
            return this;
        }

        public AmqpDataExBuilder groupSequence(
            int groupSequence)
        {
            dataExRW.propertiesItem(p -> p.groupSequence(groupSequence));
            return this;
        }

        public AmqpDataExBuilder replyToGroupId(
            String replyToGroupId)
        {
            dataExRW.propertiesItem(p -> p.replyToGroupId(replyToGroupId));
            return this;
        }

        public byte[] build()
        {
            final AmqpDataExFW amqpDataEx = dataExRW.build();
            final byte[] result = new byte[amqpDataEx.sizeof()];
            amqpDataEx.buffer().getBytes(0, result);
            return result;
        }
    }

    public static class AmqpAbortExBuilder
    {
        private final AmqpAbortExFW.Builder abortExRW;

        public AmqpAbortExBuilder()
        {
            MutableDirectBuffer writeBuffer = new UnsafeBuffer(new byte[MAX_BUFFER_SIZE]);
            this.abortExRW = new AmqpAbortExFW.Builder()
                .wrap(writeBuffer, 0, writeBuffer.capacity());
        }

        public AmqpAbortExBuilder typeId(
            int typeId)
        {
            abortExRW.typeId(typeId);
            return this;
        }

        public AmqpAbortExBuilder condition(
            String condition)
        {
            abortExRW.condition(condition);
            return this;
        }

        public byte[] build()
        {
            final AmqpAbortExFW amqpAbortEx = abortExRW.build();
            final byte[] result = new byte[amqpAbortEx.sizeof()];
            amqpAbortEx.buffer().getBytes(0, result);
            return result;
        }
    }

    @Function
    public static AmqpRouteExBuilder routeEx()
    {
        return new AmqpRouteExBuilder();
    }

    @Function
    public static AmqpBeginExBuilder beginEx()
    {
        return new AmqpBeginExBuilder();
    }

    @Function
    public static AmqpDataExBuilder dataEx()
    {
        return new AmqpDataExBuilder();
    }

    @Function
    public static AmqpAbortExBuilder abortEx()
    {
        return new AmqpAbortExBuilder();
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
