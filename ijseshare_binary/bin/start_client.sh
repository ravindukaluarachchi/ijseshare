#!/bin/bash
java -jar IJSEShareClient.jar
if [ $? -ne 0 ]
then
zenity --info --text="Please install java 7 to run this program " --title="error"
fi
