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
package org.reaktivity.specification.amqp.streams;

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

public class SessionIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("scripts", "org/reaktivity/specification/amqp/session");

    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "${scripts}/begin.exchange/client",
        "${scripts}/begin.exchange/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeBegin() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/end.exchange/client",
        "${scripts}/end.exchange/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeEnd() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/begin.channel.max.exceeded/client",
        "${scripts}/begin.channel.max.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldRejectBeginWhenChannelMaxExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/begin.then.close/client",
        "${scripts}/begin.then.close/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeBeginThenClose() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/begin.multiple.sessions/client",
        "${scripts}/begin.multiple.sessions/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeMultipleBegin() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/incoming.window.exceeded/client",
        "${scripts}/incoming.window.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldEndSessionWhenIncomingWindowExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/send.to.client.multiple.sessions/client",
        "${scripts}/send.to.client.multiple.sessions/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldSendToClientMultipleSessions() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.sessions.interleaved/client",
        "${scripts}/transfer.to.client.when.sessions.interleaved/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenSessionsInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.sessions.interleaved/client",
        "${scripts}/transfer.to.server.when.sessions.interleaved/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenSessionsInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.sessions.interleaved.and.max.frame.size.exceeded/client",
        "${scripts}/transfer.to.client.when.sessions.interleaved.and.max.frame.size.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenSessionsInterleavedAndMaxFrameSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.sessions.interleaved.and.fragmented/client",
        "${scripts}/transfer.to.client.when.sessions.interleaved.and.fragmented/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenSessionsInterleavedAndFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.sessions.interleaved.and.fragmented/client",
        "${scripts}/transfer.to.server.when.sessions.interleaved.and.fragmented/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenSessionsInterleavedAndFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/incoming.window.reduced/client",
        "${scripts}/incoming.window.reduced/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldHandleReducedIncomingWindow() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/end.exchange.simultaneous/client",
        "${scripts}/end.exchange.simultaneous/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldEndSessionSimultaneously() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/discard.after.end/client",
        "${scripts}/discard.after.end/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldDiscardInboundAfterOutboundEnd() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
}
