/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.gateway.scripting;

import org.json.JSONException;
import org.python.core.PyDictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bwdesigngroup.ignition.configmanager.common.ConfigResource;
import com.bwdesigngroup.ignition.configmanager.common.scripting.ConfigScriptModule;
import com.bwdesigngroup.ignition.configmanager.gateway.GatewayHook;
import com.inductiveautomation.ignition.common.project.ProjectInvalidException;
import com.inductiveautomation.ignition.common.project.RuntimeProject;
import com.inductiveautomation.ignition.common.project.resource.ProjectResource;
import com.inductiveautomation.ignition.common.project.resource.ResourcePath;
import com.inductiveautomation.ignition.common.script.builtin.SystemUtilities;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;

/**
 *
 * @author Keith Gamble
 */
public class GatewayScriptModule extends ConfigScriptModule{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String projectName;

    public GatewayScriptModule(String projectName) {
        logger.trace("Initializing GatewayScriptModule for {}", projectName);
        this.projectName = projectName;
    }


    @Override
    protected PyDictionary getConfigImpl(String configPath) throws ProjectInvalidException, JSONException {
        

        return this.getProjectResourceObject(this.getProjectResource(configPath));
    }

    public ProjectResource getProjectResource(String path) throws ProjectInvalidException {
        GatewayContext gateway = GatewayHook.getGatewayContext();

        RuntimeProject project = gateway.getProjectManager()
            .getProject( this.projectName )
            .orElseThrow() // throws NoSuchElementException if project not found
            .validateOrThrow();

        ResourcePath resourcePath = new ResourcePath(ConfigResource.RESOURCE_TYPE, path);

        ProjectResource resource = (ProjectResource) project.getResource(resourcePath).orElseThrow();    

        return resource;
    }

    public PyDictionary getProjectResourceObject(ProjectResource resource) throws JSONException {
        String resourceJson = ConfigResource.deserializeConfig(resource);

        return (PyDictionary) SystemUtilities.jsonDecode(resourceJson);
    }

    
}
