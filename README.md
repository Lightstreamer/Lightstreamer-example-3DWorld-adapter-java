# Lightstreamer - 3D World Demo - Java Adapter
<!-- START DESCRIPTION lightstreamer-example-3dworld-adapter-java -->

This demo shows how well the real-time positions of objects in a multiplayer 3D world can be delivered over WebSockets and HTTP via Lightstreamer Server. Particular attention is paid to aspects of real-time communication and opportunities to minimize the network bandwidth utilization.

## Details
As example of a client using this adapter, you may refer to the [3D World Demo - HTML (Three.js) Client](https://github.com/Weswit/Lightstreamer-example-3DWorld-client-javascript) and view the corresponding [Live Demo](http://demos.lightstreamer.com/3DWorldDemo/)

__To fully understand this demo, please read the article__: [Optimizing Multiplayer 3D Game Synchronization Over the Web](http://blog.lightstreamer.com/2013/10/optimizing-multiplayer-3d-game.html)

There are two ways the demo can work.
- First way: the physics engine runs on the client side, which periodically synchronizes with the authoritative server. 
- Second way: the physics engine runs on the server side only and the positional and rotational data for all the players in a world are transmitted to all the clients. In other words, the clients are pure renderers.

This project includes the implementation of the Adapter Set for Lightstreamer written in Java.

### Java Data Adapter and MetaData Adapter

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

<!-- END DESCRIPTION lightstreamer-example-3dworld-adapter-java -->

## Install

* Download and install Lightstreamer Vivace (make sure you use Vivace edition, otherwise you will see a limit on the event rate; Vivace comes with a free non-expiring demo license for 20 connected users).
* Get the `deploy.zip` file of the [latest release](https://github.com/Weswit/Lightstreamer-example-3DWorld-adapter-java/releases) and unzip it.
* Copy the just unzipped `3DWorldDemo` folder into the `adapters` folder of your Lightstreamer Server installation.
* Download [croftsoft](http://sourceforge.net/projects/croftsoft/files/) library and compile a `croftsoft-math.jar` version. Please make sure to include: applet, io, inlp, lang and math packages.
* Copy the just compiled `croftsoft-math.jar` file in the `3DWorldDemo/lib` folder.
* [Optional] Supply a specific "LS_3DWorldDemo_Logger" and "LS_demos_Logger" category in logback configuration `Lightstreamer/conf/lightstreamer_log_conf.xml`.
* Launch Lightstreamer Server.
* Launch a client like the [3D World Demo - HTML (Three.js) Client](https://github.com/Weswit/Lightstreamer-example-3DWorld-client-javascript) 

## Build
To build your own version of LS_3DWorldDemo_Adapters.jar, instead of using the one provided in the deploy.zip file, follow these steps:

* Clone this project.
* Get the `ls-adapter-interface.jar`, `ls-generic-adapters.jar`, and `log4j-1.2.15.jar` files from the [Lightstreamer distribution](http://www.lightstreamer.com/download) and copy them into the `lib` folder.
* Download [croftsoft](http://sourceforge.net/projects/croftsoft/files/) library and compile a `croftsoft-math.jar` version. Please make sure to include: applet, io, inlp, lang and math packages.
* Put the just compiled `croftsoft-math.jar` file in the `lib` folder.
* Build the java source files in the `src` folder into a `LS_3DWorldDemo_Adapters.jar` file. Here is an example for that:
```sh
  >javac -source 1.7 -target 1.7 -nowarn -g -classpath lib/croftsoft-math.jar;lib/ls-adapter-interface.jar;lib/ls-generic-adapters.jar;lib/log4j-1.2.15.jar -sourcepath src -d tmp_classes src/com/lightstreamer/adapters/DemoQuat3d/Move3dAdapter.java
  >jar cvf LS_3DWorldDemo_Adapters.jar -C tmp_classes com
```
* Copy the just compiled `LS_3DWorldDemo_Adapters.jar` in the `adapters/3DWorldDemo/lib` folder of your Lightstreamer Server installation.

## See Also

* [Optimizing Multiplayer 3D Game Synchronization Over the Web](http://blog.lightstreamer.com/2013/10/optimizing-multiplayer-3d-game.html)

### Clients Using This Adapter
<!-- START RELATED_ENTRIES -->

* [Lightstreamer - 3D World Demo - Three.js Client](https://github.com/Weswit/Lightstreamer-example-3DWorld-client-javascript)

<!-- END RELATED_ENTRIES -->

### Related Projects

* [Lightstreamer - Reusable Metadata Adapters - Java Adapter](https://github.com/Weswit/Lightstreamer-example-ReusableMetadata-adapter-java)

## Lightstreamer Compatibility Notes

- Compatible with Lightstreamer SDK for Java Adapters since 5.1
