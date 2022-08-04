### Process Config Manager

##### Adding Configs
In the designer, a new workspace will be added for "Process Configs"
![WorkspaceTitle](/img/WorkspaceTitle.png)

Right clicking "Process Configs" and selecting "Add Config" will add a new config.
![AddConfig](/img/AddNewConfig.png)

Each config should be valid JSON, so that the module can properly convert it to a python array of dictionaries
![Config](/img/JsonConfig.png)

#### Scripting Functions
| Function | Description | Example | Return |
| --- | --- | --- | --- |
| `system.config.getConfig(String path)` | Returns the config located at the project path provided. | `system.config.getConfig("MyFolder/Test Config")` | `{'Hello': 'World'}` |