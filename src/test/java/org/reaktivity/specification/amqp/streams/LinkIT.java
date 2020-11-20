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

public class LinkIT
{
    private final K3poRule k3po = new K3poRule()
        .addScriptRoot("scripts", "org/reaktivity/specification/amqp/link");

    private final TestRule timeout = new DisableOnDebug(new Timeout(5, SECONDS));

    @Rule
    public final TestRule chain = outerRule(k3po).around(timeout);

    @Test
    @Specification({
        "${scripts}/attach.as.receiver.only/client",
        "${scripts}/attach.as.receiver.only/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeAttachAsReceiver() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/attach.as.sender.only/client",
        "${scripts}/attach.as.sender.only/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeAttachAsSender() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/attach.as.sender.then.receiver/client",
        "${scripts}/attach.as.sender.then.receiver/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeAttachAsSenderThenReceiver() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/attach.as.receiver.then.sender/client",
        "${scripts}/attach.as.receiver.then.sender/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeAttachAsReceiverThenSender() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/attach.as.receiver.when.source.does.not.exist/client",
        "${scripts}/attach.as.receiver.when.source.does.not.exist/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldAttachAsReceiverWhenSourceDoesNotExist() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/attach.as.sender.when.target.does.not.exist/client",
        "${scripts}/attach.as.sender.when.target.does.not.exist/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldAttachAsSenderWhenTargetDoesNotExist() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.at.least.once/client",
        "${scripts}/transfer.to.client.at.least.once/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientAtLeastOnce() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.at.least.once/client",
        "${scripts}/transfer.to.server.at.least.once/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerAtLeastOnce() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/detach.exchange/client",
        "${scripts}/detach.exchange/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldDetachLink() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/link.credit.exceeded/client",
        "${scripts}/link.credit.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldDetachLinkCreditExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.headers/client",
        "${scripts}/transfer.to.client.with.headers/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithHeaders() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.delivery.annotations/client",
        "${scripts}/transfer.to.client.with.delivery.annotations/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithDeliveryAnnotations() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.annotations/client",
        "${scripts}/transfer.to.client.with.annotations/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithAnnotations() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.headers/client",
        "${scripts}/transfer.to.server.with.headers/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithHeaders() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.delivery.annotations/client",
        "${scripts}/transfer.to.server.with.delivery.annotations/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithDeliveryAnnotations() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.annotations/client",
        "${scripts}/transfer.to.server.with.annotations/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithAnnotations() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.properties/client",
        "${scripts}/transfer.to.client.with.properties/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.properties/client",
        "${scripts}/transfer.to.server.with.properties/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.footer/client",
        "${scripts}/transfer.to.client.with.footer/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithFooter() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.footer/client",
        "${scripts}/transfer.to.server.with.footer/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithFooter() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.max.frame.size.exceeded/client",
        "${scripts}/transfer.to.client.when.max.frame.size.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenMaxFrameSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.max.frame.size.exceeded/client",
        "${scripts}/transfer.to.server.when.max.frame.size.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenMaxFrameSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.fragmented/client",
        "${scripts}/transfer.to.client.when.fragmented/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.fragmented/client",
        "${scripts}/transfer.to.server.when.fragmented/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.links.interleaved/client",
        "${scripts}/transfer.to.client.when.links.interleaved/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenLinksInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.links.interleaved/client",
        "${scripts}/transfer.to.server.when.links.interleaved/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenLinksInterleaved() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.links.interleaved.and.max.frame.size.exceeded/client",
        "${scripts}/transfer.to.client.when.links.interleaved.and.max.frame.size.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenLinksInterleavedAndMaxFrameSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.links.interleaved.and.fragmented/client",
        "${scripts}/transfer.to.client.when.links.interleaved.and.fragmented/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenLinksInterleavedAndFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.links.interleaved.and.fragmented/client",
        "${scripts}/transfer.to.server.when.links.interleaved.and.fragmented/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenLinksInterleavedAndFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/max.frame.size.exceeded.with.multiple.sessions.and.links/client",
        "${scripts}/max.frame.size.exceeded.with.multiple.sessions.and.links/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldCloseConnectionWhenMaxFrameSizeExceededWithMultipleSessions() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.array8/client",
        "${scripts}/transfer.to.client.with.array8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithArray8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.array8/client",
        "${scripts}/transfer.to.server.with.array8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithArray8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.array32/client",
        "${scripts}/transfer.to.client.with.array32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithArray32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.array32/client",
        "${scripts}/transfer.to.server.with.array32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithArray32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.boolean/client",
        "${scripts}/transfer.to.client.with.boolean/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithBoolean() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.boolean/client",
        "${scripts}/transfer.to.server.with.boolean/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithBoolean() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.byte/client",
        "${scripts}/transfer.to.client.with.byte/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithByte() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.byte/client",
        "${scripts}/transfer.to.server.with.byte/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithByte() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.char/client",
        "${scripts}/transfer.to.client.with.char/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithChar() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.char/client",
        "${scripts}/transfer.to.server.with.char/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithChar() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.false/client",
        "${scripts}/transfer.to.client.with.false/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithFalse() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.false/client",
        "${scripts}/transfer.to.server.with.false/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithFalse() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.int/client",
        "${scripts}/transfer.to.client.with.int/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithInt() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.int/client",
        "${scripts}/transfer.to.server.with.int/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithInt() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.list0/client",
        "${scripts}/transfer.to.client.with.list0/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithList0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.list0/client",
        "${scripts}/transfer.to.server.with.list0/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithList0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.list8/client",
        "${scripts}/transfer.to.client.with.list8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithList8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.list8/client",
        "${scripts}/transfer.to.server.with.list8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithList8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.list32/client",
        "${scripts}/transfer.to.client.with.list32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithList32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.list32/client",
        "${scripts}/transfer.to.server.with.list32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithList32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.long/client",
        "${scripts}/transfer.to.client.with.long/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithLong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.long/client",
        "${scripts}/transfer.to.server.with.long/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithLong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.map8/client",
        "${scripts}/transfer.to.client.with.map8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithMap8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.map8/client",
        "${scripts}/transfer.to.server.with.map8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithMap8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.map32/client",
        "${scripts}/transfer.to.client.with.map32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithMap32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.map32/client",
        "${scripts}/transfer.to.server.with.map32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithMap32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.multiple.data/client",
        "${scripts}/transfer.to.client.with.multiple.data/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithMultipleData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.multiple.data/client",
        "${scripts}/transfer.to.server.with.multiple.data/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithMultipleData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.multiple.sequence/client",
        "${scripts}/transfer.to.client.with.multiple.sequence/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithMultipleSequence() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.multiple.sequence/client",
        "${scripts}/transfer.to.server.with.multiple.sequence/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithMultipleSequence() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.null/client",
        "${scripts}/transfer.to.client.with.null/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithNull() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.null/client",
        "${scripts}/transfer.to.server.with.null/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithNull() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.short/client",
        "${scripts}/transfer.to.client.with.short/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithShort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.short/client",
        "${scripts}/transfer.to.server.with.short/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithShort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.single.data/client",
        "${scripts}/transfer.to.client.with.single.data/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithSingleData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.single.data/client",
        "${scripts}/transfer.to.server.with.single.data/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithSingleData() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.single.sequence/client",
        "${scripts}/transfer.to.client.with.single.sequence/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithSingleSequence() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.single.sequence/client",
        "${scripts}/transfer.to.server.with.single.sequence/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithSingleSequence() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.smallint/client",
        "${scripts}/transfer.to.client.with.smallint/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithSmallInt() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.smallint/client",
        "${scripts}/transfer.to.server.with.smallint/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithSmallInt() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.smalllong/client",
        "${scripts}/transfer.to.client.with.smalllong/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithSmallLong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.smalllong/client",
        "${scripts}/transfer.to.server.with.smalllong/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithSmallLong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.smalluint/client",
        "${scripts}/transfer.to.client.with.smalluint/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithSmallUint() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.smalluint/client",
        "${scripts}/transfer.to.server.with.smalluint/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithSmallUint() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.smallulong/client",
        "${scripts}/transfer.to.client.with.smallulong/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithSmallUlong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.smallulong/client",
        "${scripts}/transfer.to.server.with.smallulong/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithSmallUlong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.str8utf8/client",
        "${scripts}/transfer.to.client.with.str8utf8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithStr8Utf8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.str8utf8/client",
        "${scripts}/transfer.to.server.with.str8utf8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithStr8Utf8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.str32utf8/client",
        "${scripts}/transfer.to.client.with.str32utf8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithStr32Utf8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.str32utf8/client",
        "${scripts}/transfer.to.server.with.str32utf8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithStr32Utf8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.sym8/client",
        "${scripts}/transfer.to.client.with.sym8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithSym8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.sym8/client",
        "${scripts}/transfer.to.server.with.sym8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithSym8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.sym32/client",
        "${scripts}/transfer.to.client.with.sym32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithSym32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.sym32/client",
        "${scripts}/transfer.to.server.with.sym32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithSym32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.timestamp/client",
        "${scripts}/transfer.to.client.with.timestamp/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithTimestamp() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.timestamp/client",
        "${scripts}/transfer.to.server.with.timestamp/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithTimestamp() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.true/client",
        "${scripts}/transfer.to.client.with.true/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithTrue() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.true/client",
        "${scripts}/transfer.to.server.with.true/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithTrue() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.ubyte/client",
        "${scripts}/transfer.to.client.with.ubyte/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithUbyte() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.ubyte/client",
        "${scripts}/transfer.to.server.with.ubyte/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithUbyte() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.uint/client",
        "${scripts}/transfer.to.client.with.uint/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithUint() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.uint/client",
        "${scripts}/transfer.to.server.with.uint/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithUint() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.uint0/client",
        "${scripts}/transfer.to.client.with.uint0/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithUint0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.uint0/client",
        "${scripts}/transfer.to.server.with.uint0/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithUint0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.ulong/client",
        "${scripts}/transfer.to.client.with.ulong/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithUlong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.ulong/client",
        "${scripts}/transfer.to.server.with.ulong/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithUlong() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.ulong0/client",
        "${scripts}/transfer.to.client.with.ulong0/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithUlong0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.ulong0/client",
        "${scripts}/transfer.to.server.with.ulong0/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithUlong0() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.ushort/client",
        "${scripts}/transfer.to.client.with.ushort/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithUshort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.ushort/client",
        "${scripts}/transfer.to.server.with.ushort/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithUshort() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.vbin8/client",
        "${scripts}/transfer.to.client.with.vbin8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithVbin8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.vbin8/client",
        "${scripts}/transfer.to.server.with.vbin8/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithVbin8() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.vbin32/client",
        "${scripts}/transfer.to.client.with.vbin32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithVbin32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.vbin32/client",
        "${scripts}/transfer.to.server.with.vbin32/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithVbin32() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.first.fragment.aborted/client",
        "${scripts}/transfer.to.client.when.first.fragment.aborted/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenFirstFragmentAborted() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/attach.as.receiver.then.detach.with.error.then.flow/client",
        "${scripts}/attach.as.receiver.then.detach.with.error.then.flow/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldNotTriggerErrorWhenReceivingFlowAfterDetach() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.middle.fragment.aborted/client",
        "${scripts}/transfer.to.client.when.middle.fragment.aborted/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenMiddleFragmentAborted() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
    @Test
    @Specification({
        "${scripts}/handle.max.exceeded/client",
        "${scripts}/handle.max.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldCloseConnectionWhenHandleMaxExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.when.last.fragment.aborted/client",
        "${scripts}/transfer.to.client.when.last.fragment.aborted/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWhenLastFragmentAborted() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
    @Test
    @Specification({
        "${scripts}/reject.attach.when.handle.in.use/client",
        "${scripts}/reject.attach.when.handle.in.use/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldCloseConnectionWhenAttachWithHandleInUse() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.first.fragment.aborted/client",
        "${scripts}/transfer.to.server.when.first.fragment.aborted/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenFirstFragmentAborted() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.max.message.size.exceeded/client",
        "${scripts}/transfer.to.server.max.message.size.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldAttachAsSenderThenDetachWhenMaxMessageSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.middle.fragment.aborted/client",
        "${scripts}/transfer.to.server.when.middle.fragment.aborted/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenMiddleFragmentAborted() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.max.message.size.exceeded/client",
        "${scripts}/transfer.to.client.max.message.size.exceeded/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldAttachAsReceiverThenDetachWhenMaxMessageSizeExceeded() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.when.last.fragment.aborted/client",
        "${scripts}/transfer.to.server.when.last.fragment.aborted/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWhenLastFragmentAborted() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.then.flow.with.echo.on.session/client",
        "${scripts}/transfer.to.server.then.flow.with.echo.on.session/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerThenFlowWithEchoOnSession() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.then.flow.with.echo.on.link/client",
        "${scripts}/transfer.to.server.then.flow.with.echo.on.link/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerThenFlowWithEchoOnLink() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/reject.flow.with.inconsistent.fields/client",
        "${scripts}/reject.flow.with.inconsistent.fields/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldCloseConnectionWhenFlowWithInconsistentFields() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/flow.without.handle/client",
        "${scripts}/flow.without.handle/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldSupportFlowWithoutHandle() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/flow.with.unattached.handle/client",
        "${scripts}/flow.with.unattached.handle/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldRejectFlowWithUnattachedHandle() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.large.delivery.count/client",
        "${scripts}/transfer.to.server.with.large.delivery.count/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithLargeDeliveryCount() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/reject.transfer.with.more.inconsistent.fields/client",
        "${scripts}/reject.transfer.with.more.inconsistent.fields/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldRejectTransferWithMoreWhenFieldsAreInconsistent() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.large.next.incoming.id/client",
        "${scripts}/transfer.to.server.with.large.next.incoming.id/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithLargeNextIncomingId() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.invalid.delivery.id/client",
        "${scripts}/transfer.to.server.with.invalid.delivery.id/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithInvalidDeliveryId() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server.with.application.properties/client",
        "${scripts}/transfer.to.server.with.application.properties/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerWithApplicationProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.client.with.application.properties/client",
        "${scripts}/transfer.to.client.with.application.properties/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClientWithApplicationProperties() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/reject.durable.message.when.durable.not.supported/client",
        "${scripts}/reject.durable.message.when.durable.not.supported/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldRejectDurableMessageWhenDurableNotSupported() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
}
