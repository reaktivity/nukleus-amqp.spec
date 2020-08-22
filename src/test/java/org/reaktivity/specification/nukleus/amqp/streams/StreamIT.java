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
package org.reaktivity.specification.nukleus.amqp.streams;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.rules.RuleChain.outerRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.kaazing.k3po.junit.annotation.ScriptProperty;
import org.kaazing.k3po.junit.annotation.Specification;
import org.kaazing.k3po.junit.rules.K3poRule;

public class StreamIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("streams", "org/reaktivity/specification/nukleus/amqp/streams");

    private final TestRule timeout = new DisableOnDebug(new Timeout(10, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/connect.as.receiver.only/client",
        "${streams}/connect.as.receiver.only/server"
    })
    public void shouldConnectAsReceiverOnly() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/connect.as.receiver.then.sender/client",
        "${streams}/connect.as.receiver.then.sender/server"
    })
    public void shouldConnectAsReceiverThenSender() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/connect.as.sender.only/client",
        "${streams}/connect.as.sender.only/server"
    })
    public void shouldConnectAsSenderOnly() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/connect.as.sender.then.receiver/client",
        "${streams}/connect.as.sender.then.receiver/server"
    })
    public void shouldConnectAsSenderThenReceiver() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/disconnect.abort/client",
        "${streams}/disconnect.abort/server"
    })
    public void shouldAbortConnection() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.at.least.once/client",
        "${streams}/send.to.client.at.least.once/server"
    })
    public void shouldSendToClientAtLeastOnce() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.at.least.once/client",
        "${streams}/send.to.server.at.least.once/server"
    })
    public void shouldSendToServerAtLeastOnce() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/incoming.window.exceeded/client",
        "${streams}/incoming.window.exceeded/server"
    })
    public void shouldEndSessionWhenIncomingWindowExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.through.multiple.sessions/client",
        "${streams}/send.to.client.through.multiple.sessions/server"
    })
    public void shouldSendToClientThroughMultipleSessions() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.annotations/client",
        "${streams}/send.to.client.with.annotations/server"
    })
    public void shouldSendToClientWithAnnotations() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.annotations/client",
        "${streams}/send.to.server.with.annotations/server"
    })
    public void shouldSendToServerWithAnnotations() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.properties/client",
        "${streams}/send.to.client.with.properties/server"
    })
    public void shouldSendToClientWithProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.properties/client",
        "${streams}/send.to.server.with.properties/server"
    })
    public void shouldSendToServerWithProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.application.properties/client",
        "${streams}/send.to.client.with.application.properties/server"
    })
    public void shouldSendToClientWithApplicationProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.application.properties/client",
        "${streams}/send.to.server.with.application.properties/server"
    })
    public void shouldSendToServerWithApplicationProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.when.max.frame.size.exceeded/client",
        "${streams}/send.to.client.when.max.frame.size.exceeded/server"
    })
    public void shouldSendToClientWhenMaxFrameSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.when.max.frame.size.exceeded/client",
        "${streams}/send.to.server.when.max.frame.size.exceeded/server"
    })
    public void shouldSendToServerWhenMaxFrameSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.when.fragmented/client",
        "${streams}/send.to.client.when.fragmented/server"
    })
    public void shouldSendToClientWhenFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.when.fragmented/client",
        "${streams}/send.to.server.when.fragmented/server"
    })
    public void shouldSendToServerWhenFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.when.links.interleaved/client",
        "${streams}/send.to.client.when.links.interleaved/server"
    })
    public void shouldSendToClientWhenLinksInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.when.links.interleaved/client",
        "${streams}/send.to.server.when.links.interleaved/server"
    })
    public void shouldSendToServerWhenLinksInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.when.links.interleaved.and.fragmented/client",
        "${streams}/send.to.client.when.links.interleaved.and.fragmented/server"
    })
    public void shouldSendToClientWhenLinksInterleavedAndFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.when.links.interleaved.and.fragmented/client",
        "${streams}/send.to.server.when.links.interleaved.and.fragmented/server"
    })
    public void shouldSendToServerWhenLinksInterleavedAndFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.when.sessions.interleaved/client",
        "${streams}/send.to.client.when.sessions.interleaved/server"
    })
    public void shouldSendToClientWhenSessionsInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.when.sessions.interleaved/client",
        "${streams}/send.to.server.when.sessions.interleaved/server"
    })
    public void shouldSendToServerWhenSessionsInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.when.sessions.interleaved.and.fragmented/client",
        "${streams}/send.to.client.when.sessions.interleaved.and.fragmented/server"
    })
    public void shouldSendToClientWhenSessionsInterleavedAndFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.when.sessions.interleaved.and.fragmented/client",
        "${streams}/send.to.server.when.sessions.interleaved.and.fragmented/server"
    })
    public void shouldSendToServerWhenSessionsInterleavedAndFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.when.sessions.interleaved.and.max.frame.size.exceeded/client",
        "${streams}/send.to.client.when.sessions.interleaved.and.max.frame.size.exceeded/server"
    })
    public void shouldSendToClientWhenSessionsInterleavedAndMaxFrameSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.when.links.interleaved.and.max.frame.size.exceeded/client",
        "${streams}/send.to.client.when.links.interleaved.and.max.frame.size.exceeded/server"
    })
    public void shouldSendToClientFragmentedAndLinkedInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/link.credit.exceeded/client",
        "${streams}/link.credit.exceeded/server"
    })
    public void shouldDetachLinkWhenLinkCreditExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/max.frame.size.exceeded.with.multiple.sessions.and.links/client",
        "${streams}/max.frame.size.exceeded.with.multiple.sessions.and.links/server"
    })
    public void shouldCloseConnectionWhenMaxFrameSizeExceededWithMultipleSessions() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.array8/client",
        "${streams}/send.to.client.with.array8/server"
    })
    public void shouldSendToClientWithArray8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.array8/client",
        "${streams}/send.to.server.with.array8/server"
    })
    public void shouldSendToServerWithArray8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.array32/client",
        "${streams}/send.to.client.with.array32/server"
    })
    public void shouldSendToClientWithArray32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.array32/client",
        "${streams}/send.to.server.with.array32/server"
    })
    public void shouldSendToServerWithArray32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.boolean/client",
        "${streams}/send.to.client.with.boolean/server"
    })
    public void shouldSendToClientWithBoolean() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.boolean/client",
        "${streams}/send.to.server.with.boolean/server"
    })
    public void shouldSendToServerWithBoolean() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.byte/client",
        "${streams}/send.to.client.with.byte/server"
    })
    public void shouldSendToClientWithByte() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.byte/client",
        "${streams}/send.to.server.with.byte/server"
    })
    public void shouldSendToServerWithByte() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.char/client",
        "${streams}/send.to.client.with.char/server"
    })
    public void shouldSendToClientWithChar() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.char/client",
        "${streams}/send.to.server.with.char/server"
    })
    public void shouldSendToServerWithChar() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.false/client",
        "${streams}/send.to.client.with.false/server"
    })
    public void shouldSendToClientWithFalse() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.false/client",
        "${streams}/send.to.server.with.false/server"
    })
    public void shouldSendToServerWithFalse() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.int/client",
        "${streams}/send.to.client.with.int/server"
    })
    public void shouldSendToClientWithInt() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.int/client",
        "${streams}/send.to.server.with.int/server"
    })
    public void shouldSendToServerWithInt() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.list0/client",
        "${streams}/send.to.client.with.list0/server"
    })
    public void shouldSendToClientWithList0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.list0/client",
        "${streams}/send.to.server.with.list0/server"
    })
    public void shouldSendToServerWithList0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.list8/client",
        "${streams}/send.to.client.with.list8/server"
    })
    public void shouldSendToClientWithList8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.list8/client",
        "${streams}/send.to.server.with.list8/server"
    })
    public void shouldSendToServerWithList8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }


    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.list32/client",
        "${streams}/send.to.client.with.list32/server"
    })
    public void shouldSendToClientWithList32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.list32/client",
        "${streams}/send.to.server.with.list32/server"
    })
    public void shouldSendToServerWithList32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.long/client",
        "${streams}/send.to.client.with.long/server"
    })
    public void shouldSendToClientWithLong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.long/client",
        "${streams}/send.to.server.with.long/server"
    })
    public void shouldSendToServerWithLong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.map8/client",
        "${streams}/send.to.client.with.map8/server"
    })
    public void shouldSendToClientWithMap8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.map8/client",
        "${streams}/send.to.server.with.map8/server"
    })
    public void shouldSendToServerWithMap8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.map32/client",
        "${streams}/send.to.client.with.map32/server"
    })
    public void shouldSendToClientWithMap32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.map32/client",
        "${streams}/send.to.server.with.map32/server"
    })
    public void shouldSendToServerWithMap32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.multiple.data/client",
        "${streams}/send.to.client.with.multiple.data/server"
    })
    public void shouldSendToClientWithMultipleData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.multiple.data/client",
        "${streams}/send.to.server.with.multiple.data/server"
    })
    public void shouldSendToServerWithMultipleData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.multiple.sequence/client",
        "${streams}/send.to.client.with.multiple.sequence/server"
    })
    public void shouldSendToClientWithMultipleSequence() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.multiple.sequence/client",
        "${streams}/send.to.server.with.multiple.sequence/server"
    })
    public void shouldSendToServerWithMultipleSequence() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.null/client",
        "${streams}/send.to.client.with.null/server"
    })
    public void shouldSendToClientWithNull() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.null/client",
        "${streams}/send.to.server.with.null/server"
    })
    public void shouldSendToServerWithNull() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.short/client",
        "${streams}/send.to.client.with.short/server"
    })
    public void shouldSendToClientWithShort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.short/client",
        "${streams}/send.to.server.with.short/server"
    })
    public void shouldSendToServerWithShort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.single.data/client",
        "${streams}/send.to.client.with.single.data/server"
    })
    public void shouldSendToClientWithSingleData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.single.data/client",
        "${streams}/send.to.server.with.single.data/server"
    })
    public void shouldSendToServerWithSingleData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.single.sequence/client",
        "${streams}/send.to.client.with.single.sequence/server"
    })
    public void shouldSendToClientWithSingleSequence() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.single.sequence/client",
        "${streams}/send.to.server.with.single.sequence/server"
    })
    public void shouldSendToServerWithSingleSequence() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.smallint/client",
        "${streams}/send.to.client.with.smallint/server"
    })
    public void shouldSendToClientWithSmallInt() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.smallint/client",
        "${streams}/send.to.server.with.smallint/server"
    })
    public void shouldSendToServerWithSmallInt() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.smalllong/client",
        "${streams}/send.to.client.with.smalllong/server"
    })
    public void shouldSendToClientWithSmallLong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.smalllong/client",
        "${streams}/send.to.server.with.smalllong/server"
    })
    public void shouldSendToServerWithSmallLong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.smalluint/client",
        "${streams}/send.to.client.with.smalluint/server"
    })
    public void shouldSendToClientWithSmallUint() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.smalluint/client",
        "${streams}/send.to.server.with.smalluint/server"
    })
    public void shouldSendToServerWithSmallUint() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.smallulong/client",
        "${streams}/send.to.client.with.smallulong/server"
    })
    public void shouldSendToClientWithSmallUlong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.smallulong/client",
        "${streams}/send.to.server.with.smallulong/server"
    })
    public void shouldSendToServerWithSmallUlong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.str8utf8/client",
        "${streams}/send.to.client.with.str8utf8/server"
    })
    public void shouldSendToClientWithStr8Utf8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.str8utf8/client",
        "${streams}/send.to.server.with.str8utf8/server"
    })
    public void shouldSendToServerWithStr8Utf8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.str32utf8/client",
        "${streams}/send.to.client.with.str32utf8/server"
    })
    public void shouldSendToClientWithStr32Utf8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.str32utf8/client",
        "${streams}/send.to.server.with.str32utf8/server"
    })
    public void shouldSendToServerWithStr32Utf8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.sym8/client",
        "${streams}/send.to.client.with.sym8/server"
    })
    public void shouldSendToClientWithSym8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.sym8/client",
        "${streams}/send.to.server.with.sym8/server"
    })
    public void shouldSendToServerWithSym8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.sym32/client",
        "${streams}/send.to.client.with.sym32/server"
    })
    public void shouldSendToClientWithSym32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.sym32/client",
        "${streams}/send.to.server.with.sym32/server"
    })
    public void shouldSendToServerWithSym32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.timestamp/client",
        "${streams}/send.to.client.with.timestamp/server"
    })
    public void shouldSendToClientWithTimestamp() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.timestamp/client",
        "${streams}/send.to.server.with.timestamp/server"
    })
    public void shouldSendToServerWithTimestamp() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.true/client",
        "${streams}/send.to.client.with.true/server"
    })
    public void shouldSendToClientWithTrue() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.true/client",
        "${streams}/send.to.server.with.true/server"
    })
    public void shouldSendToServerWithTrue() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.ubyte/client",
        "${streams}/send.to.client.with.ubyte/server"
    })
    public void shouldSendToClientWithUbyte() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.ubyte/client",
        "${streams}/send.to.server.with.ubyte/server"
    })
    public void shouldSendToServerWithUbyte() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.uint/client",
        "${streams}/send.to.client.with.uint/server"
    })
    public void shouldSendToClientWithUint() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.uint/client",
        "${streams}/send.to.server.with.uint/server"
    })
    public void shouldSendToServerWithUint() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.uint0/client",
        "${streams}/send.to.client.with.uint0/server"
    })
    public void shouldSendToClientWithUint0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.uint0/client",
        "${streams}/send.to.server.with.uint0/server"
    })
    public void shouldSendToServerWithUint0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.ulong/client",
        "${streams}/send.to.client.with.ulong/server"
    })
    public void shouldSendToClientWithUlong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.ulong/client",
        "${streams}/send.to.server.with.ulong/server"
    })
    public void shouldSendToServerWithUlong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.ulong0/client",
        "${streams}/send.to.client.with.ulong0/server"
    })
    public void shouldSendToClientWithUlong0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.ulong0/client",
        "${streams}/send.to.server.with.ulong0/server"
    })
    public void shouldSendToServerWithUlong0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.ushort/client",
        "${streams}/send.to.client.with.ushort/server"
    })
    public void shouldSendToClientWithUshort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.ushort/client",
        "${streams}/send.to.server.with.ushort/server"
    })
    public void shouldSendToServerWithUshort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.vbin8/client",
        "${streams}/send.to.client.with.vbin8/server"
    })
    public void shouldSendToClientWithVbin8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.vbin8/client",
        "${streams}/send.to.server.with.vbin8/server"
    })
    public void shouldSendToServerWithVbin8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.client.with.vbin32/client",
        "${streams}/send.to.client.with.vbin32/server"
    })
    public void shouldSendToClientWithVbin32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Test
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    @Specification({
        "${streams}/send.to.server.with.vbin32/client",
        "${streams}/send.to.server.with.vbin32/server"
    })
    public void shouldSendToServerWithVbin32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }
}
