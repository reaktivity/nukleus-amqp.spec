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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.kaazing.k3po.lang.internal.el.ExpressionFactoryUtils.newExpressionFactory;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.abortEx;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.beginEx;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.dataEx;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.matchDataEx;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.randomBytes;
import static org.reaktivity.specification.amqp.internal.AmqpFunctions.routeEx;
import static org.reaktivity.specification.amqp.internal.types.AmqpBodyKind.VALUE;

import java.nio.ByteBuffer;

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
import org.reaktivity.specification.amqp.internal.types.AmqpPropertiesFW;
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
    public void shouldEncodeAmqpRouteExtension()
    {
        final byte[] array = routeEx()
            .address("clients")
            .capabilities("RECEIVE_ONLY")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpRouteExFW amqpRouteEx = new AmqpRouteExFW().wrap(buffer, 0, buffer.capacity());

        assertEquals(amqpRouteEx.address().asString(), "clients");
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
            .bodyKind("VALUE")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(amqpDataEx.deliveryId(), 0);
        assertEquals(amqpDataEx.deliveryTag().toString(), "AMQP_BINARY [length=2, bytes=octets[2]]");
        assertEquals(amqpDataEx.messageFormat(), 0);
        assertEquals(amqpDataEx.flags(), 1);
        assertEquals(amqpDataEx.bodyKind().get(), VALUE);
    }

    @Test
    public void shouldEncodeAmqpDataExtensionWithDeferred()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deferred(100)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .bodyKind("VALUE")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(amqpDataEx.deliveryId(), 0);
        assertEquals(amqpDataEx.deliveryTag().toString(), "AMQP_BINARY [length=2, bytes=octets[2]]");
        assertEquals(amqpDataEx.messageFormat(), 0);
        assertEquals(amqpDataEx.flags(), 1);
        assertEquals(amqpDataEx.deferred(), 100);
        assertEquals(amqpDataEx.bodyKind().get(), VALUE);
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
            .bodyKind("VALUE")
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
            .bodyKind("VALUE")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        AmqpPropertiesFW properties = amqpDataEx.properties();
        assertTrue(properties.hasMessageId());
        assertEquals("message1", properties.messageId().stringtype().asString());
        assertTrue(properties.hasUserId());
        assertEquals("octets[5]", properties.userId().bytes().toString());
        assertTrue(properties.hasTo());
        assertEquals("clients", properties.to().asString());
        assertTrue(properties.hasSubject());
        assertEquals("subject1", properties.subject().asString());
        assertTrue(properties.hasReplyTo());
        assertEquals("localhost", properties.replyTo().asString());
        assertTrue(properties.hasCorrelationId());
        assertEquals("correlationId1", properties.correlationId().stringtype().asString());
        assertTrue(properties.hasContentType());
        assertEquals("content_type", properties.contentType().asString());
        assertTrue(properties.hasContentEncoding());
        assertEquals("content_encoding", properties.contentEncoding().asString());
        assertTrue(properties.hasAbsoluteExpiryTime());
        assertEquals(12345L, properties.absoluteExpiryTime());
        assertTrue(properties.hasCreationTime());
        assertEquals(12345L, properties.creationTime());
        assertTrue(properties.hasGroupId());
        assertEquals("group_id1", properties.groupId().asString());
        assertTrue(properties.hasGroupSequence());
        assertEquals(1, properties.groupSequence());
        assertTrue(properties.hasReplyToGroupId());
        assertEquals("reply_group_id", properties.replyToGroupId().asString());
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
            .bodyKind("VALUE")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        AmqpPropertiesFW properties = amqpDataEx.properties();
        assertTrue(properties.hasMessageId());
        assertEquals(12345L, properties.messageId().ulong());
        assertTrue(properties.hasUserId());
        assertEquals("octets[5]", properties.userId().bytes().toString());
        assertTrue(properties.hasTo());
        assertEquals("clients", properties.to().asString());
        assertTrue(properties.hasSubject());
        assertEquals("subject1", properties.subject().asString());
        assertTrue(properties.hasReplyTo());
        assertEquals("localhost", properties.replyTo().asString());
        assertTrue(properties.hasCorrelationId());
        assertEquals(12345L, properties.correlationId().ulong());
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
            .messageId("message1".getBytes(UTF_8))
            .userId("user1")
            .to("clients")
            .subject("subject1")
            .replyTo("localhost")
            .correlationId("correlation1".getBytes(UTF_8))
            .bodyKind("VALUE")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        AmqpPropertiesFW properties = amqpDataEx.properties();
        assertTrue(properties.hasMessageId());
        assertEquals("octets[8]", properties.messageId().binary().bytes().toString());
        assertTrue(properties.hasUserId());
        assertEquals("octets[5]", properties.userId().bytes().toString());
        assertTrue(properties.hasTo());
        assertEquals("clients", properties.to().asString());
        assertTrue(properties.hasSubject());
        assertEquals("subject1", properties.subject().asString());
        assertTrue(properties.hasReplyTo());
        assertEquals("localhost", properties.replyTo().asString());
        assertTrue(properties.hasCorrelationId());
        assertEquals("octets[12]", properties.correlationId().binary().bytes().toString());
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
            .property("annotation", "property1")
            .bodyKind("VALUE")
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
            .bodyKind("VALUE")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        assertEquals(0x0F, amqpDataEx.flags());
    }

    @Test
    public void shouldEncodeAmqpDataExtensionWithPropertiesAndApplicationProperties()
    {
        final byte[] array = dataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .messageId("message1")
            .property("annotation1", "property1")
            .property("annotation2", "property2")
            .bodyKind("VALUE")
            .build();

        DirectBuffer buffer = new UnsafeBuffer(array);
        AmqpDataExFW amqpDataEx = new AmqpDataExFW().wrap(buffer, 0, buffer.capacity());
        AmqpPropertiesFW properties = amqpDataEx.properties();
        assertTrue(properties.hasMessageId());
        assertEquals("message1", properties.messageId().stringtype().asString());
        amqpDataEx.applicationProperties().forEach(a ->
        {
            String key = a.key().asString();
            switch (key)
            {
            case "annotation1":
                assertEquals(a.value().asString(), "property1");
                break;
            case "annotation2":
                assertEquals(a.value().asString(), "property2");
                break;
            }
        });
    }

    @Test
    public void shouldFailWhenBuildWithoutSettingField() throws Exception
    {
        BytesMatcher matcher = matchDataEx().build();
        ByteBuffer byteBuf = ByteBuffer.allocate(1024);
        assertNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldFailWhenBuildWithoutSettingDeliveryId() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
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
            .bodyKind(b -> b.set(VALUE))
            .build();

        assertNotNull(matcher.match(byteBuf));
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
            .bodyKind(b -> b.set(VALUE))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithDeferred() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deferred(100)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("BATCHABLE", "ABORTED", "RESUME", "SETTLED")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deferred(100)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(15)
            .bodyKind(b -> b.set(VALUE))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionTypeId() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(5)
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
            .flags(1)
            .bodyKind(b -> b.set(VALUE))
            .build();

        matcher.match(byteBuf);
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionDeferred() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deferred(120)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(0)
            .flags("SETTLED")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deferred(60)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .bodyKind(b -> b.set(VALUE))
            .build();

        matcher.match(byteBuf);
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionDeliveryId() throws Exception
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
            .flags(1)
            .bodyKind(b -> b.set(VALUE))
            .build();

        matcher.match(byteBuf);
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionDeliveryTag() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("01")
            .messageFormat(0)
            .flags("SETTLED")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .bodyKind(b -> b.set(VALUE))
            .build();

        matcher.match(byteBuf);
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionMessageFormat() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deliveryId(0)
            .deliveryTag("00")
            .messageFormat(1)
            .flags("SETTLED")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .bodyKind(b -> b.set(VALUE))
            .build();

        matcher.match(byteBuf);
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionFlags() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deliveryId(0)
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
            .bodyKind(b -> b.set(VALUE))
            .build();

        matcher.match(byteBuf);
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionAnnotations() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .annotation("annotation2", "2")
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
            .bodyKind(b -> b.set(VALUE))
            .build();

        matcher.match(byteBuf);
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionProperties() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .deliveryId(0)
            .messageId("message2")
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
            .properties(p -> p.messageId(m -> m.stringtype("message1"))
                              .userId(u -> u.bytes(b2 -> b2.set("user1".getBytes())))
                              .to("clients"))
            .bodyKind(b -> b.set(VALUE))
            .build();

        matcher.match(byteBuf);
    }

    @Test(expected = Exception.class)
    public void shouldNotMatchAmqpDataExtensionApplicationProperties() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .property("property4", "1")
            .property("property3", "2")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .properties(p -> p.messageId(m -> m.stringtype("message1"))
                              .userId(u -> u.bytes(b2 -> b2.set("user1".getBytes())))
                              .to("clients"))
            .applicationProperties(b -> b.item(i -> i.key("property1").value("1"))
                                         .item(i -> i.key("property2").value("2")))
            .bodyKind(b -> b.set(VALUE))
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
            .bodyKind(b -> b.set(VALUE))
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
            .properties(p -> p.messageId(m -> m.stringtype("message1"))
                              .userId(u -> u.bytes(b2 -> b2.set("user1".getBytes())))
                              .to("clients"))
            .bodyKind(b -> b.set(VALUE))
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
            .properties(b -> b.messageId(m -> m.ulong(1L))
                              .userId(u -> u.bytes(b2 -> b2.set("user1".getBytes())))
                              .to("clients"))
            .bodyKind(b -> b.set(VALUE))
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
            .properties(p -> p.messageId(m -> m.binary(b1 -> b1.bytes(b2 -> b2.set("message1".getBytes()))))
                              .userId(u -> u.bytes(b2 -> b2.set("user1".getBytes(UTF_8))))
                              .to("clients")
                              .subject("subject1")
                              .replyTo("localhost")
                              .correlationId(c -> c.stringtype("correlationId1"))
                              .contentType("content_type")
                              .contentEncoding("content_encoding")
                              .absoluteExpiryTime(12345L)
                              .creationTime(12345L)
                              .groupId("group_id1")
                              .groupSequence(1)
                              .replyToGroupId("reply_group_id"))
            .bodyKind(b -> b.set(VALUE))
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
            .properties(p -> p.messageId(m -> m.binary(b1 -> b1.bytes(b2 -> b2.set("message1".getBytes()))))
                              .userId(u -> u.bytes(b2 -> b2.set("user1".getBytes(UTF_8))))
                              .to("clients")
                              .subject("subject1")
                              .replyTo("localhost")
                              .correlationId(c -> c.ulong(12345L))
                              .contentType("content_type")
                              .contentEncoding("content_encoding")
                              .absoluteExpiryTime(12345L)
                              .creationTime(12345L)
                              .groupId("group_id1")
                              .groupSequence(1)
                              .replyToGroupId("reply_group_id"))
            .bodyKind(b -> b.set(VALUE))
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
            .properties(p -> p.messageId(m -> m.binary(b1 -> b1.bytes(b2 -> b2.set("message1".getBytes()))))
                              .userId(u -> u.bytes(b2 -> b2.set("user1".getBytes(UTF_8))))
                              .to("clients")
                              .subject("subject1")
                              .replyTo("localhost")
                              .correlationId(c -> c.binary(b3 -> b3.bytes(b4 -> b4.set("correlationId1".getBytes()))))
                              .contentType("content_type")
                              .contentEncoding("content_encoding")
                              .absoluteExpiryTime(12345L)
                              .creationTime(12345L)
                              .groupId("group_id1")
                              .groupSequence(1)
                              .replyToGroupId("reply_group_id"))
            .bodyKind(b -> b.set(VALUE))
            .build();

        assertNotNull(matcher.match(byteBuf));
    }

    @Test
    public void shouldMatchAmqpDataExtensionWithOnlyApplicationProperties() throws Exception
    {
        BytesMatcher matcher = matchDataEx()
            .typeId(0)
            .property("property1", "1")
            .property("property2", "2")
            .build();

        ByteBuffer byteBuf = ByteBuffer.allocate(1024);

        new AmqpDataExFW.Builder().wrap(new UnsafeBuffer(byteBuf), 0, byteBuf.capacity())
            .typeId(0)
            .deliveryId(0)
            .deliveryTag(b -> b.bytes(b2 -> b2.set("00".getBytes())))
            .messageFormat(0)
            .flags(1)
            .properties(p -> p.messageId(m -> m.stringtype("message1"))
                              .userId(u -> u.bytes(b2 -> b2.set("user1".getBytes())))
                              .to("clients"))
            .applicationProperties(b -> b.item(i -> i.key("property1").value("1"))
                                         .item(i -> i.key("property2").value("2")))
            .bodyKind(b -> b.set(VALUE))
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
