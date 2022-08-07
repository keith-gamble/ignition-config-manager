/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.client.scripting;

import org.json.JSONException;
import org.python.core.PyDictionary;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.bwdesigngroup.ignition.configmanager.common.Constants;
import com.bwdesigngroup.ignition.configmanager.common.scripting.ConfigScriptModule;
import com.bwdesigngroup.ignition.configmanager.common.scripting.ConfigScripts;
import com.inductiveautomation.ignition.client.gateway_interface.ModuleRPCFactory;
import com.inductiveautomation.ignition.common.project.ProjectInvalidException;
/**
 *
 * @author Keith Gamble
 */
public class ClientScriptModule extends ConfigScriptModule{
    private final ConfigScripts rpc;
    // private final Logger logger = LoggerFactory.getLogger(getClass());

    public ClientScriptModule() {
        rpc = ModuleRPCFactory.create(
            Constants.MODULE_ID,
            ConfigScripts.class
        );
    }

    @Override
    public PyDictionary getConfigImpl(String configPath, String scope) throws ProjectInvalidException, JSONException {
        return rpc.getConfigImpl(configPath, scope);
    }

}
