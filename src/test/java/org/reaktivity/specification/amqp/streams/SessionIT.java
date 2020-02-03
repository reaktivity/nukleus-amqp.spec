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
}
