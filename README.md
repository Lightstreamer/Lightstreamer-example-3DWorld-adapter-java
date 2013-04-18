
3D World Demo Adapter
=====================


A demo showing the potentials of the integration of Lightstreamer in any multiplayer 3D world simulation. 
This project include the full Adapter Set for Lightstreamer written in Java. Particular attention is paid to aspects of real-time communication and opportunities to minimize the network bandwidth utilization. Consider that the Demo allows two modality:
- the physics calculations are performed both client side and server side, the rendering is based on client side physics calculations that is resync-ed with the server every N seconds. On the other hand the Matrix panel is only fed with server-side data.
- the physics engine runs completely on the server side and the positional and rotational data for all the players in the game are transmitted to all the clients with a frequency of up to 100Hz.

Java Data Adapter and MetaData Adapter
--------------------------------------

A Java Adapter implementing both SmartDataProvider interface and MetadataProviderAdapter interface to inject data to lightstreamer server with real time information about the movement of every player in the world.
Both traslations and rotations in 3d space are calculated by the adapter and reatrasmitted to the clients in a 7-values array: Position for axis X, Position for axis Y, Position for axis Z, and a Quaternion object for rotation information <x, y, z, Quat(X, Y, Z, W)>.
Precision and format of data in output are configurable by each player among the choice: 
- binary base 64 encoded float numbers single precision;
- binary base 64 encoded float numbers double precision;
- string with fixed decimal places length (ranging from 1 to 18).

The adapter receive input commands from Lightstreamer server that forwards messages arrived from clients to the adapter in relation to:
- change nick name for the player;
- change last message for the player;
- change world scenario for the player;
- movement commands.

Build the Adapter Set
---------------------

Before you can build the Adapter Set some dependencies need to be solved:

-  Get the ls-adapter-interface.jar, ls-generic-adapters.jar, and log4j-1.2.15.jar files from the [Lightstreamer 5 Colosseo distribution](http://www.lightstreamer.com/download).
-  Download [croftsoft](http://sourceforge.net/projects/croftsoft/files/) library and compile a croftsoft-math.jar version. Please be sure to include: applet, io, inlp, lang and math packages.

Build the java source files (in the src folder) into a LS_3DWorldDemo_Adapters.jar file. Here an axample for that:
```sh
  >javac -source 1.7 -target 1.7 -nowarn -g -classpath compile_libs\croftsoft\croftsoft-math.jar;compile_libs\ls-adapter-interface\ls-adapter-interface.jar;compile_libs\ls-generic-adapters\ls-generic-adapters.jar;compile_libs\log4j-1.2.15.jar -sourcepath src -d tmp_classes src\com\lightstreamer\adapters\DemoQuat3d\Move3dAdapter.java
  
  >jar cvf LS_3DWorldDemo_Adapters.jar -C tmp_classes com
```

Prepare Lightstreamer
---------------------

1.    Download and install Lightstreamer.
2.    Go to the "adapters" folder of your Lightstreamer Server installation. Create a new folder and call it "3DWorldDemo". Create a "lib" folder inside it.
3.    Copy the "ls-adapter-interface.jar" file from "Lightstreamer/lib" in the newly created "lib" folder.
4.    Copy the "croftsoft-math.jar" file from "Lightstreamer/lib" in the newly created "lib" folder.
5.    Copy the jar of the adapter compiled in the previous section in the newly created "lib" folder.
6.    Copy the "adapters.xml" file from the Deployment_LS folder of this project inside the "3DWorldDemo" folder.
7.    [Optional] Supply a specific "LS_3DWorldDemo_Logger" and "LS_demos_Logger" category in logback configuration <"Lightstreamer/conf/lightstreamer_log_conf.xml>.
8.    Launch Lightstreamer.


Lightstreamer Compatibility Notes
---------------------------------

- Compatible with Lightstreamer Server 5.1 or newer.