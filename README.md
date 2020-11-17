# Lightstreamer - 3D World Demo - Java Adapter
<!-- START DESCRIPTION lightstreamer-example-3dworld-adapter-java -->

The *3D World Demo* shows how well the real-time positions of objects in a multiplayer 3D world can be delivered over WebSockets and HTTP via Lightstreamer Server. Particular attention is paid to aspects of real-time communication and opportunities to minimize the network bandwidth utilization.

This project shows the Data Adapter and Metadata Adapters for the *3D World Demo* and how they can be plugged into Lightstreamer Server.

As an example of a client using this adapter, you may refer to the [3D World Demo - HTML (Three.js) Client](https://github.com/Lightstreamer/Lightstreamer-example-3DWorld-client-javascript) and view the corresponding [Live Demo](http://demos.lightstreamer.com/3DWorldDemo/)

## Details

__To fully understand this demo, please read the article__: [Optimizing Multiplayer 3D Game Synchronization Over the Web](http://blog.lightstreamer.com/2013/10/optimizing-multiplayer-3d-game.html)

There are two ways the demo can work.
- First way: the physics engine runs on the client side, which periodically synchronizes with the authoritative server. 
- Second way: the physics engine runs on the server side only and the positional and rotational data for all the players in a world are transmitted to all the clients. In other words, the clients are pure renderers.

### Java Data Adapter and MetaData Adapter

This project includes the implementation of both the [SmartDataProvider](https://lightstreamer.com/api/ls-adapter-inprocess/latest/com/lightstreamer/interfaces/data/SmartDataProvider.html) interface and the [MetadataProviderAdapter](https://lightstreamer.com/api/ls-adapter-inprocess/latest/com/lightstreamer/interfaces/metadata/MetadataProviderAdapter.html) interface to inject data into Lightstreamer server with real-time information about the movement of every object in the world.
Both translations and rotations in 3D space are calculated by the adapter and transmitted to the clients in a 7-value array: position for axis X, position for axis Y, position for axis Z, and a quaternion object for rotation information: x, y, z, Quat(X, Y, Z, W).
Precision and format of data in output are configurable by each user among these choices: 
- binary Base64 encoded float numbers in single precision;
- binary Base64 encoded float numbers in double precision;
- string with fixed decimal places (ranging from 1 to 15).

The adapter receives input commands from Lightstreamer server, which forwards messages arrived from clients to the adapter in relation to:
- Changing nickname for the player;
- Changing last message for the player;
- Changing world scenario for the player;
- Movement commands.

<!-- END DESCRIPTION lightstreamer-example-3dworld-adapter-java -->


#### The Adapter Set Configuration
This Adapter Set Name is configured and will be referenced by the clients as `DEMOMOVE3D`.

The `adapters.xml` file for this demo should look like:
```xml   
<?xml version="1.0"?>
<adapters_conf id="DEMOMOVE3D">

    <metadata_adapter_initialised_first>Y</metadata_adapter_initialised_first>

    <metadata_provider>
        <adapter_class>com.lightstreamer.adapters.DemoQuat3d.Move3dMetaAdapter</adapter_class>

        <!--
          TCP port on which Sun/Oracle's JMXMP connector will be
          listening.
        -->
        <param name="jmxPort">9999</param>
        
        <!--
          Max number of players in server-side modality.
        -->
        <param name="Max_Srv_Players">10</param>
		  
    </metadata_provider>
    
    <data_provider>
        <adapter_class>com.lightstreamer.adapters.DemoQuat3d.Move3dAdapter</adapter_class>
  
        <!--
          Frame rate for physics calculations. In milliseconds.
        -->
        <param name="frameRate">10</param>
        
        <!--
          Default option for number of decimals.
        -->
        <param name="precision">8</param>
        
        <!--
          Max interval of time without send any commands after which
          the player is forcibly disconnected.
        -->
        <param name="Max_Inactivity">60000</param>
        
        <!--
          Number of Ghost players in the <Default> world. Ghosts are automatically
          killed when the number of real players is approaching the overcrowded 
          value and regenerate when this number decreases.
        -->
        <param name="Ghost_Players">7</param>
        
        <!--
          Max number of total players in the all the worlds.
        -->
        <param name="Max_Players">100</param>
        
        <!--
          Max number of players in the same world.
        -->
        <param name="overcrowded">10</param>
    </data_provider>
</adapters_conf>
```

<i>NOTE: not all configuration options of an Adapter Set are exposed by the file suggested above. 
You can easily expand your configurations using the generic template, see the [Java In-Process Adapter Interface Project](https://github.com/Lightstreamer/Lightstreamer-lib-adapter-java-inprocess#configuration) for details.</i><br>
<br>
Please refer [here](https://lightstreamer.com/docs/ls-server/latest/General%20Concepts.pdf) for more details about Lightstreamer Adapters.

## Install

If you want to install a version of this demo in your local Lightstreamer server, follow these steps:
* Download *Lightstreamer Server* (Lightstreamer Server comes with a free non-expiring demo license for 20 connected users; this should be preferred to using COMMUNITY edition, otherwise you would see a limit on the event rate) from [Lightstreamer Download page](https://lightstreamer.com/download/), and install it, as explained in the `GETTING_STARTED.TXT` file in the installation home directory.
* Get the `deploy.zip` file of the [latest release](https://github.com/Lightstreamer/Lightstreamer-example-3DWorld-adapter-java/releases) and unzip it.
* Copy the just unzipped `3DWorldDemo` folder into the `adapters` folder of your Lightstreamer Server installation.
* [Optional] Customize the specific "LS_3DWorldDemo_Logger" and "LS_demos_Logger" categories in log4j configuration file `3DWorldDemo/classes/log4j2.xml`.
* Launch Lightstreamer Server.
* Launch a client like the [3D World Demo - HTML (Three.js) Client](https://github.com/Lightstreamer/Lightstreamer-example-3DWorld-client-javascript) 

## Build

To build your own version of `example-3DWorld-adapter-java-0.0.1-SNAPSHOT.jar` instead of using the one provided in the `deploy.zip` file from the [Install](https://github.com/Lightstreamer/Lightstreamer-example-3DWorld-adapter-java#install) section above, you have two options:
either use [Maven](https://maven.apache.org/) (or other build tools) to take care of dependencies and building (recommended) or gather the necessary jars yourself and build it manually.
For the sake of simplicity only the Maven case is detailed here.

### Maven

You can easily build and run this application using Maven through the pom.xml file located in the root folder of this project. As an alternative, you can use an alternative build tool (e.g. Gradle, Ivy, etc.) by converting the provided pom.xml file.

Assuming Maven is installed and available in your path you can build the demo by running
```sh 
 mvn install dependency:copy-dependencies 
```

## See Also

* [Optimizing Multiplayer 3D Game Synchronization Over the Web](http://blog.lightstreamer.com/2013/10/optimizing-multiplayer-3d-game.html)

### Clients Using This Adapter
<!-- START RELATED_ENTRIES -->

* [Lightstreamer - 3D World Demo - Three.js Client](https://github.com/Lightstreamer/Lightstreamer-example-3DWorld-client-javascript)

<!-- END RELATED_ENTRIES -->

### Related Projects

* [Lightstreamer - Reusable Metadata Adapters - Java Adapter](https://github.com/Lightstreamer/Lightstreamer-example-ReusableMetadata-adapter-java)

## Lightstreamer Compatibility Notes

- Compatible with Lightstreamer SDK for Java In-Process Adapters since 7.3.
- For a version of this example compatible with Lightstreamer SDK for Java Adapters version 6.0, please refer to [this tag](https://github.com/Lightstreamer/Lightstreamer-example-3DWorld-adapter-java/tree/pre_mvn).
- For a version of this example compatible with Lightstreamer SDK for Java Adapters version 5.1, please refer to [this tag](https://github.com/Lightstreamer/Lightstreamer-example-3DWorld-adapter-java/tree/for_Lightstreamer_5.1).
