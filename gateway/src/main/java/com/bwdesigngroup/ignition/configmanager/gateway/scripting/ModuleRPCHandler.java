/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.gateway.scripting;

import org.json.JSONException;
import org.python.core.PyDictionary;

import com.inductiveautomation.ignition.common.project.ProjectInvalidException;

/**
 *
 * @author Keith Gamble
 */
public class ModuleRPCHandler {
    private String projectName;
    private GatewayScriptModule gatewayScriptModule;
    public ModuleRPCHandler(String projectName) {
		this.projectName = projectName;
        this.gatewayScriptModule = new GatewayScriptModule(this.projectName);
    }

    public PyDictionary getConfigImpl(String configPath, String scope) throws ProjectInvalidException, JSONException {
        return this.gatewayScriptModule.getConfigImpl(configPath, scope);
    }
}
