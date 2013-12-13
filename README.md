# Lightstreamer - 3D World Demo - Java Adapter #

This demo shows how well the real-time positions of objects in a multiplayer 3D world can be delivered over WebSockets and HTTP via Lightstreamer Server. Particular attention is paid to aspects of real-time communication and opportunities to minimize the network bandwidth utilization.

__To fully understand this demo, please read the article__: [Optimizing Multiplayer 3D Game Synchronization Over the Web](http://blog.lightstreamer.com/2013/10/optimizing-multiplayer-3d-game.html)

You can play online with the demo at [http://demos.lightstreamer.com/3DWorldDemo/](http://demos.lightstreamer.com/3DWorldDemo/)

There are two ways the demo can work.
- First way: the physics engine runs on the client side, which periodically synchronizes with the authoritative server. 
- Second way: the physics engine runs on the server side only and the positional and rotational data for all the players in a world are transmitted to all the clients. In other words, the clients are pure renderers.

This project includes the implementation of the Adapter Set for Lightstreamer written in Java.

## Java Data Adapter and MetaData Adapter ##

A Java Adapter implementing both the [SmartDataProvider](http://www.lightstreamer.com/docs/adapter_java_api/com/lightstreamer/interfaces/data/SmartDataProvider.html) interface and the [MetadataProviderAdapter](http://www.lightstreamer.com/docs/adapter_java_api/com/lightstreamer/interfaces/metadata/MetadataProviderAdapter.html) interface to inject data into Lightstreamer server with real time information about the movement of every object in the world.
Both translations and rotations in 3D space are calculated by the adapter and transmitted to the clients in a 7-value array: position for axis X, position for axis Y, position for axis Z, and a quaternion object for rotation information: x, y, z, Quat(X, Y, Z, W).
Precision and format of data in output are configurable by each user among these choices: 
- binary Base64 encoded float numbers in single precision;
- binary Base64 encoded float numbers in double precision;
- string with fixed decimal places (ranging from 1 to 15).

The adapter receives input commands from Lightstreamer server, which forwards messages arrived from clients to the adapter in relation to:
- changing nick name for the player;
- changing last message for the player;
- changing world scenario for the player;
- movement commands.

# Build #

Before you can build the Adapter Set, some dependencies need to be solved:

-  Get the ls-adapter-interface.jar, ls-generic-adapters.jar, and log4j-1.2.15.jar files from the [Lightstreamer distribution](http://www.lightstreamer.com/download).
-  Download [croftsoft](http://sourceforge.net/projects/croftsoft/files/) library and compile a croftsoft-math.jar version. Please make sure to include: applet, io, inlp, lang and math packages.

Build the java source files (in the src folder) into a LS_3DWorldDemo_Adapters.jar file. Here is an example for that:
```sh
  >javac -source 1.7 -target 1.7 -nowarn -g -classpath compile_libs/croftsoft/croftsoft-math.jar;compile_libs/ls-adapter-interface/ls-adapter-interface.jar;compile_libs/ls-generic-adapters/ls-generic-adapters.jar;compile_libs/log4j-1.2.15.jar -sourcepath src -d tmp_classes src/com/lightstreamer/adapters/DemoQuat3d/Move3dAdapter.java
  
  >jar cvf LS_3DWorldDemo_Adapters.jar -C tmp_classes com
```

# Deploy #

1.    Download and install Lightstreamer Vivace (make sure you use Vivace edition, otherwise you will see a limit on the event rate; Vivace comes with a free non-expiring demo license for 20 connected users).
2.    Go to the "adapters" folder of your Lightstreamer Server installation. Create a new folder and call it "3DWorldDemo". Create a "lib" folder inside it.
3.    Copy the "ls-adapter-interface.jar" file from "Lightstreamer/lib" in the newly created "lib" folder.
4.    Copy the "croftsoft-math.jar" file from "Lightstreamer/lib" in the newly created "lib" folder.
5.    Copy the jar of the adapter compiled in the previous section in the newly created "lib" folder.
6.    Copy the "adapters.xml" file from the Deployment_LS folder of this project inside the "3DWorldDemo" folder.
7.    [Optional] Supply a specific "LS_3DWorldDemo_Logger" and "LS_demos_Logger" category in logback configuration <"Lightstreamer/conf/lightstreamer_log_conf.xml>.
8.    Launch Lightstreamer.

# See Also #

## Explanatory Article ##

* [Optimizing Multiplayer 3D Game Synchronization Over the Web](http://blog.lightstreamer.com/2013/10/optimizing-multiplayer-3d-game.html)

## Clients using this Adapter ##

* [Lightstreamer - 3D World Demo - Three.js Client](https://github.com/Weswit/Lightstreamer-example-3DWorld-client-javascript)

## Related projects ##

* [Lightstreamer - Reusable Metadata Adapters - Java Adapter](https://github.com/Weswit/Lightstreamer-example-ReusableMetadata-adapter-java)

# Lightstreamer Compatibility Notes #

- Compatible with Lightstreamer SDK for Java Adapters since 5.1
