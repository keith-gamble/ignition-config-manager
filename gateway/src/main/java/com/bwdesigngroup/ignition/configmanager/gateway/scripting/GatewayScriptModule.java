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

import com.bwdesigngroup.ignition.configmanager.common.resources.AbstractConfigResource;
import com.bwdesigngroup.ignition.configmanager.common.resources.GatewayConfigResource;
import com.bwdesigngroup.ignition.configmanager.common.resources.ProjectConfigResource;
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
public class GatewayScriptModule extends ConfigScriptModule {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String projectName;

    public GatewayScriptModule(String projectName) {
        logger.trace("Initializing GatewayScriptModule for {}", projectName);
        this.projectName = projectName;
    }

    
    @Override
    public PyDictionary getConfigImpl(String configPath, String scope) throws ProjectInvalidException, JSONException {
        ProjectResource resource;

        if (scope.equals("project")) {
            resource = GatewayScriptModule.getProjectResource(configPath, this.projectName);
        } else if (scope.equals("gateway")) {
            resource = GatewayScriptModule.getGatewayResource(configPath, this.projectName);
        } else {
            // TODO: This does not correctly get thrown all the way back to the client
            throw new ProjectInvalidException("Invalid scope: " + scope);
        }

        // TODO: Add something that throws an exception if no resource is found
        
        return this.decodeResourceObject(resource);
    }

    // TODO: Convert this to work as gateway resources in some way
    public static ProjectResource getGatewayResource(String path, String projectName) throws ProjectInvalidException {
        GatewayContext gateway = GatewayHook.getGatewayContext();

        RuntimeProject project = gateway.getProjectManager()
            .getProject( projectName )
            .orElseThrow() // throws NoSuchElementException if project not found
            .validateOrThrow();

        ResourcePath resourcePath = new ResourcePath(GatewayConfigResource.RESOURCE_TYPE, path);

        // TODO: Throw an exception a little more clear about the resource being wrong here
        ProjectResource resource = (ProjectResource) project.getResource(resourcePath).orElseThrow(() -> new ProjectInvalidException("Resource not found: " + resourcePath));    

        return resource;
    }

    public static ProjectResource getProjectResource(String path, String projectName) throws ProjectInvalidException {
        GatewayContext gateway = GatewayHook.getGatewayContext();

        RuntimeProject project = gateway.getProjectManager()
            .getProject( projectName )
            .orElseThrow() // throws NoSuchElementException if project not found
            .validateOrThrow();

        ResourcePath resourcePath = new ResourcePath(ProjectConfigResource.RESOURCE_TYPE, path);

        // TODO: Throw an exception a little more clear about the resource being wrong here
        ProjectResource resource = (ProjectResource) project.getResource(resourcePath).orElseThrow(() -> new ProjectInvalidException("Resource not found: " + resourcePath));   

        return resource;
    }

    public PyDictionary decodeResourceObject(ProjectResource resource) throws JSONException {
        String resourceJson = AbstractConfigResource.deserializeConfig(resource);

        return (PyDictionary) SystemUtilities.jsonDecode(resourceJson);
    }

    
}
