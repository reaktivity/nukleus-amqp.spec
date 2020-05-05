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
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.matchDataEx;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.randomBytes;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.routeEx;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import org.agrona.DirectBuffer;
import org.agrona.concurrent.UnsafeBuffer;
import org.junit.Before;
import org.junit.Test;
import org.kaazing.k3po.lang.el.BytesMatcher;
import org.kaazing.k3po.lang.internal.el.ExpressionContext;
import org.reaktivity.specification.amqp.internal.AmqpFunctions.AmqpBeginExBuilder;
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
    public void shouldEncodeWsRouteExtension()
    {
        final byte[] array = routeEx()
            .targetAddress("clients")
            .capabilities("RECEIVE_ONLY")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpRouteExFW amqpRouteEx = new AmqpRouteExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(amqpRouteEx.targetAddress().asString(), "clients");
        assertEquals(amqpRouteEx.capabilities().toString(), "RECEIVE_ONLY");
    }

    @Test
    public void shouldEncodeAmqpBeginExtension()
    {
        final byte[] array = beginEx()
            .typeId(0)
            .address("clients")
            .capabilities("RECEIVE_ONLY")
            .senderSettleMode("SETTLED")
            .receiverSettleMode("FIRST")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpBeginExFW amqpBeginEx = new AmqpBeginExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(amqpBeginEx.address().asString(), "clients");
        assertEquals(amqpBeginEx.capabilities().toString(), "RECEIVE_ONLY");
        assertEquals(amqpBeginEx.senderSettleMode().toString(), "SETTLED");
        assertEquals(amqpBeginEx.receiverSettleMode().toString(), "FIRST");
    }

    @Test
    public void shouldEncodeAmqpDataExtensionWithRequiredFields()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(amqpDataEx.deliveryId(), 0);
        assertEquals(amqpDataEx.deliveryTag().toString(), "AMQP_BINARY [length=2, bytes=octets[2]]");
        assertEquals(amqpDataEx.messageFormat(), 0);
        assertEquals(amqpDataEx.flags(), 1);
    }

    @Test
    public void shouldEncodeAmqpDataExtensionWithAnnotations()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .annotation("annotation1", "1")
            .annotation(1L, "0")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        amqpDataEx.annotations().forEach(a ->
            assertEquals(a.value().toString(), "AMQP_BINARY [length=1, bytes=octets[1]]"));
    }

    @Test
    public void shouldEncodeAmqpDataExtensionWithProperties()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .messageId("message1")
            .userId("user1")
            .to("clients")
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
                assertEquals(p.to().asString(), "clients");
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
    public void shouldEncodeAmqpDataExtensionWithLongProperties()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .messageId(12345L)
            .userId("user1")
            .to("clients")
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
    public void shouldEncodeAmqpDataExtensionWithByteArrayProperties()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .messageId("message1".getBytes(StandardCharsets.UTF_8))
            .userId("user1")
            .to("clients")
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
    public void shouldEncodeAmqpDataExtensionWithApplicationProperties()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .applicationProperty("annotation", "property1")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        amqpDataEx.applicationProperties().forEach(a ->
        {
            assertEquals(a.key().asString(), "annotation");
            assertEquals(a.value().asString(), "property1");
        });
    }

    @Test
    public void shouldEncodeAmqpDataExtensionWithAllAmqpTransferFlagSet()
    {
        final byte[] array = dataEx()
                .typeId(0)
                .deliveryId(0)
                .deliveryTag("00")
                .messageFormat(0)
                .flags("BATCHABLE", "ABORTED", "RESUME", "SETTLED")
                .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(0x0F, amqpDataEx.flags());
    }

    @Test
    public void shouldMatchAmqpDataExtension() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("BATCHABLE", "ABORTED", "RESUME", "SETTLED")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(15)
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtension() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deliveryId(2)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(15)
            .build();

        matcher.match(byteBuf);
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithOnlyAnnotations() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .annotation("annotation1", "1")
            .annotation(1L, "0")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .annotations(b -> b.item(i -> i.key(k -> k.name("annotation1"))
                                           .value(v -> v.bytes(b2 -> b2.set("1".getBytes()))))
                               .item(i -> i.key(k2 -> k2.id(1L))
                                           .value(v -> v.bytes(b2 -> b2.set("0".getBytes())))))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithOnlyPropertiesWithStringMessageId() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deliveryId(0)
            .messageId("message1")
            .userId("user1")
            .to("clients")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .properties(b -> b.item(i -> i.messageId(m -> m.stringtype("message1")))
                              .item(i2 -> i2.userId(u -> u.bytes(b2 -> b2.set("user1".getBytes()))))
                              .item(i3 -> i3.to("clients")))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithOnlyPropertiesWithLongMessageId() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .messageId(1L)
            .userId("user1")
            .to("clients")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .properties(b -> b.item(i -> i.messageId(m -> m.ulong(1L)))
                .item(i2 -> i2.userId(u -> u.bytes(b2 -> b2.set("user1".getBytes()))))
                .item(i3 -> i3.to("clients")))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithOnlyPropertiesWithBinaryMessageId() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .messageId("message1".getBytes())
            .userId("user1")
            .to("clients")
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

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .properties(b -> b.item(i -> i.messageId(m -> m.binary(b1 -> b1.bytes(b2 -> b2.set("message1".getBytes())))))
                              .item(i2 -> i2.userId(u -> u.bytes(b2 -> b2.set("user1".getBytes(StandardCharsets.UTF_8)))))
                              .item(i3 -> i3.to("clients"))
                              .item(i4 -> i4.subject("subject1"))
                              .item(i5 -> i5.replyTo("localhost"))
                              .item(i6 -> i6.correlationId(c -> c.stringtype("correlationId1")))
                              .item(i7 -> i7.contentType("content_type"))
                              .item(i8 -> i8.contentEncoding("content_encoding"))
                              .item(i9 -> i9.absoluteExpiryTime(12345L))
                              .item(i10 -> i10.creationTime(12345L))
                              .item(i11 -> i11.groupId("group_id1"))
                              .item(i12 -> i12.groupSequence(1))
                              .item(i13 -> i13.replyToGroupId("reply_group_id")))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithOnlyPropertiesWithLongCorrelationId() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .messageId("message1".getBytes())
            .userId("user1")
            .to("clients")
            .subject("subject1")
            .replyTo("localhost")
            .correlationId(12345L)
            .contentType("content_type")
            .contentEncoding("content_encoding")
            .absoluteExpiryTime(12345L)
            .creationTime(12345L)
            .groupId("group_id1")
            .groupSequence(1)
            .replyToGroupId("reply_group_id")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .properties(b -> b.item(i -> i.messageId(m -> m.binary(b1 -> b1.bytes(b2 -> b2.set("message1".getBytes())))))
                .item(i2 -> i2.userId(u -> u.bytes(b2 -> b2.set("user1".getBytes(StandardCharsets.UTF_8)))))
                .item(i3 -> i3.to("clients"))
                .item(i4 -> i4.subject("subject1"))
                .item(i5 -> i5.replyTo("localhost"))
                .item(i6 -> i6.correlationId(c -> c.ulong(12345L)))
                .item(i7 -> i7.contentType("content_type"))
                .item(i8 -> i8.contentEncoding("content_encoding"))
                .item(i9 -> i9.absoluteExpiryTime(12345L))
                .item(i10 -> i10.creationTime(12345L))
                .item(i11 -> i11.groupId("group_id1"))
                .item(i12 -> i12.groupSequence(1))
                .item(i13 -> i13.replyToGroupId("reply_group_id")))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithOnlyPropertiesWithBinaryCorrelationId() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .messageId("message1".getBytes())
            .userId("user1")
            .to("clients")
            .subject("subject1")
            .replyTo("localhost")
            .correlationId("correlationId1".getBytes())
            .contentType("content_type")
            .contentEncoding("content_encoding")
            .absoluteExpiryTime(12345L)
            .creationTime(12345L)
            .groupId("group_id1")
            .groupSequence(1)
            .replyToGroupId("reply_group_id")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .properties(b -> b.item(i -> i.messageId(m -> m.binary(b1 -> b1.bytes(b2 -> b2.set("message1".getBytes())))))
                .item(i2 -> i2.userId(u -> u.bytes(b2 -> b2.set("user1".getBytes(StandardCharsets.UTF_8)))))
                .item(i3 -> i3.to("clients"))
                .item(i4 -> i4.subject("subject1"))
                .item(i5 -> i5.replyTo("localhost"))
                .item(i6 -> i6.correlationId(c -> c.binary(b3 -> b3.bytes(b4 -> b4.set("correlationId1".getBytes())))))
                .item(i7 -> i7.contentType("content_type"))
                .item(i8 -> i8.contentEncoding("content_encoding"))
                .item(i9 -> i9.absoluteExpiryTime(12345L))
                .item(i10 -> i10.creationTime(12345L))
                .item(i11 -> i11.groupId("group_id1"))
                .item(i12 -> i12.groupSequence(1))
                .item(i13 -> i13.replyToGroupId("reply_group_id")))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithOnlyApplicationProperties() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .applicationProperty("property1", "1")
            .applicationProperty("property2", "2")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .properties(b -> b.item(i -> i.messageId(m -> m.stringtype("message1")))
                              .item(i2 -> i2.userId(u -> u.bytes(b2 -> b2.set("user1".getBytes()))))
                              .item(i3 -> i3.to("clients")))
            .applicationProperties(b -> b.item(i -> i.key("property1").value("1"))
                                         .item(i -> i.key("property2").value("2")))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldEncodeAmqpAbortExtension()
    {
        final byte[] array = abortEx()
            .typeId(0)
            .condition("amqp:link:transfer-limit-exceeded")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpAbortExFW amqpAbortEx = new AmqpAbortExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(amqpAbortEx.condition().asString(), "amqp:link:transfer-limit-exceeded");
    }

    @Test
    public void shouldRandomizeBytes() throws Exception
    {
        final byte[] bytes = randomBytes(600);

        assertNotNull(bytes);
        assertEquals(600, bytes.length);
    }
}
