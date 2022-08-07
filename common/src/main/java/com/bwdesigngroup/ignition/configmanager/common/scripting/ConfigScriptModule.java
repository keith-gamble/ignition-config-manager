/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.common.scripting;


import java.util.Optional;

import org.json.JSONException;
import org.python.core.PyDictionary;
import org.python.core.PyObject;

import com.inductiveautomation.ignition.common.BundleUtil;
import com.inductiveautomation.ignition.common.project.ProjectInvalidException;
import com.inductiveautomation.ignition.common.script.PyArgParser;
import com.inductiveautomation.ignition.common.script.builtin.KeywordArgs;
import com.inductiveautomation.ignition.common.script.hints.NoHint;
import com.inductiveautomation.ignition.common.script.hints.ScriptFunction;
/**
 *
 * @author Keith Gamble
 */
public abstract class ConfigScriptModule implements ConfigScripts{

    

    static {
        BundleUtil.get().addBundle(
            "ConfigScripts",
            ConfigScriptModule.class.getClassLoader(),
            "ConfigScripts"
        );
    }

    @ScriptFunction(docBundlePrefix = "ConfigScripts")
    @KeywordArgs(names={"configPath", "scope"}, types={String.class, String.class})
    public PyDictionary getConfig(PyObject[] pyArgs, String[] keywords) throws ProjectInvalidException, JSONException {
        PyArgParser args = PyArgParser.parseArgs(pyArgs, keywords, this.getClass(), "getConfig");

		String configPath = args.requireString("configPath");
        
        // Get the scope from the keywords or the args, or set project to be the default
        Optional<String> scope = args.getString("scope");

        return getConfigImpl(configPath, scope.orElse("gateway"));
    }

    @NoHint
    public abstract PyDictionary getConfigImpl(String configPath, String scope) throws ProjectInvalidException, JSONException;

}