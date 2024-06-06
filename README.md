# Karta
Karta, meaning map.  

This is a small clipboard scanner meant to ease taking coordinates in the MMO Pantheon.

## User Guide
* Start the program by running "Run.bat".
  * A Windows CMD window will open, leave it or minimize it for as long as you want to run the program to record locations.
  * Click anywhere in the CMD window (put it in focus), then press **CTL + C** to stop it.

### Requirements
* Java
  * To check if you already have it, start CMD / Command Prompt (Windows flag, type "cmd", press Enter)
    * Type "java -version", if you have it, its version will be displayed instead of an error message saying it doesn't know what "java" is.
  * Can be downloaded from: https://www.java.com/download/ie_manual.jsp

## Goals
* Be a simple application for assisting players mapping the game world of Pantheon.
  * Write locations stored in clipboard to a ".csv" file to limit the manual steps needed.
* Be usable by a somewhat technical user and not just programmers.

## Developer Guide
### Requirements
* Java Development Kit 8 (JDK 8)

### Branches
* Create a new branch from develop or directly in develop when working on a new version.
* When making a patch, make a branch directly from main.

### Build the project
* Run "build.sh" to build the project, will create a target folder.
* Make sure that "VERSION" in "build.sh" matches project version.
* Use "-SNAPSHOT" at the end of a version that is not released. e.g. "1.1.0-SNAPSHOT"

### Release
* "git pull" first to make sure no merge conflicts.
  ```sh
  git pull
  ```
* Have all features tested in develop
* Merge in develop into main
  ```sh
  git checkout main
  git merge develop
  ```
* Remove "-SNAPSHOT" from "build.sh" script.
* Build the project and verify that it still works.
* Commit as release.
* Tag the release and push it
  ```sh
  git tag -a 1.1.0 -m "release 1.1.0"
  git push origin 1.1.0
  ```
* Zip the built code in target, example "Karta_1.0.0.zip"
  * Create a release on Github and upload the zip.
* Merge changes to main back to develop
  ```sh
  git checkout develop
  git merge main
  git push
  ```
* Dump version in "build.sh" and add back "-SNAPSHOT".
  * Commit changes and push.

### Patching
1. Create a branch from main.
  ```sh
  git checkout main
  git pull
  git checkout -b new-branch-name
  ```

2. Bump the patch number in version, "build.sh".
3. Fix the issue and test it.
4. Tag the new version and create a new release on Github.

### Versioning
* Major: Updated for major or breaking changes. Version example: 2.X.X
* Minor: Updated for normal features or changes. Version example: X.1.X
* Patch: Fix for an issue with a release. Version example: X.X.1

### Intellij
If using Intellij, make sure that;
* The right SDK (JDK) is selected in Project Structure
* That Language Level is set to 8 in Project Structure
  * Missing this can result in building for latest language level when running the build.sh directly from Intellij.

