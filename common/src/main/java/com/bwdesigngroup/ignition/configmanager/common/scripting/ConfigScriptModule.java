/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.common.scripting;

import org.json.JSONException;
import org.python.core.PyDictionary;
import org.python.core.PyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @SuppressWarnings("unused")
    private final Logger logger = LoggerFactory.getLogger(getClass());

    static {
        BundleUtil.get().addBundle(
            "ConfigScripts",
            ConfigScriptModule.class.getClassLoader(),
            "ConfigScripts"
        );
    }



    // @Override
    // @ScriptFunction(docBundlePrefix = "ConfigScripts")
    // public PyObject getConfig(String configPath) throws ProjectInvalidException, JSONException {
        
    //     // If configPath is still null, then lets throw an exception
    //     if (configPath == null) {
    //         throw new IllegalArgumentException("configPath is a required keyword or positional argument");
    //     }

    //     return getConfigImpl(configPath);
    // }

    @NoHint
    public PyDictionary getConfigOverRPC(String configPath) throws ProjectInvalidException, JSONException {
        return getConfigImpl(configPath);
    }


    @Override
    @ScriptFunction(docBundlePrefix = "ConfigScripts")
    @KeywordArgs(names={"configPath"}, types={String.class})
    public PyDictionary getConfig(PyObject[] pyArgs, String[] keywords) throws ProjectInvalidException, JSONException {
        PyArgParser args = PyArgParser.parseArgs(pyArgs, keywords, new String[] {"configPath"}, new Class[] {String.class}, "getConfig");

		String configPath = args.requireString("configPath");

        return getConfigImpl(configPath);
    }

    protected abstract PyDictionary getConfigImpl(String configPath) throws ProjectInvalidException, JSONException;



}