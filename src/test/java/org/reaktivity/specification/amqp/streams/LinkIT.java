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
        "${scripts}/transfer.to.client/client",
        "${scripts}/transfer.to.client/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToClient() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.to.server/client",
        "${scripts}/transfer.to.server/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldTransferToServerAtMostOnce() throws Exception
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
        "${scripts}/detach.link/client",
        "${scripts}/detach.link/server"})
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
    public void shouldRejectTransferWhenLinkCreditMaxExceeded() throws Exception
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
}
