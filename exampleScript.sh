#!/bin/bash

# run ExampleScript.kts, using custom library kotlin-script-resources.jar, with the command line arguments passed to this shell script
kotlinc -classpath kotlin-script-resources.jar -script ExampleScript.kts -- "$@"
