/*
 * Copyright 2021 Keith Gamble
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package com.bwdesigngroup.ignition.configmanager.common.resources;

import java.nio.charset.Charset;

import com.bwdesigngroup.ignition.configmanager.common.Constants;
import com.inductiveautomation.ignition.common.project.resource.ProjectResource;
/**
 *
 * @author Keith Gamble
 */
public abstract class AbstractConfigResource {
    public static final String DATA_KEY = Constants.FILE_NAME;
    public String json = "{}";

    public AbstractConfigResource(String JSON) {
        this.json = JSON;
    }

    public AbstractConfigResource(ProjectResource resource) {
        this.json = deserializeConfig(resource);
    }

    public static String deserializeConfig(ProjectResource resource) {
        byte[] data = resource.getData(AbstractConfigResource.DATA_KEY);

        if (data != null) {
            return new String(data, Charset.forName("UTF-8"));
        } else {
            return "{}";
        }
    }
}
