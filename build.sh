#!/bin/bash

SRCDIR=./src
PLUGIN_YML="plugin.yml"
JARFILE=Claim
REPOSITORY=https://github.com/derbejijing/claim-plugin
BUILD_TOOLS_DOWNLOAD="https://hub.spigotmc.org/jenkins/job/BuildTools/lastSuccessfulBuild/artifact/target/BuildTools.jar"
MAIN=io.github.derbejijing.claim.Main

CLASSPATH_JAR="spigot-api.jar"
MAINPATH=$(echo $MAIN | tr "." "/")
MAINPATH="${MAINPATH}.class"

COLOR_RED='\033[0;31m'
COLOR_GREEN='\033[0;32m'
COLOR_YELLOW='\033[1;33m'
COLOR_LIGHT_BLUE='\033[1;34m'
COLOR_RESET='\033[0m'

if [[ -n $1 ]]; then
	if [[ $1 = "--setup-env" ]]; then
		
		if ! [[ -d "build-api-dir" ]]; then
			mkdir "build-api-dir"
			cd "build-api-dir"
		else
			cd "build-api-dir"
		fi

		if ! [[ -f "BuildTools.jar" ]]; then
			echo -e "${COLOR_GREEN}Starting download of BuildTools.jar...${COLOR_RESET}"
			curl -s $BUILD_TOOLS_DOWNLOAD -O
			echo -e "${COLOR_GREEN}Done!${COLOR_RESET}"
		fi
		
		echo -e "${COLOR_GREEN}Running build tools... this might take some time${COLOR_RESET}"
		java -jar "BuildTools.jar"
		
		if ! [[ $? -eq 0 ]]; then exit; fi

		files=$(find Spigot/Spigot-API/target -name "*-SNAPSHOT-shaded.jar")
		cp ${files[0]} ../${CLASSPATH_JAR}

		cd ..
		echo -e "${COLOR_GREEN}API setup done!${COLOR_RESET}"
	fi
fi 

if ! [[ -f "$CLASSPATH_JAR" ]]; then
	echo -e "${COLOR_RED}The spigot api could not be found!"
	echo -e "${COLOR_RED}Run with ${COLOR_RESET}--setup-env${COLOR_RED} or"
	echo -e "${COLOR_RED}Follow the instructions on${COLOR_RESET} https://www.spigotmc.org/wiki/buildtools/ ${COLOR_RED}, to generate the api jar!"
	echo -e "${COLOR_RED}Save the shaded api jar as ${COLOR_RESET}${CLASSPATH_JAR}"
	exit
fi

cd $SRCDIR

find . -name "*.java" > sources.txt

echo -e "${COLOR_YELLOW}Compiling classes..."
javac -cp ../${CLASSPATH_JAR} @sources.txt
echo -e "${COLOR_GREEN}Done compiling!${COLOR_RESET}"


echo -e "${COLOR_YELLOW}\nBuilding jar file:"
jar -cfe ${JARFILE}_tmp.jar $MAIN $MAINPATH

echo -e "Classes... ["
find . -name "*.class" > sources.txt
while read line; do
	echo -e ${COLOR_LIGHT_BLUE}$line
	jar -uf ${JARFILE}_tmp.jar $line
done < sources.txt
echo -e "${COLOR_YELLOW}] ...Classes${COLOR_RESET}"


echo -e "${COLOR_YELLOW}\nPlugin YML... ["
echo -e ${COLOR_LIGHT_BLUE}${PLUGIN_YML}
jar -uf ${JARFILE}_tmp.jar $PLUGIN_YML
echo -e "${COLOR_YELLOW}] ...Plugin YML"
echo -e "${COLOR_GREEN}\nDone packing!"


echo -e "${COLOR_YELLOW}\nCleaning..."
rm sources.txt
rm -rf *.class
find . -name '*.class' -delete
chmod +x ${JARFILE}_tmp.jar
mv ${JARFILE}_tmp.jar ../${JARFILE}.jar
echo -e "${COLOR_GREEN}Done cleaning"

echo -e "\nSuccessfully built jar file \"${JARFILE}.jar\"!${COLOR_RESET}"
