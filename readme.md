# Process Config Manager

## Adding Configs
1. In the designer, a new workspace will be added for "Process Configs"
2. Right clicking "Process Configs" and selecting "Add Config" will add a new config.
3. Each config should be valid JSON, so that the module can properly convert it to a python array of dictionaries

## Scripting Functions
| Function | Description | Example | Return |
| --- | --- | --- | --- |
| `system.config.getConfig(String path)` | Returns the config located at the project path provided. | `system.config.getConfig("MyFolder/Test Config")` | `{'Hello': 'World'}` |
