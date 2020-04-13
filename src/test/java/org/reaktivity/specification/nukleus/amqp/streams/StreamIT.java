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

import org.junit.Ignore;
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
        "${streams}/send.to.client.at.most.once/client",
        "${streams}/send.to.client.at.most.once/server"
    })
    public void shouldSendToClientAtMostOnce() throws Exception
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
        "${streams}/send.to.server.at.most.once/client",
        "${streams}/send.to.server.at.most.once/server"
    })
    public void shouldSendToServerAtMostOnce() throws Exception
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
        "${streams}/send.to.client.fragmented/client",
        "${streams}/send.to.client.fragmented/server"
    })
    public void shouldSendToClientFragmented() throws Exception
    {
        k3po.start();
        k3po.notifyBarrier("ROUTED_CLIENT");
        k3po.finish();
    }

    @Ignore
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
}
