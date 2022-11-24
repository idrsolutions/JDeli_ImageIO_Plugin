# JDeli ImageIO Plugin

---

This is a plugin to extend ImageIO with JDeli. You will require JDeli to use this plugin, if you don't already have JDeli, you can download the trial [here](https://www.idrsolutions.com/jdeli/).


# Installation

---

This can be done by copying the repository, or the build jar to your class path. 

We also have a [pre-made combined JDeli and ImageIO jar](https://www.idrsolutions.com/jdeli/trial-download) which contains the latest of both.

**Using Maven**

First setup JDeli as guided on our [support page](https://support.idrsolutions.com/jdeli/tutorials/add-jdeli-as-a-maven-dependency)
Once JDeli has been set up You can build JDeli_imageIO_Plugin and the jar will include JDeli.
You can then add this plugin into your pom as a dependency. 

    <dependancies>
        <dependency>
            <groupId>com.idrsolutions</groupId>
            <artifactId>JDeli_ImageIO_Plugin</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>


# Registering and unregistering formats for reading and writing

---

By default the plugin now comes with HEIC registered and all other formats JDeli supports are unregistered.

To register all formats:
```java
ImageIOSupport.registerReaders(InputFormat.values());

ImageIOSupport.registerWriters(OutputFormat.values());
```

Register a single format:
```java
ImageIOSupport.registerReader(InputFormat.BMP);

ImageIOSupport.registerWriter(OutputFormat.BMP);
```

To unregister all formats:
```java
ImageIOSupport.unregisterReaders(InputFormat.values());

ImageIOSupport.unregisterWriters(OutputFormat.values());
```

Unregister a single format:
```java
ImageIOSupport.unregisterReader(InputFormat.BMP);

ImageIOSupport.unregisterWriter(OutputFormat.BMP);
```

# Who do I talk to?

---

Found a bug, or have a suggestion / improvement? Let us know through the Issues page.

Got questions? You can contact us [here](https://idrsolutions.atlassian.net/servicedesk/customer/portal/8).

---


Copyright 2021 IDRsolutions

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and limitations under the License.
