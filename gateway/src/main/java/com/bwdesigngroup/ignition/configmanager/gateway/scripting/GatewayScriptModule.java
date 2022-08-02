/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.gateway.scripting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bwdesigngroup.ignition.configmanager.common.ConfigResource;
import com.bwdesigngroup.ignition.configmanager.common.scripting.ConfigScriptModule;
import com.bwdesigngroup.ignition.configmanager.gateway.GatewayHook;
import com.inductiveautomation.ignition.common.project.ProjectInvalidException;
import com.inductiveautomation.ignition.common.project.RuntimeProject;
import com.inductiveautomation.ignition.common.project.resource.ResourcePath;
import com.inductiveautomation.ignition.common.script.ScriptContext;
import com.inductiveautomation.ignition.gateway.model.GatewayContext;

/**
 *
 * @author Keith Gamble
 */
public class GatewayScriptModule extends ConfigScriptModule{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected String getConfigImpl(String configPath) throws ProjectInvalidException {
        return this.getProjectResource(configPath);
    }

    public String getProjectResource(String path) throws ProjectInvalidException {
        GatewayContext gateway = GatewayHook.getGatewayContext();

        // String projectName = gateway.getSystemProperties().getGatewayScriptingProject();
        String projectName = ScriptContext.defaultProject();
        logger.info("Requesting resource {} from project {}", path, projectName);

        RuntimeProject project = gateway.getProjectManager()
        .getProject(projectName)
        .orElseThrow() // throws NoSuchElementException if project not found
        .validateOrThrow(); // throws ProjectInvalidException if the project is invalid
        
        ResourcePath resourcePath = new ResourcePath(ConfigResource.RESOURCE_TYPE, path);

        return project.getResource(resourcePath).toString();
    }

    
}
