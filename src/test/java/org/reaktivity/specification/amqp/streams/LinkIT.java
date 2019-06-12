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
        "${scripts}/attach.as.sender.and.receiver/client",
        "${scripts}/attach.as.sender.and.receiver/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeAttachAsSenderAndReceiver() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/attach.as.receiver.and.sender/client",
        "${scripts}/attach.as.receiver.and.sender/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldExchangeAttachAsReceiverAndSender() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.as.receiver/client",
        "${scripts}/transfer.as.receiver/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldReceiveTransferAndSendDisposition() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }

    @Test
    @Specification({
        "${scripts}/transfer.as.sender/client",
        "${scripts}/transfer.as.sender/server"})
    @ScriptProperty("serverTransport \"nukleus://streams/amqp#0\"")
    public void shouldSendTransferAndReceiveDisposition() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_SERVER");
        k3po.finish();
    }
}
