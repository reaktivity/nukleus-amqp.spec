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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.kaazing.k3po.lang.internal.el.ExpressionFactoryUtils.newExpressionFactory;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.abortEx;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.beginEx;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.dataEx;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.routeEx;

import java.nio.charset.StandardCharsets;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.agrona.DirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Before;
import org.junit.Test;
import org.kaazing.k3po.lang.internal.el.ExpressionContext;
import org.reaktivity.specification.amqp.internal.AmqpFunctions.AmqpBeginExBuilder;
import org.reaktivity.specification.amqp.internal.types.AmqpAnnotationKeyFW;
import org.reaktivity.specification.amqp.internal.types.AmqpMessagePropertyFW;
import org.reaktivity.specification.amqp.internal.types.control.AmqpRouteExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpAbortExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpBeginExFW;
import org.reaktivity.specification.amqp.internal.types.stream.AmqpDataExFW;

public class AmqpFunctionsTest
{
    private ExpressionFactory factory;
    private ELContext ctx;

    @Before
    public void setUp() throws Exception
    {
        factory = newExpressionFactory();
        ctx = new ExpressionContext();
    }

    @Test
    public void shouldLoadFunctions() throws Exception
    {
        String expressionText = "${amqp:beginEx()}";
        ValueExpression expression = factory.createValueExpression(ctx, expressionText, AmqpBeginExBuilder.class);
        AmqpBeginExBuilder builder = (AmqpBeginExBuilder) expression.getValue(ctx);
        assertNotNull(builder);
    }

    @Test
    public void shouldEncodeWsRouteExt()
    {
        final byte[] array = routeEx()
            .targetAddress("queue://queue")
            .capabilities("RECEIVE")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpRouteExFW amqpRouteEx = new AmqpRouteExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(amqpRouteEx.targetAddress().asString(), "queue://queue");
        assertEquals(amqpRouteEx.capabilities().toString(), "RECEIVE");
    }

    @Test
    public void shouldEncodeAmqpBeginExt()
    {
        final byte[] array = beginEx()
            .typeId(0)
            .channel(1)
            .address("queue://queue")
            .capabilities("RECEIVE")
            .senderSettleMode("SETTLED")
            .receiverSettleMode("FIRST")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpBeginExFW amqpBeginEx = new AmqpBeginExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(amqpBeginEx.channel(), 1);
        assertEquals(amqpBeginEx.address().asString(), "queue://queue");
        assertEquals(amqpBeginEx.capabilities().toString(), "RECEIVE");
        assertEquals(amqpBeginEx.senderSettleMode().toString(), "SETTLED");
        assertEquals(amqpBeginEx.receiverSettleMode().toString(), "FIRST");
    }

    @Test
    public void shouldEncodeAmqpDataExtWithRequiredFields()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags(1)
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(amqpDataEx.deliveryId(), 0);
        assertEquals(amqpDataEx.deliveryTag().toString(), "AMQP_BINARY [length=2, bytes=octets[2]]");
        assertEquals(amqpDataEx.messageFormat(), 0);
        assertEquals(amqpDataEx.flags(), 1);
    }

    @Test
    public void shouldEncodeAmqpDataExtWithAnnotations()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags(1)
            .annotation("x-opt-jms-dest", "0")
            .annotation(1L, "00")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        amqpDataEx.annotations().forEach(a ->
        {
            switch (a.key().kind())
            {
            case AmqpAnnotationKeyFW.KIND_ID:
                assertEquals(a.value().toString(), "AMQP_BINARY [length=2, bytes=octets[2]]");
                break;
            case AmqpAnnotationKeyFW.KIND_NAME:
                assertEquals(a.value().toString(), "AMQP_BINARY [length=1, bytes=octets[1]]");
                break;
            }
        });
    }

    @Test
    public void shouldEncodeAmqpDataExtWithProperties()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags(1)
            .messageId("message1")
            .userId("user1")
            .to("queue://queue")
            .subject("subject1")
            .replyTo("localhost")
            .correlationId("correlationId1")
            .contentType("content_type")
            .contentEncoding("content_encoding")
            .absoluteExpiryTime(12345L)
            .creationTime(12345L)
            .groupId("group_id1")
            .groupSequence(1)
            .replyToGroupId("reply_group_id")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        amqpDataEx.properties().forEach(p ->
        {
            switch (p.kind())
            {
            case AmqpMessagePropertyFW.KIND_MESSAGE_ID:
                assertEquals(p.messageId().stringtype().asString(), "message1");
                break;
            case AmqpMessagePropertyFW.KIND_USER_ID:
                assertEquals(p.userId().bytes().toString(), "octets[5]");
                break;
            case AmqpMessagePropertyFW.KIND_TO:
                assertEquals(p.to().asString(), "queue://queue");
                break;
            case AmqpMessagePropertyFW.KIND_SUBJECT:
                assertEquals(p.subject().asString(), "subject1");
                break;
            case AmqpMessagePropertyFW.KIND_REPLY_TO:
                assertEquals(p.replyTo().asString(), "localhost");
                break;
            case AmqpMessagePropertyFW.KIND_CORRELATION_ID:
                assertEquals(p.correlationId().stringtype().asString(), "correlationId1");
                break;
            case AmqpMessagePropertyFW.KIND_CONTENT_TYPE:
                assertEquals(p.contentType().asString(), "content_type");
                break;
            case AmqpMessagePropertyFW.KIND_CONTENT_ENCODING:
                assertEquals(p.contentEncoding().asString(), "content_encoding");
                break;
            case AmqpMessagePropertyFW.KIND_ABSOLUTE_EXPIRY_TIME:
                assertEquals(p.absoluteExpiryTime(), 12345L);
                break;
            case AmqpMessagePropertyFW.KIND_CREATION_TIME:
                assertEquals(p.absoluteExpiryTime(), 12345L);
                break;
            case AmqpMessagePropertyFW.KIND_GROUP_ID:
                assertEquals(p.groupId().asString(), "group_id1");
                break;
            case AmqpMessagePropertyFW.KIND_GROUP_SEQUENCE:
                assertEquals(p.groupSequence(), 1);
                break;
            case AmqpMessagePropertyFW.KIND_REPLY_TO_GROUP_ID:
                assertEquals(p.replyToGroupId().asString(), "reply_group_id");
                break;
            }
        });
    }

    @Test
    public void shouldEncodeAmqpDataExtWithLongProperties()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags(1)
            .messageId(12345L)
            .userId("user1")
            .to("queue://queue")
            .subject("subject1")
            .replyTo("localhost")
            .correlationId(12345L)
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        amqpDataEx.properties().forEach(p ->
        {
            switch (p.kind())
            {
            case AmqpMessagePropertyFW.KIND_MESSAGE_ID:
                assertEquals(p.messageId().ulong(), 12345L);
                break;
            case AmqpMessagePropertyFW.KIND_CORRELATION_ID:
                assertEquals(p.correlationId().ulong(), 12345L);
                break;
            }
        });
    }

    @Test
    public void shouldEncodeAmqpDataExtWithByteArrayProperties()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags(1)
            .messageId("message1".getBytes(StandardCharsets.UTF_8))
            .userId("user1")
            .to("queue://queue")
            .subject("subject1")
            .replyTo("localhost")
            .correlationId("correlation1".getBytes(StandardCharsets.UTF_8))
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        amqpDataEx.properties().forEach(p ->
        {
            switch (p.kind())
            {
            case AmqpMessagePropertyFW.KIND_MESSAGE_ID:
                assertEquals(p.messageId().binary().bytes().toString(), "octets[8]");
                break;
            case AmqpMessagePropertyFW.KIND_CORRELATION_ID:
                assertEquals(p.correlationId().binary().bytes().toString(), "octets[12]");
                break;
            }
        });
    }

    @Test
    public void shouldEncodeAmqpAbortExt()
    {
        final byte[] array = abortEx()
            .typeId(0)
            .condition("amqp:link:transfer-limit-exceeded")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpAbortExFW amqpAbortEx = new AmqpAbortExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(amqpAbortEx.condition().asString(), "amqp:link:transfer-limit-exceeded");
    }
}
