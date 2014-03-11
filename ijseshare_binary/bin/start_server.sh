#!/bin/bash
ip="$(zenity --entry --text="enter the ip of this pc" --title="start server")"
java -Djava.rmi.server.hostname=$ip -jar IJSEShareServer.jar $ip

if [ $? -ne 0 ]
then
zenity --info --text="Please install java 7 to run this program " --title="error"
fi
